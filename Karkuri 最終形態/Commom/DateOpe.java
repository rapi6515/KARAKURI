package Commom;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateOpe {
	
    static DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    static Calendar cal = Calendar.getInstance();
    
    public static int datenIspecttion(String day) {

    	//日付OK
    	int status = 0;

    	sdf.setLenient(false); // 厳密な解析を設定
       
        if (day==null || day.isEmpty()) {
        	//未入力	
        	status=-1;
        }else {
        	if (day.length() !=10) {
        	//入力フォーマット不正	
        		status=-2;
        	}else {
		        try {
		        	day=day.replace("-", "/");
		        	Date date = sdf.parse(day);
		        } catch (ParseException e) {
		        	//暦日不正
		        	status=-9;
		        }
        	}
        }

        return status;
    }
    public static String dateInterval(String day,int interval,String type) {
    	
    	String retVal="";		//戻値
    	Date dt = null;			//date変数

    	sdf.setLenient(false); // 厳密な解析を設定
    	
    	try {
    		// Date型に変換
			dt = sdf.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
    	cal.setTime(dt);						//カレンダー設定
    	if (type.equals("day")) {
    		cal.add(Calendar.DATE, interval);	//カレンダー日を加減算
    	}else if (type.equals("momth")) {
    		cal.add(Calendar.MONTH, interval);	//カレンダー月を加減算
    	}else if (type.equals("year")) {
    		cal.add(Calendar.YEAR, interval);	//カレンダー年を加減算
    	}else if (type.equals("hour")) {
    		cal.add(Calendar.HOUR, interval);	//カレンダー時を加減算
    	}else if (type.equals("minute")) {
    		cal.add(Calendar.MINUTE, interval);	//カレンダー分を加減算
    	}
    	dt = cal.getTime();
    	retVal = sdf.format(dt);
    	
    	return retVal;
    }
    
    public static Date stingTodate(String day) {
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    	Date date = null;
    	
		try {
			date = sdf.parse(day);
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    	
    	return new Date(date.getTime());
    }
    
}