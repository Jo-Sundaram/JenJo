
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class IO {

    // Variables and methods needed for writing to a file

    private static PrintWriter fileOut; //<-- creates textfile in folder

    public static void createOutputFile(String filename) /*<-- CREATES new file (filename)
								 in current folder and places a reference in the object "fileOut"
								 parameter fileName represents the name of the text file*/
    {
        createOutputFile(filename,false);//<-- Calls this function that clears and
        //reWrites file using boolean
        try {
            fileOut = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
        }
        catch(IOException e) {
            System.out.println("***Cannot create file: " + filename + " ***");
        }
    }

    public static void createOutputFile(String fileName, boolean append) { /*<-- This creates a new file (fileName)
	 	in the current folder and places reference in fileOut. 
	 	Appends TRUE if you want to add to existing file. 
	 	Appends FALSE if you want to clear and reWrite existing file
	 	 */

        try { //<-- tries to make new textfile, if there is an error, will display message
            fileOut = new PrintWriter(new BufferedWriter(new FileWriter(fileName, append)));
        }
        catch(IOException e) {
            System.out.println("***Cannot create file: " + fileName + " ***");
        }
    }

    public static void print(String text) /* <-- Does the same function as System.out.print() but writes
	directly to the current text file
	String text - characters that will be added to the file	
	*/
    {
        fileOut.print(text);
    }


    public static void println(String text)
        /* Text is added and new line is inserted at end of characters
         * */
    {
        fileOut.println(text);
    }

    /*Closes file that is being written to
     * MUST BE CALLED WHEN YOU ARE FINISHED WRITING IN ORDER TO SAVE THE FILE
     */

    /*VARAIBLES AND METHODS NEEDED FOR READING FROM A FILE*/

    public static void closeOutputFile()
    {
        fileOut.close();
    }

    private static BufferedReader fileIn;
    /*Opens a file called (fileName) and places a reference to it in the object fileIn
     * This file must be in the current folder
     */

    public static void openInputFile(String fileName)
    {
        try {
            fileIn = new BufferedReader(new FileReader(fileName));
        }
        catch(FileNotFoundException e) {
            System.out.println("***Cannot open " + fileName + " ***");
        }
    }


    /*
     * Reads the next line from the file and returns it to be stored in a String
     */
    public static String readLine()
    {
        try {
            return fileIn.readLine();
        }
        catch(IOException e) {
            return null;
        }
    }


    /*
     * Closes file that is currently being read from
     */
    public static void closeInputFile()
    {
        try {
            fileIn.close();
        }
        catch(IOException e) {}
    }
}
