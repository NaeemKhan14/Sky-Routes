package F28DA_CW2;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SkyRoutesTest {
	
	IRoutes fi = new SkyRoutes();
	FlightsReader fr;

	@Before public void initialize() {
		try {
			fr = new FlightsReader(FlightsReader.MOREAIRLINECODES);
			fi.populate(fr.getAirlines(), fr.getAirports(), fr.getFlights());
		} catch (FileNotFoundException | SkyRoutesException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void leastCostTest() {
		try {
			IRoute i = fi.leastCost("EDI", "DXB");
			assertEquals(407, i.airTime());
			assertEquals(119, i.connectingTime());
			assertEquals(526, i.totalTime());
			assertEquals(2, i.totalHop());
			assertEquals(363, i.totalCost());
			assertTrue(i.getFlights().contains("LH1662"));
			assertTrue(i.getFlights().contains("QF0873"));

			IRoute j = fi.leastCost("DXB", "EDI");
			assertEquals(368, j.airTime());
			assertEquals(100, j.connectingTime());
			assertEquals(468, j.totalTime());
			assertEquals(2, j.totalHop());
			assertEquals(357, j.totalCost());
			assertTrue(j.getFlights().contains("BA6172"));
			assertTrue(j.getFlights().contains("BA6327"));
		} catch (SkyRoutesException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void leastCostExclusionTest() {
		
		ArrayList<String> exclusionList = new ArrayList<String>();
		
		exclusionList.add("FRA");
		exclusionList.add("LHR");
		exclusionList.add("IST");
		
		try {
			IRoute i = fi.leastCost("EDI", "DXB", exclusionList);
			assertEquals(381, i.airTime());
			assertEquals(1827, i.connectingTime());
			assertEquals(2208, i.totalTime());
			assertEquals(3, i.totalHop());
			assertEquals(387, i.totalCost());
			assertFalse(i.getStops().contains("FRA"));
			assertFalse(i.getStops().contains("IST"));
			assertFalse(i.getStops().contains("LHR"));
			
		} catch (SkyRoutesException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void leastHopTest() {
		try {
			IRoute i = fi.leastHop("DXB", "MAD");
			IRoute j = fi.leastHop("EDI", "SYD");
			assertEquals(1, i.totalHop());
			assertEquals(3, j.totalHop());
		} catch (SkyRoutesException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void leastHopExclusionTest() {
		
		List<String> exclusionList = new ArrayList<String>();
		exclusionList.add("CDG");
		exclusionList.add("SCL");
		
		try {
			IRoute i = fi.leastCost("EDI", "SYD", exclusionList);
			assertEquals(3, i.totalHop());
			assertFalse(i.getStops().contains("SCL"));
			assertFalse(i.getStops().contains("CDG"));
		} catch (SkyRoutesException e) {
			e.printStackTrace();
		}
		
	}

}
