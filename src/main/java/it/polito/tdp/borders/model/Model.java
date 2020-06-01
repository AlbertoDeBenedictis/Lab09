package it.polito.tdp.borders.model;

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

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	BordersDAO dao;
	Map<Integer,Country> countryMap;
	Graph<Country,DefaultEdge> grafo;

	public Model() {
		dao = new BordersDAO();
		
	}
	
	/**
	 * Restituisce l'insieme dei vertici del grafo
	 * @return
	 */
	public Set<Country> getVertexSet() {
		return this.grafo.vertexSet();
	}
	
	public int getComponentiConnesse(Graph<Country,DefaultEdge> grafo) {
		
//		Creo il connectivity inspector
		ConnectivityInspector inspector = new ConnectivityInspector(grafo);
		
//		Ritorno la numerosità della lista delle componenti connesse	
		return inspector.connectedSets().size();
	}
	

	
	
	/**
	 * Dato un anno per i confini, costruisce un grafo
	 * @param anno
	 */
	public Graph<Country,DefaultEdge> creaGrafo(int anno) {
		
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		
//		Conviene creare una mappa di nazioni
		countryMap = new HashMap<>();
		
//		Carico la mappa di nazioni
		dao.loadAllCountries(countryMap);
		
//		Aggiungo al grafo i vertici, richiamando tutti i valori della mappa degli stati
		Graphs.addAllVertices(this.grafo, countryMap.values());
		
//		Prendo la lista dei confini passando l'anno specificato
		List<Border> listaConfini = dao.getCountryPairs(anno);
		
//		Scorro la lista dei confini e per ognuno aggiungo il confine come arco 
//		(Ho impostato la disuguaglianza dei codici per evitare i doppioni)
		for(Border confine: listaConfini) {
			
			Country stato1= countryMap.get(confine.getCode_1());
			Country stato2= countryMap.get(confine.getCode_2());
			
//			Creo l'arco (sono sicuro di non avere archi duplicati perchè code1<code2
			this.grafo.addEdge(stato1, stato2);
		}
		
		System.out.println("Grafo creato:\n#Vertici " + grafo.vertexSet().size() + "\n#Archi "+grafo.edgeSet().size());
		return grafo;
	}

}
