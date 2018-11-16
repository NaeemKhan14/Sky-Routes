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
		
		for(String[] data : getEdgeData()) {
			flightsList.add(data[2]);
		}
		
		return flightsList;
	}
	
	/**
	 * Takes the shortest path and prints the result in our desired format
	 * @param travelInfo Shortest path to destination from our graph edges
	 * @throws SkyRoutesException
	 */
	
	public void display() throws SkyRoutesException {
		
		System.out.println(String.format("%1$-5s %2$-15s %3$-5s %4$-10s %5$-15s %6$s", "leg", "leave", "At", "On", "Arrive", "At"));
		
		int count = 1;
		
		for(String[] result : getEdgeData()) {
			System.out.println(String.format("%1$-5d %2$-15s %3$-5s %4$-10s %5$-15s %6$s", count++, result[0], result[1], result[2], result[3], result[4]));
		}
		System.out.println("Total Journey Cost = £" + totalCost());
		System.out.println("Total Time in the Air = " + totalTime() + " minutes");
	}
	
	@Override
	public int totalHop() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int totalCost() {
		return (int) shortestPath.getWeight();
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
