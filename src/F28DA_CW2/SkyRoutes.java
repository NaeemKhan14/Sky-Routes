package F28DA_CW2;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.AsUnweightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;


public class SkyRoutes implements IRoutes {

	private static FlightsReader flightsReader;
	private static DirectedWeightedMultigraph<String, FlightsInfo> graph;
	private static HashMap<String, String> airportNames, flightsCost;
	private static Routes flightsList;
	
	public SkyRoutes() {
		graph = new DirectedWeightedMultigraph<String, FlightsInfo>(FlightsInfo.class);
		airportNames = new HashMap<String, String>();
		try {
			flightsReader = new FlightsReader(FlightsReader.MOREAIRLINECODES);
		} catch (FileNotFoundException | SkyRoutesException e) {
			e.printStackTrace();
		}
	}
	
	public static void partA() {
		
		SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> simpleGraph = 
				new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		simpleGraph.addVertex("Dubai");
		simpleGraph.addVertex("Edinburgh");
		simpleGraph.addVertex("Heathrow");
		simpleGraph.addVertex("Sydney");
		simpleGraph.addVertex("Kuala Lumpur");
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
		
		
		System.out.println("The following airports are used:");
		
		for(String s : simpleGraph.vertexSet()) {
			System.out.println("      " + s);
		}
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter the start airport");
		String from = scan.nextLine().trim();
		
		System.out.println("Please enter the destination airport");
		String to = scan.nextLine().trim();
		scan.close();
		
		DijkstraShortestPath<String, DefaultWeightedEdge> d = new DijkstraShortestPath<>(simpleGraph);
		GraphPath<String, DefaultWeightedEdge> shortestPath = d.getPath(from, to);
		
		System.out.println("Shortest (i.e. cheapest) path:");
		
		for(int i = 0; i < shortestPath.getVertexList().size()-1; i++) {
			System.out.println(i+1 + ". " + shortestPath.getVertexList().get(i) + " -> " + shortestPath.getVertexList().get(i+1));
		}
		
		System.out.println("Cost of shortest (i.e. cheapest) path = £" + (int) shortestPath.getWeight());
		
	}

	public static void partB() {
		
		IRoutes skyRoutes = new SkyRoutes();
		skyRoutes.populate(flightsReader.getAirlines(), flightsReader.getAirports(), flightsReader.getFlights());
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter the start airport");
		String start = scan.next().toUpperCase();
		
		System.out.println("Please enter the destination airport");
		String end = scan.next().toUpperCase();
		scan.close();
		
		System.out.println("Route for " + airportNames.get(start) + " to " + airportNames.get(end));
		try {
			IRoute travelInfo = skyRoutes.leastCost(start, end);
			System.out.println(String.format("%1$-5s %2$-15s %3$-5s %4$-10s %5$-15s %6$s",
					"leg", "leave", "At", "On", "Arrive", "At"));
			
			int count = 1;
			
			for(String[] result : flightsList.getEdgeData()) {
				System.out.println(String.format("%1$-5d %2$-15s %3$-5s %4$-10s %5$-15s %6$s", count++, 
						result[0], result[1], result[2], result[3], result[4]));
			}
			System.out.println("Total Journey Cost = £" + travelInfo.totalCost());
			System.out.println("Total travel time = " + travelInfo.totalTime() + " minutes");
		} catch (SkyRoutesException e) {
			e.printStackTrace();
		}

	}

