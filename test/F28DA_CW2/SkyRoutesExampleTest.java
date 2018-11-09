package F28DA_CW2;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

public class SkyRoutesExampleTest {

	IRoutes fi = new SkyRoutes();
	FlightsReader fr;

	@Before public void initialize() {
		try {
			fr = new FlightsReader(FlightsReader.AIRLINECODES);
			fi.populate(fr.getAirlines(), fr.getAirports(), fr.getFlights());
		} catch (FileNotFoundException | SkyRoutesException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void test1() {
		try {
			IRoute i = fi.leastCost("EDI", "DXB");
			assertEquals(2,i.totalHop());
			assertEquals(364,i.totalCost());
		} catch (SkyRoutesException e) {
			fail();
		}
	}

}
