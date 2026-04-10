package com.todo.testCases;

import com.todo.apis.TodoApis;
import com.todo.data.ErrorMessages;
import com.todo.models.Error;
import com.todo.models.ToDo;
import com.todo.steps.ToDoSteps;
import com.todo.steps.UserSteps;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.given;

public class TodoTest {

    @Test
    public void shouldBeAbleToAddToDO(){
        ToDo todo = ToDoSteps.generateToDo();
        String token = UserSteps.getToken();

        Response response = TodoApis.addToDo(todo, token);
        ToDo returnedTask = response.body().as(ToDo.class);
        assertThat(response.statusCode(), equalTo(201));
        assertThat(returnedTask.getItem(), equalTo(todo.getItem()));
        assertThat(returnedTask.getIsCompleted(), equalTo(false));


    }
    @Test
    public void shouldNotBeAbleToIfIsCompletedIsMissing(){
        ToDo task = new ToDo("Learn Appium");
        String token = UserSteps.getToken();
        Response response = TodoApis.addToDo(task, token);

        Error returnedError = response.body().as(Error.class);
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.IS_COMPLETED_IS_REQUIRED));
    }

    @Test
    public void shouldBeAbleToGetTaskByID(){
        String token = UserSteps.getToken();
        ToDo todo = ToDoSteps.generateToDo();
        String taskID = ToDoSteps.getToDoID(todo, token);
        Response response = TodoApis.getToDo(taskID, token);
        ToDo returnedTask = response.body().as(ToDo.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedTask.getItem(), equalTo(todo.getItem()));
        assertThat(returnedTask.getIsCompleted(), equalTo(false));


    }
    @Test
    public void shouldBeAbleToDeleteTaskByID(){
        String token = UserSteps.getToken();
        ToDo todo = ToDoSteps.generateToDo();
        String taskID = ToDoSteps.getToDoID(todo, token);
        Response response = TodoApis.deleteTask(taskID, token);
        ToDo returnedTask = response.body().as(ToDo.class);
        assertThat(returnedTask.getItem(), equalTo(todo.getItem()));
        assertThat(returnedTask.getIsCompleted(), equalTo(false));
    }
}
