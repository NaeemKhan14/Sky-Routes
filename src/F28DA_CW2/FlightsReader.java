package F28DA_CW2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class FlightsReader {

	public static String[] AIRLINECODES = { "BA", "EK" };
	public static String[] MOREAIRLINECODES = { "AF", "BA", "CX", "CZ", "DL", "EK", "JJ", "KL", "LH", "NH", "QF", "QR",
			"TK", "UA" };

	private static final File flightsDatasetFolder = new File("FlightsDataset");
	private static final File flightsDatasetFlights = new File(flightsDatasetFolder, "flights.csv");
	private static final File flightsDatasetAirports = new File(flightsDatasetFolder, "airports.csv");
	private static final File flightsDatasetAirlines = new File(flightsDatasetFolder, "airlines.csv");

	private HashSet<String[]> airlines;
	private HashSet<String[]> airports;
	private HashSet<String[]> flights;

	/**
	 * Reads the flights dataset for a given list of airline codes (the
	 * {@code AIRLINECODES} and {@code MOREAIRLINECODES} constants are selections of
	 * airline codes available in the dataset). To use this class:
	 * <ol>
	 * <li>Make sure the provided {@code FlightsDataset} directory is
	 * accessible by your program (Eclipse: the directory needs to be in your
	 * project directory, not in {@code src})</li>
	 * <li>Create an object of the class FlightsReader by providing a list of
	 * airlines: <br>
	 * {@code FlightsReader reader = new FlightsReader(FlightsReader.AIRLINES);}</li>
	 * <li>If no exception is raised, the list of airlines, airports and flights are
	 * available using the following methods. If an exception is raised, check the
	 * location of the {@code FlightsDataset} directory. <br>
	 * <ul>
	 * <li>{@code reader.getAirlines();}</li>
	 * <li>{@code reader.getAirpors();}</li>
	 * <li>{@code reader.getFlights();}</li>
	 * </ul>
	 * </ol>
	 * This flights dataset was derived from the OpenFlights database.
	 */
	public FlightsReader(String[] includeAirlineCodes) throws FileNotFoundException, SkyRoutesException {
		this.airlines = new HashSet<String[]>();
		this.airports = new HashSet<String[]>();
		this.flights = new HashSet<String[]>();

		// Adding airlines
		HashSet<String> airlinesNeeded = new HashSet<String>();
		for (String a : includeAirlineCodes) {
			airlinesNeeded.add(a);
		}
		HashSet<String> airlinesAvailable = new HashSet<String>();
		this.airlines = new HashSet<String[]>();
		Scanner airlinesScanner = new Scanner(flightsDatasetAirlines);
		while (airlinesScanner.hasNextLine()) {
			String line = airlinesScanner.nextLine();
			String[] fields = line.split(",");
			String airlineCode = fields[0];
			boolean contained = airlinesNeeded.remove(airlineCode);
			if (contained) {
				airlinesAvailable.add(airlineCode);
				this.airlines.add(fields);
			}
		}
		airlinesScanner.close();
		if (!airlinesNeeded.isEmpty()) {
			throw new SkyRoutesException("Missing airline code(s): " + airlinesNeeded.toString());
		}

		// Adding flights, and building list of needed airport
		HashSet<String> airportsNeeded = new HashSet<String>();
		Scanner flightsScanner = new Scanner(flightsDatasetFlights);
		while (flightsScanner.hasNextLine()) {
			String line = flightsScanner.nextLine();
			String[] fields = line.split(",");
			String flightCode = fields[0];
			String airlineCode = flightCode.substring(0, 2);
			if (airlinesAvailable.contains(airlineCode)) {
				String from = fields[1];
				String to = fields[3];
				airportsNeeded.add(from);
				airportsNeeded.add(to);
				this.flights.add(fields);
			}
		}
		flightsScanner.close();

		// Adding airports
		Scanner airportsScanner = new Scanner(flightsDatasetAirports);
		while (airportsScanner.hasNextLine()) {
			String line = airportsScanner.nextLine();
			String[] fields = line.split(",");
			if (airportsNeeded.contains(fields[0])) {
				this.airports.add(fields);
			}
		}
		airportsScanner.close();
	}

	/**
	 * Returns a hash set of airline details (0: airline code, 1: airline name, 2:
	 * airline country)
	 */
	public HashSet<String[]> getAirlines() {
		return this.airlines;
	}

	/**
	 * Returns a hash set of airport details (0: airport code, 1: city, 2: airport
	 * name)
	 */
	public HashSet<String[]> getAirports() {
		return this.airports;
	}

	/**
	 * Returns a hash set of flight details (0: flight code, 1: airline code, 2:
	 * airport code of departure, 3: departure time GMT, 4: airport code of arrival,
	 * 5: arrival time GMT, 6: flight cost)
	 */
	public HashSet<String[]> getFlights() {
		return this.flights;
	}

}