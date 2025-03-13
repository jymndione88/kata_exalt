package com.bank.account;

import com.bank.account.domain.model.TypeCompte;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AccountApplicationEndToEndTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	MessageSource messageSource;

	@Test
    @Order(1)
	public void doitCreerOperationDepot() {
		// Given
		String numeroCompte = "FR123456788";
		BigDecimal montant = new BigDecimal("100.00");

        Map<String, Object> operation = new HashMap<>();
        operation.put("typeOperation", "DEPOT");
        operation.put("montant", montant);

		// When
		String response = restTemplate.patchForObject(
				"http://localhost:" + port + "/comptes/operation/{numeroCompte}",
                operation,
				String.class,
				numeroCompte
		);

		// Then
		Assertions.assertEquals(messageSource.getMessage("message.succes.generique", new Object[]{operation.get("typeOperation"), operation.get("montant"), numeroCompte}, Locale.getDefault()), response);
	}

	@Test
	@Order(2)
	public void doitRetournerCompteExistePas() {
		// Given
		String numeroCompte = "FRxxx";
		BigDecimal montant = new BigDecimal("100.00");

		Map<String, Object> operation = new HashMap<>();
		operation.put("typeOperation", "DEPOT");
		operation.put("montant", montant);

		// When
		String response = restTemplate.patchForObject(
				"http://localhost:" + port + "/comptes/operation/{numeroCompte}",
				operation,
				String.class,
				numeroCompte
		);

		// Then
		try {
			JSONObject object = new JSONObject(response);
			Assertions.assertEquals("Le compte de numero FRxxx est introuvable.", object.get("message"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	@Order(3)
	public void doitCreerOperationRetrait() {
		// Given
		String numeroCompte = "FR123456788";
		BigDecimal montant = new BigDecimal("10.00");

		Map<String, Object> operation = new HashMap<>();
		operation.put("typeOperation", "RETRAIT");
		operation.put("montant", montant);

		// When
		String response = restTemplate.patchForObject(
				"http://localhost:" + port + "/comptes/operation/{numeroCompte}",
				operation,
				String.class,
				numeroCompte
		);

		// Then
		Assertions.assertEquals(messageSource.getMessage("message.succes.generique", new Object[]{operation.get("typeOperation"), operation.get("montant"), numeroCompte}, Locale.getDefault()), response);
	}

	@Test
	@Order(4)
	public void doitCreerReleve() {
		// Given
		String numeroCompte = "FR123456789";

		// When
		ResponseEntity<String> response = restTemplate.getForEntity(
				"http://localhost:" + port + "/comptes/releve/{numeroCompte}",
				String.class,
				numeroCompte
		);

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertNotNull(response.getBody());
	}

	@Test
	@Order(5)
	public void doitRetournerMontantDoitEtreSuperieur() {
		// Given
		String numeroCompte = "FR123456789";
		BigDecimal montant = new BigDecimal("-1");

		Map<String, Object> operation = new HashMap<>();
		operation.put("typeOperation", "DEPOT");
		operation.put("montant", montant);

		// When
		String response = restTemplate.patchForObject(
				"http://localhost:" + port + "/comptes/operation/{numeroCompte}",
				operation,
				String.class,
				numeroCompte
		);

		// Then
		try {
			JSONObject object = new JSONObject(response);
			Assertions.assertEquals(messageSource.getMessage("compte.montant.valide", null, Locale.getDefault()), object.get("message"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	@Order(6)
	public void doitRetournerSoldeInsuffisant() {
		// Given
		String numeroCompte = "FR123456789";
		BigDecimal montant = new BigDecimal("99999");

		Map<String, Object> operation = new HashMap<>();
		operation.put("typeOperation", "RETRAIT");
		operation.put("montant", montant);

		// When
		String response = restTemplate.patchForObject(
				"http://localhost:" + port + "/comptes/operation/{numeroCompte}",
				operation,
				String.class,
				numeroCompte
		);

		// Then
		try {
			JSONObject object = new JSONObject(response);
			Assertions.assertEquals(messageSource.getMessage("compte.solde.insuffisant", null, Locale.getDefault()), object.get("message"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	@Order(7)
	public void doitRetournerPlafondAtteint() {
		// Given
		String numeroCompte = "FR123456789";
		BigDecimal montant = new BigDecimal("99999");

		Map<String, Object> operation = new HashMap<>();
		operation.put("typeOperation", "DEPOT");
		operation.put("montant", montant);

		// When
		String response = restTemplate.patchForObject(
				"http://localhost:" + port + "/comptes/operation/{numeroCompte}",
				operation,
				String.class,
				numeroCompte
		);

		// Then
		try {
			JSONObject object = new JSONObject(response);
			Assertions.assertEquals(messageSource.getMessage("compte.plafond.atteint", null, Locale.getDefault()), object.get("message"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	@Order(8)
	public void doitCreerCompteCOURANT() {
		// Given
		TypeCompte typeCompte = TypeCompte.COURANT;

		// When
		String response = restTemplate.postForObject(
				"http://localhost:" + port + "/comptes/{typeCompte}",
				null,
				String.class,
				typeCompte
		);

		// Then
		Assertions.assertNotNull( response);
		try {
			JSONObject object = new JSONObject(response);
			Assertions.assertEquals(0, Integer.valueOf(object.get("solde").toString()));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	@Order(9)
	public void doitRetournerBadRequest() {
		// Given
		String numeroCompte = "FR123456789";
		BigDecimal montant = new BigDecimal("99999");

		Map<String, Object> operation = new HashMap<>();
		operation.put("typeOperation", "DEPOTxxx");
		operation.put("montant", montant);

		// When
		String response = restTemplate.patchForObject(
				"http://localhost:" + port + "/comptes/operation/{numeroCompte}",
				operation,
				String.class,
				numeroCompte
		);

		// Then
		try {
			JSONObject object = new JSONObject(response);
			Assertions.assertEquals(400, object.get("statut"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
