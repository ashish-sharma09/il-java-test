package co.uk.henry;

import co.uk.henry.basket.BasketService;
import co.uk.henry.basket.BasketServiceImpl;
import co.uk.henry.product.ProductCSVRepositoryImpl;
import co.uk.henry.product.ProductService;
import co.uk.henry.product.ProductServiceImpl;
import co.uk.henry.promotion.PromotionRepositoryImpl;
import co.uk.henry.promotion.PromotionServiceImpl;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Application {

    private final BasketService basketService;
    private final ProductService productService;

    public Application() throws URISyntaxException {

        this.productService = new ProductServiceImpl(
                new ProductCSVRepositoryImpl(withPathFor("items.csv"))
        );

        this.basketService =
                new BasketServiceImpl(
                        new PromotionServiceImpl(
                                new PromotionRepositoryImpl(withPathFor("promotions.json"))
                        )
                );
    }

    public BasketService getBasketService() {
        return basketService;
    }

    public ProductService getProductService() {
        return productService;
    }

    private Path withPathFor(String productFile) throws URISyntaxException {
        return Paths.get(this.getClass().getClassLoader().getResource(productFile).toURI());
    }
}
