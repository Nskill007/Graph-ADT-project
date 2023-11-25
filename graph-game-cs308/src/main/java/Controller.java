// Controller.java
import javax.sql.rowset.spi.SyncResolver;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;
public class Controller {

    JButton buttonStart;
    JButton buttonSubmit;
    JButton buttonQuit;

    // Variables to update game state
    private static int attempts = 0;

    private static int score = 1000;

    private static FullGraph gameGraph;
    private static FullGraph levelGraph;
    private int startNode;
    private int endNode;
    private Timer timer;

    private ArrayList<Integer> shortestPath;


    private ArrayList<Integer> currentAnswer;

    private boolean completedLevel = false;
    private boolean completedGame = false;

    public Controller(FullGraph gameFullGraph, GUIManager gui, FullGraph firstLevelGraph) {
        gameGraph = gameFullGraph;
        levelGraph = firstLevelGraph;
        buttonSubmit = new JButton();
        buttonSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Trying to submit answer");
                submitAnswer(gui);
            }
        });
        buttonQuit = new JButton();
        buttonQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Trying to quit game");
                gui.setUpScreen(buttonStart);
            }
        });
        // Creates button that starts game when pressed, this is used on the instruction page to tell when the user wants to start
        buttonStart = new JButton();
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Trying to start game");
                gui.setUpGameScreen(buttonSubmit, buttonQuit);
                playGame(gui);
            }
        });
    }

    // Sets up screen
    public void setup(FullGraph gameGraph, GUIManager gui) throws FileNotFoundException {
        // Set up game
        System.out.println("Game is being set up");
        gui.setUpScreen(buttonStart);
    }

    // Main method to run game logic!
    public void playGame(GUIManager gui) {

        if (timer == null) {
            // Start the timer when the game starts
            startTimer(gui);
        }
        playLevel(gui);
    }

    public void playLevel(GUIManager gui){

        System.out.println("Starting Level!");
        int maxAttempts = 10;
        attempts = 0;

        //set start and end nodes
        ArrayList<Integer> startEnd = levelGraph.getStartEndNodes();
        startNode = startEnd.get(0);
        endNode = startEnd.get(1);
        gui.updatePickedNodesColor(startNode, endNode);

        //get shortest path
        shortestPath = levelGraph.findShortestPath2(startNode, endNode);
        System.out.println("shortest path = " + shortestPath);

        //sets up gui with initial data
        gui.guessField.setText("");
        gui.input.setText("Nodes: " + startNode + "->" + endNode);

        //message area start text
        gui.messageArea.append("Enter a valid path from " + startNode + " to " + endNode);

    }

    public void submitAnswer(GUIManager gui){
        ArrayList<Integer> guessArrayList = new ArrayList<Integer>();
        String guessString;
        //get input
        guessString = gui.getInput();
        //check against answer

        String[] guessArray = guessString.split(",");
        for(int x=0;x<guessArray.length;x++){
            try {
                int newInt = Integer.parseInt(guessArray[x].strip());
                guessArrayList.add(newInt);
            } catch (Exception e){
                System.out.println("Entered an invalid input!");
                gui.messageArea.setText("You entered an invalid input!");
                //e.printStackTrace();
                return;
            }
        }
        if(guessArrayList.equals(shortestPath)){
            correctAnswer(gui);
        }
        else {System.out.println("your guess was = " + guessArrayList);
            wrongAnswer(gui);
        }
    }

    public void correctAnswer(GUIManager gui){
        System.out.println("You guessed the correct path well done!");
        gui.messageArea.setText("Well done, travelling to next constellation! ");
        levelGraph = FullGraph.generateLevel(gameGraph);
        gui.nextLevel();
        gui.setFullGraph(levelGraph);
        gui.makeGraph();
        playLevel(gui);
        if(gui.getLevel() > 5){
            System.out.println("Your score was " + gui.time);
            gui.setUpWinScreen();
        }
    }

    public void wrongAnswer(GUIManager gui){
        System.out.println("Wrong");
        attempts++;
        gui.updateAttempts();
        System.out.println(attempts);
        gui.updateTimer(10);
        if (attempts == 10) {
            if(gui.getLevel() == 1){
                gui.messageArea.setText("Out of attempts :( The correct answer was " + shortestPath);
                //resetGame(gui);
                timer.cancel();
                gui.time = 0;
                gui.attempts = 0;
                attempts = 0;
                gui.setUpLoseScreen(shortestPath);
            } else {
                gui.backLevel();
                levelGraph = FullGraph.generateLevel(gameGraph);
                gui.setFullGraph(levelGraph);
                gui.makeGraph();
                gui.messageArea.setText("Out of attempts. The correct answer was " + shortestPath + " with length " + gameGraph.findPathLength(shortestPath) + ". ");
                playLevel(gui);
            }
        } else {
            gui.messageArea.setText("Incorrect answer! You have " + (10 - attempts) + " attempts left! Keep Trying!");
        }
    }

    public void startTimer(GUIManager gui) {

        this.timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                gui.updateTimer(1);
            }
        };

        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    public Timer getTimer(){
        return timer;
    }

    public void resetGame(GUIManager gui) {
        if (timer != null) {
            timer.cancel(); // Stop the timer if it's running
        }
        gui.setUpScreen(buttonStart);
    }
}



