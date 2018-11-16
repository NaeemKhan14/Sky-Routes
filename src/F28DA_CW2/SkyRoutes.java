package F28DA_CW2;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;


public class SkyRoutes implements IRoutes {

	private static DirectFlightsAndLeastCost directCost;
	private static FlightsReader flightsReader;
	private static SimpleDirectedWeightedGraph<String, FlightsInfo> graph;
	private static HashMap<String, String> airlineNames;
	private static Routes flightsList;
	
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
		
		IRoutes skyRoutes = new SkyRoutes();
		skyRoutes.populate(flightsReader.getAirlines(), flightsReader.getAirports(), flightsReader.getFlights());
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter the start airport");
		String start = formatString(scan.next().split(" "));
		
		System.out.println("Please enter the destination airport");
		String end = formatString(scan.next().split(" "));
		scan.close();
		
		System.out.println("Route for " + start + " to " + end);
		try {
			@SuppressWarnings("unused")
			IRoute travelInfo = skyRoutes.leastCost(start, end);
			flightsList.display();
		} catch (SkyRoutesException e) {
			e.printStackTrace();
		}
		
	}

	public static void partC() {
		// TO IMPLEMENT
	}

	public static void main(String[] args) {
		partB();
		
	}
	
	/**
	 * This method converts the user input to match the vertices in our graph
	 * In case malformed String input is given, like combination of lower and
	 * upper case letters, this will convert it into a String which is 
	 * compatible with our graph
	 * 
	 * @param word The array of words
	 * @return A single formatted string
	 */
	
	public static String formatString(String[] word) {
		
		String result = "";
		
		for(int i = 0; i < word.length; i++) {
			/*Get the first letter of the word, convert it into uppercase, and change the rest of the words
			to lower case.*/
			result += word[i].substring(0, 1).toUpperCase() + word[i].substring(1, word[i].length()).toLowerCase();
			/*
			 * If the array has more than one values, that means there is a space between the word. In which
			 * case we add a space after each word we check in the word array only if it is not the final 
			 * value in the word array (that would mean it's the final word and we do not want space in the
			 * end of the word).
			 */
			if(word.length > 1 && i < word.length-1) {
				result += " ";
			}
		}

		return result;
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
			airlineNames.put(airportsList[0], airportsList[1]); // Put airport names as keys, and airport codes as their values
			graph.addVertex(airportsList[1]); // Add the airport names as vertices
		}
		
		/*
		 * Get the flights data from flights database and add them to edges
		 * with their relevant information
		 */
		
		for(String[] flightsInfo : routes) {
			FlightsInfo flightData = new FlightsInfo(flightsInfo[0], airlineNames.get(flightsInfo[1]), flightsInfo[2], airlineNames.get(flightsInfo[3]), flightsInfo[4]);
			graph.addEdge(airlineNames.get(flightsInfo[1]), airlineNames.get(flightsInfo[3]), flightData);
			graph.setEdgeWeight(flightData, (int) Double.parseDouble(flightsInfo[5]));
		}
		
		// FIX THIS!!!!!!!
		return true;
	}

	
	@Override
	public IRoute leastCost(String from, String to) throws SkyRoutesException {
		return flightsList = new Routes(graph, from, to);
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
