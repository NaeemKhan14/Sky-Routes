package F28DA_CW2;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;


public class SkyRoutes implements IRoutes {

	private static DirectFlightsAndLeastCost directCost;
	private static FlightsReader flightsReader;
	private static SimpleDirectedWeightedGraph<String, FlightsInfo> graph;
	private static HashMap<String, String> airlineNames;
	
	public SkyRoutes() {
		graph = new SimpleDirectedWeightedGraph<String, FlightsInfo>(FlightsInfo.class);
		airlineNames = new HashMap<String, String>();
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
		
		IRoutes s = new SkyRoutes();
		s.populate(flightsReader.getAirlines(), flightsReader.getAirports(), flightsReader.getFlights());
		
		GraphPath<String, FlightsInfo> shortestPath = DijkstraShortestPath.findPathBetween(graph, "EDI", "SYD");
		
		for(int i = 0; i < shortestPath.getEdgeList().size(); i++) {
			System.out.println(shortestPath.getEdgeList().get(i));
		}
		
		System.out.println(shortestPath.getWeight());
	}

	public static void partC() {
		// TO IMPLEMENT
	}

	public static void main(String[] args) {
		partB();
		
	}

	@Override
	public boolean populate(HashSet<String[]> airlines, HashSet<String[]> airports, HashSet<String[]> routes) {

		for(String[] airportsList : airports) {
			for(int i = 0; i < airportsList.length; i+=3) {
				airlineNames.put(airportsList[0], airportsList[1]);
				graph.addVertex(airportsList[i]);
			}
		}
		
		for(String[] flightsInfo : routes) {
			for(int i = 0; i < flightsInfo.length; i++) {
				FlightsInfo flightData = new FlightsInfo(flightsInfo[0], airlineNames.get(flightsInfo[1]), flightsInfo[2], airlineNames.get(flightsInfo[3]), flightsInfo[4]);
				graph.addEdge(flightsInfo[1], flightsInfo[3], flightData);
				graph.setEdgeWeight(flightData, (int) Double.parseDouble(flightsInfo[5]));
			}
		}
		
		// FIX THIS!!!!!!!
		return true;
	}

	@Override
	public IRoute leastCost(String from, String to) throws SkyRoutesException {
		return null;
	}

	@Override
	public IRoute leastHop(String from, String to) throws SkyRoutesException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRoute leastCost(String from, String to, List<String> excluding) throws SkyRoutesException {
		
		return null;
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
