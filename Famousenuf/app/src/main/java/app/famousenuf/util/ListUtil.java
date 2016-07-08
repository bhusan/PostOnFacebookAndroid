package app.famousenuf.util;

import java.util.List;

/**
 * Created by bharatbhusan on 2/7/16.
 */
public class ListUtil {
    public static boolean isListEmpty(List<?> list) {
        if(list==null||list.isEmpty())
        {
            return true;
        }
        return false;
    }
}
