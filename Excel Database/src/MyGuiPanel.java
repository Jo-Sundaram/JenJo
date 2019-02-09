import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

@SuppressWarnings("serial")
public class MyGuiPanel implements ActionListener {
    // some row and column values for our JTextArea
    private static final int TXT_AREA_ROWS = 25;
    private static final int TXT_AREA_COLS = 80;
    JTextArea area;
    JScrollPane scroll;
    JButton button;
    JFrame frame = new JFrame();
    JFrame frame2 = new JFrame();

    static boolean vis1 = true;
    static boolean vis2 = false;

    // create the JTextArea, passing in the rows and columns values


    public MyGuiPanel(){
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        GridLayout grid = new GridLayout(0,2);
        frame.setLayout(grid);

        area = new JTextArea(10,20);
        scroll = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        frame.add(scroll);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        button = new JButton("click");
        frame.add(button);


        frame2.setSize(500,500);
        frame2.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame2.add(new JLabel("Hello"));
        frame2.setLocationRelativeTo(null);
        frame2.setVisible(vis2);
    }



    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
           frame.setVisible(false);
           frame2.setVisible(true);
        }
    }

    public static void main(String[] args) {

        new MyGuiPanel();
    }
}