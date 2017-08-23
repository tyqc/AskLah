package com.gillyweed.android.asklah;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Envy 15 on 17/7/2017.
 */

public class ConvertDateTime {

    public static String convertTime(String dateTimeString)
    {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateTime dateTime = new DateTime(date);

        return dateTime.toString("dd/MM/yyyy HH:mm aa");
    }

    public static String convertTimeNotification(String dateTimeString)
    {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateTime dateTime = new DateTime(date);

        return dateTime.toString("dd/MM/yyyy HH:mm aa");
    }

    public static Date convertToDate(String dateTimeString)
    {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static int compareDate(String date1, String date2)
    {
        Date dateObj1 = null;

        Date dateObj2 = null;

        try
        {
            dateObj1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").parse(date1);

            dateObj2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").parse(date2);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return dateObj1.compareTo(dateObj2);
    }
}