	public static void partC() {
		IRoutes skyRoutes = new SkyRoutes();
		skyRoutes.populate(flightsReader.getAirlines(), flightsReader.getAirports(), flightsReader.getFlights());
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter the start airport");
		String start = scan.next().toUpperCase();
		
		System.out.println("Please enter the destination airport");
		String end = scan.next().toUpperCase();
		scan.close();
		
		System.out.println("Route for " + airportNames.get(start) + " to " + airportNames.get(end));
		try {
			List<String> exclusionList = new ArrayList<String>();
			exclusionList.add("NTQ");
			exclusionList.add("LPQ");
			
			IRoute travelInfo = skyRoutes.leastHop(start, end);
			System.out.println(String.format("%1$-5s %2$-15s %3$-5s %4$-10s %5$-15s %6$s",
					"leg", "leave", "At", "On", "Arrive", "At"));
			
			int count = 1;
			
			for(String[] result : flightsList.getEdgeData()) {
				System.out.println(String.format("%1$-5d %2$-15s %3$-5s %4$-10s %5$-15s %6$s", count++, 
						airportNames.get(result[0]), result[1], result[2], airportNames.get(result[3]), result[4]));
			}
			
			System.out.println("Total Journey Cost = £" + travelInfo.totalCost());
			System.out.println("Total Number of Connections: " + travelInfo.totalHop());
			System.out.println("Total Time in Air: " + travelInfo.airTime() + " Minutes");
			System.out.println("Total Time Spent Waiting: " + travelInfo.connectingTime() + " Minutes");
			System.out.println("Total travel time = " + travelInfo.totalTime() + " Minutes");

		} catch (SkyRoutesException e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		partC();
//		partB();
//		partC();
		
	}

	/**
	 * Populates the graph with the airlines, airports and flights information.
	 * Returns true if the operation was successful.
	 *
	 * This method has implemented HashMap to map the airport names
	 * with their airport codes. This helps in getting the names for
	 * the desired airport using its code (i.e. DXB for Dubai)
	 */
	
	@Override
	public boolean populate(HashSet<String[]> airlines, HashSet<String[]> airports, HashSet<String[]> routes) {

		// Get the airports data and all the airport codes as vertices
		for(String[] airportsList : airports) {
			airportNames.put(airportsList[0], airportsList[1]); // Put airport codes as keys, and airport names as their values
			// 
			if(!graph.addVertex(airportsList[0])) { 
				return false;
			}
		}
		
		/*
		 * Get the flights data from flights database and add them to edges
		 * with their relevant information
		 */
		
		for(String[] flightsInfo : routes) {
			FlightsInfo edgeInfo = new FlightsInfo(flightsInfo[0], flightsInfo[1], flightsInfo[2], 
					flightsInfo[3], flightsInfo[4]);
			if(!graph.addEdge(flightsInfo[1], flightsInfo[3], edgeInfo)) {
				return false;
			}
			graph.setEdgeWeight(edgeInfo, Integer.parseInt(flightsInfo[5]));
		}
		
		return true;
	}

	@Override
	public IRoute leastCost(String from, String to) throws SkyRoutesException {
		// If the airports we are looking for do not exist in our graph,
		// it will throw an error
		if(!graph.containsVertex(from) || !graph.containsVertex(to)) {
			throw new SkyRoutesException("Target airport does not exist");
		}
		
		return flightsList = new Routes(graph, from, to);
	}

	@Override
	public IRoute leastHop(String from, String to) throws SkyRoutesException {
		
		if(!graph.containsVertex(from) || !graph.containsVertex(to)) {
			throw new SkyRoutesException("Target airport does not exist");
		}
		/*
		 * In case of least hops, the graph used is an unweighted graph, it
		 * does not contain any edge weights. Since every flight code is unique
		 * in the flights database, I stored each flight's price in a hashmap,
		 * and used it in Routes class to get that flight's price.
		 */
		flightsCost = new HashMap<>();
		
		for(String[] flightsInfo : flightsReader.getFlights()) {
			flightsCost.put(flightsInfo[0], flightsInfo[5]);
		}
		
		AsUnweightedGraph<String, FlightsInfo> unweightedGraph = new AsUnweightedGraph<>(graph);
		return flightsList = new Routes(unweightedGraph, from, to, flightsCost);
	}

	@Override
	public IRoute leastCost(String from, String to, List<String> excluding) throws SkyRoutesException {
		
		if(!graph.containsVertex(from) || !graph.containsVertex(to)) {
			throw new SkyRoutesException("Target airport does not exist");
		}
		
		/* 
		 *	Remove all the edges of the given airport so they do not appear
		 *	in our search
		*/
		for(String airportCodes: excluding) {
			graph.removeAllEdges(graph.edgesOf(airportCodes));
		}
		
		return flightsList = new Routes(graph, from, to);
	}

	@Override
	public IRoute leastHop(String from, String to, List<String> excluding) throws SkyRoutesException {
		
		if(!graph.containsVertex(from) || !graph.containsVertex(to)) {
			throw new SkyRoutesException("Target airport does not exist");
		}
		
		flightsCost = new HashMap<>();
		
		for(String[] flightsInfo : flightsReader.getFlights()) {
			flightsCost.put(flightsInfo[0], flightsInfo[5]);
		}
		
		for(String airportCodes: excluding) {
			graph.removeAllEdges(graph.edgesOf(airportCodes));
		}
		

		AsUnweightedGraph<String, FlightsInfo> unweightedGraph = new AsUnweightedGraph<>(graph);
		
		return flightsList = new Routes(unweightedGraph, from, to, flightsCost);
	}

	@Override
	public String leastCostMeetUp(String at1, String at2) throws SkyRoutesException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String leastHopMeetUp(String at1, String at2) throws SkyRoutesException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String leastTimeMeetUp(String at1, String at2, String startTime) throws SkyRoutesException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IRoute> allRoutesCost(String from, String to, List<String> excluding, int maxCost)
			throws SkyRoutesException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IRoute> allRoutesHop(String from, String to, List<String> excluding, int maxHop)
			throws SkyRoutesException {
		// TODO Auto-generated method stub
		return null;
	}

}
