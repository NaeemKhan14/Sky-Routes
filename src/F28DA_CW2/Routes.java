/**
 * 
 */
package F28DA_CW2;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

/**
 * @author Naeem Khan
 * 
 * This class stores all the infomation we need to for our travel.
 * Such as total cost and time of the journey and such. 
 */

public class Routes implements IRoute {
	
	private GraphPath<String, FlightsInfo> shortestPath;
	private int totalCost;
	DateTimeFormatter dateFormat;
	
	/**
	 * Constructor of this class which finds the shortest path for our
	 * journey
	 * 
	 * @param graph Graph to look up the shortest path in
	 * @param from  Departure airport
	 * @param to	Destination airport
	 */
	
	public Routes(SimpleDirectedWeightedGraph<String, FlightsInfo> graph, String from, String to) {
		shortestPath = DijkstraShortestPath.findPathBetween(graph, from, to);
		totalCost = (int) shortestPath.getWeight();
		dateFormat = DateTimeFormatter.ofPattern("HHmm");
	}

	@Override
	public List<String> getStops() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/** Returns the list of airports codes composing the route */
	
	@Override
	public List<String> getFlights() {
		List<String> flightsList = new ArrayList<String>();
		
		for(int i = 0; i < shortestPath.getEdgeList().size(); i++) {		
			flightsList.add(shortestPath.getEdgeList().get(i).toString());
		}
		
		return flightsList;
	}
	
	/**
	 * Takes the shortest path and prints the result in our desired format
	 * @param travelInfo Shortest path to destination from our graph edges
	 * @throws SkyRoutesException
	 */
	
	public void display(IRoute travelInfo) throws SkyRoutesException {
		
		System.out.println("Route for Edinburgh to Sydney");
		System.out.println("Leg   Leave  At     On     Arrive     At");
		int count = 1;
		
		for(String res : travelInfo.getFlights()) {
			String[] result = res.split(":");
			System.out.print(count++ + " ");
			for(int i = 0; i < result.length; i++) {
				if(i == result.length-1) {
					System.out.println(result[i]);
				} else {
					System.out.print(result[i] + ", ");
				}
				
			}
		}
		System.out.println("Total Journey Cost = £" + travelInfo.totalCost());
		System.out.println("Total Time in the Air = " + totalTime() + " minutes");
	}
	
	@Override
	public int totalHop() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int totalCost() {
		return totalCost;
	}
	
	// Reference: https://stackoverflow.com/questions/28353725/java-subtract-localtime
	// Reference: https://stackoverflow.com/questions/53254475/localtime-difference-between-two-times
	
	/** 
	 * This method takes the departure and arrival time of each flight from the
	 * flight data, and calculates the amount of time spent in the air
	 * 
	 * @return total number of minutes spent in the air
	 */
	
	@Override
	public int airTime() {
		
		int result = 0;
		
		for(String[] flightData : getEdgeData()) {
			LocalTime departureTime = LocalTime.parse(flightData[1], dateFormat);
			LocalTime arrivalTime = LocalTime.parse(flightData[4], dateFormat);
			
			/*
			 * If the value we get from comparing the difference between two times is
			 * below 0 (i.e. difference between 1400 -> 0900 gives us a negative because 
			 * LocalTime() cannot differentiate between day changes). In which case we add
			 * a whole day to our result to get the correct value. The code below does that.
			 */
			
			result += (MINUTES.between(departureTime, arrivalTime) + 1440) % 1440;
		}

		
		return result;
	}
	@Override
	public int connectingTime() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	// Reference: https://stackoverflow.com/questions/28353725/java-subtract-localtime
	// Reference: https://stackoverflow.com/questions/53254475/localtime-difference-between-two-times
	
	/** 
	 * This method takes the departure and arrival time of each flight from the
	 * flight data, and calculates the amount of time spent on the journey
	 * 
	 * @return total number of minutes spent on the journey
	 */
	
	@Override
	public int totalTime() {
		
		int result = 0;
		
		for(String[] flightData : getEdgeData()) {
			LocalTime departureTime = LocalTime.parse(flightData[1], dateFormat);
			LocalTime arrivalTime = LocalTime.parse(flightData[4], dateFormat);
			LocalTime interval = arrivalTime;
			
			if(result == 0) {
				// If it's the first value, just add the difference between departure and arrival time of that flight
				result += (MINUTES.between(departureTime, arrivalTime) + 1440) % 1440;
			} else {
				// This algorithm is explained above in airTime() method
				result += (MINUTES.between(interval, departureTime) + 1440) % 1440;
				result += (MINUTES.between(departureTime, arrivalTime) + 1440) % 1440;
			}
			
		}
		
		return result;
	}
	
	/**
	 * This method returns all the data of each flight that is the shortest route
	 * towards the target
	 * 
	 * @return List of all the data of each connection flight (i.e. graph edge)
	 */
	
	private List<String[]> getEdgeData() {
		
		List<String[]> edgeData = new ArrayList<String[]>();
		
		for(FlightsInfo edges : shortestPath.getEdgeList()) {
			String[] edge = edges.toString().split(":");
			edgeData.add(edge);
		}
		
		return edgeData;
	}
}
