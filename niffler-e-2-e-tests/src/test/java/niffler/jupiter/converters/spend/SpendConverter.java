package niffler.jupiter.converters.spend;

import com.fasterxml.jackson.databind.ObjectMapper;
import niffler.data.json.SpendJson;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class SpendConverter implements ArgumentConverter {

    private final ClassLoader cl = this.getClass().getClassLoader();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public SpendJson convert(final Object source, final ParameterContext context) throws ArgumentConversionException {
        if (source instanceof String) {
            try (final InputStream resource = cl.getResourceAsStream((String) source);
                 final InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(resource))) {
                return mapper.readValue(reader, SpendJson.class);
            } catch (IOException e) {
                throw new ArgumentConversionException("Failed to convert", e);
            }
        }
        throw new ArgumentConversionException("Required value type - String");
    }
}
