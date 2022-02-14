package co.uk.henry.promotion

import com.google.gson.JsonSyntaxException
import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths
import java.time.Period

class PromotionRepositoryImplSpec extends Specification {

    private PromotionRepository promotionRepository;

    def "error when promotions json file does not exist"() {
        when: "a non-existing file is provided"
        Path path = Path.of("nonExistingFile")
        promotionRepository = new PromotionRepositoryImpl(path)

        then:
        thrown(IllegalStateException)
    }

    def "A single promotion can be parsed and provided from the file"() {
        given: "a promotions json file"
        def jsonFilePath = Paths.get(this.class.classLoader.getResource("promotions-with-no-max-quantity.json").toURI())
        promotionRepository = new PromotionRepositoryImpl(jsonFilePath)

        when: "promotions are fetched"
        def promotions = promotionRepository.getPromotions()

        then: "all promotions returned"
        promotions == [
                new Promotion (
                        "P1", PromotionType.MAIN, "1",
                        new Quantity(1), new Discount(DiscountType.MONEY, DiscountUnit.PERCENT, 10),
                        new ValidityPeriod(Period.parse("-P1D"), Period.parse("P7D"))
                )
        ]
    }

    def "when max quantity is not given then max quantity is max integer"() {
        given: "a promotions json file"
        def jsonFilePath = Paths.get(this.class.classLoader.getResource("promotions-1.json").toURI())
        promotionRepository = new PromotionRepositoryImpl(jsonFilePath)

        when: "promotions are fetched"
        def promotions = promotionRepository.getPromotions()

        then: "all promotions returned"
        promotions == [
                new Promotion (
                        "P1", PromotionType.MAIN, "1",
                        new Quantity(1,2), new Discount(DiscountType.MONEY, DiscountUnit.PERCENT, 10),
                        new ValidityPeriod(Period.parse("-P1D"), Period.parse("P7D"))
                )
        ]
    }

    def "A related main and sub promotion can be parsed and provided from the file"() {
        given: "a promotions json file containing main and sub promotions"
        def jsonFilePath = Paths.get(this.class.classLoader.getResource("promotions-2.json").toURI())
        promotionRepository = new PromotionRepositoryImpl(jsonFilePath)

        when: "promotions are fetched"
        def promotions = promotionRepository.getPromotions()

        then: "all promotions returned"
        promotions == [
                new Promotion (
                        "P2", PromotionType.SUB, "2",
                        new Quantity(1,1), new Discount(DiscountType.MONEY, DiscountUnit.PERCENT, 50),
                        ValidityPeriod.FOREVER
                ),
                new Promotion (
                        "P3", PromotionType.MAIN, "3",
                        new Quantity(1,1), new Discount(DiscountType.SUB, "P2"),
                        new ValidityPeriod(Period.parse("P3D"), Period.parse("P1M"))
                )
        ]
    }

    def "Error during file parsing is thrown as a RuntimeException"() {
        given: "a promotions json file containing promotions"
        def jsonFilePath = Paths.get(
                this.class.classLoader.getResource("incorrect-promotions.json").toURI()
        )
        promotionRepository = new PromotionRepositoryImpl(jsonFilePath)

        when: "promotions are fetched"
        promotionRepository.getPromotions()

        then: "exception is thrown"
        def runtimeException = thrown(RuntimeException)
        runtimeException instanceof JsonSyntaxException
    }
}
