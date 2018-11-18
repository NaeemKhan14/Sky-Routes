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
	private DateTimeFormatter dateFormat;
	
	/**
	 * Constructor of this class which finds the shortest path for our
	 * journey
	 * 
	 * @param graph Graph to look up the shortest path in
	 * @param from  Departure airport
	 * @param to	Destination airport
	 */
	
	public Routes(SimpleDirectedWeightedGraph<String, FlightsInfo> graph, String from, String to) {
		DijkstraShortestPath<String, FlightsInfo> shortestPathGraph = new DijkstraShortestPath<String, FlightsInfo>(graph);
		shortestPath = shortestPathGraph.getPath(from, to);
		dateFormat = DateTimeFormatter.ofPattern("HHmm");	
	}

	/** Returns the list of airports codes composing the route */
	
	@Override
	public List<String> getStops() {
		
		List<String> airportsList = new ArrayList<String>();
		
		for(String[] data : getEdgeData()) {
			if(!airportsList.contains(data[0])) {
				airportsList.add(data[0]);
			}
			if(!airportsList.contains(data[3])) {
				airportsList.add(data[3]);
			}
		}
		
		return airportsList;
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
	
	@Override
	public int totalHop() {
		return getEdgeData().size();
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
	
	/** Returns the total time in connection of the route */
	
	@Override
	public int connectingTime() {
		int result = 0;
		
		for(int i = 0; i < getEdgeData().size()-1; i++) {
			LocalTime arrivalTime = LocalTime.parse(getEdgeData().get(i)[4], dateFormat);
			LocalTime departureTime = LocalTime.parse(getEdgeData().get(i+1)[1], dateFormat);
			
			result += (MINUTES.between(arrivalTime, departureTime) + 1440) % 1440;
		}
	
		return result;
	}
	
	/** 
	 * @return total number of minutes spent on the journey
	 */
	
	@Override
	public int totalTime() {	
		return airTime() + connectingTime();
	}
	
	/**
	 * This method returns all the data of each flight that is the shortest route
	 * towards the target
	 * 
	 * @return List of all the data of each connection flight (i.e. graph edge)
	 */
	
	@Override
	public List<String[]> getEdgeData() {
		
		List<String[]> edgeData = new ArrayList<String[]>();
		
		for(FlightsInfo edges : shortestPath.getEdgeList()) {
			String[] edge = edges.toString().split(":");
			edgeData.add(edge);
		}
		
		return edgeData;
	}
}
