package com.acidtango.inditex.backendtest;

import com.acidtango.inditex.backendtest.store.orders.infrastructure.controllers.PostOrderController.DTO.CreateOrderRequestDto;
import com.acidtango.inditex.backendtest.store.orders.infrastructure.controllers.PostOrderController.DTO.OrderLinesDto;
import com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.GetProductsController.DTO.GetProductsElementDto;
import com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.GetProductsController.DTO.GetProductsResponseDto;
import com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.PostProductController.DTO.PostProductRequestDto;
import com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.PostProductController.DTO.PostProductResponseDto;
import com.acidtango.inditex.backendtest.store.stock.infrastructure.controllers.PostStockController.DTO.PostStockRequestDto;
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
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductListingTests {
    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    String product1Name = "V-NECH BASIC SHIRT";
    String product2Name = "CONTRASTING FABRIC T-SHIRT";
    String product3Name = "RAISED PRINT T-SHIRT";
    String product4Name = "PLEATED T-SHIRT";
    String product5Name = "CONTRASTING LACE T-SHIRT";
    String product6Name = "SLOGAN T-SHIRT";

    @Test
    void lists_the_products_with_the_given_order_criteria_having_sales_units_preference() throws Exception {
        replicateExerciseStatementSetup();

        var products = given()
                .port(port)
                .queryParam("salesUnitsEpsilon", 1)
                .queryParam("stockEpsilon", 0)
                .get("/products")
                .getBody()
                .as(GetProductsResponseDto.class)
                .items();

        assertThat(products).hasSize(6);
        assertThat(products.get(0).name()).isEqualTo(product5Name); // 650
        assertThat(products.get(1).name()).isEqualTo(product1Name); // 100
        assertThat(products.get(2).name()).isEqualTo(product3Name); // 80
        assertThat(products.get(3).name()).isEqualTo(product2Name); // 50
        assertThat(products.get(4).name()).isEqualTo(product6Name); // 20
        assertThat(products.get(5).name()).isEqualTo(product4Name); // 3
    }

    @Test
    void lists_the_products_with_the_given_order_criteria_having_stock_preference() throws Exception {
        replicateExerciseStatementSetup();

        var products = given()
                .port(port)
                .queryParam("salesUnitsEpsilon", 0)
                .queryParam("stockEpsilon", 1)
                .get("/products")
                .getBody()
                .as(GetProductsResponseDto.class)
                .items();

        assertThat(products).hasSize(6);
        assertThat(products.get(0).name()).isEqualTo(product4Name); // 25 + 30 + 10 = 65
        assertThat(products.get(1).name()).isEqualTo(product2Name); // 35 + 9  + 9  = 53
        assertThat(products.get(2).name()).isEqualTo(product3Name); // 20 + 2  + 20 = 42
        assertThat(products.get(3).name()).isEqualTo(product6Name); // 9  + 2  + 5  = 16
        assertThat(products.get(4).name()).isEqualTo(product1Name); // 4  + 9  + 0  = 13
        assertThat(products.get(5).name()).isEqualTo(product5Name); // 0  + 1  + 0  = 1
    }

    private void replicateExerciseStatementSetup() {
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
                .body(new PostProductRequestDto(name))
                .contentType(ContentType.JSON)
                .post("/products")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(PostProductResponseDto.class)
                .id();
    }
}
