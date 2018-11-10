package F28DA_CW2;

import java.io.FileNotFoundException;
import java.util.HashSet;

public class hello {
	public static void a() throws FileNotFoundException, SkyRoutesException {
		FlightsReader f = new FlightsReader(FlightsReader.MOREAIRLINECODES);
		
		HashSet<String[]> airlines = f.getAirlines();
		String result = "";
		
		for(String[] s : airlines) {
			for(int i = 0; i < s.length; i++) {
				result += s[i] + " ";
			}
			result += "\n";
		}
		
		//System.out.println(result);
		
		HashSet<String[]> airports = f.getAirports();
		String resultAirports = "";
		
		for(String[] s : airports) {
			for(int i = 0; i < s.length; i++) {
				resultAirports += s[i] + " ";
			}
			resultAirports += "\n";
		}
		//System.out.println(resultAirports);
		
		HashSet<String[]> flights = f.getFlights();
		String resultFlights = "";
		
		for(String[] s : flights) {
			for(int i = 0; i < s.length; i++) {
				resultFlights += s[i] + " ";
			}
			resultFlights += "\n";
		}
		System.out.println(resultFlights);
		
	}
	public static void main(String[] args) throws FileNotFoundException, SkyRoutesException {
		a();
	}
}
