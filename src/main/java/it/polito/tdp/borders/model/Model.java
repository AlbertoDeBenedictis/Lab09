package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	BordersDAO dao;
	Map<Integer, Country> countryMap;
	Graph<Country, DefaultEdge> grafo;

	public Model() {
		dao = new BordersDAO();

	}

	/**
	 * Dato uno stato di partenza, effettua una visita nella componente connessa, ritornando
	 * la lista di stati "vicini"
	 * @param stato
	 * @return
	 */
	public List<Country> trovaVicini(Country stato){
		
//		Inseriamo il grafo da visitare, incluso il vertice da cui dobbiamo partire 
		BreadthFirstIterator<Country,DefaultEdge> bfv = new BreadthFirstIterator<>(this.grafo,stato);
	
//		Inizializziamo la lista da ritornare
		List<Country> vicini = new ArrayList<>();
		
//		Iniziamo a visitare il grafo, registriamo in una lista di stati tutti i vertici che attraversa
		while(bfv.hasNext()) {
			vicini.add(bfv.next());
		}
		
		return vicini;
	}
	
	
	/**
	 * Restituisce l'insieme dei vertici del grafo
	 * 
	 * @return
	 */
	public Set<Country> getVertexSet() {
		return this.grafo.vertexSet();
	}

	public int getComponentiConnesse(Graph<Country, DefaultEdge> grafo) {

//		Creo il connectivity inspector
		ConnectivityInspector<Country, DefaultEdge> inspector = new ConnectivityInspector<>(grafo);

//		Ritorno la numerosità della lista delle componenti connesse	
		return inspector.connectedSets().size();
	}

	/**
	 * Dato un anno per i confini, costruisce un grafo
	 * 
	 * @param anno
	 */
	public Graph<Country, DefaultEdge> creaGrafo(int anno) {

		this.grafo = new SimpleGraph<>(DefaultEdge.class);

//		Conviene creare una mappa di nazioni
		countryMap = new HashMap<>();

//		Carico la mappa di nazioni
		dao.loadAllCountries(countryMap);

//		Prendo la lista dei confini passando l'anno specificato
		List<Border> listaConfini = dao.getCountryPairs(anno, countryMap);

//		VERTICI: aggiungo i componenti di ogni confine grazie alla countryMap
		for (Border confine : listaConfini) {

			Country stato1 = confine.getC1();
			Country stato2 = confine.getC2();

//			Aggiungo gli stati come vertici 
			grafo.addVertex(stato1);
			grafo.addVertex(stato2);
			
			
//			Creo l'arco (sono sicuro di non avere archi duplicati perchè code1<code2
			this.grafo.addEdge(stato1, stato2);
		}

		System.out
				.println("Grafo creato:\n#Vertici " + grafo.vertexSet().size() + "\n#Archi " + grafo.edgeSet().size());
		return grafo;
	}

}
