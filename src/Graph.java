import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.*;

import javax.swing.JButton;

import acm.graphics.*;
import acm.program.*;

public class Graph extends GraphicsProgram {
    private static final String BUTTON_1_TEXT = "EDIT MODE";
    private static final String BUTTON_2_TEXT = "MAKE NODES";
    private static final String BUTTON_3_TEXT = "MAKE EDGES";
    private static final String BUTTON_4_TEXT = "PICK START NODE";
    private static final String BUTTON_5_TEXT = "PICK DESTINATION NODE";
    private static final String BUTTON_6_TEXT = "CALCULATE MY PATH!";
    
    private static final long serialVersionUID = 0;
    private static boolean edit = false;
    private static boolean makingNodes = false;
    private static boolean makingEdges = false;
    private static boolean pickingStart = false;
    private static boolean pickingDestination = false;

    private static final GLabel editLabel = new GLabel("EDIT MODE", 20,50);
    private static final GLabel nodeLabel = new GLabel("MAKING NODES", 20,80);
    private static final GLabel edgeLabel = new GLabel("MAKING EDGES", 20, 80);
    private static final GLabel startLabel = new GLabel("PICK A START NODE", 20, 80);
    private static final GLabel destLabel = new GLabel("PICK A DESTINATION NODE", 20, 80);
    private static GLabel doneLabel;
    private static ArrayList<GraphNode> nodes = new ArrayList<GraphNode>();
    private static ArrayList<Edge> edges = new ArrayList<Edge>();

    private static int start = -1;
    private static int end = -1;
    
    private static GraphNode startNode;
    private static GraphNode destinationNode;

    public void mouseClicked(MouseEvent e) {
        int x1 = e.getX();
        int y1 = e.getY();

        if (pickingStart) {
        	for (int i = 0; i < nodes.size(); i++) {
        		double d = distance(x1, nodes.get(i).getX(), y1, nodes.get(i).getY());
        		if (d < 10) {
        			for (int k = 0; k < nodes.size(); k++) {
        				nodes.get(k).getVisRep().setColor(Color.BLUE);
        				try {
    						destinationNode.getVisRep().setColor(Color.RED);
    					}
    					catch (NullPointerException exc) {}
        			}
        			startNode = nodes.get(i);
        			startNode.getVisRep().setColor(Color.BLACK);
        		}
        	}
        }
        else if (pickingDestination) {
        	for (int i = 0; i < nodes.size(); i++) {
        		double d = distance(x1, nodes.get(i).getX(), y1, nodes.get(i).getY());
        		if (d < 10) {
        			for (int k = 0; k < nodes.size(); k++) {
        					nodes.get(k).getVisRep().setColor(Color.BLUE);
        					try {
        						startNode.getVisRep().setColor(Color.BLACK);
        					}
        					catch (NullPointerException exc) {}
        			}
        			destinationNode = nodes.get(i);
        			destinationNode.getVisRep().setColor(Color.RED);
        		}
        	}
        }
        else if (makingNodes) {
        	GOval myOval = new GOval(e.getX(), e.getY(), 10, 10);
            myOval.setColor(Color.BLUE);
            myOval.setFilled(true);
            add(myOval);
            
            nodes.add(new GraphNode(e.getX(), e.getY(), myOval));
            
        }
        if (makingEdges) {
            if (start == -1) {
                double d;
                for (int i = 0; i < nodes.size(); i++) {
                    d = distance(x1, nodes.get(i).getX(), y1, nodes.get(i).getY());
                    if (d < 10) {
                        start = i;
                        break;
                    }
                }
            }
            else {
                int x0 = nodes.get(start).getX();
                int y0 = nodes.get(start).getY();
                double d;
                for (int i = 0; i < nodes.size(); i++) {
                    d = distance(x1, nodes.get(i).getX(), y1, nodes.get(i).getY());
                    if (d < 10) {
                        if (i != start) {
                            end = i;
                            
                            GraphNode aNode = nodes.get(start);
                            GraphNode bNode = nodes.get(end);
                            
                            GLine line = new GLine(aNode.getX(),aNode.getY(),bNode.getX(),bNode.getY());
                            line.setColor(Color.BLUE);
                            line.setVisible(true);
                            add(line);
                            
                            Edge thisEdge = new Edge(nodes.get(start), nodes.get(end), line);
                            edges.add(thisEdge);
                            double val = thisEdge.getCost();
                            
                            GLabel length = new GLabel(""+val, (x0 + x1)/2, (y0 + y1)/2);
                            length.setColor(Color.BLACK);
                            length.setFont(new Font("Times New Roman", 20, 20));
                            
                            (nodes.get(end)).addNeighbor(nodes.get(start));
                            (nodes.get(end)).addNeighborCost(val);
                            (nodes.get(end)).addNeighborEdge(thisEdge);
                            
                            (nodes.get(start)).addNeighbor(nodes.get(i));
                            (nodes.get(start)).addNeighborCost(val);
                            (nodes.get(start)).addNeighborEdge(thisEdge);
                            
                            add(length);
                        }
                    }
                }
                start = -1;
                end = -1;
            }
        }
    }
    public double distance(int x1, int x2, int y1, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
    public void configLabels() {
        editLabel.setVisible(edit);
        nodeLabel.setVisible(makingNodes);
        edgeLabel.setVisible(makingEdges);
        startLabel.setVisible(pickingStart);
        destLabel.setVisible(pickingDestination);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(BUTTON_1_TEXT)) {
            edit = !edit;
            if (!edit) {
                makingNodes = false;
                makingEdges = false;
                pickingStart = false;
                pickingDestination = false;
            }
            start = -1;
            end = -1;
            configLabels();
        }
        else if (e.getActionCommand().equals(BUTTON_2_TEXT)) {
            if (edit) {
                makingNodes = !makingNodes;
                makingEdges = false;
                pickingStart = false;
                pickingDestination = false;
                configLabels();
            }
        }
        else if (e.getActionCommand().equals(BUTTON_3_TEXT)) {
            if (edit) {
                makingEdges = !makingEdges;
                makingNodes = false;
                pickingStart = false;
                pickingDestination = false;
                configLabels();
            }
        }
        else if (e.getActionCommand().equals(BUTTON_4_TEXT)) {
        	if (edit) {
        		pickingStart = !pickingStart;
        		makingNodes = false;
        		makingEdges = false;
        		pickingDestination = false;
        		configLabels();
        	}
        }
        else if (e.getActionCommand().equals(BUTTON_5_TEXT)) {
        	if (edit) {
        		pickingDestination = !pickingDestination;
        		makingNodes = false;
        		makingEdges = false;
        		pickingStart = false;
        		configLabels();
        	}
        }
        else if (e.getActionCommand().equals(BUTTON_6_TEXT)) {
        	if (edit) {
        		edit = false;
        		makingNodes = false;
        		makingEdges = false;
        		pickingStart = false;
        		pickingDestination = false;
        		configLabels();
        	}
        	try {
        		double d = (new PathFinder()).computeShortestPath(startNode, destinationNode);
        		doneLabel = new GLabel("FINAL DISTANCE: " + (new DecimalFormat("0.00").format(d)), 250, 50);
        		doneLabel.setFont(new Font("TIMES NEW ROMAN", 100, 30));
        		doneLabel.setColor(Color.BLACK);
        		add(doneLabel);
        		
        	}
        	catch (NullPointerException exc) {
        		System.out.println("Pick a start and end node first!");
        	}
        }
    }

