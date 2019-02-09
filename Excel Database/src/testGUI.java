import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;


public class testGUI extends JFrame implements ActionListener {
    static JTextArea area, area2, area3, area4, area5;  // these are all the gui components
    private JButton button, button2;
    private JTextField nameField, periodsField, dateField;
    private JScrollPane scroll, scroll2, scroll3;
    public static String searchName, enterPeriods, enterDate;
    static ArrayList<String> N = new ArrayList<>();
    static ArrayList<String> Reps = new ArrayList<>();
    static ArrayList<String> OnCall = new ArrayList<>();
    public static ArrayList<String> periodList;
    public static boolean cvis = true;


    public testGUI() {
        /* GridLayout is a type of GUI layout to organize things into a grid.
         * With this layout, we don't need to specify the width and height of text areas, buttons etc
         * as the grid will automatically re-size them.
         * There are a lot of flaws with this layout */
        GridLayout grid = new GridLayout(4, 4, 0, 0);
        this.setSize(1200, 800);
        this.setResizable(true);
        this.setLayout(grid);
        this.setTitle("On-Call schedule");

        Container c = getContentPane();
        c.setBackground(new Color(114, 1, 22));
        c.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);


       /* JLabel titleLabel = new JLabel("On-Call Database");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));// FONT
        titleLabel.setForeground(Color.black); // COLOUR
        titleLabel.setHorizontalAlignment(JLabel.CENTER); // ALIGNMENT
        c.add(titleLabel);
        c.add(new JLabel());/* Flaw #1 - in order to allow some components
      to have their own row or column, we need to add empty labels to fill up the cell space around it
        c.add(new JLabel());// empty space fillers
        c.add(new JLabel());
        */

        JLabel nameLabel = new JLabel("Enter Name");
        nameLabel.setFont(new Font("Georgia", Font.BOLD, 20)); // FONT
        nameLabel.setForeground(Color.white);  // COLOR
        nameLabel.setHorizontalAlignment(JLabel.CENTER);//ALIGNMENT
        c.add(nameLabel);

        JLabel periods = new JLabel("Enter Periods of Absence");
        periods.setFont(new Font("Georgia", Font.BOLD, 20));// FONT
        periods.setForeground(Color.white);  // COLOR
        periods.setHorizontalAlignment(JLabel.CENTER);// ALIGNMENT
        c.add(periods);

        JLabel date = new JLabel("Enter Date of Absence");
        date.setFont(new Font("Georgia", Font.BOLD, 20)); // FONT
        date.setForeground(Color.white); // COLOR
        date.setHorizontalAlignment(JLabel.CENTER);// ALIGNMENT
        c.add(date);

        c.add(new JLabel());// empty space filler

        // Name text field to search for name
        nameField = new JTextField();
        nameField.setFont(new Font("Georgia", Font.PLAIN, 20));// FONT
        c.add(nameField);

        // Periods text field to enter periods
        periodsField = new JTextField();
        periodsField.setFont(new Font("Georgia", Font.PLAIN, 20));// FONT
        c.add(periodsField);

        // Date text field to enter date
        dateField = new JTextField();
        dateField.setFont(new Font("Georgia", Font.PLAIN, 20));// FONT
        c.add(dateField);

        // Search button
        button = new JButton("Enter");
        button.setOpaque(false);
        button.addActionListener(this);
        c.add(button);

        // First text area prints the name
        area = new JTextArea(10, 20);
        area.setEditable(false);
        area.setFont(new Font("Georgia", Font.PLAIN, 20));

        /* Scroll bar - takes the component that needs a scroll bar on it, and options for vertical and horizonal scroll bars*/
        scroll = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        c.add(scroll);
        setLocationRelativeTo(null);// We need to add this location thing for some reason idk y
        setVisible(true);

        // Second text area prints the periods
        area2 = new JTextArea(10, 20);
        area2.setFont(new Font("Georgia", Font.PLAIN, 20));
        area2.setEditable(false);
        c.add(area2);
        setLocationRelativeTo(null);
        setVisible(true);

