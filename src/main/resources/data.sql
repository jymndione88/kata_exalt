--INSERT INTO operation (id, numero_compte, date_operation, montant, motif, type_operation)
--VALUES ('550e8400-e29b-41d4-a716-446655440000', 'FR123456788', '2025-02-07', 2200.00, 'Salaire', 'DEPOT');
--
--INSERT INTO operation (id, numero_compte, date_operation, montant, motif, type_operation)
--VALUES ('550e8400-e29b-41d4-a716-446655440001', 'FR123456788', '2025-01-09', 700.00, 'loyer', 'RETRAIT');

INSERT INTO compte (dtype, id, numero_compte, solde, type_compte, montant_decouvert)
VALUES ('COURANT', '550e8400-e29b-41d4-a716-446655440000', 'FR123456788', 0.00, 'COURANT', 100.00);

INSERT INTO compte (dtype, id, numero_compte, solde, type_compte, plafond)
VALUES ('LIVRET', '550e8400-e29b-41d4-a716-446655440001', 'FR123456789', 0.00, 'LIVRET', 22950.00);