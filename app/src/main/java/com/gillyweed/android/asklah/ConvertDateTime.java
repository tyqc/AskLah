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
}
