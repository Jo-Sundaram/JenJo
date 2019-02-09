import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DataAccess {
    public static String[] teachers;
    public static String[] periods;
    public static ArrayList<String> periodList;
    public static String name;
    public static String date;
    public static String[] b;
    public static int dateNum;

    public static void main(String[] args) throws FileNotFoundException {
        DataAccess da = new DataAccess();
        da.readData();
        da.searchData();
        da.findReplacement();
        da.storeData();
    }

    // Read from text file (I actually don't really need this)
    public void readData() {
        int numLines = 0;

        IO.openInputFile("Sample Names.txt");
        String lines = IO.readLine();
        numLines++;

        while (lines != null) {
            numLines++;
            lines = IO.readLine();
        }
        IO.closeInputFile();

        teachers = new String[numLines];
        IO.openInputFile("Sample Names.txt");
        for (int i = 0; i < numLines; i++) {
            teachers[i] = IO.readLine();
        }
        IO.closeInputFile();

    }

    // search through text file for the absent teacher and enter information on teacher
    public void searchData() throws FileNotFoundException {
        Scanner key = new Scanner(System.in);
        Scanner s = new Scanner(new File("Sample Names.txt"));
        List<String> list = new ArrayList<String>();
        int index = 1;
            /* Skip column headings.
            Read each line, ensuring correct format.*/
        while (s.hasNext()) {
            //  s.nextLine();         // read and skip 'id'
            list.add(s.next()); // read and store 'name'
            s.nextLine();         // read and skip 'age'
        }
        //  System.out.println(name);
        name = list.get(index);
        int listLength = list.size();
        System.out.println("enter name to find:");
        String find = key.nextLine();
        while (listLength > 0) {
            if (name.contains(find)) {
                System.out.println(name);

                System.out.println("Enter the date of absence: ");
                date = key.nextLine();

                System.out.println("Enter the number of periods separated by space: ");

                periods = key.nextLine().split(" ");
                periodList = new ArrayList<>(Arrays.asList(periods));


                System.out.println("Absent Teacher: ");
                System.out.println(name + " " + date + "; " + periodList);
                listLength = -1;


            } else {
                index++;
                name = list.get(index);
            }

            if (index == listLength) {// Fix finding name not in text file
                System.out.println("name not found");
                listLength = -1;
            }
        }

    }

    public void determineOnCall() {
        boolean day1;
        boolean day2;

        b = date.split("");
        dateNum = Integer.parseInt(b[b.length - 1]);

        if (dateNum % 2 == 1) {
            day1 = true;
            day2 = false;
        } else {
            day2 = true;
            day1 = false;
        }


    }

    // takes the information and searches for a replacement teacher
    public void findReplacement() throws FileNotFoundException {// Next step is to sort the On call days based on the date entered
        Scanner s = new Scanner(new File("Sample Names.txt"));
        List<String> onCall = new ArrayList<>();
        List<String> replacement = new ArrayList<>();
        String last;
        String newTeacher;
        String duty;

        while (s.hasNextLine()) {
            //onCall.add(s.next());
            String[] tokens = s.nextLine().split(" ");
            newTeacher = tokens[0];
            replacement.add(newTeacher);
            last = tokens[tokens.length - 1];
            // System.out.println(last);
            onCall.add(last);
        }

        int index = 1;
        int callLength = onCall.size();
        duty = onCall.get(index);
        //find = replacement.get(index);

        while (callLength > index) {
            for (int i = 0; i < periods.length; i++) {
                if (duty.contains(periods[i])) {
                    System.out.println("Replacement Found: ");
                    System.out.println(replacement.get(index) + "," + duty);
                    System.out.println();
                }
            }
            index++;
            duty = onCall.get(index);

            for (int i = 0; i < periods.length; i++) {
                if (index == callLength - 1 && !duty.contains(periods[i])) {
                    callLength = index - 1;
                }
            }

        }

    }

    // Writes the information of the absent teacher and replacement teacher into another text file
    public void storeData() {
        IO.createOutputFile("Selections.txt", true);
        IO.println(name + " " + date + "; " + periodList);
        IO.closeOutputFile();


    }

}
