package co.uk.henry.promotion;

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
    private final Gson gson;

    public PromotionRepositoryImpl(final Path promotionsFilePath) {
        this.gson = new GsonBuilder()
            .registerTypeAdapter(ValidityPeriod.class, new ValidityPeriodDeserializer())
            .registerTypeAdapter(Quantity.class, new QuantityDeserializer())
            .create();

        if (promotionsFilePath.toFile().exists()) {
            this.promotionsFilePath = promotionsFilePath;
        } else {
            throw new IllegalStateException("Promotions file does not exist");
        }
    }

    @Override
    public List<Promotion> getPromotions() {
        try {
            return this.gson.fromJson(
                Files.newBufferedReader(promotionsFilePath),
                new TypeToken<ArrayList<Promotion>>(){}.getType()
            );
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static class ValidityPeriodDeserializer implements JsonDeserializer<ValidityPeriod> {
        @Override
        public ValidityPeriod deserialize(
            JsonElement json, Type typeOfT, JsonDeserializationContext context
        ) throws JsonParseException {
            final String validFrom = json.getAsJsonObject().get("validFrom").getAsString();
            final String validTo = json.getAsJsonObject().get("validTo").getAsString();

            if (validFrom.equals("na") && validTo.equals("na")) {
                return ValidityPeriod.FOREVER;
            }

            return new ValidityPeriod(Period.parse(validFrom), Period.parse(validTo));
        }
    }

    private static class QuantityDeserializer implements JsonDeserializer<Quantity> {
        @Override
        public Quantity deserialize(
            JsonElement json, Type typeOfT, JsonDeserializationContext context
        ) throws JsonParseException {
            final int minQuantity = json.getAsJsonObject().get("minQuantity").getAsInt();
            final JsonElement maxQuantityElementValue = json.getAsJsonObject().get("maxQuantity");

            if (maxQuantityElementValue == null) {
                return new Quantity(minQuantity);
            }
            final int maxQuantity = maxQuantityElementValue.getAsInt();
            return new Quantity(minQuantity, maxQuantity);
        }
    }
}
