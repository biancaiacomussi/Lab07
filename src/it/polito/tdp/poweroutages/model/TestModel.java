package it.polito.tdp.poweroutages.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		System.out.println(model.getNercList());
		
		System.out.println(model.trovaSoluzione(new Nerc(3, "MAAC"), 200, 4));
	}
}
