package co.uk.henry.promotion;

import java.nio.file.Path;
import java.util.List;

public class PromotionRepositoryImpl implements PromotionRepository {

    private final Path promotionsFilePath;

    public PromotionRepositoryImpl(final Path promotionsFilePath) {
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