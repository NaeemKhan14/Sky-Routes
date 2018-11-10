package F28DA_CW2;

import java.io.FileNotFoundException;
import java.util.HashMap;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class hello {
	
	public static void main(String[] args) throws FileNotFoundException, SkyRoutesException {
		
		SimpleDirectedWeightedGraph<String, FlightsInfo> m = new SimpleDirectedWeightedGraph<String, FlightsInfo>(FlightsInfo.class);
		
		FlightsReader fr = new FlightsReader(FlightsReader.MOREAIRLINECODES);
		HashMap<String, String> airLines = new HashMap<String, String>();
		
		for(String[] s : fr.getAirports()) {
			for(int i = 0; i < s.length; i+=3) {
				airLines.put(s[0], s[1]);
				m.addVertex(s[i]);
			}
		}
		
		for(String[] s : fr.getFlights()) {
			for(int i = 0; i < s.length; i++) {
				FlightsInfo f = new FlightsInfo(s[0], airLines.get(s[1]), s[2], airLines.get(s[3]), s[4]);
				m.addEdge(s[1], s[3], f);
				m.setEdgeWeight(f, (int) Double.parseDouble(s[5]));
			}
		}

		GraphPath<String, FlightsInfo> shortestPath = DijkstraShortestPath.findPathBetween(m, "EDI", "SYD");
		System.out.println(shortestPath);
		System.out.println(shortestPath.getWeight());
		System.out.println(airLines.get("Edinburgh"));
	}
}
