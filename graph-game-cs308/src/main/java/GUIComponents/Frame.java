package GUIComponents;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    public Frame(int BOARD_WIDTH, int BOARD_HEIGHT){
        //setting up Frame with default settings
        this.setTitle("StarPath");
        this.setSize(BOARD_WIDTH, BOARD_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

}