    public void init() {
        setSize(800,800);
        setTitle("Graphics Practice!");
        addMouseListeners();

        JButton button1 = new JButton(BUTTON_1_TEXT);
        button1.addActionListener(this);
        add(button1, SOUTH);

        JButton button2 = new JButton(BUTTON_2_TEXT);
        button2.addActionListener(this);
        add(button2, SOUTH);

        JButton button3 = new JButton(BUTTON_3_TEXT);
        button3.addActionListener(this);
        add(button3, SOUTH);
        
        JButton button4 = new JButton(BUTTON_4_TEXT);
        button4.addActionListener(this);
        add(button4, SOUTH);
        
        JButton button5 = new JButton(BUTTON_5_TEXT);
        button5.addActionListener(this);
        add(button5, SOUTH);
        
        JButton button6 = new JButton(BUTTON_6_TEXT);
        button6.addActionListener(this);
        add(button6, NORTH);

        editLabel.setColor(Color.RED);
        editLabel.setFont(new Font("Times New Roman", 100, 30));
        add(editLabel);

        nodeLabel.setColor(Color.RED);
        nodeLabel.setFont(new Font("Times New Roman", 100, 30));
        add(nodeLabel);

        edgeLabel.setColor(Color.RED);
        edgeLabel.setFont(new Font("Times New Roman", 100, 30));
        add(edgeLabel);
        
        startLabel.setColor(Color.RED);
        startLabel.setFont(new Font("Times New Roman", 100, 30));
        add(startLabel);
        
        destLabel.setColor(Color.RED);
        destLabel.setFont(new Font("Times New Roman", 100, 30));
        add(destLabel);

        editLabel.setVisible(false);
        nodeLabel.setVisible(false);
        edgeLabel.setVisible(false);
        startLabel.setVisible(false);
        destLabel.setVisible(false);
    }

    public void run() {
    	
    }
}