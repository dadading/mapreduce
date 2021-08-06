package top10;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 */

public class DateUtils {
    private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

    public static String transDateFormat(String dt) {
        String res = "1970-01-01";
        Date date = null;
        try {
            date = sdf1.parse(dt);
            res = sdf2.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("日期转化失败" + dt);
        }

        return res;
    }
}
