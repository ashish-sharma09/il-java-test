package co.uk.henry.promotion

import spock.lang.Specification

import java.nio.file.Path

class PromotionRepositoryImplSpec extends Specification {

    private PromotionRepository promotionRepository;

    def "error when promotions json file does not exist"() {
        when: "a non-existing file is provided"
        Path path = Path.of("nonExistingFile")
        promotionRepository = new PromotionRepositoryImpl(path)

        then:
        thrown(IllegalStateException)
    }
}
