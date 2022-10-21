package com.acidtango.inditex.backendtest;

import com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.GetProductsController.DTO.GetProductsResponseDto;
import com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.PostProductController.DTO.PostProductRequestDto;
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

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RestockTests {
    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @Test
    void creates_a_product() throws Exception {
        var notImportantProductName = "V-NECH BASIC SHIRT";
        var expectedAmountToRestock = 2;
        given()
                .port(port)
                .body(new PostProductRequestDto(notImportantProductName))
                .contentType(ContentType.JSON)
                .post("/products")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        var product = given()
                .port(port)
                .get("/products")
                .getBody()
                .as(GetProductsResponseDto.class)
                .items()
                .get(0);

        given()
                .port(port)
                .body(new PostStockRequestDto(expectedAmountToRestock))
                .contentType(ContentType.JSON)
                .post("/products/" + product.id() + "/variants/" + product.stock().large().id() + "/stock")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        var products = given()
                .port(port)
                .get("/products")
                .getBody()
                .as(GetProductsResponseDto.class)
                .items();

        assertThat(products).hasSize(1);
        assertThat(products.get(0).stock().large().amount()).isEqualTo(expectedAmountToRestock);
    }
}
