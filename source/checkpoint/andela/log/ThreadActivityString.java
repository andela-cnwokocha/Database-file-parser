package checkpoint.andela.log;

import java.text.*;
import java.util.*;

/**
 * Created by chidi on 1/20/16.
 */
public interface ThreadActivityString {

  default String threadActivity(String uniqueId, Object threadClass){
    Date currentTime = new Date();
    Locale myLocale = new Locale("en");
    String myTime = DateFormat.getTimeInstance(DateFormat.DEFAULT, myLocale).format(currentTime);
    String myDate = DateFormat.getDateInstance(DateFormat.DEFAULT, myLocale).format(currentTime);
    return (threadClass.toString() +" thread ("+myDate+" "+myTime+") wrote UNIQUE-ID -> "+uniqueId+" to buffer.\n");
  }
}
