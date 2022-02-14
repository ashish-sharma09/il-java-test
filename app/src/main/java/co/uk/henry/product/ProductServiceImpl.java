package co.uk.henry.product;

import co.uk.henry.model.Item;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Item> getItems() {
        try {
            return productRepository.getProducts();
        } catch(Exception exception) {
            throw new ProductServiceException("Error from product repository", exception);
        }
    }
}
