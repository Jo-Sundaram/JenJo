//  https://www.youtube.com/watch?v=lDqP5Y01ce0&t=567s

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class TextAreaLoad {
    Scanner s;
    Scanner c;
    static String Name;
    static ArrayList<String> teachers = new ArrayList<>();
    static ArrayList<String> list = new ArrayList<>();
    static ArrayList<String> onCall = new ArrayList<>();
    static ArrayList<String> replacement = new ArrayList<>();

    // opens Sample Names.txt
    public void open() {
        try {
            s = new Scanner(new File("Teacher Schedule.txt"));
            System.out.println("it is working");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    // reads through Sample Names.txt
    public void read() {
        do {
            Name = s.next();
            teachers.add(Name);
            s.nextLine();

        } while (s.hasNext());

        for (int i = 0; i < teachers.size(); i++)
            System.out.println(teachers.get(i));

    }


    // takes the information and searches for a replacement teacher
    public void findReplacement() throws FileNotFoundException{// Next step is to sort the On call days based on the date entered
        c = new Scanner(new File("Teacher Schedule.txt"));
        String last;
        String newTeacher;

        while (c.hasNextLine()) {
            //onCall.add(s.next());
            String[] tokens = c.nextLine().split(" ");
            newTeacher = tokens[0];
            replacement.add(newTeacher);
            last = tokens[tokens.length - 1];
            // System.out.println(last);
            onCall.add(last);
        }
        c.close();

    }

}


