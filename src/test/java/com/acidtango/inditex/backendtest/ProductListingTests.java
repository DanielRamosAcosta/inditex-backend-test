package com.acidtango.inditex.backendtest;

import com.acidtango.inditex.backendtest.store.orders.infrastructure.controllers.CreateOrderRequestDto;
import com.acidtango.inditex.backendtest.store.orders.infrastructure.controllers.OrderLinesDto;
import com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.CreateProductRequestDto;
import com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.CreateProductResponseDto;
import com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.GetProductsElementDto;
import com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.GetProductsResponseDto;
import com.acidtango.inditex.backendtest.store.stock.infrastructure.controllers.PostStockRequestDto;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static io.restassured.RestAssured.given;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class ProductListingTests {
    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @Test
    void lists_the_products_with_the_given_order_criteria() throws Exception {
        replicateExerciseStatementSetup();
    }

    private void replicateExerciseStatementSetup() {
        var product1Name = "V-NECH BASIC SHIRT";
        var product2Name = "CONTRASTING FABRIC T-SHIRT";
        var product3Name = "RAISED PRINT T-SHIRT";
        var product4Name = "PLEATED T-SHIRT";
        var product5Name = "CONTRASTING LACE T-SHIRT";
        var product6Name = "SLOGAN T-SHIRT";

        var productId1 = createProduct(product1Name);
        var productId2 = createProduct(product2Name);
        var productId3 = createProduct(product3Name);
        var productId4 = createProduct(product4Name);
        var productId5 = createProduct(product5Name);
        var productId6 = createProduct(product6Name);

        var products = given()
                .port(port)
                .get("/products")
                .getBody()
                .as(GetProductsResponseDto.class)
                .items();

        restockVariants(getProduct(productId1, products), 100, 4, 9, 0);
        restockVariants(getProduct(productId2, products), 50, 35, 9, 9);
        restockVariants(getProduct(productId3, products), 80, 20, 2, 20);
        restockVariants(getProduct(productId4, products), 3, 25, 30, 10);
        restockVariants(getProduct(productId5, products), 650, 0, 1, 0);
        restockVariants(getProduct(productId6, products), 20, 9, 2, 5);
    }

    private void restockVariants(GetProductsElementDto product, Integer salesUnits, Integer smallAmount, Integer mediumAmount, Integer largeAmount) {
        Integer id = product.id();
        Integer defaultVariantId = product.stock().small().id();
        restock(id, defaultVariantId, smallAmount + salesUnits);
        restock(id, product.stock().medium().id(), mediumAmount);
        restock(id, product.stock().large().id(), largeAmount);

        given()
                .port(port)
                .body(new CreateOrderRequestDto(List.of(new OrderLinesDto(id, defaultVariantId, salesUnits))))
                .contentType(ContentType.JSON)
                .post("/orders")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    private static GetProductsElementDto getProduct(Integer productId1, List<GetProductsElementDto> products) {
        return products
                .stream()
                .filter(p -> p.id().equals(productId1))
                .findFirst()
                .get();
    }

    private void restock(Integer productId1, Integer product1SmallVariantId, Integer amount) {
        given()
                .port(port)
                .body(new PostStockRequestDto(amount))
                .contentType(ContentType.JSON)
                .post("/products/" + productId1 + "/variants/" + product1SmallVariantId + "/stock")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    private Integer createProduct(String name) {
        return given()
                .port(port)
                .body(new CreateProductRequestDto(name))
                .contentType(ContentType.JSON)
                .post("/products")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(CreateProductResponseDto.class)
                .id();
    }
}
