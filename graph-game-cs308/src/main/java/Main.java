/*
    A graph based path finding game built in java for our cs308 class assignment
 */

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {


        System.setProperty("org.graphstream.ui", "swing");
        FullGraph gameGraph = new FullGraph();
        gameGraph.populateGraph();
        FullGraph firstLevel = FullGraph.generateLevel(gameGraph);
        GUIManager gui = new GUIManager(firstLevel);
        Controller controller = new Controller(gameGraph, gui, firstLevel);

        controller.setup(gameGraph, gui);

//        FullGraph.generateLevel(gameGraph);

    }
}

