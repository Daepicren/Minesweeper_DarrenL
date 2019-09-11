/* Darren Liu
 * Creates a Mine object
 * June 12th, 2017
 */

class Mine extends Tile {


    Mine(){
        super();
    }

    // Returns a String that styles the element to red
    static String returnExplodingPressedStyle(){
        return "   -fx-background-color:  #ff0000;" +
                "  -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                "    -fx-font-family: \"Courier New\";\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-font-size: 18px;\n";
    }
}
