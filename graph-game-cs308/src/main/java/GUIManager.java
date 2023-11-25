import GUIComponents.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import GUIComponents.Frame;
import GUIComponents.Label;
import org.graphstream.graph.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.swing_viewer.*;
import org.graphstream.ui.view.*;

public class GUIManager implements ActionListener, GUIADT {

    //maybe have an array that holds each node, its position and adjacent nodes and render it each time an action is performed

    static final int BOARD_WIDTH = 1200;
    static final int BOARD_HEIGHT = 800;
    Frame frame;
    SetScreen setScreen;
    Screen screen;
    WinScreen winScreen;
    LoseScreen loseScreen;
    int time;
    int attempts;
    int level = 1;

    Label timeLabel;
    Label attemptsLabel;
    Label levelLabel;
    Label attemptLabel;

    Label input;
    JButton button;
    int remainingSeconds;

    //graphstream variables
    Graph graph;
    Viewer viewer;
    View view;
    JTextField node1Field;
    JTextField node2Field;
    JTextField guessField;
    JTextArea messageArea;

    private FullGraph fullGraph;

    public GUIManager(FullGraph graph){
        fullGraph = graph;
    }

    public void setUpScreen(JButton startBtn) {

        this.button = button;
        System.out.println("Loading Instructions");
        if(screen != null){
            frame.dispose();
        }

        frame = new Frame(BOARD_WIDTH, BOARD_HEIGHT);

        setScreen = new SetScreen(BOARD_WIDTH, BOARD_HEIGHT);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorImage = toolkit.getImage("src/main/resources/cursor.png");
        Point hotspot = new Point(0, 0); // You can adjust the hotspot as needed
        Cursor customCursor = toolkit.createCustomCursor(cursorImage, hotspot, "Custom Cursor");
        frame.setCursor(customCursor);

        startBtn.setBounds(425, 600, 300, 80);
        startBtn.setFocusable(false);
        startBtn.setText("Start your Journey!");
        startBtn.setFont(new Font("MV Boli", Font.PLAIN, 25));
        setScreen.add(startBtn);
        frame.add(setScreen);
        frame.setVisible(true);
    }

    public void setFullGraph(FullGraph graph){
        this.fullGraph = graph;
    }

    public void setUpGameScreen(JButton buttonSubmit, JButton buttonQuit) {

        System.out.println("Loading game screen!");
        makeGraph();
        frame.remove(setScreen);
        screen = new Screen(BOARD_WIDTH, BOARD_HEIGHT);
        JPanel graphPanel = new JPanel(new BorderLayout());
        graphPanel.add((Component) view);
        graphPanel.setBounds(50, 50, 1100, 550);
        screen.add(graphPanel);
        timeLabel = new Label("Time: " + time, 250, 10, 100, 75);
        levelLabel = new Label("Level: " + level, 450, 10, 100, 75);
        attemptsLabel = new Label("Attempts: " + attempts, 80, 10, 100, 75);
        input = new Label("Select 2 Nodes!", 750, 10, 800, 75);
        screen.add(timeLabel);
        screen.add(levelLabel);
        screen.add(attemptsLabel);
        screen.add(input);
        frame.add(screen);
        frame.repaint();
        frame.setVisible(true);
        createInputFieldsAndButtons(buttonSubmit, buttonQuit);
        createMessageArea();
    }

    public void setUpWinScreen(){
        frame.remove(screen);
        winScreen = new WinScreen(BOARD_WIDTH, BOARD_HEIGHT, this.time);
        frame.add(winScreen);
        frame.repaint();
    }

    public void setUpLoseScreen(ArrayList<Integer> shortestPath){
        frame.remove(screen);
        loseScreen = new LoseScreen(BOARD_WIDTH, BOARD_HEIGHT, this.time, shortestPath);
        frame.add(loseScreen);
        frame.repaint();
    }


    private void setUpFirstScreen() {
        frame.getContentPane().removeAll();
        frame.repaint();
        setUpScreen(button);
    }

    public void createInputFieldsAndButtons(JButton buttonSubmit, JButton buttonQuit) {

        screen.setLayout(null);

        //dont need
        node1Field = new JTextField(5);
        node2Field = new JTextField(5);
        node1Field.setBounds(50, 625, 100, 30);
        node2Field.setBounds(200, 625, 100, 30);


        guessField = new JTextField(20);
        guessField.setBounds(50, 675, 250, 30);
        //guessField.setEnabled(false);

        //dont need
        JLabel node1Label = new JLabel("Enter first node:");
        node1Label.setBounds(50, 600, 100, 25);
        node1Label.setForeground(Color.white);
        JLabel node2Label = new JLabel("Enter second node:");
        node2Label.setBounds(200, 600, 120, 25);

        node2Label.setForeground(Color.white);
        JLabel guessLabel = new JLabel("Enter your guess:");
        guessLabel.setBounds(50, 650, 120, 25);
        guessLabel.setForeground(Color.white);

        ImageIcon submitButtonIcon = new ImageIcon("src/main/resources/star.png");
        int buttonWidth = 200;
        int buttonHeight = 80;
        Image submitButtonImage = submitButtonIcon.getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledSubmitButtonIcon = new ImageIcon(submitButtonImage);

        buttonSubmit.setIcon(scaledSubmitButtonIcon);
        buttonSubmit.setBounds(350, 625, buttonWidth, buttonHeight);
        buttonSubmit.setBorderPainted(false);
        buttonSubmit.setContentAreaFilled(false);

        buttonSubmit.setText("Submit");
        buttonSubmit.setFont(new Font("MV Boli", Font.PLAIN, 20));
        buttonSubmit.setForeground(Color.BLACK);
        buttonSubmit.setVerticalTextPosition(SwingConstants.CENTER);
        buttonSubmit.setHorizontalTextPosition(SwingConstants.CENTER);

        buttonQuit.setIcon(scaledSubmitButtonIcon);
        buttonQuit.setText("Quit");
        buttonQuit.setBounds(550, 625, 200, 80);
        buttonQuit.setFont(new Font("MV Boli", Font.PLAIN, 20));
        buttonQuit.setForeground(Color.BLACK);
        buttonQuit.setVerticalTextPosition(SwingConstants.CENTER);
        buttonQuit.setHorizontalTextPosition(SwingConstants.CENTER);
        buttonQuit.setBorderPainted(false);
        buttonQuit.setContentAreaFilled(false);
        //screen.add(node1Label);
        //screen.add(node1Field);
        //screen.add(node2Label);
        //screen.add(node2Field);
        screen.add(buttonQuit);
        screen.add(guessLabel);
        screen.add(guessField);
        screen.add(buttonSubmit);

    }

