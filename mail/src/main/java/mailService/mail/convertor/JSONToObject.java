package mailService.mail.convertor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class JSONToObject {

    final static Gson gson = new Gson();

    public static <T> T convertToObject(String fromValue, Class<T> target) {
        try {
            return gson.fromJson(fromValue, target);
        } catch (JsonSyntaxException exp) {
            exp.printStackTrace();
        }
        return null;
    }
}
