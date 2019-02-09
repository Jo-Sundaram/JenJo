
// Imports galore

import java.awt.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.*;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;


/* The following two classes named RadioButtonRender and RadioButtonEdit, are borrowed from this site:
 * http://www.java2s.com/Code/Java/Swing-Components/RadioButtonTableExample.htm
 * These are used to detect the user's radio button selection on the second tab of the program
 * From Lines: 28 to 58 */
class RadioButtonRender implements TableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value == null)
            return null;
        return (Component) value;
    }
}

class RadioButtonEdit extends DefaultCellEditor implements ItemListener {
    private JRadioButton jrb;

    public RadioButtonEdit(JCheckBox checkBox) {
        super(checkBox);
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value == null)
            return null;
        jrb = (JRadioButton) value;
        jrb.addItemListener(this);
        return (Component) value;
    }

    public Object getCellEditorValue() {
        jrb.removeItemListener(this);
        return jrb;
    }

    public void itemStateChanged(ItemEvent e) {
        super.fireEditingStopped();
    }
}

//----------------------------------------------------------------------------//
// Our main Gui class
public class Gui extends JFrame {
    // JTable objects for the tables used on each tab
    JTable enterTeacher, confirmed, viewReps, missing, selections;

    // these are objects that store the values of the information
    // entered in the table on the first tab
    Object searchName, enterPeriods, enterDate;

    // these are all of the buttons used on each tab to navigate
    // through the GUI and initialize the backend programming
    static JButton upload = new JButton("Upload Files");
    JButton next1 = new JButton("Next");
    JButton back2 = new JButton("Back");
    JButton next2 = new JButton("Next");
    JButton back3 = new JButton("Back");

    // Group of radio buttons used on tab 2
    ButtonGroup group1 = new ButtonGroup();

    // These ArrayLists/ LinkedLists are used to store the information taken
    // from the two text files used in our program
    static LinkedList<String> teacherNames = new LinkedList<>();
    static LinkedList<String> storeName = new LinkedList<>();
    static LinkedList<String> storeDuty = new LinkedList<>();
    static LinkedList<String> storeSpare = new LinkedList<>();
    static ArrayList<String> dates = new ArrayList<>();
    static ArrayList<String> spares = new ArrayList<>();
    // String used in the searchDate() method
    static String date;
    // Background image found here: https://sirwilfridlaurierss.ocdsb.ca/academics/technological_education
    static BufferedImage bg = null;
    static BufferedReader g;


    // this is initializer for the tabbed pane that holds all the "tabs"
    // for our GUI
    JTabbedPane tab = new JTabbedPane();

    // these are scroll panes that give the tables their scroll bar on the side
    JScrollPane scroll1, scroll2, scroll3, scroll4, scroll5;

    ///////~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~////////

    /* the following are the components for each table
     * which consist of a String array used containing headers for each column
     * and a 2D Object array with items displayed in each cell of the table
     * Help found from: https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
     * Lines: 111 - 122
     */
    String[] headers = {"Enter Last Name of Teacher", "Period Absent", "Date: dd/mm/yyyy"};/// on tab one
    Object[][] data = {
            {"", "", ""},
    };
    String[] headers2 = {"Absent Teacher", "For Periods", "Date", "Day"}; /// on tab 2
    Object[][] absent = {
            {"", "", "", ""},
    };
    String[] headers3 = {"Replacement", "On-Call Period", "Date"}; /// on tab 3
    Object[][] onCalls = {
            {" ", " ", " "},
    };
    ///////~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~////////

    /* This is the main Gui() function that calls two
     * other functions which create other components of the GUI
     */
    public Gui() {
        createTables();// this calls the function that creates tables in each screen
        createTabs();// this calls the function that creates the tabs

    }

