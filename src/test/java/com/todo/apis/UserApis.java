package com.todo.apis;

import com.todo.base.spec;
import com.todo.data.Route;
import com.todo.models.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserApis {
    public static Response register(User user){
        return given()
                .spec(spec.getRequestSpec())
                .body(user)
                .when()
                .post(Route.RIGISTER_ROUTE)
                .then().extract().response();
    }
    public static Response login(User user){
        return given()
                .spec(spec.getRequestSpec())
                .body(user)
                .when()
                .post(Route.LOGIN_ROUTE)
                .then()
                .extract().response();
    }
}
