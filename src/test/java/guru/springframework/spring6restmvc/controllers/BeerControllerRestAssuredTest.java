package guru.springframework.spring6restmvc.controllers;

import io.restassured.RestAssured;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;

/**
 * Created by Igli
 * on 30.10.24.
 */
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(BeerControllerRestAssuredTest.testConfig.class)
@ComponentScan("guru.springframework.spring6restmvc")
public class BeerControllerRestAssuredTest {

    @Configuration
    public static class testConfig{
     @Bean
        public SecurityFilterChain springSecurityFilterChain(HttpSecurity http) throws Exception {
         http.authorizeRequests().anyRequest().permitAll();
         return http.build();

     }

    }

    @LocalServerPort
    Integer localPort;

    private  final String API_PATH = "/api/v1/beers";
    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost" ;
        RestAssured.port = localPort;
    }

   @Test
   public void getAllBeers() {
        given().contentType("application/json").when().get(API_PATH).then()
                .assertThat().statusCode(200);

   }
}
