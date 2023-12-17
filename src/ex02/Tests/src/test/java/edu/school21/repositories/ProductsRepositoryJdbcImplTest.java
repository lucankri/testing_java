package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImplTest {
    private ProductsRepository productsRepository;

    private final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
            new Product(1L, "Winston", BigDecimal.valueOf(200)),
            new Product(2L, "Kent", BigDecimal.valueOf(160)),
            new Product(3L, "Bond", BigDecimal.valueOf(170)),
            new Product(4L, "Philip Morris Compact", BigDecimal.valueOf(150)),
            new Product(5L, "Parliament", BigDecimal.valueOf(270)),
            new Product(6L, "LD Autograph", BigDecimal.valueOf(138)),
            new Product(7L, "Донской Табак", BigDecimal.valueOf(126)),
            new Product(8L, "Richmond", BigDecimal.valueOf(194))
    );
    private final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(5L, "Parliament", BigDecimal.valueOf(270));
    private final Product EXPECTED_UPDATED_PRODUCT = new Product(4L, "Philip Morris", BigDecimal.valueOf(184));
    private final Product EXPEXTED_SAVE_PRODUCT = new Product(13L, "Marlboro", BigDecimal.valueOf(210));


    @BeforeEach
    public void init() {
        productsRepository = new ProductsRepositoryJdbcImpl(new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build());
    }

    @Test
    public void findALLTest() {
        Assertions.assertEquals(EXPECTED_FIND_ALL_PRODUCTS, productsRepository.findAll());
    }

    @Test
    public void findByIdTest() {
        Assertions.assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, productsRepository.findById(EXPECTED_FIND_BY_ID_PRODUCT.getId()).get());
    }

    @Test
    public void updateTest() {
        productsRepository.update(new Product(EXPECTED_UPDATED_PRODUCT.getId(), EXPECTED_UPDATED_PRODUCT.getName(),
                EXPECTED_UPDATED_PRODUCT.getPrice()));
        Assertions.assertEquals(EXPECTED_UPDATED_PRODUCT, productsRepository.findById(EXPECTED_UPDATED_PRODUCT.getId()).get());
    }

    @Test
    public void saveTest() {
        productsRepository.save(new Product(EXPEXTED_SAVE_PRODUCT.getId(), EXPEXTED_SAVE_PRODUCT.getName(),
                EXPEXTED_SAVE_PRODUCT.getPrice()));
        Assertions.assertEquals(EXPEXTED_SAVE_PRODUCT, productsRepository.findById(EXPEXTED_SAVE_PRODUCT.getId()).get());
    }

    @Test
    public void deleteTest() {
        productsRepository.delete(2L);
        Assertions.assertEquals(Optional.empty(), productsRepository.findById(2L));
    }
}