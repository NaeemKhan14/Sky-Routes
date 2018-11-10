package F28DA_CW2;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;


public class SkyRoutes implements IRoutes {

	private static DirectFlightsAndLeastCost directCost;
	
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
		// TO IMPLEMENT
	}

	public static void partC() {
		// TO IMPLEMENT
	}

	public static void main(String[] args) {
		partA();
	}

	@Override
	public boolean populate(HashSet<String[]> airlines, HashSet<String[]> airports, HashSet<String[]> routes) {
		// TODO Auto-generated method stub
		return false;
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
