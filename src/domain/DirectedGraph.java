package domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * A weighted directed graph data structure to hold the input data and store in graph format.
 * @author Rushikesh Teli
 *
 * @param <V>
 */
public class DirectedGraph<V> {

	/**
     * Adjacency list implementation, 
     * Map is used to map each vertex to its list of adjacent vertices.
     */   
    private Map<V,Set<Edge<V>>> neighbors = new HashMap<V,Set<Edge<V>>>();
    
    /**
     * String representation of graph.
     */
    public String toString () {
        StringBuffer s = new StringBuffer();
        for (V v: neighbors.keySet()) s.append("\n    " + v + " -> " + neighbors.get(v));
        return s.toString();                
    }
    
    /**
     * Add a vertex to the graph.  Nothing happens if vertex is already in graph.
     */
    public void add (V vertex) {
        if (neighbors.containsKey(vertex)) return;
        neighbors.put(vertex, new HashSet<Edge<V>>());
    }
    
    /**
     * True iff graph contains vertex.
     */
    public boolean contains (V vertex) {
        return neighbors.containsKey(vertex);
    }
    
    /**
     * Add an edge to the graph; 
     * if either vertex does not exist, it's added.
     * This implementation allows the creation of multi-edges and self-loops.
     */
    public void add (V from, V to, int weight) {
        this.add(from); 
        this.add(to);
        neighbors.get(from).add(new Edge<V>(to, weight));
    }
    
    public int getWeight(V from, V to) {
    	try{
	        for(Edge<V> e :  neighbors.get(from)){
	            if(e.getVertex().equals(to))
	                return e.getWeight();
	        }
    	}catch(Exception ex){
    		return -1;
    	}
        return -1;
    }
    
    public LinkedList<Edge<V>> getEdges(V vertex) {
        HashSet<Edge<V>> adjacent = (HashSet<Edge<V>>) neighbors.get(vertex);
        if(adjacent==null) {
            return new LinkedList<Edge<V>>();
        }
        return new LinkedList<Edge<V>>(adjacent);
    }
    
	public Map<V, Set<Edge<V>>> getNeighbors() {
		return neighbors;
	}
}