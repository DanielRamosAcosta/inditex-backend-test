package com.acidtango.inditex.backendtest;

import com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.GetProductsElementDto;
import com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.GetProductsResponseDto;
import com.jayway.jsonpath.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class ProductCreationTests {
    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @Test
    void creates_a_product() throws Exception {
        RestAssuredMockMvc.mockMvc(mockMvc);
        var productName = "V-NECH BASIC SHIRT";

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "V-NECH BASIC SHIRT"
                                }
                        """)
                )
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(get("/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items.[0].name").value(productName))
                .andExpect(jsonPath("$.items.[0].stock.large").value(0))
                .andExpect(jsonPath("$.items.[0].stock.medium").value(0))
                .andExpect(jsonPath("$.items.[0].stock.small").value(0));
    }

    @Test
    void rest_assured_works_with_strings() throws Exception {
        var data = given()
                .port(port)
                .when()
                .get("/products")
                .getBody()
                .asString();

        System.out.println(data);
    }
}
