SELECT id, number, registered FROM credit_cards WHERE account_id IN (SELECT id FROM accounts WHERE clients_id = ? AND id = ?);