    public void createMessageArea() {
        messageArea = new JTextArea(5, 20);
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setBackground(new Color(0, 0, 0, 0));
        messageArea.setForeground(Color.WHITE);
        messageArea.setOpaque(false);
        messageArea.setFont(new Font("Monospaced", Font.PLAIN, 17));
        JScrollPane messageScrollPane = new JScrollPane(messageArea);
        messageScrollPane.setBounds(800, 625, 250, 150);
        messageScrollPane.setOpaque(false);
        messageScrollPane.getViewport().setOpaque(false);
        messageScrollPane.setBorder(null);
        JLabel messageAreaTitle = new JLabel("Game Instructions:");
        messageAreaTitle.setFont(new Font("Arial", Font.BOLD, 20));
        messageAreaTitle.setForeground(Color.WHITE);
        messageAreaTitle.setBounds(800, 600, 250, 30);
        screen.add(messageAreaTitle);
        screen.add(messageScrollPane);
    }

    Graph displayGraph = new SingleGraph("Display Graph");

    @Override
    public void makeGraph() {

        displayGraph.clear();

        displayGraph.setAttribute("ui.stylesheet", "graph { fill-color: black; }");

        for (int node : fullGraph.getNodes()) {
            Node graphNode = displayGraph.addNode(String.valueOf(node));
            graphNode.setAttribute("ui.label", String.valueOf(node));
            graphNode.setAttribute("ui.style", "shape: circle;size: 25; text-alignment: center; text-size: 15; fill-color: yellow; text-color: black;");
        }

        for (FullGraph.Edge edge : fullGraph.getEdges()) {
            Node node1 = displayGraph.getNode(String.valueOf(edge.node1));
            Node node2 = displayGraph.getNode(String.valueOf(edge.node2));
            Edge graphEdge = displayGraph.addEdge(String.valueOf(edge.node1) + String.valueOf(edge.node2), node1, node2);

            // Set the weight as the label of the edge
            graphEdge.setAttribute("ui.label", String.valueOf(edge.weight));
            // Set the edge style
            graphEdge.setAttribute("ui.style", "fill-color: white; shape: line; text-alignment: above; text-size: 15; text-color: white; text-background-mode: rounded-box; text-background-color: black; text-padding: 2px;");
        }

        viewer = new SwingViewer(displayGraph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.enableAutoLayout();
        view = viewer.addDefaultView(false);
    }

    @Override
    public void resetGraph() {
        if (displayGraph != null) {
            for (int node : fullGraph.getNodes()) {
                Node graphNode = displayGraph.getNode(String.valueOf(node));
                graphNode.setAttribute("ui.style", "shape: circle;size: 25; text-alignment: center; text-size: 15; fill-color: yellow; text-color: black;");
            }
        }
    }


    public void updatePickedNodesColor(int pickedNode1, int pickedNode2) {
        if (displayGraph != null) {
            Node graphNode1 = displayGraph.getNode(String.valueOf(pickedNode1));
            Node graphNode2 = displayGraph.getNode(String.valueOf(pickedNode2));

            graphNode1.setAttribute("ui.style", "shape: circle;size: 25; text-alignment: center; text-size: 15; fill-color: red; text-color: black;");
            graphNode2.setAttribute("ui.style", "shape: circle;size: 25; text-alignment: center; text-size: 15; fill-color: red; text-color: black;");
        }
    }

    @Override
    public String getInput(){
        return guessField.getText();
    }

    public void nextLevel(){
        this.level++;
        this.attempts = 0;
        screen.remove(levelLabel);
        screen.remove(attemptsLabel);
        attemptsLabel = new Label("Attempts: " + attempts, 80, 10, 100, 75);
        levelLabel = new Label("Level: " + this.level, 450, 10, 100, 75);
        screen.add(levelLabel);
        screen.add(attemptsLabel);
        screen.repaint();
    }

    public void backLevel(){
        this.level--;
        screen.remove(levelLabel);
        levelLabel = new Label("Level: " + this.level, 450, 10, 100, 75);
        screen.add(levelLabel);
        screen.repaint();
    }

    public int getLevel(){
        return this.level;
    }

    public void updateTimer(int duration){
        this.time += duration;
        screen.remove(timeLabel);
        timeLabel = new Label("Time: " + time, 250, 10, 100, 75);
        screen.add(timeLabel);
        screen.repaint();
    }

    public void updateAttempts(){
        this.attempts++;
        screen.remove(attemptsLabel);
        attemptsLabel = new Label("Attempts: " + attempts, 80, 10, 100, 75);
        screen.add(attemptsLabel);
        screen.repaint();
    }

    @Override
    public void update(){
        frame.repaint();
        screen.repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
