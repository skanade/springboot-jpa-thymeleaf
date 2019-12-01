package com.example.shun.app;

import com.example.shun.app.model.People;
import com.example.shun.app.repository.PeopleRepository;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MainApplication.class },
                webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MainSpringBootBootstrapSlowTests {

    private static final String API_ROOT = "http://localhost:8080/api/people/";

    @Autowired
    private PeopleRepository peopleRepository;

    @Test
    //@org.junit.jupiter.api.Test
    @Tag("slow")
    public void whenGetAllPeople_thenOK() {
        Response response = RestAssured.get(API_ROOT);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    //@org.junit.jupiter.api.Test
    @Tag("slow")
    public void whenCreateUpdateGetByFirstAndDelete_thenOK() {
        // create new person through uri
        People people = createPeople();
        createPeopleAsUri(people);

        // find by first name
        Response response = RestAssured.given()
                .filter(RequestLoggingFilter.logRequestTo(System.out))
                .filter(ResponseLoggingFilter.logResponseIfStatusCodeIs(200))
                .get(API_ROOT + "first/" + people.getFirst());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.as(List.class)
                .size() > 0);

        // retrieve id of just inserted person by first name
        List<People> peopleWithFirstList = peopleRepository.findByFirst(people.getFirst());
        People firstPerson = peopleWithFirstList.get(0);

        // update last name
        firstPerson.setLast("Bar2");
        response = RestAssured.given()
                .filter(RequestLoggingFilter.logRequestTo(System.out))
                .filter(ResponseLoggingFilter.logResponseIfStatusCodeIs(400))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(firstPerson)
                .put(API_ROOT + firstPerson.getId());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        // delete new person created
        response = RestAssured.given()
                .filter(RequestLoggingFilter.logRequestTo(System.out))
                .filter(ResponseLoggingFilter.logResponseIfStatusCodeIs(400))
                .delete(API_ROOT + firstPerson.getId());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    private People createPeople() {
        People people = new People();
        people.setFirst("Foo");
        people.setLast("Bar");
        return people;
    }

    private String createPeopleAsUri(People people) {
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(people)
                .post(API_ROOT);
        return API_ROOT + response.jsonPath().get("id");
    }

}
