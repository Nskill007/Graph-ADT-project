import java.util.ArrayList;

public interface GraphADT {

    //GENERAL METHODS

    //checks whether graph is empty
    public abstract boolean isEmpty();

    //returns number of nodes in the graph
    public abstract int numNodes();

    //returns number of edges in the graph
    public abstract int numEdges();

    //inserts a node into the graph with given value
    public abstract void insertNode(Integer value);

    //removes a node from the graph with given a value
    public abstract void removeNode(Integer value);

    //takes a and returns if it exists
    public abstract boolean hasNode(Integer node1);

    //takes two nodes and returns if an edge exists between them
    public abstract boolean hasEdge(Integer node1, Integer node2);

    //takes two nodes and returns the weight of the edge between them
    public abstract int getWeight(Integer node1, Integer node2);

    //takes two nodes and a weight then inserts an edge between them (our graph is static so I don't think we need this)
    public abstract void insertEdge(int node1, int node2, int weight);

    //takes two nodes and removes an edge between them if they exist (our graph is static so I don't think we need this)
    public abstract void removeEdge(int node1, int node2);

    //returns the number of nodes connected to given node
    public abstract int getDegree(Integer node);

    //returns a list of nodes connected to given node
    public abstract ArrayList<Integer> getAdjacent(Integer node);

    //returns the node that is closest to given node
    public abstract int getClosest(Integer node);

    //GRAPH SEARCHING METHODS???

    //returns the shortest path of nodes between two given nodes
    public abstract ArrayList<Integer> findShortestPath(Integer node1, Integer node2);

    //returns the paths of nodes between two given nodes
    public abstract ArrayList<Integer> findAllPaths(Integer node1, Integer node2);
}
