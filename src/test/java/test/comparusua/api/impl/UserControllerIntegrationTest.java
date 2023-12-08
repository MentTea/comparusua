package test.comparusua.api.impl;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class UserControllerIntegrationTest extends BaseContextTest {

    @Autowired
    private UserController userController;

    @Test
    void getUsers_ok() {
        val responseEntity = userController.getUsers();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        val actualUsers = responseEntity.getBody();

        assertNotNull(actualUsers);
        assertFalse(actualUsers.isEmpty());
        assertEquals(2, actualUsers.size());
    }
}