package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import domain.DirectedGraph;
import service.GraphService;

/**
 * This is a main class, which calls services to get required information. 
 * 
 * @author Rushikesh Teli.
 *
 */
public class TrainInfo {

    public static void main (String[] args) {
        DirectedGraph<String> graph = populateGraphfromInput(args);
  
        //System.out.println("The current graph: " + graph);
        GraphService graphService = new GraphService();
        System.out.println("Output #1: " + graphService.getRouteDistance("A-B-C", graph));
        System.out.println("Output #2: " + graphService.getRouteDistance("A-D", graph));
        System.out.println("Output #3: " + graphService.getRouteDistance("A-D-C", graph));
        System.out.println("Output #4: " + graphService.getRouteDistance("A-E-B-C-D", graph));
        System.out.println("Output #5: " + graphService.getRouteDistance("A-E-D", graph));
        System.out.println("Output #6: " + graphService.findTripsWithMaxNStops(graph, "C", "C", 3));
        System.out.println("Output #7: " + graphService.findTripsWithExactlyNStops(graph, "A", "C", 4));
        System.out.println("Output #8: " + graphService.getShortestRoute(graph, "A", "C"));
        System.out.println("Output #9: " + graphService.getShortestRoute(graph, "B", "B"));
        System.out.println("Output #9: " + graphService.findTripsWithMaxWeight(graph, "C", "C", 30));
    }
    
    /**
     * This method parses and validates command line input and exits if input is invalid else
     * populates the graph and return it.
     * 
     * @param args
     * @return
     */
    public static DirectedGraph<String> populateGraphfromInput(String[] args){
    	DirectedGraph<String> graph = new DirectedGraph<String>();
    	final String PATHSEPARATOR = ",";
    	if(args == null || args.length < 1){
    		System.out.println("No Directed graph input ! Returning Empy graph ....");
    		System.out.println("Usage :: java TrainInfo AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
    		return graph;
    	}
    	
    	StringBuffer sb = new StringBuffer();
    	for(int i=0;i<args.length;i++){
    		sb.append(args[i]);
    	}
    	
    	//System.out.println("Directed Graph Input :: " + sb.toString());
    	List<String> paths = new ArrayList<String>( Arrays.asList(sb.toString().split(PATHSEPARATOR)));
    	Set<String> uniquePaths = new HashSet<>();
    	String source, destination;
    	int weight;
    	for(String path : paths){
    		if(!uniquePaths.contains(path) && path.length() ==3){
    			uniquePaths.add(path);
    			source = path.substring(0, 1);
    			destination = path.substring(1, 2);
    			try{
    				weight = Integer.valueOf(path.substring(2));
    			}catch(NumberFormatException nfex){
    				weight = -1;
    			}
    			if(source.equals(destination) || weight == -1){
    				System.out.println("Invalid Input :: " + path);
    				graph = new DirectedGraph<String>();
    				break;
    			}
    			graph.add(source, destination, weight);
    		}else{
    			System.out.println("Invalid Input, Duplicate if format is valid :: " + path);
    			graph = new DirectedGraph<String>();
    		}
    	}
    	return graph;
    }
}
