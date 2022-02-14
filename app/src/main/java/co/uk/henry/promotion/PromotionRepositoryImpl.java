package co.uk.henry.promotion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class PromotionRepositoryImpl implements PromotionRepository {

    private final Path promotionsFilePath;
    private final ObjectMapper objectMapper;

    public PromotionRepositoryImpl(final Path promotionsFilePath, final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

        if (promotionsFilePath.toFile().exists()) {
            this.promotionsFilePath = promotionsFilePath;
        } else {
            throw new IllegalStateException("Promotions file does not exist");
        }
    }

    @Override
    public List<Promotion> getPromotions() {
        try {
            return new GsonBuilder()
                    .registerTypeAdapter(ValidityPeriod.class, new ValidityPeriodDeserializer())
                    .create().fromJson(
                            Files.newBufferedReader(promotionsFilePath),
                            new TypeToken<ArrayList<Promotion>>(){}.getType()
                    );

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static class ValidityPeriodDeserializer implements JsonDeserializer<ValidityPeriod> {
        @Override
        public ValidityPeriod deserialize(
                JsonElement json, Type typeOfT, JsonDeserializationContext context
        ) throws JsonParseException {
            return new ValidityPeriod(
                    Period.parse(json.getAsJsonObject().get("validFrom").getAsString()),
                    Period.parse(json.getAsJsonObject().get("validTo").getAsString()));
        }
    }
}
