package domain;

import org.junit.Test;

import junit.framework.TestCase;

public class DirectedGraphTest extends TestCase{
	
	@Test
	public void testDirectedgraphNeighbours(){
		DirectedGraph<String> graph = new DirectedGraph<>();
		graph.add("A", "B", 7);
		assertEquals(2, graph.getNeighbors().size());
		assertEquals(1, graph.getNeighbors().get("A").size());
		assertEquals(0, graph.getNeighbors().get("B").size());
		assertEquals(1, graph.getEdges("A").size());
		assertEquals(7, graph.getWeight("A", "B"));
	}

}
