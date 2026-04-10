package com.todo.testCases;

import com.todo.apis.UserApis;
import com.todo.data.ErrorMessages;
import com.todo.models.Error;
import com.todo.models.User;
import com.todo.steps.UserSteps;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class UserTest {

        @Test
        public void shouldBeAbleToRegister(){
        User user = UserSteps.generateUser();
        Response response = UserApis.register(user);
        User returnedUser = response.body().as(User.class);
        response.then().log().body();
        assertThat(response.statusCode(), equalTo(201));
        assertThat(returnedUser.getFirstName(), equalTo(user.getFirstName()));

        }

        @Test
        public void shouldNotAbleToRegisterWithTheSameEmail(){
            User user = UserSteps.getRegisteredUser();
            Response response = UserApis.register(user);
            Error returnedError = response.body().as(Error.class);
            assertThat(response.statusCode(), equalTo(400));
            assertThat(returnedError.getMessage(), equalTo(ErrorMessages.EMAIL_IS_ALREADY_REGISTERED));

        }
        @Test
        public void shouldBeAbleToLogin(){
           User user = UserSteps.getRegisteredUser();
           User loginData = new User(user.getEmail(), user.getPassword());
            Response response = UserApis.login(loginData);
            User returnedUser = response.body().as(User.class);
            assertThat(response.statusCode(), equalTo(200));
            assertThat(returnedUser.getFirstName(), equalTo(user.getFirstName()));
            assertThat(returnedUser.getAccessToken(), not(equalTo(null)));
        }
        @Test
        public void shouldNotBeAbletoLoginIfPasswordIsNotCorrect(){
            User user = UserSteps.getRegisteredUser();
            User loginData = new User(user.getEmail(), "wrongPassword");
            Response response = UserApis.login(loginData);
            Error returnedError = response.body().as(Error.class);
            assertThat(response.statusCode(), equalTo(401));
            assertThat(returnedError.getMessage(), equalTo(ErrorMessages.EMAIL_OR_PASSWORD_IS_WRONG));

        }

}
