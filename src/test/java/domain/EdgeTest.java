package domain;

import org.junit.Test;

import junit.framework.TestCase;

public class EdgeTest extends TestCase{
	
	@Test
	public void testEdge(){
		Edge<String> edge = new Edge<>("A", 6);
		assertEquals(6, edge.getWeight());
		assertEquals("A", edge.getVertex());
	}

}
