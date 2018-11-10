package F28DA_CW2;

import java.util.Set;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

/**
 * @author Naeem Khan
 *	
 *	This class is made to create and work with SimpleDirectedWeightedGraph,
 *	for part A of Sky Routes. This will help avoid cluster in main class, 
 *	and follow the concepts of OOP.
 */


public class DirectFlightsAndLeastCost {
	
	private SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> simpleGraph;
	
	public DirectFlightsAndLeastCost() {
		simpleGraph = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	}
	
	public void addGraphVertex() {
		simpleGraph.addVertex("Dubai");
		simpleGraph.addVertex("Edinburgh");
		simpleGraph.addVertex("Heathrow");
		simpleGraph.addVertex("Sydney");
		simpleGraph.addVertex("Kuala Lumpur");
	}
	
	public void addGraphEdges() {
		simpleGraph.setEdgeWeight(simpleGraph.addEdge("Edinburgh", "Heathrow"), 80);
		simpleGraph.setEdgeWeight(simpleGraph.addEdge("Heathrow" ,"Edinburgh"), 80);
		simpleGraph.setEdgeWeight(simpleGraph.addEdge("Heathrow", "Dubai"), 130);
		simpleGraph.setEdgeWeight(simpleGraph.addEdge("Dubai", "Heathrow"), 130);
		simpleGraph.setEdgeWeight(simpleGraph.addEdge("Heathrow", "Sydney"), 570);
		simpleGraph.setEdgeWeight(simpleGraph.addEdge("Sydney", "Heathrow"), 570);
		simpleGraph.setEdgeWeight(simpleGraph.addEdge("Dubai", "Kuala Lumpur"), 170);
		simpleGraph.setEdgeWeight(simpleGraph.addEdge("Kuala Lumpur", "Dubai"), 170);
		simpleGraph.setEdgeWeight(simpleGraph.addEdge("Dubai", "Edinburgh"), 190);
		simpleGraph.setEdgeWeight(simpleGraph.addEdge("Edinburgh", "Dubai"), 190);
		simpleGraph.setEdgeWeight(simpleGraph.addEdge("Kuala Lumpur", "Sydney"), 150);
		simpleGraph.setEdgeWeight(simpleGraph.addEdge("Sydney", "Kuala Lumpur"), 150);
	}
	
	public void getShortestPath(String from, String to) {
		GraphPath<String, DefaultWeightedEdge> shortestPath = DijkstraShortestPath.findPathBetween(simpleGraph, from, to);
		
		System.out.println("Shortest (i.e. cheapest) path:");
		
		for(int i = 0; i < shortestPath.getVertexList().size()-1; i++) {
			System.out.println(i+1 + ". " + shortestPath.getVertexList().get(i) + " -> " + shortestPath.getVertexList().get(i+1));
		}
		
		System.out.println("Cost of shortest (i.e. cheapest) path = £" + (int) shortestPath.getWeight());
		
	}
	
	public Set<String> getVertexes() {
		return simpleGraph.vertexSet();
	}
	
}
