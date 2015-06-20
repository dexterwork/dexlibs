import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateTest {

	public DateTest() {
		Calendar cal = Calendar.getInstance();
	    System.out.println("Now:"+cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DATE));
	    cal.set(Calendar.MONTH,1);
	    cal.set(Calendar.DATE,28);
	    System.out.println("Modify To:"+cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DATE));
	    cal.add(Calendar.DATE,1);
//	    System.out.println("add 1 Day:"+cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DATE));
	    
	    String str=cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DATE);
	    
	    DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
	    
	    try {
			Date date=df.parse(str);
			
			
			
			
			String formateDate=df.format(date);
			System.out.println("final date:"+formateDate);
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}

}
