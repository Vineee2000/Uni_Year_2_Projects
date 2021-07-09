import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.removeAll();
        panel.setSize(500,500);
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BorderLayout());

        JPanel nestedPanel = new JPanel();
        nestedPanel.setPreferredSize(new Dimension(200, 500));
        nestedPanel.setBackground(Color.green);

        JButton easyBut = new JButton("1");
        easyBut.setPreferredSize(new Dimension(200, 100));
        JButton normBut = new JButton("2");
        normBut.setPreferredSize(new Dimension(200,100));
        JButton hardBut = new JButton("3");
        hardBut.setPreferredSize(new Dimension(200,100));

        frame.add(panel);
        panel.add(nestedPanel, BorderLayout.WEST);

        nestedPanel.add(easyBut);
        nestedPanel.add(normBut);
        nestedPanel.add(hardBut);

        System.out.println(nestedPanel.getParent());

        frame.setVisible(true);
    }
}