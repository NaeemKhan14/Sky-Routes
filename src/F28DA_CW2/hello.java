package F28DA_CW2;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class hello {
	
	public static void main(String[] args) throws FileNotFoundException, SkyRoutesException {
		
		FlightsReader f = new FlightsReader(FlightsReader.AIRLINECODES);

		SimpleDirectedWeightedGraph<String, FlightsInfo> s = new SimpleDirectedWeightedGraph<String, FlightsInfo>(FlightsInfo.class);
		
		for(String[] airports : f.getAirports()) {
			s.addVertex(airports[0]);
		
		}
		
		for(String[] flightsInfo : f.getFlights()) {
			s.addEdge(flightsInfo[1], flightsInfo[3], new FlightsInfo(flightsInfo[0], flightsInfo[1], flightsInfo[2], flightsInfo[3], flightsInfo[4]));
			s.setEdgeWeight(s.getEdge(flightsInfo[1], flightsInfo[3]), Integer.parseInt(flightsInfo[5]));
		}
		
		DijkstraShortestPath<String, FlightsInfo> d = new DijkstraShortestPath<>(s);
//		List<String> l = new ArrayList<String>();
//		l.add("IST");
//		l.add("SIN");
//		
//		System.out.println(s.removeAllEdges(s.edgesOf("IST")));
		GraphPath<String, FlightsInfo> g = d.getPath("EDI", "DXB");
		System.out.println((int) g.getWeight());
		
	}
}
