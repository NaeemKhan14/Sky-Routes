package F28DA_CW2;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class hello {
	public static void pr() {
		SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> simpleGraph = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		simpleGraph.addVertex("Edinburgh");
		simpleGraph.addVertex("Dubai");
		simpleGraph.addVertex("Sydney");
		simpleGraph.addVertex("Heathrow");
		simpleGraph.addVertex("Kuala Lumpur");
		
		DefaultWeightedEdge e1 = simpleGraph.addEdge("Edinburgh", "Heathrow");
		DefaultWeightedEdge e2 = simpleGraph.addEdge("Heathrow", "Dubai");
		DefaultWeightedEdge e3 = simpleGraph.addEdge("Heathrow", "Sydney");
		DefaultWeightedEdge e4 = simpleGraph.addEdge("Dubai", "Kuala Lumpur");
		DefaultWeightedEdge e5 = simpleGraph.addEdge("Dubai", "Edinburgh");
		DefaultWeightedEdge e6 = simpleGraph.addEdge("Kuala Lumpur", "Sydney");
		
		simpleGraph.setEdgeWeight(e1, 80);
		simpleGraph.setEdgeWeight(e2, 130);
		simpleGraph.setEdgeWeight(e3, 570);
		simpleGraph.setEdgeWeight(e4, 170);
		simpleGraph.setEdgeWeight(e5, 190);
		simpleGraph.setEdgeWeight(e6, 150);
		
		GraphPath<String, DefaultWeightedEdge> d = DijkstraShortestPath.findPathBetween(simpleGraph, "Dubai", "Sydney");
		
		System.out.println(d);
	}
	public static void main(String[] args) {
		pr();
	}
}
