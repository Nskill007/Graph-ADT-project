package GUIComponents;

import javax.swing.*;
import java.awt.*;

public class Label extends JLabel {

    public Label(String title, int x, int y, int width, int height){
        this.setBounds(x,y,200,30);
        this.setForeground(Color.white);
        this.setFont(new Font("MV Boli",Font.PLAIN,25));
        this.setText(title);
    }
}