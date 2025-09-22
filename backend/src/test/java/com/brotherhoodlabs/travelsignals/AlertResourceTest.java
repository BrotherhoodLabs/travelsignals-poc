package com.brotherhoodlabs.travelsignals;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class AlertResourceTest {

    @Test
    public void testGetAlerts() {
        given()
            .when().get("/api/alerts")
            .then()
            .statusCode(200)
            .contentType("application/json");
    }

    @Test
    public void testGetCounters() {
        given()
            .when().get("/api/alerts/counters")
            .then()
            .statusCode(200)
            .contentType("application/json");
    }

    @Test
    public void testHealthEndpoint() {
        given()
            .when().get("/q/health")
            .then()
            .statusCode(200);
    }
}
