import java.util.List;

/*IMPORTANT NOTE:
 * DO NOT 
 * MAKE ANY CHANGES 
 * TO THIS INTERFACE*/
public interface GraphInterface<V>{

	/** Create and returns an adjacency lists from edge arrays */	
	public List<List<Graph<V>.Edge>> createWeightedGraph(List<V> vertices, int[][] edges);

	/** Find single source shortest paths */
	public Graph<V>.Tree getShortestPath(V sourceVertex,List<V> vertices,List<List<Graph<V>.Edge>> neighbors) ;
}
