package lab1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;

public final class Transformer {

    public static <T> String serialize(T t) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.writeValueAsString(t);
    }

    public static <T> T desirialize(String data, Class<T> clazz) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        // deserialize from the XML into a Phone object
        return xmlMapper.readValue(data, clazz);
    }

    private Transformer() {

    }
}
