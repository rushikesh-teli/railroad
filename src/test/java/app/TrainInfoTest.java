package app;

import org.junit.Test;

import domain.DirectedGraph;
import junit.framework.TestCase;

/**
 * Unit test for Traininfo App.
 */
public class TrainInfoTest extends TestCase{
    @Test
    public void testPopulateGraphfromInputEmpty(){
    	String[] args = {};
    	DirectedGraph<String> graph = TrainInfo.populateGraphfromInput(args);
    	assertNotNull(graph);
    	assertEquals(0, graph.getNeighbors().size());
    }
    
    @Test
    public void testPopulateGraphfromInputSameSourceAndDestination(){
    	String[] args = {"AA2"};
    	DirectedGraph<String> graph = TrainInfo.populateGraphfromInput(args);
    	assertNotNull(graph);
    	assertEquals(0, graph.getNeighbors().size());
    }
    
    @Test
    public void testPopulateGraphfromInputInvalidWeight(){
    	String[] args = {"AET"};
    	DirectedGraph<String> graph = TrainInfo.populateGraphfromInput(args);
    	assertNotNull(graph);
    	assertEquals(0, graph.getNeighbors().size());
    }
}
