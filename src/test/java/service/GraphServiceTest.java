package service;

import org.junit.Before;
import org.junit.Test;

import app.TrainInfo;
import domain.DirectedGraph;
import junit.framework.TestCase;

public class GraphServiceTest extends TestCase{
	
	public static final String[] input = {"AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7"};
	public static DirectedGraph<String> graph = new DirectedGraph<>();

	@Before
    public void setUp() {
		graph = TrainInfo.populateGraphfromInput(input);
	}
	
	@Test
	public void testGetPairs(){
		assertNull(GraphService.getPairs(""));
		assertNull(GraphService.getPairs("A"));
		assertEquals(3, GraphService.getPairs("A-B-C-D").size());
	}
	
	@Test
	public void testGetRouteDistance(){
		GraphService graphService = new GraphService();
		assertEquals("9", graphService.getRouteDistance("A-B-C", graph));
		assertEquals("NO SUCH ROUTE", graphService.getRouteDistance("A-E-D", graph));
	}
	
	@Test
	public void testFindTripsWithMaxNStops(){
		GraphService graphService = new GraphService();
		assertEquals(2, graphService.findTripsWithMaxNStops(graph, "C", "C", 3));
	}

	@Test
	public void testfindTripsWithExactlyNStops(){
		GraphService graphService = new GraphService();
		assertEquals(3, graphService.findTripsWithExactlyNStops(graph, "A", "C", 4));
	}
	
	@Test
	public void testGetShortestRoute(){
		GraphService graphService = new GraphService();
		assertEquals("9",graphService.getShortestRoute(graph, "B", "B"));
	}
	
	@Test
	public void testFindTripsWithMaxWeight(){
		GraphService graphService = new GraphService();
		assertEquals(7, graphService.findTripsWithMaxWeight(graph, "C", "C", 30));
	}
	
}
