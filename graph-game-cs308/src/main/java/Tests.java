import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static org.junit.Assert.*;

public class Tests{

    //Graph tests
    @Test
    public void testAddRemoveNode(){ //tests methods relating to the addition and removal of nodes
        FullGraph testGraph = new FullGraph();
        assertTrue(testGraph.isEmpty());

        testGraph.insertNode(0);
        assertTrue(testGraph.hasNode(0));

        testGraph.insertNode(1);
        testGraph.removeNode(0);
        assertFalse(testGraph.hasNode(0));
    }

    @Test
    public void testAddRemoveEdge(){ //tests methods relating to the adding and removal of edges
        FullGraph testGraph = new FullGraph();

        assertFalse(testGraph.hasEdge(0,1));
        testGraph.insertEdge(0,1,2);
        assertTrue(testGraph.hasEdge(0,1));
        assertEquals(testGraph.getWeight(0,1),2);

        testGraph.insertEdge(1,2,3);
        assertEquals(testGraph.getDegree(1),2);
        assertEquals(testGraph.getClosest(1),0);

        testGraph.removeEdge(0,1);
        assertFalse(testGraph.hasEdge(0,1));
    }

    @Test
    //shows a new level will always contain 13 nodes
    public void newLevel() throws FileNotFoundException {
        FullGraph testGraph = new FullGraph();
        testGraph.populateGraph();
        FullGraph levelGraph = FullGraph.generateLevel(testGraph);
        assertEquals(levelGraph.getNodes().size(), 13);
    }

    @Test
    //shows path length of a given path (used to show hints to users)
    public void findLengthOfPath() throws FileNotFoundException {
        FullGraph testGraph = new FullGraph();
        testGraph.populateGraph();
        ArrayList<Integer> paths = new ArrayList<Integer>();
        paths.add(0);
        paths.add(47);
        paths.add(5);
        assertEquals(testGraph.findPathLength(paths), 4);
    }

    @Test
    //shows each level will be random
    public void randomGraph() throws FileNotFoundException {
        FullGraph testGraph = new FullGraph();
        testGraph.populateGraph();
        FullGraph levelGraph = FullGraph.generateLevel(testGraph);
        FullGraph levelGraph2 = FullGraph.generateLevel(testGraph);
        FullGraph levelGraph3 = FullGraph.generateLevel(testGraph);
        assertEquals(levelGraph==levelGraph2, false);
        assertEquals(levelGraph2==levelGraph3, false);
        assertEquals(levelGraph==levelGraph3, false);
    }

    @Test
    //shows path finding
    public void pathFinding() throws FileNotFoundException {
        FullGraph testGraph = new FullGraph();
        testGraph.populateGraph();
        ArrayList<Integer> paths = new ArrayList<Integer>();
        paths.add(0);
        paths.add(47);
        paths.add(5);
        assertEquals(testGraph.findShortestPath2(0, 5), paths);
        assertNotEquals(testGraph.findShortestPath2(0, 4), paths);
    }

    @Test
    //tests of single node functionality
    public void getClosestNode() throws FileNotFoundException {
        FullGraph testGraph = new FullGraph();
        testGraph.populateGraph();
        assertEquals(testGraph.getClosest(0), 28);
        assertEquals(testGraph.getDegree(0), 7);
        assertEquals(testGraph.getWeight(0, 1), 7);
    }

    //Controller tests
    @Test
    public void testTimer(){
        FullGraph testGraph = new FullGraph();
        GUIManager testGui = new GUIManager(testGraph);
        Controller controller = new Controller(testGraph, testGui, testGraph);

        assertNull(controller.getTimer());

        int t1 = testGui.time;
        controller.startTimer(testGui);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                int t2 = testGui.time;
                assertNotEquals(t1, t2);
            }
        }, 300L);
    }
}
