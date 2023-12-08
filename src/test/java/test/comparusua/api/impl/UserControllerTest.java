package test.comparusua.api.impl;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import test.comparusua.api.model.UserDto;
import test.comparusua.convertors.EntityRestConverter;
import test.comparusua.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static test.comparusua.util.StubModelHelper.stubUserDtoList;
import static test.comparusua.util.StubModelHelper.stubUsers;

class UserControllerTest {

    private final UserService userService = Mockito.mock(UserService.class);
    private final EntityRestConverter converter = Mockito.mock(EntityRestConverter.class);

    private final UserController userController = new UserController(
            userService,
            converter
    );

    @Test
    void getUsers_ok() {
        val mockUsers = stubUsers();
        val mockUserDtoList = stubUserDtoList();

        when(userService.getAllUsers()).thenReturn(mockUsers);
        when(converter.mapToUserDto(mockUsers)).thenReturn(mockUserDtoList);

        ResponseEntity<List<UserDto>> responseEntity = userController.getUsers();

        verify(userService, times(1)).getAllUsers();
        verify(converter, times(1)).mapToUserDto(mockUsers);

        assertEquals(mockUserDtoList, responseEntity.getBody());
    }
}