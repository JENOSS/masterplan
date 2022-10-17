package board.common;

import java.util.Calendar;

public class CurrentDate {

    public int year (){
        return Calendar.getInstance().get(Calendar.YEAR);
    }
    public int month (){
        return Calendar.getInstance().get(Calendar.MONTH)+1;
    }
    public int date (){
        return Calendar.getInstance().get(Calendar.DATE);
    }


}
