package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import domain.DirectedGraph;
import domain.Edge;
/**
 * This class serves as a service for all functionality required for railroad company.
 * @author Rushikesh Teli
 *
 */
public class GraphService {
	
	/* Constants: Route Separator and No Such Route text*/
	public static final String SEPARATOR = "-";
	public static final String NO_SUCH_ROUTE = "NO SUCH ROUTE";
	
	/**
	 * Get all pairs (edges) for a given route journey.
	 * Example: A-B-C-D will have 3 pairs A-B, B-C & C-D
	 * 
	 * @param journey
	 * @return
	 */
	public static List<String> getPairs(String journey){
		List<String> vertices = new ArrayList<String>( Arrays.asList(journey.split(SEPARATOR)));
		List<String> paths = new ArrayList<>();
		if(vertices == null || vertices.size()<2){
			return null;
		}
		String path = "";
		for(int i=0;i<vertices.size()-1;i++){
			path = vertices.get(i) + vertices.get(i+1);
			paths.add(path);
		}
		return paths;
	}
	
	/**
	 * Returns String representation of distance.
	 * 
	 * @param journey
	 * @param graph
	 * @return
	 */
	public String getRouteDistance(String journey, DirectedGraph<String> graph){
		int totalDistance = getDistance(journey, graph);
		return totalDistance == -1 ? NO_SUCH_ROUTE:String.valueOf(totalDistance);
	}
	
	/**
	 * Returns distance for given journey.
	 * 
	 * @param journey
	 * @param graph
	 * @return
	 */
	public static int getDistance(String journey, DirectedGraph<String> graph){
		int totalDistance=0;
		List<String> paths = getPairs(journey);
		if(paths == null) return -1;
		int distance;
		for(String path : paths){
			distance = graph.getWeight(path.substring(0, 1), path.substring(1,2));
			if(distance == -1){
				totalDistance = -1;
				break;
			}else{
				totalDistance+=distance;
			}
		}
		return totalDistance;
	}
	
	/**
	 * This method is a wrapper around depthfirst search implementation.
	 * It accepted graph, source and destination and will return all
	 * distinct paths.
	 * 
	 * @param graph
	 * @param source
	 * @param destination
	 * @return
	 */
	public static List<String> findDistinctPaths(DirectedGraph<String> graph, String source, String destination){
		LinkedList<String> visited = new LinkedList<String>();
		ArrayList<String> uniquePaths = new ArrayList<>();
		uniquePaths = (ArrayList<String>) depthFirst(graph,uniquePaths, visited, source, destination);
		return uniquePaths;
	}

	/**
	 * The depth first search implementation for directed graph.
	 * 
	 * @param graph
	 * @param uniquePaths
	 * @param visited
	 * @param source
	 * @param destination
	 * @return
	 */
	public static List<String> depthFirst(DirectedGraph<String> graph, ArrayList<String> uniquePaths, LinkedList<String> visited, String source, String destination){
		LinkedList<Edge<String>> nodes = graph.getEdges(visited.size()==0 ? source : visited.getLast());
        for (Edge<String> node : nodes) {
            if (visited.contains(node.getVertex())) {
                continue;
            }
            if (node.getVertex().equals(destination)) {
                visited.add(node.getVertex());
                addPath(visited, uniquePaths, source);
                visited.removeLast();
                break;
            }
        }
        for (Edge<String> node : nodes) {
            if (visited.contains(node.getVertex()) || node.getVertex().equals(destination)) {
                continue;
            }
            visited.addLast(node.getVertex());
            depthFirst(graph, uniquePaths, visited, source, destination);
            visited.removeLast();
        }
		return uniquePaths;
	}

	/**
	 * Helper method for depth first search to build path 
	 * 
	 * @param visited
	 * @param uniquePaths
	 * @param source
	 */
	private static void addPath(LinkedList<String> visited, ArrayList<String> uniquePaths, String source) {
		StringBuffer sb = new StringBuffer();
		sb.append(source);
		sb.append(SEPARATOR);
		sb.append(String.join(SEPARATOR, visited));
		uniquePaths.add(sb.toString());
    }

	/**
	 * This method will calculate number of stops on a given journey.
	 * 
	 * @param journey
	 * @return
	 */
	public static int getNumberOfStopsOnPath(String journey){
		List<String> vertices = new ArrayList<String>( Arrays.asList(journey.split(SEPARATOR)));
		return vertices.size()-1;
	}
	
	/**
	 * This is a recursive method which will generate all possible combinations of distinct paths
	 * up to and including number of stops specified by length argument.
	 * 
	 * @param allPossiblePathsByLength
	 * @param allPaths
	 * @param destinationLoops
	 * @param length
	 * @return
	 */
	public static List<String> addAllPossiblePathsByLength(List<String> allPossiblePathsByLength, List<String> allPaths, List<String> destinationLoops, int length){
		for(String path : allPaths){
			int numOfstops = getNumberOfStopsOnPath(path);
			allPossiblePathsByLength.add(path);
			if(numOfstops <= length){
				List<String> pathsWithDestinationLoop = appendDestinationLoops(path, destinationLoops);
				addAllPossiblePathsByLength(allPossiblePathsByLength, pathsWithDestinationLoop, destinationLoops, length);
			}
		}
		return allPossiblePathsByLength;
	}

