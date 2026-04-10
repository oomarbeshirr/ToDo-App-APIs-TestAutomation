package com.todo.steps;

import com.github.javafaker.Faker;
import com.todo.apis.TodoApis;
import com.todo.models.ToDo;
import io.restassured.response.Response;

public class ToDoSteps {
    public static ToDo generateToDo(){
        Faker faker = new Faker();
        String item = faker.book().title();
        Boolean isCompleted = false;
        return new ToDo(item, isCompleted);

    }
    public static String getToDoID(ToDo todo, String token){
        Response response = TodoApis.addToDo(todo, token);
        return response.body().path("_id");

    }
}
