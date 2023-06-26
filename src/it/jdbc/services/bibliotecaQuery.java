package it.jdbc.services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import it.jdbc.dbhandler.bibliotecaHandler;

public class bibliotecaQuery {

	bibliotecaHandler istanza = bibliotecaHandler.getInstance();

	public void procGetAgeAutoriNazione() {

		Scanner inserimento = new Scanner(System.in);

		System.out.println("Inserisci la nazione:");
		String nazione = inserimento.nextLine();
		Map<String, Integer> autoriEta = new HashMap<>();

		try {
			Connection connessione = istanza.getConnection();
			CallableStatement stmt = connessione.prepareCall("{CALL get_age_autori_nazione(?)}");
			stmt.setString(1, nazione);

			boolean hasResults = stmt.execute();

			if (hasResults) {
				ResultSet resultSet = stmt.getResultSet();

				while (resultSet.next()) {
					String colonna1 = resultSet.getString("nome");
					String colonna2 = resultSet.getString("cognome");
					int colonna3 = resultSet.getInt("eta");

					if (colonna3 == -1) { // se l'età è -1 significa che l'autore è deceduto
						System.out.println(colonna1 + ", " + colonna2 + ", " + "AUTORE DECEDUTO");
					} else {
						System.out.println(colonna1 + ", " + colonna2 + ", " + colonna3);
						autoriEta.put(colonna1 + " " + colonna2, colonna3); // Aggiunge all'HashMap nome-cognome autore ed età
					}
				}
				resultSet.close();

			} else {
				System.out.println("Non ci sono autori");
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
