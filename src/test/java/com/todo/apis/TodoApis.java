package com.todo.apis;

import com.todo.base.spec;
import com.todo.data.Route;
import com.todo.models.ToDo;
import com.todo.models.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TodoApis {
    public static Response addToDo(ToDo task, String token){
        return given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(task)
                .auth().oauth2(token)
                .when()
                .post(Route.TODOS_ROUTE)
                .then().extract().response();
    }
    public static Response getToDo(String task, String token){
        return given()
                .spec(spec.getRequestSpec())
                .auth().oauth2(token)
                .when().get(Route.TODOS_ROUTE +"/"+ task)
                .then().extract().response();
    }
    public static Response deleteTask(String task, String token){
        return   given()
                .spec(spec.getRequestSpec())
                .auth().oauth2(token)
                .when().delete(Route.TODOS_ROUTE +"/"+ task)
                .then().extract().response();
    }
}
