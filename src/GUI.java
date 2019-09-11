import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Label;

/* Darren Liu
 * Main GUI
 * June 12th, 2017
 */

public class GUI extends Application{

    private Image Flag = new Image(getClass().getResourceAsStream("Flag.png"),25,25,true,true);
    private Image Mine = new Image(getClass().getResourceAsStream("Mine.png"),25,25,true, true);

    private static boolean lost = false;
    private static boolean firstRun = true;

    private static int totalButtons = 0;
    private static int verticalLength = 10;
    private static int horizontalLength = 10;
    private static int numFlags = 0;

    private static String temp;

    private static int[] mineIndexes;
    private static Tile[][] tiles;

    static Label timeElapsed;

    private static Label minesLeft,  finishMessage, highScoreLabel;
    private static Button restart;
    private static TextField enterSize, userName;
    private static GridPane tileGrid;

    private MyTimer timer;

    public static void main(String [] args){
        launch(args);
    }

    public void start(Stage myStage){

        //Using the startGame method prevents stackOverFlow errors
        startGame(myStage);

        //creates and starts the timer
        timer = new MyTimer();
        timer.start();

        //resets the game
        restart.setOnAction((event -> {

            //resets variables
            firstRun = false;
            lost = false;
            Highscore.scores.clear();
            totalButtons = 0;
            numFlags = 0;
            timer.stop();

            //if the user size is appropriate, restart the game with the new size
           if((getUserHeight()>=4)&&(getUserHeight()<=25)&&(getUserLength()>=4)&&(getUserLength()<=25)){
               temp = userName.getText();
               horizontalLength = getUserLength();
               verticalLength = getUserHeight();
               start(myStage);
           }
           else{
               finishMessage.setVisible(true);
               finishMessage.setText("Invalid Game Size");
           }
        }));
    }

    private void startGame(Stage myStage){
        myStage.setTitle("Minesweeper_DarrenL");
        myStage.setScene(createGameBoard());
        myStage.show();

        //this is used so that even when you press
        if(firstRun){
            userName.setPromptText("Enter Name");
        }
    }

    //returns the users desired game grid length inputted inside the label
    private int getUserLength(){
        String input = enterSize.getText();

        try {
            //gets everything before the x
            input = input.substring(0, input.indexOf('x'));
            input = input.replaceAll(" ","");
            return Integer.parseInt(input);

        }catch(Exception e){
            finishMessage.setVisible(true);
            finishMessage.setText("Invalid Game Size");
            return horizontalLength;
        }

    }

    //returns the users desired game grid height inputted inside the label
    private int getUserHeight(){
        String input = enterSize.getText();

        try {
            //gets everything past the x
            input = input.substring(input.indexOf('x'));
            input = input.replaceAll("[x ]","");
            return Integer.parseInt(input);

        }catch(Exception e){
            finishMessage.setVisible(true);
            finishMessage.setText("Invalid Game Size");
            return verticalLength;
        }
    }

    //creates the scene
    private Scene createGameBoard(){
        tileGrid = createTileHolder();
        VBox sideUI = createSideUIPanel();
        HBox UI = new HBox();
        UI.getChildren().addAll(tileGrid,sideUI);
        Scene gameBoard = new Scene(UI);
        return gameBoard;

    }

    //creates the GridPane along with initializing the buttons
    private GridPane createTileHolder(){

        //creates and stores n amount of random integers
        mineIndexes = returnMineIndexes();

        //initializes the size of the button array
        tiles = new Tile[verticalLength][horizontalLength];

        //initializes the pane to hold the buttons
        GridPane g = new GridPane();
        g.setVgap(1);
        g.setHgap(1);
        g.setPadding(new Insets(2,0,5,5));
        g.setAlignment(Pos.TOP_CENTER);

        //creates the Tiles inside the GridPane
        for(int y = 0; y<verticalLength; y++){

            for(int x = 0; x<horizontalLength; x++){

                //depending on what the buttonID of the tile that is about to created, it will create either a mine or a block accordingly
                tiles[y][x] = createTile();
                g.add(tiles[y][x], x, y);

            }
        }

        //adds values to the tiles within radius of a mine
        incrementTiles();

        return g;
    }

