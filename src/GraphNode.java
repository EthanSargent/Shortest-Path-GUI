import java.util.*;
import acm.graphics.GOval;
public class GraphNode {

	private int x, y;
	private ArrayList<GraphNode> neighbors;
	private ArrayList<Double> neighborCosts;
	private ArrayList<Edge> neighborEdges;
	private GOval visRep;
	
	public GraphNode(int X, int Y, GOval vR) {
		x = X;
		y = Y;
		visRep = vR;
		
		neighbors = new ArrayList<GraphNode>();
		neighborCosts = new ArrayList<Double>();
		neighborEdges = new ArrayList<Edge>();
	}
	public boolean equals(GraphNode other) {
		if (other.getX() == x && other.getY() == y) {
			return true;
		}
		return false;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public GOval getVisRep() {
		return visRep;
	}
	
	public void addNeighbor(GraphNode g) {
		neighbors.add(g);
	}
	public ArrayList<GraphNode> getNeighbors() {
		return neighbors;
	}
	public GraphNode getNeighbor(int i) {
		return neighbors.get(i);
	}
	
	public void addNeighborCost(Double d) {
		neighborCosts.add(d);
	}
	public ArrayList<Double> getNeighborCosts() {
		return neighborCosts;
	}
	public double getNeighborCost(int i) {
		return neighborCosts.get(i);
	}
	
	public void addNeighborEdge(Edge e) {
		neighborEdges.add(e);
	}
	public ArrayList<Edge> getNeighborEdges() {
		return neighborEdges;
	}
	public Edge getNeighborEdge(int i) {
		return neighborEdges.get(i);
	}
	
}
