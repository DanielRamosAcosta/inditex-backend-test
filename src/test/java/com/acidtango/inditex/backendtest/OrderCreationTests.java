package com.acidtango.inditex.backendtest;

import com.acidtango.inditex.backendtest.store.orders.infrastructure.controllers.CreateOrderRequestDto;
import com.acidtango.inditex.backendtest.store.orders.infrastructure.controllers.OrderLinesDto;
import com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.CreateProductRequestDto;
import com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.GetProductsResponseDto;
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
@DirtiesContext
public class OrderCreationTests {
    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @Test
    void creates_an_order_over_a_product() throws Exception {

        given()
                .port(port)
                .body(new CreateProductRequestDto("V-NECH BASIC SHIRT"))
                .contentType(ContentType.JSON)
                .post("/products")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        given()
                .port(port)
                .body(new CreateOrderRequestDto(List.of(new OrderLinesDto(1, 1, 2))))
                .contentType(ContentType.JSON)
                .post("/orders")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        var response = given()
                .port(port)
                .get("/products")
                .getBody()
                .as(GetProductsResponseDto.class);

        assertThat(response.items()).hasSize(1);
        assertThat(response.items().get(0).name()).isEqualTo("V-NECH BASIC SHIRT");
        assertThat(response.items().get(0).salesUnits()).isEqualTo(2);
    }
}
