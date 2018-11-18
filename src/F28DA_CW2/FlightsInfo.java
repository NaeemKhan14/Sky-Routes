package F28DA_CW2;

import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * @author Naeem Khan
 *	This class stores the individual flight information in the edge
 *	of the graph
 */
public class FlightsInfo extends DefaultWeightedEdge {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5801941127947694079L;
	
	// Fields
	private final String flightCode, arrivalTime, departureTime, arrivalAirport, departureAirport;
	
	// Constructor
	public FlightsInfo(String flightCode, String departureAirport, String departureTime, String arrivalAirport,
			String arrivalTime) {
		this.flightCode = flightCode;
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
	}
	
	/**
	 * Overriding the default toString() method of DefaultWeightedEdge
	 * class to display the output that we require
	 */
	
	@Override
	public String toString() {
		return departureAirport + ":" + departureTime + ":" + flightCode + ":" + arrivalAirport + ":" + arrivalTime;
	}

}
