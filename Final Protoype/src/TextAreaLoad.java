//  https://www.youtube.com/watch?v=lDqP5Y01ce0&t=567s

import javax.print.attribute.standard.NumberUp;
import javax.swing.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

class TextAreaLoad {
    Scanner s;
    Scanner c;
    Scanner d;
    BufferedReader b;
    BufferedReader f;
    BufferedReader g;
    static String Name;
    static String fileName;
    static LinkedList<String> teachers = new LinkedList<>();
    static ArrayList<String> spares = new ArrayList<>();
    static ArrayList<String> onCall = new ArrayList<>();
    static LinkedList<String> replacement = new LinkedList<>();
    public static ArrayList<String> dates = new ArrayList<>();

    // Opens the text file "Teacher Schedule 2.txt"
 /*   public void open() {
        try {
            s = new Scanner(new File("Teacher Schedule 2.txt"));
            System.out.println("it is working");

        } catch (IOException e) {
            System.out.println("File not found");
        }
    }
*/

 /* Opens text file "Teacher Schedule 2.txt"
 * The InputStream code was borrowed from:
 * Alvin Alexander. Last updated: May 4 2017
 * https://alvinalexander.com/blog/post/java/read-text-file-from-jar-file
 * Lines: 42 - 47*/
    public void open() throws IOException, NullPointerException {
        InputStream inputStream = TextAreaLoad.class.getResourceAsStream("/Teacher Schedule 2.txt");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        b = new BufferedReader(inputStreamReader);
        s = new Scanner(b);
    }

    // reads through "Teacher Schedule 2.txt" and stores names in an arraylist
  /*public void read() {
        while (s.hasNextLine()) {
            String[] teacherNames = s.nextLine().split(" ");
            Name = teacherNames[0];
            teachers.add(Name);
        }
        s.close();
    }
*/

/* reads through "Teacher Schedule 2.txt" and stores names in an arraylist */
    public void read() throws IOException, NullPointerException {
        String line = null;
        while ((line = b.readLine()) != null) {
            System.out.println(line);
            String[] teacherNames =line.split(" ");
            Name = teacherNames[0];
            teachers.add(Name);
        }

    }


/* This function also stores the information of the teachers,
 * but it used to find matching replacements.
 * The InputStream code was borrowed from:
 * Alvin Alexander. Last updated: May 4 2017
 * https://alvinalexander.com/blog/post/java/read-text-file-from-jar-file
 * Lines: 80 - 83*/
    public void findReplacement() throws FileNotFoundException {// Next step is to sort the On call days based on the date entered
        InputStream inputStream = TextAreaLoad.class.getResourceAsStream("/Teacher Schedule 2.txt");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        g = new BufferedReader(inputStreamReader);
        c = new Scanner(g);
        String last;
        String spare;
        String newTeacher;

        while (c.hasNextLine()) {
            String[] tokens = c.nextLine().split(" ");
            newTeacher = tokens[0];
            replacement.add(newTeacher);
            last = tokens[2];
            spare = tokens[1];
            spares.add(spare);
            onCall.add(last);
        }
        c.close();

        for (int i = 0; i < teachers.size(); i++)
            System.out.println(teachers.get(i) + ", " + spares.get(i) + ", " + onCall.get(i));
    }

    /* Opens "Calendar.txt" */
   /* public void openCalendar() {
        try {
            d = new Scanner(new File("Calendar.txt"));
            //System.out.println("it is working");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
*/
    /* Opens "Calendar.txt"
    * The InputStream code was borrowed from:
     * Alvin Alexander. Last updated: May 4 2017
     * https://alvinalexander.com/blog/post/java/read-text-file-from-jar-file
     * Lines: 119 - 122 */
   public void openCalendar(){
       InputStream inputStream = TextAreaLoad.class.getResourceAsStream("/Calendar.txt");
       InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
       f = new BufferedReader(inputStreamReader);
       c = new Scanner(f);

   }
    /* Reads through "Calendar.txt" and stores dates in an array list */
  /*  public void readCalendar() {
        do {
            dates.add(d.nextLine());
        } while (d.hasNextLine());

    }
*/


    /* Reads through "Calendar.txt" and stores dates in an array list */
  public void readCalendar() throws IOException, NullPointerException{
      String line = null;
      while ((line = f.readLine()) != null) {
          dates.add(line);
      }

  }
    /* Unimplemented Feature:
     * Allow user to upload their own text files containing schedule and calendar
     * Code borrowed from:
     * http://www.java2s.com/Tutorials/Java/Swing_How_to/JFileChooser/Create_various_JFileChooser.htm */
    public void chooseFile(Gui gui) {
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(true);
        fc.setCurrentDirectory(new File("C:\\tmp"));
        Gui.upload.addActionListener(e -> {
            int retVal = fc.showOpenDialog(gui);
            if (retVal == JFileChooser.APPROVE_OPTION) {
                File[] selectedfiles = fc.getSelectedFiles();
                File file = fc.getSelectedFile();

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < selectedfiles.length; i++) {
                    sb.append(file.getPath());
                    sb.append("\n");
                }
                JOptionPane.showMessageDialog(gui, sb.toString());
                fileName = sb.toString();
                System.out.println(fileName);
            }
        });


    }


    /* Unimplemented feature: https://alvinalexander.com/blog/post/java/read-text-file-from-jar-file*/
    public String readFromJARFile(String filename) throws IOException {
        InputStream is = getClass().getResourceAsStream(filename);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        isr.close();
        is.close();
        return sb.toString();
    }

}


