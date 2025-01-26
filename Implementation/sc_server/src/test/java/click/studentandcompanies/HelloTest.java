package click.studentandcompanies;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class HelloTest {
// todo: RestAssured sembra carina come libreria per il testing. Molto easy da usare vedi sotto.
//  Il problema é che fa la stessa roba di Postman. Ci serve? Ah e in piú sta libreria ha qualche problemino di sicurezza ;)

//    @BeforeEach
//    public void setup() {
//        RestAssured.baseURI = "http://localhost:8443";
//    }
//
//    @Test
//    public void testHelloEndpoint() {
//        given().
//                header("Authorization", "Bearer token").
//                when().
//                get("/application-api/hello").
//                then().
//                statusCode(200).
//                body(equalTo("Hello, Spring Boot!"));
//    }

}