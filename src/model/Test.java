package model;


import java.time.LocalTime;
import java.time.Duration;

public class Test {
    public static void main(String[] args) {
        String timeString1 = "20:06:00";
        String timeString2 = "22:30:45";

        LocalTime time1 = LocalTime.parse(timeString1);
        LocalTime time2 = LocalTime.parse(timeString2);

        Duration duration = Duration.between(time1, time2);

        long diffInHours = duration.toHours();

        if (diffInHours >= 2) {
            System.out.println(diffInHours);
        } else {
            System.out.println("Difference: " + diffInHours + " hours");
        }
    }
}
