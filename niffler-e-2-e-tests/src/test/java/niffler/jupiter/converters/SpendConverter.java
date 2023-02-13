package niffler.jupiter.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import niffler.model.SpendJson;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

import java.io.IOException;
import java.io.InputStream;

public class SpendConverter implements ArgumentConverter {

    private final ClassLoader cl = this.getClass().getClassLoader();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public SpendJson convert(Object source, ParameterContext context) throws ArgumentConversionException {
        if(source instanceof String){
            try (InputStream resourceAsStream = cl.getResourceAsStream(String.valueOf(source))) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        throw new ArgumentConversionException("");
    }
}
