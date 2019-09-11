
import javafx.animation.AnimationTimer;

/* Darren Liu
 * Creates a timer object
 * June 13th, 2017
 */

public class MyTimer extends AnimationTimer{

    int time;
    private long timestamp, fraction;

    //starts the timer
    public void start() {
        time = 0;
        fraction = 0;
        timestamp = System.currentTimeMillis() - fraction;
        super.start();
    }

    //stops the timer
    public void stop() {
        super.stop();
        fraction = System.currentTimeMillis() - timestamp;
    }

    public void handle(long now) {
        long newTime = System.currentTimeMillis();
        if (timestamp + 1000 <= newTime) {
            long deltaT = (newTime - timestamp) / 1000;
            time += deltaT;
            timestamp += 1000 * deltaT;
            GUI.timeElapsed.setText("Time: " + (Long.toString(time)));
        }
    }
}

