/* Darren Liu
 * Creates a Block object
 * June 12th, 2017
 */

class Block extends Tile {

    Block(){
        super();
    }

    // Returns a white button styling that appears flat or depending on the number a color
    String returnPressedStyle() {
            switch (value){
                case 1: return "   -fx-background-color:  #ffffff;" +
                        "  -fx-background-radius: 5,4,3,5;\n" +
                        "    -fx-background-insets: 0,1,2,0;\n" +
                        "    -fx-font-family: \"Courier New\";\n" +
                        "    -fx-text-fill: blue;\n" +
                        "    -fx-font-size: 18px;\n";
                case 2: return "   -fx-background-color:  #ffffff;" +
                        "  -fx-background-radius: 5,4,3,5;\n" +
                        "    -fx-background-insets: 0,1,2,0;\n" +
                        "    -fx-font-family: \"Courier New\";\n" +
                        "    -fx-text-fill: green;\n" +
                        "    -fx-font-size: 18px;\n";
                case 3: return "   -fx-background-color:  #ffffff;" +
                        "  -fx-background-radius: 5,4,3,5;\n" +
                        "    -fx-background-insets: 0,1,2,0;\n" +
                        "    -fx-font-family: \"Courier New\";\n" +
                        "    -fx-text-fill: red;\n" +
                        "    -fx-font-size: 18px;\n";
                case 4: return "   -fx-background-color:  #ffffff;" +
                        "  -fx-background-radius: 5,4,3,5;\n" +
                        "    -fx-background-insets: 0,1,2,0;\n" +
                        "    -fx-font-family: \"Courier New\";\n" +
                        "    -fx-text-fill: indigo;\n" +
                        "    -fx-font-size: 18px;\n";
                case 5: return "   -fx-background-color:  #ffffff;" +
                        "  -fx-background-radius: 5,4,3,5;\n" +
                        "    -fx-background-insets: 0,1,2,0;\n" +
                        "    -fx-font-family: \"Courier New\";\n" +
                        "    -fx-text-fill: maroon;\n" +
                        "    -fx-font-size: 18px;\n";
                case 6: return "   -fx-background-color:  #ffffff;" +
                        "  -fx-background-radius: 5,4,3,5;\n" +
                        "    -fx-background-insets: 0,1,2,0;\n" +
                        "    -fx-font-family: \"Courier New\";\n" +
                        "    -fx-text-fill: cyan;\n" +
                        "    -fx-font-size: 18px;\n";
                case 7: return "   -fx-background-color:  #ffffff;" +
                        "  -fx-background-radius: 5,4,3,5;\n" +
                        "    -fx-background-insets: 0,1,2,0;\n" +
                        "    -fx-font-family: \"Courier New\";\n" +
                        "    -fx-text-fill: black;\n" +
                        "    -fx-font-size: 18px;\n";
                case 8: return "   -fx-background-color:  #ffffff;" +
                        "  -fx-background-radius: 5,4,3,5;\n" +
                        "    -fx-background-insets: 0,1,2,0;\n" +
                        "    -fx-font-family: \"Courier New\";\n" +
                        "    -fx-text-fill: grey;\n" +
                        "    -fx-font-size: 18px;\n";
                default: return "   -fx-background-color:  #ffffff;" +
                        "  -fx-background-radius: 5,4,3,5;\n" +
                        "    -fx-background-insets: 0,1,2,0;\n" +
                        "    -fx-font-family: \"Courier New\";\n" +
                        "    -fx-text-fill: black;\n" +
                        "    -fx-font-size: 18px;\n";
            }

    }

}