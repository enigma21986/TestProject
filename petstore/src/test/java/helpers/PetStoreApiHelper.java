package helpers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import model.Pet;
import org.apache.http.HttpStatus;

import java.math.BigInteger;

public abstract class PetStoreApiHelper {

    private static final String BASE_URI = "https://petstore.swagger.io/v2";
    private static final String PET_PATH = "/pet";

    public static RequestSpecification getRequestSpecification() {
        RequestSpecification requestSpec = RestAssured.given().header("Content-Type", ContentType.JSON);
        return requestSpec
                .baseUri(BASE_URI)
                .request().log().all();
    }

    public static Pet createPet(Pet pet){
        return getRequestSpecification()
                .body(pet)
                .when()
                .post(PET_PATH)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(Pet.class);
    }

    public static Pet getPetById(BigInteger id){
        return getRequestSpecification()
                .when()
                .get(PET_PATH + "/" + id)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(Pet.class);
    }

    public static void failGetPetById(BigInteger id){
        getRequestSpecification()
                .when()
                .get(PET_PATH + "/" + id)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    public static void deletePet(BigInteger id) {
        getRequestSpecification()
                .when()
                .delete(PET_PATH + "/" + id)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK);
    }

}
