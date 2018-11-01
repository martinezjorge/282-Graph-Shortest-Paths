import java.lang.reflect.Array;
import java.util.*;

public class Graph<V> implements GraphInterface<V>{

	/** Construct an empty */
	public Graph() {
	}

	// Jorge Martinez
	/** Create and returns an adjacency lists from edge arrays */
	public List<List<Edge>> createWeightedGraph(List<V> vertices, int[][] edges) {
		List<List<Edge>> neighbors = new ArrayList<>();
		for(int k = 0; k < vertices.size(); k++) {
			List<Edge> vertex = new ArrayList<>();
			for (int[] edge : edges) {
				Edge newEdge = new Edge(edge[0], edge[1], edge[2]);
				if (edge[0] == k)
					vertex.add(newEdge);
			}
			neighbors.add(vertex);
		}
		return neighbors;
	}

	// Aram Balayan
	// Jorge Martinez
	/** Find single source shortest paths */
	public Tree getShortestPath(V sourceVertex,List<V> vertices,List<List<Edge>> neighbors) {

		int source = 0;
		for(int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i).equals(sourceVertex))
				source = i;
		}

		int [] parent = new int[vertices.size()];
		parent[source] = -1;

		boolean [] done = new boolean[vertices.size()];
		done[source] = true;

		List<Edge> edgePath = neighbors.get(source);
		Queue<Edge> sortedQ = sortedQueue(edgePath);

		while(!sortedQ.isEmpty()){
			Edge targetEdge = sortedQ.remove();
			if (!done[targetEdge.v]) {
				done[targetEdge.v] = true;
				parent[targetEdge.v] = targetEdge.u;
				Queue<Edge> moreEdges = sortedQueue(neighbors.get(targetEdge.v));
				while(!moreEdges.isEmpty())
					sortedQ.add(moreEdges.remove());
				List<Edge> toBeSorted = new ArrayList<>();
				while(!sortedQ.isEmpty())
					toBeSorted.add(sortedQ.remove());
				sortedQ = sortedQueue(toBeSorted);
			}
		}
		return new Tree(source, parent, bestWeights(parent, neighbors, vertices.size()));
	}

	// Aram Balayan
	// Jorge Martinez
	public Queue<Edge> sortedQueue(List<Edge> edgePath){

		Queue<Edge> pQ = new LinkedList<Edge>();

		boolean [] minTrue = new boolean[edgePath.size()];
		int minIndex = 0;

		for(int k = 0; k < edgePath.size(); k++) {
			double minWeight = -1;
			for (int i = 0; i < edgePath.size(); i++) {
				if (minWeight == -1 && !minTrue[i]) {
					minWeight = edgePath.get(i).weight;
					minIndex = i;
				} else if (edgePath.get(i).weight < minWeight && !minTrue[i]) {
					minWeight = edgePath.get(i).weight;
					minIndex = i;
				}
			}
			minTrue[minIndex] = true;
			pQ.add(edgePath.get(minIndex));
		}
		return pQ;
	}

	// Aram Balayan
	// Jorge Martinez
	public double [] bestWeights(int [] parent, List<List<Edge>> neighbors, int verticesSize){

		double[] weights = new double[verticesSize];

		for(int currentVertex = 0; currentVertex<verticesSize; currentVertex++) {
			int currentIndex = currentVertex;
			double weight = 0;
			while (parent[currentIndex] != -1) {
				List<Edge> edges = neighbors.get(parent[currentIndex]);
				for (Edge edge : edges) {
					if (edge.v == currentIndex) {
						weight += edge.weight;
						currentIndex = parent[currentIndex];
					}
				}
			}
			weights[currentVertex] = weight;
		}
		return weights;
	}



	/** Edge inner class inside the Graph class */
	/*EDGE CLASS STORES THE EDGE SUCH THAT u AND v ARE THE TWO VERTEX CONNECTED BY THE EDGE AND
	 * weight IS THE WEIGHT OF THE EDGE
	 * */
	public class Edge {
		public int u; // Starting vertex of the edge
		public int v; // Ending vertex of the edge
		public double weight; //Weight of the edge
		/** Construct an edge for (u, v, weight) */
		public Edge(int u, int v, double weight) {
			this.u = u; //Important
			this.v = v;
			this.weight=weight;
		}
	}

	/** Tree inner class inside the Graph class */
	/*TREE CLASS STORES THE TREE SUCH THAT root IS THE ROOT NODE OF TREE (i.e. sourceVertex FOR SHORTEST DISTANCE TREE)
	 * parent[i] STORES THE PARENT OF NODE i IN THE TREE
	 * NOTE: PARENT OF root is -1 (i.e. parent[root]=-1
	 * cost[i] is COST OF PATH FROM root (i.e. sourceVertex) to Node i
	 * */
	public class Tree {
		private int root; // The root of the tree
		private int[] parent; // Store the parent of each vertex, Parent of root node is -1
		private double[] cost; // cost of each vertex from root i.e. source

		/** Construct a tree with root, parent, cost */
		public Tree(int root, int[] parent,double[] cost) {
			this.root = root;//Important
			this.parent = parent;
			this.parent[root]=-1;
			this.cost = cost;
		}

		/** Return the root of the tree */
		public int getRoot() {
			return root;//Important
		}

		/** Return the path of vertices from a vertex to the root */
		public List<V> getPath(int index,List<V> vertices) {
			ArrayList<V> path = new ArrayList<>();//Important
			do {
				path.add(vertices.get(index));
				index = parent[index];
			} while (index != -1);

			return path;
		}

		/** Print a path from the root to vertex v */
		public void printPath(int index,List<V> vertices) {
			List<V> path = getPath(index,vertices); //Important
			System.out.print("A path from " + vertices.get(root) + " to " +
					vertices.get(index) + ": ");
			for (int i = path.size() - 1; i >= 0; i--)
				System.out.print(path.get(i) + " ");
		}

		/** Print paths from all vertices to the source */
		public void printAllPaths(List<V> vertices) {
			System.out.println("All shortest paths from " +
					vertices.get(getRoot()) + " are:");//Important
			for (int i = 0; i < cost.length; i++) {
				printPath(i,vertices); // Print a path from i to the source
				System.out.println("(cost: " + cost[i] + ")"); // Path cost
			}
		}
	}
}