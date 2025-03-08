package com.bank.account;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AccountApplicationEndToEndTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void doitCreerDepot() {
		// Given
		UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
		BigDecimal montant = new BigDecimal("100.00");

		// When
		ResponseEntity<String> response = restTemplate.postForEntity(
				"http://localhost:" + port + "/compte/depot/{id}?montant={montant}",
				null,
				String.class,
				id,
				montant
		);

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals("Montant déposé avec succès.", response.getBody());
	}

	@Test
	public void doitRetournerCompteExistePas() {
		// Given
		UUID id = UUID.randomUUID();
		BigDecimal montant = new BigDecimal("100.00");

		// When
		ResponseEntity<String> response = restTemplate.postForEntity(
				"http://localhost:" + port + "/compte/depot/{id}?montant={montant}",
				null,
				String.class,
				id,
				montant
		);

		Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		Assertions.assertEquals("Ce compte n'existe pas.", response.getBody());
	}

	@Test
	public void doitCreerRetrait() {
		// Given
		UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
		BigDecimal montant = new BigDecimal("10.00");

		// When
		ResponseEntity<String> response = restTemplate.postForEntity(
				"http://localhost:" + port + "/compte/retrait/{id}?montant={montant}",
				null,
				String.class,
				id,
				montant
		);

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals("Montant retiré avec succès.", response.getBody());
	}

	@Test
	public void doitCreerReleve() {
		// Given
		UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");

		// When
		ResponseEntity<String> response = restTemplate.postForEntity(
				"http://localhost:" + port + "/compte/releve/{id}",
				null,
				String.class,
				id
		);

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertNotNull(response.getBody());
	}

	@Test
	public void doitRetournerMontantDoitEtreSuperieur() {
		// Given
		UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
		BigDecimal montant = new BigDecimal("-1");

		// When
		ResponseEntity<String> response = restTemplate.postForEntity(
				"http://localhost:" + port + "/compte/depot/{id}?montant={montant}",
				null,
				String.class,
				id,
				montant
		);

		Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		Assertions.assertEquals("Le montant du dépôt doit être supérieur à 0.", response.getBody());
	}

	@Test
	public void doitRetournerSoldeInsuffisant() {
		// Given
		UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
		BigDecimal montant = new BigDecimal("99999");

		// When
		ResponseEntity<String> response = restTemplate.postForEntity(
				"http://localhost:" + port + "/compte/retrait/{id}?montant={montant}",
				null,
				String.class,
				id,
				montant
		);

		Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		Assertions.assertEquals("Votre solde est insuffisant.", response.getBody());
	}

}
