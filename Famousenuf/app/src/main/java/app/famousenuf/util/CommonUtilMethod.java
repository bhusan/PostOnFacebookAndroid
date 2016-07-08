package app.famousenuf.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by bharatbhusan on 2/7/16.
 */
public class CommonUtilMethod {

    public static <T> T getObjectFromJson(Class<T> cl, String data) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(data, cl);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
