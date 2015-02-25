import java.awt.Color;
import java.util.*;

public class PathFinder {

	private static final String algSelection = "Djikstra";
	private static ArrayList<GraphNode> visited;

	public PathFinder() {
		visited = new ArrayList<GraphNode>();
	}
	public double computeShortestPath(GraphNode start, GraphNode end, ArrayList<GraphNode> nodes) {
		switch (algSelection) {
		case ("A*"):
			return A_star(start, end);
		case("Djikstra"):
			return Djikstra(start, end);
		default:
			return 0;
		}
	}
	public double A_star(GraphNode current, GraphNode end) {
		return A_starCalc(current, end, current, 0);
	}
	/*A_starCalc WILL GET STUCK for unclosed graphs
	 * TODO: unstuck component
	 */
	public double A_starCalc(GraphNode current, GraphNode end, GraphNode lastVisited, double dTraveled) {
		visited.add(current);
		if (current.equals(end)) {
			current.getVisRep().setColor(Color.RED);
			return dTraveled;
		}
		else {
			int bestIndex = 0;
			double bestDistance = Double.MAX_VALUE;
			double cHStat;
			GraphNode bestChoice = lastVisited;
			for (int i = 0; i < current.getNeighbors().size(); i++) {
				if (current.getNeighbor(i).equals(lastVisited)) {
					continue;
				}
				else {
					cHStat = current.getNeighborCosts().get(i) + getDistance(current.getNeighbor(i), end);
					if (cHStat < bestDistance) {
						bestDistance = cHStat;
						bestIndex = i;
					}
				}
			}
			bestChoice = current.getNeighbor(bestIndex);
			/*
			 * Recolor the path
			 */
			current.getNeighborEdge(bestIndex).getVisRep().setColor(Color.RED);
			current.getVisRep().setColor(Color.RED);
			return A_starCalc(bestChoice, end, current, dTraveled + bestDistance);
		}
	}
	public double Djikstra(GraphNode current, GraphNode end) {
		current.setTentativeDistance(0);
		return Djikstra_Search(current, end);
	}
	public double Djikstra_Search(GraphNode current, GraphNode end) {
		if (current.equals(end)) {
			return current.getTentativeDistance();
		}
		else {
			int bestChoice = 0;
			for (int i = 0; i < current.getNeighbors().size(); i++) {
				if (current.getNeighborCost(i) + current.getTentativeDistance() < current.getNeighbor(i).getTentativeDistance()
					&& current.getNeighbor(i).visited == false) {
					current.getNeighbor(i).setTentativeDistance(current.getNeighborCost(i) + current.getTentativeDistance());
				}
				if (current.getNeighborCost(i) < current.getNeighborCost(bestChoice) && current.getNeighbor(i).visited == false) {
					bestChoice = i;
				}
			}
			current.setVisited(true);
			return Djikstra_Search(current.getNeighbor(bestChoice), end);
		}
	}
	public static double getDistance(GraphNode n1, GraphNode n2) {
		double a = Math.pow(n1.getX() - n2.getX(), 2) + Math.pow(n1.getY() - n2.getY(), 2); 
		return Math.sqrt(a);
	}
}
