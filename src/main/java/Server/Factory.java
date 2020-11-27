package Server;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class that serialize to Json.
 * @author Isaac
 * @version 1.0
 * @since 29/10/2020
 */
public class Factory {
    static Logger logger=LogManager.getLogger("Abstract Factory");
    static ObjectMapper mapp = new ObjectMapper();

    /**
     * Serialize a object using the factory mapper.
     * @author Isaac
     * @version 1.0
     * @since 26/11/2020
     */
    public static String Serializer(Object Serial) throws JsonProcessingException {
        return mapp.writeValueAsString(Serial);
    }


}

