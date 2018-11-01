import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*IMPORTANT NOTE:
 * DO NOT 
 * MAKE ANY CHANGES 
 * TO THIS CLASS.*/
public class Test{
	public static void main(String[] args) throws FileNotFoundException {

		int NumberOfVertices=0;
		int NumberOfEdges=0;
		int NumberOfOrigin=0;
		ArrayList<String> vert= new ArrayList<>();
		int[][] edges=null;
		/*In Eclipse and other IDE the input file needs to be taken from src/input.txt path
		 * as all the java files are part of src directory by default*/

		File file = new File("input.txt");
		if (!file.isFile())
			file = new File("src/input.txt");
		
		Scanner sc = new Scanner(file);
		NumberOfVertices=sc.nextInt();
		NumberOfEdges=sc.nextInt();
		NumberOfOrigin=sc.nextInt();
		for(int i=0;i<NumberOfVertices;i++) {
			vert.add(sc.next());
		}

		edges = new int[NumberOfEdges][3];

		for(int i=0;i<NumberOfEdges;i++) {
			edges[i][0]=sc.nextInt();
			edges[i][1]=sc.nextInt();
			edges[i][2]=sc.nextInt();
		}

		//Generate graph with vertices and edges
		Graph<String> graph1 =  new Graph<>();

		List<List<Graph<String>.Edge>> adjList = graph1.createWeightedGraph(vert, edges);

		//build tree for shortest path from Chicago to every node
		for(int i=0;i<NumberOfOrigin;i++) {
			// then I'll get an error here because getShortestPath is not done yet
			Graph<String>.Tree tree1 = graph1.getShortestPath(sc.next(),vert,adjList);
			//print shortest path to each node from Chicago
			tree1.printAllPaths(vert);
			System.out.println();
		}

	}
}