        // Third text area prints the date
        area3 = new JTextArea(10, 20);
        area3.setFont(new Font("Georgia", Font.PLAIN, 20));
        area3.setEditable(false);
        c.add(area3);
        setLocationRelativeTo(null);
        setVisible(true);

        // Search button
        button2 = new JButton("Find Replacements");
        button2.setOpaque(false);
        button2.addActionListener(this);
        c.add(button2);

        JLabel found = new JLabel("Replacements Found: ");
        found.setFont(new Font("Georgia", Font.BOLD, 20));// FONT
        found.setForeground(Color.white);  // COLOR
        found.setHorizontalAlignment(JLabel.CENTER);// ALIGNMENT
        c.add(found);

        area4 = new JTextArea(10, 20);
        area4.setFont(new Font("Georgia", Font.PLAIN, 20));
        area4.setEditable(false);
        scroll2 = new JScrollPane(area4, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        c.add(scroll2);
        setLocationRelativeTo(null);// We need to add this location thing for some reason idk y
        //setVisible(false);

        area5 = new JTextArea(10, 20);
        area5.setFont(new Font("Georgia", Font.PLAIN, 20));
        area5.setEditable(false);
        scroll3 = new JScrollPane(area5, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        c.add(scroll3);
        scroll2.getVerticalScrollBar().setModel(scroll3.getVerticalScrollBar().getModel());
        setLocationRelativeTo(null);// We need to add this location thing for some reason idk y
        setVisible(true);

    }

    // this prints the textfile into the first text area at the start
    public void write() {
        N = TextAreaLoad.teachers;
        for (int i = 1; i < N.size(); i++)
            area.append(N.get(i) + "\n");

    }

    // this is for searching a name through the text file
    public void EnterAndSearch() {
        searchName = nameField.getText();
        enterPeriods = periodsField.getText();
        enterDate = dateField.getText();
        int index = 1;
        String name = N.get(index);
        int len = N.size();
        while (len > 0) {
            if (searchName.equals("") && N.get(index).contains(searchName)) {
                len = -1;
            } else if (N.get(index).contains(searchName)) {
                area.setText(name + "\n");
                area2.setText(enterPeriods + "\n");
                area3.setText(enterDate + "\n");
                len = -1;
            } else {
                index++;
                name = N.get(index);
            }
            if (index == N.size() - 1) {
                area.setText("Name not found" + "\n");
            }
        }
    }

    // this is for finding replacements for the given period(s)
    public void Find() {
        Reps = TextAreaLoad.replacement;
        OnCall = TextAreaLoad.onCall;
        int callLength = OnCall.size();

        String duty;
        String[] periods = periodsField.getText().split(",");
        periodList = new ArrayList<>(Arrays.asList(periods));

        int index = 1;
        duty = OnCall.get(index);

        while (callLength > index) {
            for (int i = 0; i < periodList.size(); i++) {
                if (duty.contains(periodList.get(i))) {
                    area4.append(Reps.get(index) + "\n");
                    area5.append(OnCall.get(index) + "\n");
                }
            }
            index++;
            duty = OnCall.get(index);

            for (int i = 0; i < periods.length; i++) {
                if (index == callLength - 1 && !duty.contains(periods[i])) {
                    area4.append("No more replacements found" + "\n");

                    callLength = index - 1;

                }
            }
        }
    }


    // When you press the button it will display the search findings
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == button) {
            EnterAndSearch();
        }
        if (e.getSource() == button2) {
            area4.setText(" ");
            area5.setText(" ");
            Find();
        }
    }


    public static void main(String[] args) throws FileNotFoundException {
        // TODO Auto-generated method stub
        // DISPLAYS THE GUI
        testGUI gui = new testGUI();

        // Takes the functions from TextAreaLoad.java
        TextAreaLoad tla = new TextAreaLoad();
        tla.open();
        tla.read();
        gui.write();
        tla.findReplacement();
    }


}
