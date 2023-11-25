import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FullGraph implements GraphADT {

    // custom edge data type so we can store it in one array

    class Edge {
        public int node1;
        public int node2;
        public int weight;

        public Edge(int node1, int node2, int weight){
            this.node1 = node1;
            this.node2 = node2;
            this.weight = weight;
        }
        public void display(){
            System.out.println("Node1: " + node1 + ", Node2: " + node2 + ", Weight: " + weight);
        }
    }

    //fields
    private ArrayList<Integer> nodes;
    private ArrayList<Edge> edges;

    public FullGraph(){
        nodes = new ArrayList<Integer>();
        edges = new ArrayList<Edge>();
    }

    // added helper methods

    // will read file and add data into nodes and edges arrays
    public void populateGraph() throws FileNotFoundException {
        boolean node1Found = false;
        boolean node2Found = false;
        try {
            // create file and filereader
            File graphFile = new File("src/main/resources/graph.txt");
            Scanner scanner = new Scanner(graphFile);
            // for each line, split data into segments and add to corresponding array (file is in "1 1 {weight: 1}" format so will need to use split)
            while(scanner.hasNext()) {
                String data = scanner.nextLine();
                // splits line by space to get each element of edge data
                String[] split = data.split("\\s+");
                int node1 = Integer.parseInt(split[0]);
                int node2 = Integer.parseInt(split[1]);
                // removing annoying bracket from end of line
                int weight = Integer.parseInt(split[3].substring(0, split[3].length() - 1));
                // create new Edge with this data and add to edge array
                insertEdge(node1, node2, weight);
                // add to nodes array if node1 or node2 value isn't already in nodes array
                if(!hasNode(node1)){
                    insertNode(node1);
                }
                if(!hasNode(node2)){
                    insertNode(node2);
                }
            }
            Collections.sort(nodes);
            scanner.close();
        } catch (FileNotFoundException e){
            System.out.println("File not found or has been corrupted");
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("Error reading graph file");
            e.printStackTrace();
        }
    }

    public void displayGraph(){
        for (Edge x : edges) {
            x.display();
        }
    }

    public static FullGraph generateLevel(FullGraph graph){
        FullGraph level = new FullGraph();
        ArrayList<Edge> graphEdges = graph.getEdges();

        Random random = new Random();
        // generate random number from 0 to 49
        Integer currentNode = random.nextInt(50);
        level.insertNode(currentNode);

        Integer nextNode;
        ArrayList<Integer> adjacent = graph.getAdjacent(currentNode);

        while(level.getEdges().size() < 12) {
            currentNode = level.getNodes().get(random.nextInt(level.getNodes().size()));
            adjacent = graph.getAdjacent(currentNode);
            nextNode = adjacent.get(random.nextInt(adjacent.size()));
            //System.out.println(adjacent);
            //System.out.println(nextNode);
            while(!graph.hasEdge(level.getNodes().get(random.nextInt(level.getNodes().size())), nextNode)) {
                nextNode = adjacent.get(random.nextInt(adjacent.size()));
//                    System.out.println(level.getNodes().get(i));
//                    System.out.println(nextNode);
//                    System.out.println("not contained");
            }
            if (level.hasNode(nextNode)) {
                continue;
            }
//                System.out.println(level.getNodes().get(i));
//                System.out.println(nextNode);
//                System.out.println("contained");
            level.insertNode(nextNode);
            level.insertEdge(currentNode, nextNode, graph.getWeight(currentNode, nextNode));
            //System.out.println(level.getNodes());
        }

        System.out.println("finished level: " + level.getNodes());



        for(int i = 0; i < level.getNodes().size()-1; i++) {
            for (int j = i+1; j < level.getNodes().size(); j++) {
                Integer current = level.getNodes().get(i);
                Integer next = level.getNodes().get(j);
                if (graph.hasEdge(current, next) && !level.hasEdge(current, next)) {
                    level.insertEdge(current, next, graph.getWeight(current, next));
                }
            }
        }

        return level;
    }

    public ArrayList<Integer> getStartEndNodes(){
        ArrayList<Integer> startEnd = new ArrayList<Integer>();
        int start = -1;
        int end = -1;
        Random random = new Random();
        start = random.nextInt(12);
        int tempEnd = random.nextInt(12);
        while(tempEnd == start) {
            tempEnd = random.nextInt(12);
        }
        start = nodes.get(start);
        end = nodes.get(tempEnd);
        startEnd.add(start);
        startEnd.add(end);

        return startEnd;
    }

    public ArrayList<Integer> getNodes(){
        return nodes;
    }

    public ArrayList<Edge> getEdges(){
        return edges;
    }

    // inherited methods

    @Override
    public boolean isEmpty() {
        return nodes.size() == 0;
    }

    @Override
    public int numNodes() {
        return nodes.size();
    }

    @Override
    public int numEdges() {
        return edges.size();
    }

    @Override
    public void insertNode(Integer value) {
        nodes.add(value);
    }

    public int getWeight(Integer node1, Integer node2){
        for(Edge x : edges){
            if(node1 == x.node1 && node2 == x.node2 || node1 == x.node2 && node2 == x.node1){
                return x.weight;
            }
        }
        return -1;
    }

    @Override
    public void removeNode(Integer value) {
        int index = 0;
        for (int x : nodes) {
            if(x == value){
                nodes.remove(index);
            }
            index++;
        }
    }

    @Override
    public boolean hasEdge(Integer node1, Integer node2) {
        for (Edge x : edges) {
            if(x.node1 == node1 && x.node2 == node2 || x.node1 == node2 && x.node2 == node1){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasNode(Integer node1){
        boolean nodeFound = false;
        for (int x : nodes) {
            if (x == node1) {
                nodeFound = true;
            }
        }
        return nodeFound;
    }

    @Override
    public void insertEdge(int node1, int node2, int weight) {
        Edge edge = new Edge(node1, node2, weight);
        edges.add(edge);
    }

    @Override
    public void removeEdge(int node1, int node2) {
        int index = 0;
        for (Edge x : edges) {
            if(x.node1 == node1 && x.node2 == node2 || x.node1 == node2 && x.node2 == node1){
                edges.remove(index);
            }
        }
    }

    @Override
    public int getDegree(Integer node) {
        int degreeCount = 0;
        for (Edge x : edges) {
            if(x.node1 == node || x.node2 == node){
                degreeCount++;
            }
        }
        return degreeCount;
    }

    public int getClosest(Integer node){
        int lowestPath = Integer.MAX_VALUE;
        int lowestNode = -1;
        for (Edge x : edges) {
            if(x.node1 == node){
                if(x.weight < lowestPath){
                    lowestPath = x.weight;
                    lowestNode = x.node2;
                }
            }
            if(x.node2 == node){
                if(x.weight < lowestPath){
                    lowestPath = x.weight;
                    lowestNode = x.node1;
                }
            }
        }
        // will return -1 if no adjacent node is found
        return lowestNode;
    }


    @Override
    public ArrayList<Integer> getAdjacent(Integer node) {
        ArrayList<Integer> adjacentNodes = new ArrayList<Integer>();
        for (Edge x : edges) {
            if(x.node1 == node){
                adjacentNodes.add(x.node2);
            }
            if(x.node2 == node){
                adjacentNodes.add(x.node1);
            }
        }
        return adjacentNodes;
    }


    @Override
    public ArrayList<Integer> findAllPaths(Integer node1, Integer node2) {
        // array used to hold distances from root node
        int[] shortestDistanceFromRoot = new int[numNodes()];
        // array used to tell if node has been expanded
        boolean[] visited = new boolean[numNodes()];
        int[] previousVertex = new int[numNodes()];
        ArrayList<Integer> paths = new ArrayList<Integer>();
        // set up distance array with max values
        for(int x=0; x<shortestDistanceFromRoot.length; x++){
            shortestDistanceFromRoot[x] = Integer.MAX_VALUE;
            previousVertex[x] = -1;
        }
        // set root node to 0
        shortestDistanceFromRoot[node1] = 0;
        previousVertex[node1] = 0;


        // loop until each node has been visited
        for (int i = 0; i < numNodes(); i++) {
            int minDistance = Integer.MAX_VALUE;
            int currentNode = -1;
            // find the closest unvisited node to the source node
            for (int j = 0; j < numNodes(); j++) {
                if (!visited[j] && shortestDistanceFromRoot[j] < minDistance) {
                    currentNode = j;
                    minDistance = shortestDistanceFromRoot[j];
                }
            }
            // mark the current node as visited
            visited[currentNode] = true;
            // update the shortest distances to its adjacent nodes
            for (Integer adjNode : getAdjacent(currentNode)) {
                int edgeWeight = getWeight(currentNode, adjNode);
                int newDistance = shortestDistanceFromRoot[currentNode] + edgeWeight;
                if (newDistance < shortestDistanceFromRoot[adjNode]) {
                    shortestDistanceFromRoot[adjNode] = newDistance;
                    previousVertex[adjNode] = currentNode;
                }
            }
        }


        // backtracking along visited list to create path
        int currentVertex = node2;
        while (currentVertex != node1 && currentVertex != -1) {
            paths.add(currentVertex);
            currentVertex = previousVertex[currentVertex];
        }
        if (currentVertex == node1) {
            paths.add(node1);
            Collections.reverse(paths);
        }
        return paths;
    }

    @Override
    public ArrayList<Integer> findShortestPath(Integer node1, Integer node2) {
        // array used to hold distances from root node
        int[] shortestDistanceFromRoot = new int[numNodes()];
        // array used to tell if node has been expanded
        boolean[] visited = new boolean[numNodes()];
        int[] previousVertex = new int[numNodes()];
        ArrayList<Integer> path = new ArrayList<Integer>();
        // set up distance array with max values
        for(int x=0; x<shortestDistanceFromRoot.length; x++){
            shortestDistanceFromRoot[x] = Integer.MAX_VALUE;
            previousVertex[x] = -1;
        }
        // set root node to 0
        shortestDistanceFromRoot[nodes.indexOf(node1)] = 0;
        previousVertex[nodes.indexOf(node1)] = 0;
        // loop until each node has been visited
        for (int i = 0; i < numNodes(); i++) {
            int minDistance = Integer.MAX_VALUE;
            int currentNode = -1;
            // find the closest unvisited node to the source node
            for (int j = 0; j < numNodes()-1; j++) {
                if (!visited[j] && shortestDistanceFromRoot[j] < minDistance) {
                    currentNode = j;
                    minDistance = shortestDistanceFromRoot[j];
                }
            }
            // mark the current node as visited
            visited[currentNode] = true;
            // update the shortest distances to its adjacent nodes
            for (Integer adjNode : getAdjacent(currentNode)) {
                int edgeWeight = getWeight(currentNode, adjNode);
                int newDistance = shortestDistanceFromRoot[currentNode] + edgeWeight;
                if (newDistance < shortestDistanceFromRoot[adjNode]) {
                    shortestDistanceFromRoot[adjNode] = newDistance;
                    previousVertex[adjNode] = currentNode;
                }
            }
        }
        // backtracking along visited list to create path
        int currentVertex = node2;
        while (currentVertex != node1 && currentVertex != -1) {
            path.add(currentVertex);
            currentVertex = previousVertex[currentVertex];
        }
        if (currentVertex == node1) {
            path.add(node1);
            Collections.reverse(path);
        }
        return path;
    }

    public int findPathLength(ArrayList<Integer> path){
        int length = 0;
        for(int x=0;x<path.size()-1;x++){
            length += this.getWeight(path.get(x), path.get(x+1));
        }
        return length;
    }

    public ArrayList<Integer> findShortestPath2(Integer node1, Integer node2) {
        int[] shortestDistanceFromRoot = new int[this.getNodes().size()];
        boolean[] visited = new boolean[this.getNodes().size()];
        int[] previousVertex = new int[this.getNodes().size()];
        ArrayList<Integer> path = new ArrayList<Integer>();

        for (int x = 0; x < this.getNodes().size(); x++) {
            shortestDistanceFromRoot[x] = Integer.MAX_VALUE;
            previousVertex[x] = -1;
        }

        int sourceIndex = this.getNodes().indexOf(node1);
        shortestDistanceFromRoot[sourceIndex] = 0;

        for (int i = 0; i < this.getNodes().size()-1; i++) {
            int minDistance = Integer.MAX_VALUE;
            int currentNode = -1;

            for (int j = 0; j < this.getNodes().size(); j++) {
                if (!visited[j] && shortestDistanceFromRoot[j] < minDistance) {
                    currentNode = j;
                    minDistance = shortestDistanceFromRoot[j];
                }
            }

            visited[currentNode] = true;

            for (Integer adjNode : this.getAdjacent(this.getNodes().get(currentNode))) {
                int adjNodeIndex = this.getNodes().indexOf(adjNode);
                int edgeWeight = this.getWeight(this.getNodes().get(currentNode), adjNode);
                int newDistance = shortestDistanceFromRoot[currentNode] + edgeWeight;
                if (newDistance < shortestDistanceFromRoot[adjNodeIndex]) {
                    shortestDistanceFromRoot[adjNodeIndex] = newDistance;
                    previousVertex[adjNodeIndex] = currentNode;
                }
            }
        }

        int currentVertex = this.getNodes().indexOf(node2);
        while (currentVertex != sourceIndex && currentVertex != -1) {
            path.add(this.getNodes().get(currentVertex));
            currentVertex = previousVertex[currentVertex];
        }
        if (currentVertex == sourceIndex) {
            path.add(node1);
            Collections.reverse(path);
        }
        return path;
    }


}
