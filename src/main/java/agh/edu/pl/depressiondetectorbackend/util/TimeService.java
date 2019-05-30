package agh.edu.pl.depressiondetectorbackend.util;

import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TimeService {

    public Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public Date getOffsetDate(Integer offset, Date baseDate) {
        Calendar calendar = new Calendar.Builder().setInstant(baseDate).build();
        calendar.add(Calendar.DATE, offset);
        return calendar.getTime();
    }

    public long calculateDiffDateInDays(Date baseDate, Date offsetDate) {
        final int dayInMilliseconds = 60 * 60 * 1000 * 24;
        return ((offsetDate.getTime() - baseDate.getTime()) / dayInMilliseconds);
    }

    public Date convertToDate(int year, int month, int day) {
        checkMonth(month);
        month--; // Calendar::Set count months from 0 to 11 instead from 1 to 12
        Calendar calendar = new Calendar.Builder().setDate(year, month, 1).build();
        checkDayOfMonth(calendar, day);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    private void checkMonth(int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be value from 1 to 12.");
        }
    }

    private void checkDayOfMonth(Calendar calendar, int day) {
        if (calendar.getActualMaximum(Calendar.DAY_OF_MONTH) < day) {
            throw new IllegalArgumentException("Day is bigger than maximum day in selected month.");
        }
        if (day < 1) {
            throw new IllegalArgumentException("Day must be bigger than 0.");
        }
    }
    public Date minimumDate (Date firstDate, Date secondDate)
    {
        if(firstDate.after(secondDate))
        {
            return secondDate;
        }
        else
        {
            return firstDate;
        }
    }
}
