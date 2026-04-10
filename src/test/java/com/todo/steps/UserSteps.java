package com.todo.steps;

import com.github.javafaker.Faker;
import com.todo.apis.UserApis;
import com.todo.models.User;
import io.restassured.response.Response;

public class UserSteps {
    public static User generateUser(){
        Faker faker = new Faker();

        String firstName = faker.name().firstName(); // Emory
        String lastName = faker.name().lastName(); // Barton
        String email = faker.internet().emailAddress();
        String password = "helloworld";
        return new User(firstName,lastName,email, password);
    }

    public static User getRegisteredUser(){
        User user = generateUser();
        UserApis.register(user);
        return user;
    }
    public static String getToken(){
        User user = generateUser();
        Response response = UserApis.register(user);
        return response.body().path("access_token");

    }
}