    //creates the side panel with elements
    private VBox createSideUIPanel(){
        VBox v = new VBox();
        v.setPadding(new Insets(2,0,5,5));
        v.setSpacing(1);
        v.setPrefWidth(130);

        minesLeft = new Label("Mines: "+mineIndexes.length);
        styleLabel(minesLeft);
        minesLeft.setAlignment(Pos.CENTER);

        timeElapsed = new Label("Time: 0");
        styleLabel(timeElapsed);
        timeElapsed.setAlignment(Pos.CENTER);

        finishMessage = new Label("");
        styleLabel(finishMessage);
        finishMessage.setAlignment(Pos.CENTER);
        finishMessage.setVisible(false);

        restart = new Button("New Game");
        styleLabel(restart);
        restart.setAlignment(Pos.CENTER);

        enterSize = new TextField();
        styleLabel(enterSize);
        enterSize.setAlignment(Pos.CENTER);
        enterSize.setPromptText(horizontalLength+" x "+verticalLength);

        //If in the scores.txt file there is a pre-existing high score display it, else display "No Record."
        String currentHighScore = Highscore.getHighScore(horizontalLength+"x"+verticalLength);
        if(!currentHighScore.equals( "No Record.")) {
            String highScoreName = currentHighScore.substring(currentHighScore.indexOf('/') + 1, currentHighScore.lastIndexOf('/'));
            String highScoreTime = currentHighScore.substring(0, currentHighScore.indexOf('/'));
            highScoreLabel = new Label(highScoreName + ": " + highScoreTime + "s");
        }
        else{
            highScoreLabel = new Label(currentHighScore);
        }
        styleLabel(highScoreLabel);
        highScoreLabel.setAlignment(Pos.CENTER);

        userName = new TextField(temp);
        styleLabel(userName);
        userName.setAlignment(Pos.CENTER);

        //sets the max number of characters to 3
        userName.setOnKeyReleased((event -> {
            if (userName.getText().length() > 3) {
                String s = userName.getText().substring(0, 3);
                userName.setText(s);
            }
        }));

        v.getChildren().addAll(restart, userName, enterSize, minesLeft,timeElapsed, highScoreLabel, finishMessage);
        return v;
    }

    //Styles the JLabel/textfield to a uniform CSS styling
    private static Control styleLabel(Control x){
        x.setStyle(Tile.returnLabelStyle());
        x.setPadding(new Insets(2,10,5,1));
        x.setMinSize(130,50);
        return x;
    }

     //adds values to the tiles within radius of a button
    private static void incrementTiles(){
        int posX, posY;

        for( int i=0; i<mineIndexes.length; i++){
            //saves the x,y co-ordinates of current mine
            posX = getColumn(mineIndexes[i]);
            posY = getRow(mineIndexes[i]);

            //if any button within a one tile radius is instance
            // of a Block (or not a mine) increase its value
            for(int y=-1; y<2; y++){

                for(int x=-1; x<2 ; x++){

                    try{
                        if(tiles[posY + y][posX + x] instanceof Block){
                            tiles[posY + y][posX + x].value++;
                        }
                    }catch (ArrayIndexOutOfBoundsException e) {}

                }
            }
        }
    }