    // This is the function that creates tables in each screen
    public void createTables() {

        /*TAB 1
         * first table where user enters the required information of the absent teacher
         */
        enterTeacher = new JTable(data, headers);

//        {
//
//            public String getToolTipText(MouseEvent e) { //https://docs.oracle.com/javase/tutorial/uiswing/components/table.html#celltooltip
//                String tip = null;
//                Point p = e.getPoint();
//                int rowIndex = rowAtPoint(p);
//                int colIndex = columnAtPoint(p);
//                int realColumnIndex = convertColumnIndexToModel(colIndex);
//
//                if (realColumnIndex == 1) { //Sport column
//                    tip = "This person's favorite sport to "
//                            + "participate in is: "
//                            + getValueAt(rowIndex, colIndex);
//                }
//                return tip;
//            }
//        };

        //this creates the table on the first tab by taking the information of data and headers
        for (int i = 0; i < data.length; i++) { // this for loop makes the table cells bigger
            enterTeacher.setRowHeight(i, 100);
        }
        TableColumnModel columns = enterTeacher.getColumnModel();
        columns.getColumn(0).setPreferredWidth(200);
        // sets other features for the table like font and whatnot
        enterTeacher.setFont(new Font("Georgia", Font.PLAIN, 30));
        enterTeacher.getTableHeader().setFont(new Font("Georgia", Font.PLAIN, 20));
        enterTeacher.setPreferredScrollableViewportSize(new Dimension(700, 100));
        enterTeacher.setFillsViewportHeight(true);


        /* TAB 2
         * Same process for this table
         * */
        confirmed = new JTable(absent, headers2);
        for (int i = 0; i < absent.length; i++) {
            confirmed.setRowHeight(i, 100);
        }

        confirmed.setFont(new Font("Georgia", Font.PLAIN, 30));
        confirmed.getTableHeader().setFont(new Font("Georgia", Font.PLAIN, 20));
        confirmed.setPreferredScrollableViewportSize(new Dimension(700, 100));
        confirmed.setFillsViewportHeight(true);

        /*TAB 2
         * This table incorporates radio buttons to select the teacher wanted
         * as a replacement. This code was borrowed from:
         * http://www.java2s.com/Code/Java/Swing-Components/RadioButtonTableExample.htm
         * Lines:
         * */
        UIDefaults ui = UIManager.getLookAndFeel().getDefaults();
        UIManager.put("RadioButton.focus", ui.getColor("control"));

        DefaultTableModel dm = new DefaultTableModel();

        dm.setDataVector(new Object[][]{
                {" ", new JRadioButton("1")},
                {" ", new JRadioButton("2")},
                {" ", new JRadioButton("3")},
                {" ", new JRadioButton("4")},
        }, new Object[]{
                "Replacement", "Select"});

        viewReps = new JTable(dm) {
            public void tableChanged(TableModelEvent e) {
                super.tableChanged(e);
                repaint();
            }
        };
        for (int i = 0; i < 4; i++) {
            viewReps.setRowHeight(i, 100);
        }
        viewReps.setFont(new Font("Georgia", Font.PLAIN, 30));
        viewReps.getTableHeader().setFont(new Font("Georgia", Font.PLAIN, 20));
        viewReps.setPreferredScrollableViewportSize(new Dimension(700, 400));
        viewReps.setFillsViewportHeight(true);

        group1.add((JRadioButton) dm.getValueAt(0, 1));
        group1.add((JRadioButton) dm.getValueAt(1, 1));
        group1.add((JRadioButton) dm.getValueAt(2, 1));
        group1.add((JRadioButton) dm.getValueAt(3, 1));
        viewReps.getColumn("Select").setCellRenderer(
                new RadioButtonRender());
        viewReps.getColumn("Select").setCellEditor(
                new RadioButtonEdit(new JCheckBox()));

        /* TAB 3*/
        missing = new JTable(absent, headers2);
        for (int i = 0; i < absent.length; i++) {
            missing.setRowHeight(i, 100);
        }
        missing.setFont(new Font("Georgia", Font.PLAIN, 30));
        missing.getTableHeader().setFont(new Font("Georgia", Font.PLAIN, 20));
        missing.setPreferredScrollableViewportSize(new Dimension(700, 100));
        missing.setFillsViewportHeight(true);

        // this is the table that diplays all the selected replacements on tab 3
        selections = new JTable(onCalls, headers3);
        for (int i = 0; i < onCalls.length; i++) {
            selections.setRowHeight(i, 100);
        }
        selections.setFont(new Font("Georgia", Font.PLAIN, 30));
        selections.getTableHeader().setFont(new Font("Georgia", Font.PLAIN, 20));
        selections.setPreferredScrollableViewportSize(new Dimension(700, 100));
        selections.setFillsViewportHeight(true);


    }

