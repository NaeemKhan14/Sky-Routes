package F28DA_CW2;

import java.util.HashSet;
import java.util.List;

public interface IRoutes {

	/**
	 * Populates the graph with the airlines, airports and flights information.
	 * Returns true if the operation was successful.
	 */
	boolean populate(HashSet<String[]> airlines, HashSet<String[]> airports, HashSet<String[]> routes);

	/**
	 * Returns a cheapest flight route from one airport (airport code) to another
	 */
	IRoute leastCost(String from, String to) throws SkyRoutesException;

	/**
	 * Returns a least connections flight route from one airport (airport code) to
	 * another
	 */
	IRoute leastHop(String from, String to) throws SkyRoutesException;

	/**
	 * Returns a cheapest flight route from one airport (airport code) to another,
	 * excluding a list of airport (airport codes)
	 */
	IRoute leastCost(String from, String to, List<String> excluding) throws SkyRoutesException;

	/**
	 * Returns a least connections flight route from one airport (airport code) to
	 * another, excluding a list of airport (airport codes)
	 */
	IRoute leastHop(String from, String to, List<String> excluding) throws SkyRoutesException;

	/**
	 * Returns the airport code of a best airport for the meet up of two people
	 * located in two different airports (airport codes) accordingly to the routes
	 * costs
	 */
	String leastCostMeetUp(String at1, String at2) throws SkyRoutesException;

	/**
	 * Returns the airport code of a best airport for the meet up of two people
	 * located in two different airports (airport codes) accordingly to the number
	 * of connections
	 */
	String leastHopMeetUp(String at1, String at2) throws SkyRoutesException;

	/**
	 * Returns the airport code of a best airport for the earliest meet up of two
	 * people located in two different airports (airport codes) when departing at a
	 * given time
	 */
	String leastTimeMeetUp(String at1, String at2, String startTime) throws SkyRoutesException;

	/**
	 * Returns the flight routes, in increasing price cost, below or equal to a
	 * given price, from one airport (airport code) to another, excluding a list of
	 * airport (airport codes)
	 */
	List<IRoute> allRoutesCost(String from, String to, List<String> excluding, int maxCost) throws SkyRoutesException;

	/**
	 * Returns the flights routes, in increasing number of hops, below or equal to a
	 * given number of hops, from one airport (airport code) to another, excluding a
	 * list of airport (airport codes)
	 */
	List<IRoute> allRoutesHop(String from, String to, List<String> excluding, int maxHop) throws SkyRoutesException;

}
