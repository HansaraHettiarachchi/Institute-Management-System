package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Test {

    public static void main(String[] args) {
        String timeToCheck = "07:00:00";
        String startTime = "06:30:00";
        String endTime = "10:00:00";
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        try {
            Date checkTime = dateFormat.parse(timeToCheck);
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);
            
            
            if (checkTime.after(start) && checkTime.before(end)) {
                System.out.println(timeToCheck + " is within the range " + startTime + " - " + endTime);
            } else if (checkTime.equals(start) || checkTime.equals(end)) {
                System.out.println(timeToCheck + " is exactly at the start or end of the range " + startTime + " - " + endTime);
            } else {
                System.out.println(timeToCheck + " is not within the range " + startTime + " - " + endTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
