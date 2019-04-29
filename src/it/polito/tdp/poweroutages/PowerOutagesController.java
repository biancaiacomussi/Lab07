package it.polito.tdp.poweroutages;


	import java.net.URL;
	import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import javafx.event.ActionEvent;
	import javafx.fxml.FXML;
	import javafx.scene.control.Button;
	import javafx.scene.control.ComboBox;
	import javafx.scene.control.TextArea;
	import javafx.scene.control.TextField;

	public class PowerOutagesController {
		
		private Model model = new Model();

	    @FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	    @FXML
	    private ComboBox<Nerc> comboBox;

	    @FXML
	    private TextField maxYears;

	    @FXML
	    private TextField maxHours;

	    @FXML
	    private Button btnAnalysis;

	    @FXML
	    private TextArea txtResult;

	    @FXML
	    void doAnalysis(ActionEvent event) {
	    	txtResult.clear();
	    	
	    	try {
	    		Integer.parseInt(maxYears.getText());
	    	} catch(NumberFormatException e) {
	    		System.err.println("Inserisci un numero di anni massimo");
	    		txtResult.setText("Inserisci un numero di anni massimo");
	    	}
	    	
	    	try {
	    		Integer.parseInt(maxHours.getText());
	    	} catch(NumberFormatException e) {
	    		System.err.println("Inserisci un numero di ore massimo");
	    		txtResult.setText("Inserisci un numero di ore massimo");
	    	}
	    	
	    	
	    	
	    	if(comboBox.getValue()==null)
	    		txtResult.setText("Inserisci un NERC");
	    	
	    	
	    	
	    	else if (maxYears.getText().equals(""))
	    		txtResult.setText("Inserisci un numero di anni massimo");
	    	
	    	else if (maxHours.getText().equals(""))
	    		txtResult.setText("Inserisci un numero di ore massimo");
	    		
	    	else {
	    	Nerc nerc = comboBox.getValue();
	    	int hours = Integer.parseInt(maxHours.getText());
	    	int years = Integer.parseInt(maxYears.getText());
	    	model.trovaSoluzione(nerc, hours, years);
	    	txtResult.appendText(model.getPeopleAffected()+"\n");
	    	txtResult.appendText(model.getHoursOutages()+"\n");
	    	txtResult.appendText(model.getResult());
	    	}
	    }

	    @FXML
	    void initialize() {
	        assert comboBox != null : "fx:id=\"comboBox\" was not injected: check your FXML file 'PowerOutages.fxml'.";
	        assert maxYears != null : "fx:id=\"maxYears\" was not injected: check your FXML file 'PowerOutages.fxml'.";
	        assert maxHours != null : "fx:id=\"maxHours\" was not injected: check your FXML file 'PowerOutages.fxml'.";
	        assert btnAnalysis != null : "fx:id=\"btnAnalysis\" was not injected: check your FXML file 'PowerOutages.fxml'.";
	        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'PowerOutages.fxml'.";

	    }

		public void setModel(Model model) {
			this.model = model;
			comboBox.getItems().addAll(model.getNercList());
			
		}
	}

	
