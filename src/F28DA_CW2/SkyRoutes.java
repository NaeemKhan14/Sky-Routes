package F28DA_CW2;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import org.jgrapht.graph.SimpleDirectedWeightedGraph;


public class SkyRoutes implements IRoutes {

	private static DirectFlightsAndLeastCost directCost;
	private static FlightsReader flightsReader;
	private static SimpleDirectedWeightedGraph<String, FlightsInfo> graph;
	private static HashMap<String, String> airportNames;
	
	public SkyRoutes() {
		graph = new SimpleDirectedWeightedGraph<String, FlightsInfo>(FlightsInfo.class);
		airportNames = new HashMap<String, String>();
		try {
			flightsReader = new FlightsReader(FlightsReader.MOREAIRLINECODES);
		} catch (FileNotFoundException | SkyRoutesException e) {
			e.printStackTrace();
		}
	}
	
	public static void partA() {
		
		directCost = new DirectFlightsAndLeastCost();
		directCost.addGraphVertex();
		directCost.addGraphEdges();
		
		System.out.println("The following airports are used:");
		
		for(String s : directCost.getVertexes()) {
			System.out.println("      " + s);
		}
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter the start airport");
		String input = scan.next();
		
		System.out.println("Please enter the destination airport");
		String input2 = scan.next();
		scan.close();
		
		directCost.getShortestPath(input, input2);
		
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
			System.out.println(String.format("%1$-5s %2$-15s %3$-5s %4$-10s %5$-15s %6$s", "leg", "leave", "At", "On", "Arrive", "At"));
			
			int count = 1;
			
			for(String[] result : travelInfo.getEdgeData()) {
				System.out.println(String.format("%1$-5d %2$-15s %3$-5s %4$-10s %5$-15s %6$s", count++, 
						airportNames.get(result[0]), result[1], result[2], airportNames.get(result[3]), result[4]));
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
			exclusionList.add("LHR");
			exclusionList.add("BKK");
			IRoute travelInfo = skyRoutes.leastCost(start, end, exclusionList);
			System.out.println(String.format("%1$-5s %2$-15s %3$-5s %4$-10s %5$-15s %6$s", "leg", "leave", "At", "On", "Arrive", "At"));
			
			int count = 1;
			
			for(String[] result : travelInfo.getEdgeData()) {
				System.out.println(String.format("%1$-5d %2$-15s %3$-5s %4$-10s %5$-15s %6$s", count++, 
						result[0], result[1], result[2], result[3], result[4]));
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
			graph.addVertex(airportsList[0]); // Add the airport names as vertices
		}
		
		/*
		 * Get the flights data from flights database and add them to edges
		 * with their relevant information
		 */
		
		for(String[] flightsInfo : routes) {
			graph.addEdge(flightsInfo[1], flightsInfo[3], new FlightsInfo(flightsInfo[0], flightsInfo[1], flightsInfo[2], flightsInfo[3], flightsInfo[4]));
			graph.setEdgeWeight(graph.getEdge(flightsInfo[1], flightsInfo[3]), Integer.parseInt(flightsInfo[5]));
		}
		
		// FIX THIS!!!!!!!
		return true;
	}

	@Override
	public IRoute leastCost(String from, String to) throws SkyRoutesException {
		return new Routes(graph, from, to);
	}

	@Override
	public IRoute leastHop(String from, String to) throws SkyRoutesException {
		return null;
	}

	@Override
	public IRoute leastCost(String from, String to, List<String> excluding) throws SkyRoutesException {
		
		for(String airportCodes: excluding) {
			graph.removeAllEdges(graph.edgesOf(airportCodes));
		}
		
		return new Routes(graph, from, to);
	}

	@Override
	public IRoute leastHop(String from, String to, List<String> excluding) throws SkyRoutesException {
		// TODO Auto-generated method stub
		return null;
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
