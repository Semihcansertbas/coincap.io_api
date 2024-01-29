import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@Epic("CoinCap API Tests")
@Feature("CoinCap High Level API Test Cases")
public class CoinCapApiTests {


    static {
        RestAssured.baseURI = "https://api.coincap.io/v2"; // Set your base URI here
    }

    @Test
    @Link(name = "coincap.io", url = "https://coincap.io/")
    @Description("Verify Response Schema for List of Assets")
    @Severity(SeverityLevel.CRITICAL)
    @Step("Make a GET request to retrieve a list of assets")
    public void testListAssetsResponseSchema() {
        // Perform API request
        String response = given()
                .when()
                .get("/assets")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("data", not(empty()))
                .extract().asString();
        // Attach API response to Allure report
        attachApiResponseToAllure(response);
    }

    @Test
    @Link(name = "coincap.io", url = "https://coincap.io/")
    @Description("Verify Response Schema for Single Asset")
    @Severity(SeverityLevel.CRITICAL)
    @Step("Make a GET request to retrieve details for a specific asset")
    public void testSingleAssetResponseSchema() {
        String validId = "bitcoin";
        // Perform API request with path parameter
        String response = given()
                .when()
                .pathParam("id", validId)
                .get("assets/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("data.id", equalTo(validId))
                .body("data", not(empty()))
                .extract().asString();
        // Attach API response to Allure report
        attachApiResponseToAllure(response);
    }

    @Test
    @Link(name = "coincap.io", url = "https://coincap.io/")
    @Description("Verify Historical Data Endpoint")
    @Severity(SeverityLevel.CRITICAL)
    @Step("Make a GET request to retrieve historical data for a specific asset")
    public void testHistoricalDataEndpoint() {
        String validId = "bitcoin";
        // Perform API request with path parameter
        String response = given()
                .pathParam("id", validId)
                .when()
                .get("assets/{id}/history?interval=d1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("data", not(empty()))
                .extract().asString();
        // Attach API response to Allure report
        attachApiResponseToAllure(response);
    }

    @Test
    @Link(name = "coincap.io", url = "https://coincap.io/")
    @Description("Verify Sorting by Market Capital")
    @Severity(SeverityLevel.CRITICAL)
    @Step("Make a GET request to retrieve sorting by Market Capital")
    public void testSortingByMarketCap() {
        String validId = "bitcoin";
        // Perform API request with path parameter
        String response = given()
                .pathParam("id", validId)
                .when()
                .get("/assets/{id}/markets")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("data", not(empty()))
                .extract().asString();
        // Attach API response to Allure report
        attachApiResponseToAllure(response);
    }


    @Test
    @Link(name = "coincap.io", url = "https://coincap.io/")
    @Description("Verify Response Schema for Assets Rates")
    @Severity(SeverityLevel.CRITICAL)
    @Step("Make a GET request to retrieve a list of assets rates")
    public void verifyAssetsRatesResponseSchema() {
        String response = given()
                .when()
                .get("/rates")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("data", not(empty()))
                .extract().asString();
        // Attach API response to Allure report
        attachApiResponseToAllure(response);
    }

    @Test
    @Link(name = "coincap.io", url = "https://coincap.io/")
    @Description("Verify Response Schema for Single Asset Rates")
    @Severity(SeverityLevel.CRITICAL)
    @Step("Make a GET request to retrieve details for a specific asset rates")
    public void verifySingleAssetRatesResponseSchema() {
        String validId = "ethereum";
        // Perform API request with path parameter
        String response = given()
                .pathParam("id", validId)
                .when()
                .get("/rates/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("data", not(empty()))
                .extract().asString();
        // Attach API response to Allure report
        attachApiResponseToAllure(response);
    }

    @Test
    @Link(name = "coincap.io", url = "https://coincap.io/")
    @Description("Fetch Asset History with Invalid Symbol")
    @Severity(SeverityLevel.CRITICAL)
    @Step("Make a GET request to retrieve Error unable to find Asset")
    public void fetchAssetHistoryWithInvalidSymbol() {
        String invalidSymbol = ">"; // Replace with an invalid or non-existent symbol
        // Perform API request with path parameter
        String response = given()
                .pathParam("symbol", invalidSymbol)
                .queryParam("interval", "d1")
                .when()
                .get("/assets/{symbol}/history")
                .then()
                .statusCode(404)
                .contentType(ContentType.JSON)
                .body("data", not(empty()))
                .extract().asString();
        // Attach API response to Allure report
        attachApiResponseToAllure(response);
    }



            @Step("Attach API response to Allure report")
            private void attachApiResponseToAllure(String response) {
                Allure.addAttachment("API Response", "application/json", response, "json");
            }



}
