package com.example.demo;

import com.example.demo.controller.UserController;
import com.example.demo.model.User;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SpringBootApp.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class UserControllerTests {

    @Autowired
    private UserController userController;

    @Test
    public void updateByEmailValidTest() {
        Assertions.assertNotNull(userController.update("alexciurean9@gmail.com", "Alexandru", "Ciurean", "0763048121"));
    }

    @Test
    public void updateByEmailInvalidTest() {
        try {
            userController.update("alexciurean@gmail.com", "Alexandru", "Ciurean", "0763048121");
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void updatePasswordByEmailValidTest() {
        Assertions.assertNotNull(userController.updatePassword("alexciurean9@gmail.com", "parola"));
    }

    @Test
    public void updatePasswordByEmailInvalidTest() {
        try {
            userController.updatePassword("alexciurean@gmail.com", "parola");
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void loginValidTest() {
        User user = userController.findByEmail("alexciurean9@gmail.com");
        Assertions.assertNull(userController.login(user));
    }

    @Test
    public void loginInvalidTest() {
        User user = userController.findByEmail("alexciurean@gmail.com");
        try {
            userController.login(user);
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void findByEmailValidTest() {
        Assertions.assertNotNull(userController.findByEmail("alexciurean9@gmail.com"));
    }

    @Test
    public void findByEmailInvalidTest() {
        Assertions.assertNull(userController.findByEmail("alexciurean@gmail.com"));
    }
}