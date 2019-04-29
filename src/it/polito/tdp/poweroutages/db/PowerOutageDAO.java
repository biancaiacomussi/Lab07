package it.polito.tdp.poweroutages.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class PowerOutageDAO {

	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	public List<PowerOutage> getOutagesByNerc(Nerc nerc){
		
		String sql = "SELECT p.id, p.nerc_id, n.VALUE, customers_affected, date_event_began, date_event_finished FROM poweroutages AS p, nerc AS n WHERE p.nerc_id = n.id AND nerc_id = ? ORDER BY date_event_began";
		List<PowerOutage> powerOutages = new ArrayList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, nerc.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				PowerOutage n = new PowerOutage(res.getInt("p.id"), nerc, 
				res.getLong("customers_affected"), 
				res.getTimestamp("date_event_began").toLocalDateTime(),
				res.getTimestamp("date_event_finished").toLocalDateTime());
				powerOutages.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return powerOutages;
	}

}
