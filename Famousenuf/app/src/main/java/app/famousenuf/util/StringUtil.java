package app.famousenuf.util;

/**
 * Created by bharatbhusan on 2/7/16.
 */
public class StringUtil {
    public static boolean isNullOrEmpty(String s) {
        if (s == null || "".equals(s.trim())) {
            return true;
        }
        return false;
    }
}
