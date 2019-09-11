import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


/* Darren Liu
 * IO Class
 * June 17th, 2017
 */

class IO
{
    private static PrintWriter fileOut;
    private static BufferedReader fileIn;

    static void createOutputFile(String fileName)
    {
        try
        {
            fileOut = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
        }
        catch (IOException e)
        {
            System.out.println("*** Cannot create file: " + fileName + " ***");
        }
    }

    static void appendOutputFile(String fileName)
    {
        try
        {
            fileOut = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
        }
        catch (IOException e)
        {
            System.out.println("*** Cannot open file: " + fileName + " ***");
        }
    }

    static void println(String text)
    {
        fileOut.println(text);
    }

    static void closeOutputFile()
    {
        fileOut.close();
    }

    static void openInputFile(String fileName)
    {
        try
        {
            fileIn = new BufferedReader(new FileReader(fileName));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("***Cannot open " + fileName + "***");
        }
    }

    static String readLine()
            throws IOException
    //Note: if there's an error in this method it will return IOException
    {
        return fileIn.readLine();
    }

    static void closeInputFile()
            throws IOException
    //Note: if there's an error in this method it will return IOException
    {
        try {
            fileIn.close();
        } catch (IOException e){
            System.out.println("Error while closing input file.");
        }
    }
}