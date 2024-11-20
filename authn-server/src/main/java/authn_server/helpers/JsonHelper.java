package authn_server.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;

/* This class is used to serialize and deserialize requests
     Serialize -> Converting  an object into Json
     Deserialize -> Converting Json into an object
 */
public class JsonHelper {

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T asObject(final String str, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(str, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