    /*This function is what creates the tabbed screens in our GUI
     * Tabbed Pane example code found from:
     * */
    public void createTabs() {

        /*  The following  try/catch method is what invokes reading the image used as the background image
        *  Each tab panel contains the background image, and is displayed by the method found on:
        *  http://www.java2s.com/Tutorials/Java/Swing_How_to/JTabbedPane/Set_a_background_image_in_JTabbedPane.htm
        *  https://stackoverflow.com/questions/9864267/loading-image-resource/9866659
          Lines: 261 - 267
        */
        int width = getWidth(); // these two ints are what size the background image to the size of the screen
        int height = getHeight();
        try {

            bg = ImageIO.read(Gui.class.getResourceAsStream("/wallpaper background V2.png"));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        /* TAB 1*/

        // these scroll panes take each table and gives them scroll bars
        scroll1 = new JScrollPane(enterTeacher);
        scroll2 = new JScrollPane(confirmed);
        scroll3 = new JScrollPane(viewReps);
        scroll4 = new JScrollPane(missing);
        scroll5 = new JScrollPane(selections);

        // these are panels  to hold components on each tab (buttons, tables)
        JPanel pan1 = new JPanel();
        JPanel next1Pan = new JPanel();
        JPanel uploadPan = new JPanel();
        JPanel tabpan1;

        pan1.setOpaque(false);
        next1Pan.setOpaque(false);
        pan1.add(scroll1);
        next1Pan.add(next1);

        uploadPan.setLayout(new BorderLayout());
        uploadPan.setOpaque(false);
        uploadPan.add(upload, BorderLayout.WEST);

        /* Sets background image
        * method found on:
        *  http://www.java2s.com/Tutorials/Java/Swing_How_to/JTabbedPane/Set_a_background_image_in_JTabbedPane.htm
          Lines: 297 - 307
        */
        tabpan1 = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(width, height);
            }
        };
        tab.addTab("Enter Information", tabpan1); // sets the title and the components of the tab
        tabpan1.add(pan1, BorderLayout.NORTH);
        tabpan1.add(next1Pan, BorderLayout.CENTER);
        tabpan1.add(uploadPan, BorderLayout.SOUTH);
        tab.setMnemonicAt(0, KeyEvent.VK_0);

        /* TAB 2*/

        /* More panels containing the tables and buttons where user can select replacment teacher*/
        JPanel pan2 = new JPanel();
        JPanel pan3 = new JPanel();
        JPanel pan4 = new JPanel();
        JPanel pan5 = new JPanel();
        JPanel tabPan2;

        pan2.setOpaque(false);
        pan3.setOpaque(false);
        pan4.setOpaque(false);
        pan5.setOpaque(false);
        pan2.add(scroll2);
        pan3.add(scroll3);
        pan4.add(back2);
        pan4.add(next2);

