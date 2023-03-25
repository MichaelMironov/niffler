package niffler.tests.ui;

import com.codeborne.selenide.Selenide;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import niffler.data.enums.CurrencyValues;
import niffler.data.json.UserJson;
import niffler.pages.WelcomePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

@WireMockTest(httpPort = 8089)
public class WiremockTest extends BaseTest {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private UserJson mike = new UserJson();
//    String mike = "{\"id\":\"6e09ed3e-b655-4452-8aaf-1102dbe551bc\",\"username\":\"mike\",\"firstname\":\"Michael\",\"surname\":\"Jordan\",\"currency\":\"USD\",\"photo\":null}";

    @BeforeEach
    void before(){
        mike.setId(UUID.randomUUID());
        mike.setUserName("mike");
        mike.setFirstname("Michael");
        mike.setSurname("Jordan");
        mike.setCurrency(CurrencyValues.USD);
    }

    @Test
    void mainPageShouldBeDisplayedAfterSuccessLogin() throws JsonProcessingException {

        WireMock.stubFor(WireMock.get("/currentUser?=username=mike")
                        .willReturn(aResponse()
                        .withHeader("Content-type", "application/json")
                                .withStatus(200)
                                .withBody(objectMapper.writeValueAsBytes(mike))));

        WireMock.stubFor(WireMock.get("/invitations?=username=mike")
                .willReturn(aResponse()
                        .withHeader("Content-type", "application/json")
                        .withStatus(200)
                        .withBody(objectMapper.writeValueAsBytes(mike))));
//
//        while (true){
//
//        }


                Selenide.open(WelcomePage.URL, WelcomePage.class)
                        .doLogin()
                        .fillLoginPage("mike", "mir")
                        .submit();
//                        .waitForPageLoaded();
    }

}
