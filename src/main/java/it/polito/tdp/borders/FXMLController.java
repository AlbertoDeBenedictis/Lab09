/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader

	@FXML // fx:id="comboStati"
	private ComboBox<Country> comboStati; // Value injected by FXMLLoader

	@FXML // fx:id="btnVicini"
	private Button btnVicini; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML
	void doCalcolaConfini(ActionEvent event) {
		

		comboStati.getItems().clear();
		
//		Pulisco la schermata
		txtResult.clear();

		String annoString = txtAnno.getText();
		int anno;
//    	Controllo sull'input
		try {
			anno = Integer.parseInt(annoString);
		} catch (NumberFormatException nfe) {
			txtResult.setText("ERRORE! Non hai inserito un numero");
			return;
		}
		
//    	Ora sappiamo che l'anno è valido
		Graph<Country, DefaultEdge> grafo = model.creaGrafo(anno);

//    	Dopo aver creato il grafo, prendiamo l'elenco dei vertici
		Collection<Country> elencoStati = model.getVertexSet();

//		Prendo il numero di componenti connesse
		int compConn = model.getComponentiConnesse(grafo);

//    	Stampo un messaggio e la lista di ogni stato, incluso il suo grado
		txtResult.setText("GRAFO CREATO! Numero di componenti connesse: " + compConn + "\nElenco stati:\n");
		for (Country stato : elencoStati) {

			txtResult.appendText(stato.getNome() + " (" + stato.getSigla() + ") - Stati confinanti: "
					+ grafo.degreeOf(stato) + "\n");
		}
		
//		Bisogna caricare la lista di stati nella comboBox in qualche modo
		comboStati.getItems().addAll(elencoStati);
		
//		cmbNerc.getItems().addAll(nercList);
	}

	@FXML
	void doVicini(ActionEvent event) {
		
		
		
//		Intanto prendiamoci la variabile della choiceBox che non abbiamo ancora caricato 
		Country statoSelected = comboStati.getValue();
		
//		Chiamiamo la funzione del model che cerca i vicini
		List<Country> listaVicini = model.trovaVicini(statoSelected);
		listaVicini.remove(statoSelected);
//		Puliamo la schermata
		txtResult.clear();
		
//		Stampiamo al suo posto l'elenco dei vicini 
		txtResult.setText("Stati vicini a "+statoSelected+":\n\n");
		for(Country c:listaVicini) {
			txtResult.appendText(c+"\n");
		}
		

	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
		assert comboStati != null : "fx:id=\"comboStati\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnVicini != null : "fx:id=\"btnVicini\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
	}

}
