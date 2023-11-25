package GUIComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetScreen extends JPanel{

    JLabel title;
    JLabel instructionsTitle;
    JLabel instruction1;
    JLabel instruction2;
    JLabel instruction3;
    JLabel instruction4;
    Image backgroundImage;
    public SetScreen(int BOARD_WIDTH, int BOARD_HEIGHT){
        this.setBounds(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        this.setLayout(null);
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setCursor(null);
        ImageIcon starIcon = new ImageIcon("src/main/resources/star.png");
        title = new JLabel();
        title.setBounds(425,50,500,50);
        title.setFocusable(false);
        title.setText("StarPath");
        int scaledWidth = 70;
        int scaledHeight = 70;
        Image scaledImage = starIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);


        JLabel starLabel = new JLabel(scaledIcon);
        starLabel.setBounds(649, 35, scaledIcon.getIconWidth(), scaledIcon.getIconHeight());

        ImageIcon backgroundIcon = new ImageIcon("src/main/resources/space4.jpg");
        backgroundImage = backgroundIcon.getImage();

        this.add(starLabel);
        title.setFont(new Font("MV Boli",Font.PLAIN,45));
        title.setForeground(Color.white);

        instructionsTitle = new JLabel();
        instructionsTitle.setBounds(485,200,300,50);
        instructionsTitle.setFocusable(false);
        instructionsTitle.setText("Instructions:");
        instructionsTitle.setFont(new Font("MV Boli",Font.PLAIN,30));
        instructionsTitle.setForeground(Color.white);

        instruction1 = new JLabel();
        instruction1.setBounds(140,250,1000,50);
        instruction1.setFocusable(false);
        instruction1.setText("There are 5 constellations you must cross in order to graduate as a space explorer! ");
        instruction1.setFont(new Font("MV Boli",Font.PLAIN,22));
        instruction1.setForeground(Color.white);
        instruction1.setHorizontalTextPosition(SwingConstants.CENTER);

        instruction2 = new JLabel();
        instruction2.setBounds(130,300,1000,50);
        instruction2.setFocusable(false);
        instruction2.setText("Navigate between the 2 red stars and enter the shortest path in the input box below");
        instruction2.setFont(new Font("MV Boli",Font.PLAIN,22));
        instruction2.setForeground(Color.white);
        instruction2.setHorizontalTextPosition(SwingConstants.CENTER);

        instruction3 = new JLabel();
        instruction3.setBounds(240,350,1000,150);
        instruction3.setFocusable(false);
        instruction3.setText("<html>The start and end star is shown by the node label at the top<br>enter paths as such '1,23,45' including the start and end star<br>with 1 being the start star and 45 being the end star</html>");
        instruction3.setFont(new Font("MV Boli",Font.PLAIN,22));
        instruction3.setForeground(Color.white);
        instruction3.setHorizontalTextPosition(SwingConstants.CENTER);

        instruction4 = new JLabel();
        instruction4.setBounds(465,420,1000,248);
        instruction4.setFocusable(false);
        instruction4.setText("<html>Good luck explorer!</html>");
        instruction4.setFont(new Font("MV Boli",Font.PLAIN,25));
        instruction4.setFont(new Font("MV Boli",Font.PLAIN,25));
        instruction4.setForeground(Color.white);
        instruction4.setHorizontalTextPosition(SwingConstants.CENTER);

        this.add(starLabel);
        this.add(instruction2);
        this.add(instruction1);
        this.add(instruction3);
        this.add(instruction4);
        this.add(title);
        this.add(instructionsTitle);
        this.setVisible(true);
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}