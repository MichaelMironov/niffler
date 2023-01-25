package niffler.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static io.restassured.RestAssured.given;

public class AuthTest {

    public static String clientId = "client";
    public static String redirectUri = "http://127.0.0.1:3000/authorized";
    public static String scope = "openid";
    public static String username = "mike";
    public static String password = "mir";

    public static String encode(String str1, String str2) {
        return new String(Base64.getEncoder().encode((str1 + ":" + str2).getBytes()));
    }

    public static Response getCode() {
        String authorization = encode(username, password);

        return
                given()
                        .header("authorization", "Basic " + authorization)
                        .contentType(ContentType.URLENC)
                        .formParam("response_type", "code")
                        .queryParam("client_id", clientId)
                        .queryParam("redirect_uri", redirectUri)
                        .queryParam("scope", scope)
                        .post("/oauth2/authorize")
                        .then().log().all()
                        .statusCode(200)
                        .extract()
                        .response();
    }

    public static String parseForOAuth2Code(Response response) {
        return response.jsonPath().getString("code");
    }

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://auth-server:9000";
    }

    @Test
    public void getCodeTest() {
        Response response = getCode();
        String code = parseForOAuth2Code(response);
        Assertions.assertNotNull(code);
    }
}