	/**
	 * This is a recursive method which will generate all possible combinations of distinct paths
	 * up to and including given weight specified by weight argument.
	 *  
	 * @param graph
	 * @param allPossiblePathsByWeight
	 * @param allPaths
	 * @param destinationLoops
	 * @param weight
	 * @return
	 */
	public static List<String> addAllPossiblePathsByWeight(DirectedGraph<String> graph, List<String> allPossiblePathsByWeight, List<String> allPaths, List<String> destinationLoops, int weight){
		for(String path : allPaths){
			int distance = getDistance(path, graph);
			if(distance <= weight){
				allPossiblePathsByWeight.add(path);
				List<String> pathsWithDestinationLoop = appendDestinationLoops(path, destinationLoops);
				addAllPossiblePathsByWeight(graph, allPossiblePathsByWeight, pathsWithDestinationLoop, destinationLoops, weight);
			}
		}
		return allPossiblePathsByWeight;
	}

	/**
	 * Helper method that returns a list of all paths generated by appending destination loops. 
	 * 
	 * @param path
	 * @param destinationLoops
	 * @return
	 */
	public static List<String> appendDestinationLoops(String path, List<String> destinationLoops){
		List<String> appendedPaths = new ArrayList<>();
		for(String destinationLoop : destinationLoops){
			appendedPaths.add(path + destinationLoop.substring(1));
		}
		return appendedPaths;
	}

	/**
	 * This method will return all paths/routes lesser than or equal to given length.
	 * 
	 * @param graph
	 * @param source
	 * @param destination
	 * @param stops
	 * @return
	 */
	private static List<String> getAllPathsByLength(DirectedGraph<String> graph, String source, String destination,
			int stops) {
		List<String> allPossiblePathsByLength = new ArrayList<>();
		List<String> distinctPaths = findDistinctPaths(graph, source, destination);
		List<String> destinationLoops = findDistinctPaths(graph, destination, destination);
		allPossiblePathsByLength = addAllPossiblePathsByLength(allPossiblePathsByLength, distinctPaths, destinationLoops, stops);
		return allPossiblePathsByLength;
	}
	
	/**
	 * This method will return all paths/routes having distance lesser than or equal to given weight.
	 * 
	 * @param graph
	 * @param source
	 * @param destination
	 * @param weight
	 * @return
	 */
	private static List<String> getAllPathsByWeight(DirectedGraph<String> graph, String source, String destination,
			int weight) {
		List<String> allPossiblePathsByWeight = new ArrayList<>();
		List<String> distinctPaths = findDistinctPaths(graph, source, destination);
		List<String> destinationLoops = findDistinctPaths(graph, destination, destination);
		allPossiblePathsByWeight = addAllPossiblePathsByWeight(graph, allPossiblePathsByWeight, distinctPaths, destinationLoops, weight);
		return allPossiblePathsByWeight;
	}	
	
	/**
	 * This method will return trips between source and destination having maximum stops passed in argument stops.
	 * 
	 * @param graph
	 * @param source
	 * @param destination
	 * @param stops
	 * @return
	 */
	public int findTripsWithMaxNStops(DirectedGraph<String> graph, String source, String destination, int stops){
		List<String> allPaths = getAllPathsByLength(graph, source, destination, stops);
		int tripsWithMaxNStops = 0;
		for(String path : allPaths){
			int numOfstops = getNumberOfStopsOnPath(path);
				if( numOfstops <= stops){
					tripsWithMaxNStops++;
				}
		}
		return tripsWithMaxNStops;
	}
	
	/**
	 * This method will return trips between source and destination having exact stops passed in argument stops.
	 * 
	 * @param graph
	 * @param source
	 * @param destination
	 * @param stops
	 * @return
	 */
	public int findTripsWithExactlyNStops(DirectedGraph<String> graph, String source, String destination, int stops){
		List<String> allPaths = getAllPathsByLength(graph, source, destination, stops);
		int tripsWithExactlyNStops = 0;
		for(String path : allPaths){
			int numOfstops = getNumberOfStopsOnPath(path);
				if( numOfstops == stops){
					tripsWithExactlyNStops++;
				}
		}
		return tripsWithExactlyNStops;
	}
	
	/**
	 * This method will return length of shortest route between source and destination.
	 * 
	 * @param graph
	 * @param source
	 * @param destination
	 * @return
	 */
	public int getLengthOfShortestRoute(DirectedGraph<String> graph, String source, String destination){
		int shortestRouteDistance = Integer.MAX_VALUE;
		List<String> distinctPaths = findDistinctPaths(graph, source, destination);
		for(String path: distinctPaths){
			int distance = getDistance(path, graph);
			if(distance < shortestRouteDistance){
				shortestRouteDistance = distance;
			}
		}
		return shortestRouteDistance;
	}
	
	/**
	 * Wrapper method for getLengthOfShortestRoute which returns No Such Route if no such route exists.
	 * 
	 * @param graph
	 * @param source
	 * @param destination
	 * @return
	 */
	public String getShortestRoute(DirectedGraph<String> graph, String source, String destination){
		int shortestDistance = getLengthOfShortestRoute(graph, source, destination);
		return shortestDistance == Integer.MAX_VALUE ? NO_SUCH_ROUTE:String.valueOf(shortestDistance);
	}
	
	/**
	 * This method will return number of routes having weight/distance less than maximum weight specified in weight argument.
	 * 
	 * @param graph
	 * @param source
	 * @param destination
	 * @param weight
	 * @return
	 */
	public int findTripsWithMaxWeight(DirectedGraph<String> graph, String source, String destination, int weight){
		List<String> allPaths = getAllPathsByWeight(graph, source, destination, weight);
		int tripsWithMaxWeight = 0;
		for(String path : allPaths){
			int routeDistance = getDistance(path, graph);
				if( routeDistance < weight){
					tripsWithMaxWeight++;
				}
		}
		return tripsWithMaxWeight;
	}
}
