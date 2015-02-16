import java.text.*;

import acm.graphics.GLine;
public class Edge {
	private GraphNode node1;
	private GraphNode node2;
	private GLine visRep;
	private double cost;
	
	public Edge(GraphNode n1, GraphNode n2, GLine l) {
		node1 = n1;
		node2 = n2;
		visRep = l;
		
		DecimalFormat df = new DecimalFormat("0.00");
		cost = Double.parseDouble(df.format(getDistance(n1, n2)));
	}
	public GraphNode getNode1() {
		return node1;
	}
	public GraphNode getNode2() {
		return node2;
	}
	public double getCost() {
		return cost;
	}
	public static double getDistance(GraphNode n1, GraphNode n2) {
		double a = Math.pow(n1.getX() - n2.getX(), 2) + Math.pow(n1.getY() - n2.getY(), 2); 
		return Math.sqrt(a);
	}
	public GLine getVisRep() {
		return visRep;
	}
}