    //creates either a mine or a block depending on if
    // the ButtonID is a number randomly generated to be designated a mine
    private Tile createTile(){
        if(isIndexMine(totalButtons)){

            //creates a mine

            Mine m = new Mine();
            m.setStyle(m.returnUnpressedTileStyle());
            m.setMinSize(50, 50);
            m.value = -1;
            m.buttonID = totalButtons;
            totalButtons++;

            m.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    MouseButton button = event.getButton();
                    if (!m.flagVisible) {
                        if (!m.clicked) {

                            if (button == MouseButton.PRIMARY) {

                                lost = true;
                                revealMines();

                                m.setStyle(m.returnExplodingPressedStyle());
                                m.clicked = true;
                                m.setGraphic(new ImageView(Mine));

                                tileGrid.setDisable(true);
                                finishMessage.setVisible(true);
                                timer.stop();
                                finishMessage.setText("It took you \n"+timer.time+"s to lose!");


                            } else if (button == MouseButton.SECONDARY){
                                incrementFlag(m);
                            }
                        }
                    }
                    else{
                        removeFlag(m);
                    }
                }
            });

            return m;
        }

        else{
            //creates a block
            Block b = new Block();
            b.setStyle(b.returnUnpressedTileStyle());
            b.setMinSize(50, 50);
            b.buttonID = totalButtons;
            totalButtons++;
            b.setOnAction((event) -> {

                if(!b.flagVisible) {
                    if (!b.clicked) {
                        if (b.value > 0) {
                            b.setText("" + b.value);
                            b.clicked = true;
                            if((victoryOccurred())&&(!lost)){
                                victory();
                            }
                        } else if (b.value == 0) {
                            clickSurrounding(b.buttonID);
                            b.clicked = true;
                            if((victoryOccurred())&&(!lost)){
                                victory();
                            }
                        }
                    }
                    b.setStyle(b.returnPressedStyle());
                }
                else{
                    removeFlag(b);
                }

            });

            //this second action listener specifically listens for a right click, and the reason why I have .setOnAction and .setOnMouseClicked and why I cant just
            //check for the primaryButton click is because using the clickSurrounding() by telling a button to fire, it wont trigger because no physical user actually
            //clicked the button
            b.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    MouseButton button = event.getButton();
                    //adds a flag
                    if((button == button.SECONDARY)&&(!b.clicked)&&(!b.flagVisible)){
                        incrementFlag(b);
                    //removes a flag
                    }else if ((button == button.SECONDARY)&&(b.flagVisible)&&(!b.clicked)){
                        removeFlag(b);
                    }

                }
            });

            return b;
        }
    }

    //checks if the given index is a mine by looping through the mineIndexes array
    private boolean isIndexMine(int index){
        for(int i = 0; i<mineIndexes.length; i++){
            if(index == mineIndexes[i]){
                return true;
            }
        }

        return false;
    }

    //randomly selects n amounts of mines and returns them in an array
    private int[] returnMineIndexes() {
        int rdmInt, counter;
        boolean repeat = true;

        mineIndexes = new int[Math.abs(verticalLength * horizontalLength / 8)];

        for (int i = 0; i < mineIndexes.length; i++) {

            do {

                //creates a random integer
                rdmInt = ((int) (Math.random() * (0 - (verticalLength * horizontalLength)) + (verticalLength * horizontalLength)));

                counter = 0;

                //checks previous elements of the array to prevent duplicate mines
                for (int k = 0; k < i; k++) {
                    if (rdmInt != mineIndexes[k]) {
                        counter++;
                    }
                }

                //if the element is not the same as any previous element, add it to the array
                if (counter == (i)) {
                    mineIndexes[i] = rdmInt;
                    repeat = false;
                }
            }
            while (repeat);
        }
        return mineIndexes;
    }

    //clicks the surrounding tiles
    private void clickSurrounding(int index){

        //gets the x,y co-ordinates of the given tile
        int posX = getColumn(index);
        int posY = getRow(index);

        //prevents the tile from clicking itself
        try {
            tiles[posY][posX].clicked = true;
        }
        catch(ArrayIndexOutOfBoundsException e){}

        for(int y = -1; y<2; y++){
            for(int x = -1; x<2; x++){
                try{
                    //if the button is un-clicked, and is not itself click the button
                    if(!tiles[posY + y][posX +x].clicked){
                        if(!tiles[posY][posX].equals(tiles[posY + y][posX + x])){
                            if(tiles[posY+y][posX+x].flagVisible){
                                removeFlag(tiles[posY+y][posX+x]);
                            }
                            tiles[posY+y][posX+x].fire();

                        }
                    }
                }catch(ArrayIndexOutOfBoundsException e){}
            }
        }
    }

    //reveals all the mines without pressing them (used for the game over sequence)
    private void revealMines(){

        for(int y=0; y<verticalLength; y++){
            for(int x=0; x<horizontalLength; x++){
                if(tiles[y][x] instanceof Block) {
                    tiles[y][x].fire();
                }
                else{
                    tiles[y][x].setStyle(Tile.returnGreyStyle());
                    tiles[y][x].setGraphic(new ImageView(Mine));
                }
            }
        }
    }

    //returns the y pos in the grid
    private static int getRow(int index) {
        return ((index - (index % horizontalLength)) / horizontalLength);
    }

    //returns the x pos in the grid
    private static int getColumn(int index) {
        return index % horizontalLength;
    }

    //adds a flag onto the tile
    private void incrementFlag(Tile x){
        if(numFlags < mineIndexes.length) {
            x.setGraphic(new ImageView(Flag));
            x.flagVisible = true;
            numFlags++;
            minesLeft.setText("Mines: "+(mineIndexes.length-numFlags));
        }
    }

    //removes a flag onto the tile
    private void removeFlag(Tile x){
        if(numFlags > 0) {
            x.setGraphic(null);
            x.flagVisible = false;
            numFlags--;
            minesLeft.setText("Mines: "+(mineIndexes.length-numFlags));
        }
    }

    //checks if there is a victory
    private static boolean victoryOccurred(){

        int TOTAL_TILES = (horizontalLength*verticalLength-mineIndexes.length);
        int tilesCorrect = 0;

        //loops through every tile checking for how many have been clicked
        for(int y = 0; y<verticalLength; y++){
            for(int x = 0; x<horizontalLength; x++){

                if(tiles[y][x] instanceof Mine){
                    if(tiles[y][x].clicked){
                        return false;
                    }
                }else if(tiles[y][x] instanceof Tile) {
                    if(tiles[y][x].clicked){
                        tilesCorrect++;
                    }
                }
            }
        }
        if(tilesCorrect == TOTAL_TILES){
            return true;
        }
        return false;
    }

    //stops the game, timer and saves the score
    private void victory(){
        //prevents victory() from triggering multiple times
        if(!lost) {
            lost = true;
            timer.stop();
            String name = userName.getText();


            //checks if the name is valid
            if ((name == null) && (name.length()!=3)){
                name = "UNK";
            }

            //creates a Highscore object and checks if it is better than the current high score
            Highscore score = new Highscore(timer.time, name, horizontalLength, verticalLength);
            score.changeHighScore();

            finishMessage.setVisible(true);
            finishMessage.setText("You finished in\n " + timer.time + "s!");
            tileGrid.setDisable(true);
        }
    }

}
