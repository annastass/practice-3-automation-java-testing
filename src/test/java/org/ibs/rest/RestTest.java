package org.ibs.rest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.ibs.pojos.FoodPojo;
import org.junit.jupiter.api.Test;

import java.util.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class RestTest {

    private final String BASE_URL = "http://localhost:8080";
    List<FoodPojo> products = Arrays.asList(
            new FoodPojo() {{
                setName("Батат");
                setType("VEGETABLE");
                setExotic(true);
            }},
            new FoodPojo() {{
                setName("Перец");
                setType("VEGETABLE");
                setExotic(false);
            }},
            new FoodPojo() {{
                setName("Груша");
                setType("FRUIT");
                setExotic(false);
            }},
            new FoodPojo() {{
                setName("Ананас");
                setType("FRUIT");
                setExotic(true);
            }}
    );

    @Test
    void getProducts() {
        given()
                .baseUri(BASE_URL)
                .when()
                .get("/api/food")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    void addProduct() {
        List<Response> responses = new ArrayList<>();

        for (FoodPojo product : products) {
            Response response = given()
                    .baseUri(BASE_URL)
                    .contentType(ContentType.JSON)
                    .body(product)
                    .post("/api/food");

            response.then()
                    .statusCode(200)
                    .log().all();

            Map<String, String> cookies = new LinkedHashMap<>(response.getCookies());
            responses.add(response);

            for (Map.Entry<String, String> cookieEntry : cookies.entrySet()) {
                Response getProducts = given()
                        .baseUri(BASE_URL)
                        .contentType(ContentType.JSON)
                        .cookie(cookieEntry.getKey(), cookieEntry.getValue())
                        .when()
                        .get("/api/food");

                getProducts.then()
                        .statusCode(200)
                        .assertThat()
                        .body("name", hasItem(product.getName()))
                        .body("type", hasItem(product.getType()))
                        .body("exotic", hasItem(product.isExotic()));
            }
        }
    }

    @Test
    void clearData() {
        Response response = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .post("/api/data/reset");

        response.then()
                .statusCode(200)
                .log().all();

         String cookie = response.getCookies().toString();

        Response deletedList = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .cookie(cookie)
                .when()
                .get("/api/food");
        deletedList.then()
                .assertThat()
                .statusCode(200)
                .log().all()
                .body("size()", equalTo(4));
    }
}
