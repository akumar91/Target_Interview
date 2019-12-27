package myRetail.com.Functional;

import com.jayway.restassured.response.Response;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

public class ProductsControllerFunctionalTest {

    @Test
    public void getByProductById_200() {
        given().contentType("application/json")
                .when().get("http://localhost:8080/products/13860428")
                .then().statusCode(200)
                .body("id", is(13860428))
                .body("name", is("The Big Lebowski (Blu-ray)"))
                .body("current_price.currency_code", is("USD"))
                .body("_links.self.href", endsWith("products/13860428"));
    }

    @Test
    public void getByProductByIdNotFoundInDB_404() {
        given().contentType("application/json")
                .when().get("http://localhost:8080/products/13860429")
                .then().statusCode(404)
                .body("errorMessage", is("Product details not found with product id 13860429"));
    }

    @Test
    public void getByProductByIdNotFoundByRedsky_404() {
        given().contentType("application/json")
                .when().get("http://localhost:8080/products/0")
                .then().statusCode(404)
                .body("errorMessage",
                        is("Product details not found with product id 0 from redsky.target.com API"));
    }

    @Test
    public void getByProductById_400() {
        given().contentType("application/json")
                .when().get("http://localhost:8080/products/3.4")
                .then().statusCode(400)
                .body("errorMessage",
                        is("Invalid input format, For input string: \"3.4\". It should be an integer"));
    }

    @Test
    public void UpdateProductPrice_204() {
        String body = "{\"id\": \"13860428\",\"name\": \"The Big Lebowski (Blu-ray)\",\"current_price\":" +
                " {\"currency_code\": \"USD\",\"value\": 134.34}}";

        given().body(body)
                .contentType("application/json")
                .when().put("http://localhost:8080/products/13860428")
                .then().statusCode(204);

        Response response = given().contentType("application/json")
                .when().get("http://localhost:8080/products/13860428");
        assertThat(response.getBody().jsonPath().get("current_price.value"), equalTo(134.34f));
        assertThat(response.getBody().jsonPath().get("current_price.currency_code"), equalTo("USD"));
        assertThat(response.getBody().jsonPath().get("name"), equalTo("The Big Lebowski (Blu-ray)"));
    }

    @Test
    public void UpdateProductPriceIdMismatch_400() {
        String body = "{\"id\": \"13860428\",\"name\": \"The Big Lebowski (Blu-ray)\",\"current_price\":" +
                " {\"currency_code\": \"USD\",\"value\": 134.34}}";

        given().body(body)
                .contentType("application/json")
                .when().put("http://localhost:8080/products/13860427")
                .then().statusCode(400)
                .body("errorMessage", is("Product Id mismatch from URI and body"));
    }

    @Test
    public void UpdateProductPrice_404() {
        String body = "{\"id\": \"13860429\",\"name\": \"The Big Lebowski (Blu-ray)\",\"current_price\":" +
                " {\"currency_code\": \"USD\",\"value\": 134.34}}";

        given().body(body)
                .contentType("application/json")
                .when().put("http://localhost:8080/products/13860429")
                .then().statusCode(404)
                .body("errorMessage",
                        is("Product price details not found with product id {13860429} to update the record"));
    }

    @Test
    public void UpdateProductPriceWithNegativeValue_400() {
        String body = "{\"id\": \"13860428\",\"name\": \"The Big Lebowski (Blu-ray)\",\"current_price\":" +
                " {\"currency_code\": \"USD\",\"value\": -134.34}}";

        given().body(body)
                .contentType("application/json")
                .when().put("http://localhost:8080/products/13860428")
                .then().statusCode(400)
                .body("errorMessage", is("Invalid request fields: currentPrice.value because " +
                        "Price could not be less than 0 , "));
    }

}