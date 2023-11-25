package GUIComponents;

import javax.swing.*;
import java.awt.*;

public class Screen extends JPanel {

    public Screen(int BOARD_WIDTH, int BOARD_HEIGHT){
        this.setBounds(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        this.setLayout(null);
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setCursor(null);
    }


}