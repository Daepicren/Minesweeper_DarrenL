import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

/* Darren Liu
 * Parent class of Mine and Block
 * June 12th, 2017
 */

class Tile extends Button {

    ToggleButton tile;
    int value = 0;
    int buttonID = 0;
    boolean clicked = false;
    boolean flagVisible;

    Tile() {
        tile = new ToggleButton();
    }


    //returns a grey unpressed String in CSS
    static String returnUnpressedTileStyle() {
        return "   -fx-background-color:  #DCDCDC;" +
                "  -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n";
    }

    //this CSS styling is used to easily distinguish the mine when the game is over
    static String returnGreyStyle() {
        return "   -fx-background-color:  #EEEEEE;" +
                "  -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n";
    }

    //this CSS String styles the label
    static String returnLabelStyle() {
        return "   -fx-background-color:  #ffffff;" +
                "  -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-font-family: \"Courier New\";\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-font-size: 11px;\n";
    }

}