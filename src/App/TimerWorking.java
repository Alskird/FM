package App;

import javax.swing.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerWorking {

    private static int totalSecs = 0, hours, minutes, seconds;
    private static String timeString;
    private static JLabel timeLabel;

    TimerWorking(JLabel time) {
        Timer timer = new Timer();
        timeLabel = time;
        System.out.println("Приятной работы!");
        timer.scheduleAtFixedRate(new Сounter(), 1000, 1000);
    }

    static class Сounter extends TimerTask {
        public void run() {
            totalSecs++;
            hours = totalSecs / 3600;
            minutes = (totalSecs % 3600) / 60;
            seconds = totalSecs % 60;
            timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            System.out.println("Прошло: " + totalSecs);
            timeLabel.setText(timeString);
        }
    }

}
