/**
 * 
 */
package F28DA_CW2;

import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * @author Naeem Khan
 *
 */
public class FlightsInfo extends DefaultWeightedEdge {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5801941127947694079L;
	
	private final String flightCode, arrivalTime, departureTime, arrivalAirport, departureAirport;
	
	public FlightsInfo(String flightCode, String departureAirport, String departureTime, String arrivalAirport,
			String arrivalTime) {
		this.flightCode = flightCode;
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
	}
	
	@Override
	public String toString() {
		return "(" + departureAirport + " : " + departureTime + " : " + flightCode + " : " + arrivalAirport + " : " + arrivalTime + ")";
	}

}