        /* Sets background image
        * method found on:
        *  http://www.java2s.com/Tutorials/Java/Swing_How_to/JTabbedPane/Set_a_background_image_in_JTabbedPane.htm
          Lines: 337 - 347
        */
        tabPan2 = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(width, height);
            }
        };
        tab.addTab("Select Replacement", tabPan2);
        tabPan2.add(pan2, BorderLayout.NORTH);
        tabPan2.add(pan3, BorderLayout.CENTER);
        tabPan2.add(pan4, BorderLayout.SOUTH);
        tab.setMnemonicAt(0, KeyEvent.VK_0);
        /* TAB 3 */
        // these are all the components on tab 3 which displays all of the final information
        JPanel tabPan3;
        JPanel absentPan = new JPanel();
        JPanel selectPan = new JPanel();
        JPanel buttonPan = new JPanel();

        absentPan.setOpaque(false);
        selectPan.setOpaque(false);
        buttonPan.setOpaque(false);
        absentPan.add(scroll4);
        selectPan.add(scroll5);
        buttonPan.add(back3);

        /* Sets background image
        * method found on:
        *  http://www.java2s.com/Tutorials/Java/Swing_How_to/JTabbedPane/Set_a_background_image_in_JTabbedPane.htm
          Lines: 372 - 382
        */
        tabPan3 = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(width, height);
            }
        };
        tab.addTab("Display", tabPan3);
        tabPan3.add(absentPan, BorderLayout.NORTH);
        tabPan3.add(selectPan, BorderLayout.CENTER);
        tabPan3.add(buttonPan, BorderLayout.SOUTH);
        tab.setMnemonicAt(0, KeyEvent.VK_0);
        ///~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~///

        /* These are all the action listeners for each button that allows them to do stuff */

        next1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EnterAndSearch();
                searchDate();
                clearTables();
                tab.setSelectedIndex(1);
                Find();
                System.out.println(storeName);
                System.out.println(storeDuty);
                System.out.println(storeSpare);

                viewReps.setValueAt(storeName.get(0), 0, 0);
                viewReps.setValueAt(storeName.get(1), 1, 0);
                viewReps.setValueAt(storeName.get(2), 2, 0);
                viewReps.setValueAt(storeName.get(3), 3, 0);
            }
        });

        back2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                tab.setSelectedIndex(0);
            }
        });

        next2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab.setSelectedIndex(2);
                selectReps();
            }
        });

        back3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab.setSelectedIndex(1);
            }
        });

        ///~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~///
        add(tab);
        tab.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    /* The following 3 functions are what do the whole filtering and finding replacement teachers */
/// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~///
    /* This function is for searching the entered name through
     *the text file to make sure it's valid name
     */
    public void EnterAndSearch() {
        searchName = enterTeacher.getValueAt(0, 0);
        enterPeriods = enterTeacher.getValueAt(0, 1);
        confirmed.setValueAt(searchName, 0, 0);
        confirmed.setValueAt(enterPeriods, 0, 1);
        int index = 1;
        String name = teacherNames.get(index);
        int len = teacherNames.size();
        while (len > 0) {
            if (teacherNames.get(index).equals((String) searchName)) {
                len = -1;
            } else {
                index++;
                name = teacherNames.get(index);
            }
            if (index == teacherNames.size() - 1) {
                enterTeacher.setValueAt("Name not Found", 0, 0);
            }
        }
    }

    /* This determines the day A/B depending on the date entered on the first tab */
    public void searchDate() {
        enterDate = enterTeacher.getValueAt(0, 2);

        int index = 0;
        int len = dates.size();

        while (len > 0) {
            if (dates.get(index).contains((String) enterDate)) {
                date = dates.get(index);
                len = -1;
            } else {
                index += 1;
            }
            if (index == len - 1) {
                enterTeacher.setValueAt("Invalid Date", 0, 2);
//                len = -1;
            }
        }
//        System.out.println(date);

        String[] day = date.split("/");
        String evenOdd = day[0];
        String order = day[3];
        String finalDay;

        if ((Integer.parseInt(evenOdd) % 2) == 1) {
            finalDay = "1" + order;
            confirmed.setValueAt(finalDay, 0, 3);
            confirmed.setValueAt(enterDate, 0, 2);
        } else {
            finalDay = "2" + order;
            confirmed.setValueAt(finalDay, 0, 3);
            confirmed.setValueAt(enterDate, 0, 2);
        }
    }

    /* This function filters through the text file containing all the teachers
     * and their schedules and displays the matches who are available
     * to act as a replacement */
    public void Find() {
        LinkedList<String> Reps;
        ArrayList<String> OnCall;
        ArrayList<String> oncallList;
        ArrayList<String> findSpare;
        Reps = TextAreaLoad.replacement;
        OnCall = TextAreaLoad.onCall;
        spares = TextAreaLoad.spares;
        int callLength = OnCall.size();

        String duty, repName, sparePeriod;
        String oncallDay = (String) confirmed.getValueAt(0, 3);
        String absentPeriods = (String) confirmed.getValueAt(0, 1);
        //String[] oncallDay = dayValue.split(",");
        String[] periodList = absentPeriods.split(",");
        oncallList = new ArrayList<>();
        oncallList.add(oncallDay);
        findSpare = new ArrayList<>(Arrays.asList(periodList));

        int index = 1;
        duty = OnCall.get(index);
        repName = Reps.get(index);
        sparePeriod = spares.get(index);

        // This loop runs through the teacher scehdule.txt file and stores teachers
        // into the array storeName if their schedule matches the data input from the user
        while (callLength > index) {
            for (int i = 0; i < findSpare.size(); i++) {
                if (duty.contains(oncallList.get(i))) {
                    if (sparePeriod.contains(findSpare.get(i))) {
                        storeName.addFirst((repName));
                        storeDuty.addFirst((duty));
                        storeSpare.addFirst((sparePeriod));
                    }
                }
            }
            index++;

            duty = OnCall.get(index);
            repName = Reps.get(index);
            sparePeriod = spares.get(index);

            if (storeName.size() > 5) {
                callLength = index - 1;
            }
            for (int i = 0; i < oncallDay.length(); i++) {
                if (index == callLength - 1 && !duty.contains(oncallDay)) {
                    callLength = index - 1;
                }
            }
        }
    }

    /* Function to reset the tables when performing another search */
    public void clearTables() {
        storeName.clear();
        storeDuty.clear();
        viewReps.setValueAt(" ", 0, 0);
        viewReps.setValueAt(" ", 1, 0);
        viewReps.setValueAt(" ", 2, 0);
        viewReps.setValueAt(" ", 3, 0);
    }
/// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~///

    /* This function allows you to select teachers from tab 2 and display them on tab 3 */
    public void selectReps() {
        // this sets the values of the top table to the infromation entered on tab 1
        missing.setValueAt(searchName, 0, 0);
        missing.setValueAt(enterPeriods, 0, 1);
        missing.setValueAt(enterDate, 0, 2);

        int buttonNum = Integer.parseInt(getSelectedButtonText(group1));

        //--------------------------------------------------------------//
        Object selection = " ";
        Object duty = " ";


        if (buttonNum == 1) {
            selection = storeName.get(0);
            duty = storeDuty.get(0);

        } else if (buttonNum == 2) {

            selection = storeName.get(1);
            duty = storeDuty.get(1);

        } else if (buttonNum == 3) {

            selection = storeName.get(2);
            duty = storeDuty.get(2);

        } else if (buttonNum == 4) {

            selection = storeName.get(3);
            duty = storeDuty.get(3);

        } else {
            selection = " ";
            duty = " ";

        }
        selections.setValueAt(selection, 0, 0);
        selections.setValueAt(duty, 0, 1);
        selections.setValueAt(enterDate, 0, 2);

    }

    /* This function detects what button was selected
     * Code borrowed from:
     * Ken Carney, Home and Learn
     * https://www.homeandlearn.co.uk/java/java_radio_buttons.html
     * Lines: */
    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }

    /* And finally, the main class where the GUI function is called and created.
     * It also contains instances of the components from the class, TextAreaLoad,
     * which actually does the whole reading and storing information from text files.
     * The app icon image used was found here:
     * https://en.wikipedia.org/wiki/Sir_Wilfrid_Laurier_Secondary_School_(Ottawa) */
    public static void main(String[] args) throws FileNotFoundException, IOException, NullPointerException {
        Gui gui = new Gui();
        TextAreaLoad tla = new TextAreaLoad();
        teacherNames = tla.teachers;
        spares = tla.spares;
        dates = tla.dates;
        tla.open();
        tla.read();
        tla.chooseFile(gui);
        tla.findReplacement();
        tla.openCalendar();
        tla.readCalendar();

        gui.setIconImage(Toolkit.getDefaultToolkit().getImage("/Swillancer.png"));
        gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gui.setVisible(true);
        gui.setSize(1200, 750);
    }


}

/*      TO DO
 *  set up filechooser --> open text file and write to another one
 *  get sources to code found online
 *  get sources to images
 *  COMMENT EVERYTHING!!!!
 *  add instructions/tips on tab 1 for user --> https://docs.oracle.com/javase/tutorial/uiswing/components/table.html#celltooltip
 *  change table on tab 2 to scrollable to view more teachers
 *  test exported program as .exe
 *  prepare presentation
 *  */


