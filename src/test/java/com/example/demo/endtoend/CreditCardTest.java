package com.example.demo.endtoend;

import com.example.demo.repository.CreditCardRepository;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreditCardTest {

    @LocalServerPort
    private int port;

    private String uri;

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port;
    }

    @Autowired
    CreditCardRepository creditCardRepository;

    @Test
    void getCardTest() {
        when().get(uri + "/api/v1/card-request/12345678901")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(0))
                .body("oib", equalTo("12345678901"))
                .body("firstName", equalTo("Test"))
                .body("lastName", equalTo("User"))
                .body("status", equalTo("PENDING"));
    }

    @Test
    void getCardNotFoundTest() {
        when().get(uri + "/api/v1/card-request/12345611111")
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    void createCardTest() {
        assertDbEntries(3);
        createCard();
        assertDbEntries(4);
        final var opt = creditCardRepository.findByOib("16656789015");
        assertTrue(opt.isPresent());
        assertEquals("firstname", opt.get().getFirstName());
        assertEquals("lastname", opt.get().getLastName());
        assertEquals("16656789015", opt.get().getOib());
        assertEquals("REJECTED", opt.get().getStatus().toString());
        assertNotNull(opt.get().getCreatedAt());
        assertNotNull(opt.get().getUpdatedAt());
        creditCardRepository.delete(opt.get());
    }

    @Test
    void deleteCardTest() {
        assertDbEntries(3);
        createCard();
        assertDbEntries(4);
        when().delete(uri + "/api/v1/card-request/16656789015")
                .then()
                .assertThat()
                .statusCode(204);
        assertDbEntries(3);
    }

    private void assertDbEntries(int expectedEntries) {
        final var entries = creditCardRepository.findAll().spliterator().getExactSizeIfKnown();
        assertEquals(expectedEntries, entries);
    }

    private void createCard() {
        Map<String, String> request = new HashMap<>();
        request.put("oib", "16656789015");
        request.put("firstName", "firstname");
        request.put("lastName", "lastname");
        request.put("status", "REJECTED");
        given().contentType("application/json")
                .body(request)
                .when()
                .post(uri + "/api/v1/card-request")
                .then()
                .assertThat()
                .statusCode(201);
    }
}
