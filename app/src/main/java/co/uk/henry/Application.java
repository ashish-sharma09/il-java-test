package co.uk.henry;

import co.uk.henry.basket.BasketService;
import co.uk.henry.basket.BasketServiceImpl;
import co.uk.henry.model.Item;
import co.uk.henry.product.ProductCSVRepositoryImpl;
import co.uk.henry.product.ProductService;
import co.uk.henry.product.ProductServiceImpl;
import co.uk.henry.promotion.PromotionRepositoryImpl;
import co.uk.henry.promotion.PromotionServiceImpl;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.lang.String.format;

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

    public static void main(String args[]) throws URISyntaxException {

        Scanner input = new Scanner(System.in);

        while (true) {
            Application application = new Application();
            final List<Item> items = application.productService.getItems();

            showItems(items);
            addBasketItems(application, items, input);
            showbasketPrice(application, input);
        }
    }

    private static void showItems(List<Item> items) {
        System.out.println("Welcome to Henry's Grocery (Press Ctrl C to exit anytime)");
        System.out.println("We have following items in stock:");

        System.out.println("------------------------------");
        System.out.println("Code Product    Unit    Cost");

        items.forEach(item -> {
            System.out.println(format("%s       %s     %s     %s",item.getCode(), item.getName(), item.getUnit(), item.getDisplayPrice()));
        });
        System.out.println("------------------------------");
    }

    private static void showbasketPrice(Application application, Scanner input) {
        boolean daysNotEntered = true;
        int days = 0;

        while(daysNotEntered) {
            System.out.print("To be bought in days: ");
            String dayString = input.nextLine();
            try {
                days = Integer.parseInt(dayString);
                daysNotEntered = false;
            } catch(NumberFormatException exception) {
                System.out.println("Please enter valid days");
                continue;
            }
        }

        System.out.println();
        System.out.println("Your basket price: " + application.basketService.getBasketFor(Period.ofDays(days)).getDisplayTotalCost());
        System.out.println();
        System.out.println();
    }

    private static void addBasketItems(Application application, List<Item> items, Scanner input) {
        final Map<String, Item> itemCodeToItem = items.stream().collect(Collectors.toMap(Item::getCode, item -> item));
        String enterMore = "yes";
        String itemCode = null;
        String itemQuantity = null;

        while ("yes".equalsIgnoreCase(enterMore)) {

            System.out.print("Enter item code: ");
            itemCode = input.nextLine();

            final Item item = itemCodeToItem.get(itemCode);

            if (item == null) {
                System.out.println("Please enter valid code");
                continue;
            }

            System.out.print("Enter quantity: ");
            itemQuantity = input.nextLine();

            try {
                final int quantity = Integer.parseInt(itemQuantity);
                application.basketService.add(item, quantity);

            } catch(NumberFormatException exception) {
                System.out.println("Please enter valid quantity");
                continue;
            }

            System.out.print("Enter another item(yes/no): ");
            enterMore = input.nextLine();
        }
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
