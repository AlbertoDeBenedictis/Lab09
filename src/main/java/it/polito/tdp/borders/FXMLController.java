
package it.polito.tdp.borders;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML
	void doCalcolaConfini(ActionEvent event) {

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

	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
	}
}
