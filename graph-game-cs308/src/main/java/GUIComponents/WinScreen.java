package GUIComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinScreen extends JPanel {

    JLabel title;
    JLabel scoreLabel;
    JLabel scoreLabel2;
    Image backgroundImage;
    JButton button;

    public WinScreen(int BOARD_WIDTH, int BOARD_HEIGHT, int score){
        this.setBounds(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        this.setLayout(null);
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setCursor(null);

        ImageIcon starIcon = new ImageIcon("src/main/resources/star.png");
        ImageIcon backgroundIcon = new ImageIcon("src/main/resources/space4.jpg");
        backgroundImage = backgroundIcon.getImage();

        title = new JLabel();
        title.setBounds(490,50,500,50);
        title.setFocusable(false);
        title.setText("StarPath");
        title.setFont(new Font("MV Boli",Font.PLAIN,45));
        title.setForeground(Color.white);

        int scaledWidth = 70;
        int scaledHeight = 70;
        Image scaledImage = starIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel starLabel = new JLabel(scaledIcon);
        starLabel.setBounds(690, 35, scaledIcon.getIconWidth(), scaledIcon.getIconHeight());

        scoreLabel = new JLabel();
        scoreLabel.setBounds(380,150,1000,300);
        scoreLabel.setFocusable(false);
        scoreLabel.setText("Well done space explorer!");
        scoreLabel.setFont(new Font("MV Boli",Font.PLAIN,35));
        scoreLabel.setForeground(Color.white);
        scoreLabel.setHorizontalTextPosition(SwingConstants.CENTER);

        scoreLabel2 = new JLabel();
        scoreLabel2.setBounds(250,250,1000,300);
        scoreLabel2.setFocusable(false);
        scoreLabel2.setText("You completed the program in " + score + " seconds!");
        scoreLabel2.setFont(new Font("MV Boli",Font.PLAIN,35));
        scoreLabel2.setForeground(Color.white);
        scoreLabel2.setHorizontalTextPosition(SwingConstants.CENTER);

        button = new JButton();
        button.setBounds(425, 600, 300, 80);
        button.setFocusable(false);
        button.setText("Graduate!");
        button.setFont(new Font("MV Boli", Font.PLAIN, 25));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        this.add(button);
        this.add(scoreLabel2);
        this.add(scoreLabel);
        this.add(title);
        this.add(starLabel);
        this.setVisible(true);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }

}
