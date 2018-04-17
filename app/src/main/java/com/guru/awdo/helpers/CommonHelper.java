package com.guru.awdo.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Guru on 17-04-2018.
 */
public class CommonHelper {

    private SimpleDateFormat lSimpleDateFormatOfHourMinute = new SimpleDateFormat("yyyy/MM/dd/HH/mm");

    public String longDateToFormattedDate(Long pLongDate)
    {
        String lDateString;
        Date lDate = new Date(pLongDate);
        lDateString = lSimpleDateFormatOfHourMinute.format(lDate);
        return lDateString;
    }
}
