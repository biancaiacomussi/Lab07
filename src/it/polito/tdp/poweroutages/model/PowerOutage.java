package it.polito.tdp.poweroutages.model;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class PowerOutage {
	
	private int id;
	private Nerc nerc;
	private long utentiCoinvolti;
	private LocalDateTime dataInizio;
	private LocalDateTime dataFine;
	
	public PowerOutage(int id, Nerc nerc, long utentiCoinvolti, LocalDateTime dataInizio, LocalDateTime dataFine) {
		super();
		this.id = id;
		this.nerc = nerc;
		this.utentiCoinvolti = utentiCoinvolti;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
	}
	
	public int getId() {
		return id;
	}
	public Nerc getNerc() {
		return nerc;
	}
	public long getUtentiCoinvolti() {
		return utentiCoinvolti;
	}
	public LocalDateTime getDataInizio() {
		return dataInizio;
	}
	public LocalDateTime getDataFine() {
		return dataFine;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerOutage other = (PowerOutage) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public long getDurataEvento() {
		
	//Timestamp ts = Timestamp.valueOf(this.dataInizio);

		return Duration.between(this.getDataInizio(), this.getDataFine()).toHours();
	}

	@Override
	public String toString() {
		return this.dataInizio.getYear()+" "+ this.dataInizio+" "+this.dataFine +" "+ this.getDurataEvento()+" "+this.getUtentiCoinvolti();
	}
	
	
	

}
