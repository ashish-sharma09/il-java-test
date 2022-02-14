package co.uk.henry.promotion;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.util.List;

public class PromotionRepositoryImpl implements PromotionRepository {

    private final Path promotionsFilePath;
    private final ObjectMapper objectMapper;

    public PromotionRepositoryImpl(final Path promotionsFilePath, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

        if (promotionsFilePath.toFile().exists()) {
            this.promotionsFilePath = promotionsFilePath;
        } else {
            throw new IllegalStateException("Promotions file does not exist");
        }
    }

    @Override
    public List<Promotion> getPromotions() {
        return null;
    }
}
