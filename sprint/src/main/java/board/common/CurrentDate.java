package board.common;

import java.util.Calendar;

public class CurrentDate {


    Calendar cal = Calendar.getInstance();

    public int year (){
        return cal.get( cal.YEAR);
    }
    public int month (){
        return cal.get( cal.MONTH)+1;
    }
    public int date (){
        return cal.get( cal.DATE);
    }


}
