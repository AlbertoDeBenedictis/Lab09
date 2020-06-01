package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	/**
	 * Carica la mappa con tutti gli stati
	 * 
	 * @return
	 */
	public void loadAllCountries(Map<Integer, Country> countryMap) {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY ccode";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				// creo un nuovo stato
				Country stato = new Country(rs.getInt("ccode"),rs.getString("StateAbb"),rs.getString("StateNme"));
//				Lo aggiungo alla mappa, utilizzano come key il codice
				countryMap.put(stato.getCodice(), stato);
				
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	/**
	 * Restituisce una lista contenente tutti i confini, intesi come coppia di codici degli stati
	 * @param anno
	 * @return
	 */
	public List<Border> getCountryPairs(int anno) {

		String sql = "select state1no,state2no from contiguity where conttype = 1 AND year<=? AND state1no<state2no";

		List<Border> listaConfini = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

//			Aggiungo il parametro, ovvero l'anno a partire da cui voglio i confini
			st.setInt(1, anno);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				// Aggiungo il confine come coppia di identificativi dello stato
				listaConfini.add(new Border(rs.getInt("state1no"), rs.getInt("state2no")));
			}

			return listaConfini;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}

	}
}
