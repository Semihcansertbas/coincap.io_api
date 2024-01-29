
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@Epic("CoinCap API Tests")
@Feature("API Test Cases")
public class CoinCapApiTests {


    static {
        RestAssured.baseURI = "https://api.coincap.io/v2"; // Set your base URI here
    }


    @Test
    @Description("Retrieve List of Coins")
    public void testGetCoins() {
        given()
                .when()
                .get("/assets")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("data", not(empty()));

    }

    @Test
    @Description("Invalid Endpoint")
    public void testInvalidEndpoint() {
        given()
                .when()
                .get("/coinsList")
                .then()
                .statusCode(404)
                .contentType(ContentType.JSON)
                .body("error", equalTo("Not Found"));
    }

    @Test
    @Description("Retrieve Specific Coin Information")
    public void testGetCoinById() {
        String validCoinId = "bitcoin";

        given()
                .pathParam("coin_id", validCoinId)
                .when()
                .get("/coins/{coin_id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("data.id", equalTo(validCoinId));
    }

    @Test
    @Description("Invalid Coin ID")
    public void testInvalidCoinId() {
        String invalidCoinId = "inv_id";

        given()
                .pathParam("coin_id", invalidCoinId)
                .when()
                .get("/coins/{coin_id}")
                .then()
                .statusCode(404)
                .contentType(ContentType.JSON)
                .body("error", equalTo("Not Found"));
    }

    @Test
    @Description("Retrieve Price Information")
    public void testGetPrices() {
        given()
                .queryParam("ids", "bitcoin,ethereum")
                .when()
                .get("/prices")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("data", not(empty()));
    }

    @Test
    @Description("Missing Parameters")
    public void testMissingParameters() {
        given()
                .when()
                .get("/prices")
                .then()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .body("error", equalTo("Bad Request"));
    }

}
