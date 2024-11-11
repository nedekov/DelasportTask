package helpers;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiHelper {

    public static String getMemberBalance(){

        RestAssured.baseURI = "https://luckybandit.club.test-delasport.com/en";

        //  Logging in and capturing the session cookie
        RequestSpecification loginRequest = RestAssured.given()
                .auth()
                .preemptive()
                .basic("tu_mihail", "Pass112#")  // Basic authentication with credentials
                .header("Content-Type", "application/json");

        // Make the login request
        Response loginResponse = loginRequest.post("/index/operation/login");

        // Checking login success and extracting session cookie
        if (loginResponse.getStatusCode() == 200) {
            String SESS = loginResponse.getCookie("SESS");
            System.out.println("Session Cookie: " + SESS);

            // Ensuring the session cookie is not null before proceeding
            if (SESS == null) {
                System.out.println("Session cookie not retrieved.");
                return null;
            }

            // Setting up the cookie for the next request
            Cookie sessCookie = new Cookie.Builder("SESS", SESS + ";").build();
            System.out.println(sessCookie);
            //  Using the session cookie to get the member balance
            RequestSpecification getBalance = RestAssured.given()
                    .header("Accept", "application/json, text/plain, */*")
                    .header("X-Requested-With", "XMLHttpRequest")
                    .cookie(sessCookie);

            // Making the request to get member balance
            Response memberBalanceResponse = getBalance.post("/index/operation/getMemberBalance");

            // Check balance retrieval success
            if (memberBalanceResponse.getStatusCode() == 200) {
                JsonPath jsonPath = memberBalanceResponse.jsonPath();
                // Extract the 'raw_amount' value
                String rawAmount = jsonPath.getString("data.1.info.raw_amount");
                System.out.println("Balance Raw Amount: " + rawAmount);
                return rawAmount;
            } else {
                System.out.println("Failed to retrieve balance. Status code: " + memberBalanceResponse.getStatusCode());
                return null;
            }
        } else {
            System.out.println("Login failed. Status code: " + loginResponse.getStatusCode());
            return null;
        }
    }
}
