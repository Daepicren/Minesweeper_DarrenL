import java.io.IOException;
import java.util.ArrayList;

/* Darren Liu
 * Creates a highscore object
 * June 17th, 2017
 */

public class Highscore {

    private String name;
    private int xPos, yPos, time;
    static ArrayList scores = new ArrayList();

    //creates a Highscore item
    Highscore(int seconds, String nick, int x, int y) {
        time = seconds;
        name = nick.toUpperCase();
        xPos = x;
        yPos = y;
    }

    //returns the object highscore as a string
    public String toString() {
        return time+ "/" + name+"/"+xPos+"x"+yPos;
    }

    //stores the files data
    private static void getData(){
        IO.openInputFile("scores.txt");
        String line;
        try{
            while((line = IO.readLine()) != null){
                scores.add(line);
            }
        }catch (IOException e){}

        try {
            IO.closeInputFile();
        }catch(IOException e){}
    }

    //returns the current highscore for the current selected size
    static String getHighScore(String size){
        getData();
        for(int i=0; i<scores.size(); i++){
            String element = (String) scores.get(i);
            element = element.substring(element.lastIndexOf('/'));
            element = element.replaceAll("[/ ]","");

            if(element.equals(size)){
                return (String) scores.get(i);
            }
        }
        return "No Record.";
    }


    //prints the array list into scores.txt
    private static void printIntoDocument(){
        IO.createOutputFile("scores.txt");
        for(int i = 0; i<scores.size(); i++){
            IO.println((String)scores.get(i));
        }
        IO.closeOutputFile();
    }

    //checks if the new score was done in less time than the current high score and switches accordingly
    void changeHighScore(){

        scores.indexOf("6x6");
        int index = -1;

        for(int i = 0; i<scores.size(); i++) {

            String line = (String) scores.get(i);
            //gets the size of the current line;
            String size = line.substring(line.lastIndexOf('/'));
            size = size.replaceAll("[/ ]", "");

            //if the given score is the same size as the selected line
            if ((xPos + "x" + yPos).equals(size)) {
                index = i;
            }
        }
        //gets the seconds from the HighScore to compare to the new score
        if(index > -1){
            String element = (String) scores.get(index);
            element = element.substring(0, element.indexOf('/'));

            int seconds = Integer.parseInt(element);

            if(time < seconds){
                scores.set(index, toString());
                System.out.println("CHANGE DETECTED.");
                printIntoDocument();
            }
            else{
                System.out.println("NO CHANGE DETECTED.");
            }

        }else{
            System.out.println("CHANGE DETECTED.");
            scores.add(toString());
            printIntoDocument();
        }
    }
}
