package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import it.polito.tdp.poweroutages.db.PowerOutageDAO;

public class Model {

	private PowerOutageDAO podao;
	private List<Nerc> nerc;
	private List<PowerOutage> powerOutages;
	private List<PowerOutage> best;
	private long costoBest;
	long oreBest;
	
	public Model() {
		podao = new PowerOutageDAO();
		nerc = this.getNercList();
		
		
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

	public List<PowerOutage> getOutages(Nerc nerc){
		return podao.getOutagesByNerc(nerc);
	}
	
	public String getPeopleAffected() {
		long tot=0;
		for(PowerOutage p : best) {
			tot += p.getUtentiCoinvolti();
		}
		return "Tot people affected: "+tot;
	}
	
	public String getHoursOutages() {
		return "Tot hours of outage: "+oreBest;
	}
	
	public String getResult() {
		String res="";
		for(PowerOutage p: best) {
			res+=p.toString()+"\n";
		}
		return res.trim();
	}
	
	public List<PowerOutage> trovaSoluzione(Nerc nerc, int hours, int years){
		
		
		powerOutages = podao.getOutagesByNerc(nerc);
		best = new ArrayList<>();
		costoBest = 0;
		oreBest = 0;
		
		List<PowerOutage> parziale = new ArrayList<>();
		cerca(parziale, 0, years, hours);
		
		
		return best;
	}

	private void cerca(List<PowerOutage> parziale, int livello, int years, int hours) {
		
		//Casi terminali
		
		//Se ho terminato la lista di eventi, esco
		if(livello==powerOutages.size())
			return;
		
		//se ho trovato un costo migliore
		if(calcolaCosto(parziale)>costoBest) {
			costoBest = calcolaCosto(parziale);
			best = new ArrayList<>(parziale);
			oreBest = this.sommaOre(parziale);
			
		}
		
		//se abbiamo raggiunto il numero di anni massimo non ha senso continuare
		if(!this.possoAggiungere(parziale, livello, years, hours)) {
			return;
		}
		
		if(!this.testaOre(parziale, livello, hours)) {
			cerca(parziale,livello+1,years,hours); //posso non aggiungere
		} else {
		
		
		
		//posso aggiungere
		
			parziale.add(powerOutages.get(livello));
			cerca(parziale, livello+1, years, hours);
			parziale.remove(parziale.size()-1);	
			
			cerca(parziale,livello+1,years,hours);
		
		}
	}
	
	private long calcolaCosto(List<PowerOutage> parziale) {
		long costo = 0;
		
		for(PowerOutage p : parziale) {
			costo += p.getUtentiCoinvolti();
		}
		return costo;
	}

	private boolean testaOre(List<PowerOutage> parziale, int livello, int hours) {
		
		if(this.sommaOre(parziale)+powerOutages.get(livello).getDurataEvento() > hours)
			return false;
		
		return true;
	}
	
	
	private boolean possoAggiungere(List<PowerOutage> parziale, int livello, int years, int hours) {
		
		if(parziale.size()==0) //il primo elemento devo aggiungerlo sempre
			return true;
		
		if(powerOutages.get(livello).getDataInizio().getYear() - parziale.get(0).getDataFine().getYear() >=years)
			return false;
		
		return true;
	}
	
	

	public long sommaOre(List<PowerOutage> parziale) {
		long somma = 0;
		for(PowerOutage p : parziale) {
			somma += p.getDurataEvento();
		}
		
		return somma;
	}
	
	
}
