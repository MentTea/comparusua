package test.comparusua.api.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import test.comparusua.api.UsersApi;
import test.comparusua.api.model.UserDto;
import test.comparusua.convertors.EntityRestConverter;
import test.comparusua.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController implements UsersApi {

    private final UserService userService;
    private final EntityRestConverter converter;

    @Override
    public ResponseEntity<List<UserDto>> getUsers() {
        log.debug("Called getUsers");

        val users = userService.getAllUsers();

        log.info("Received users: {}", users);
        return ResponseEntity.ok(converter.mapToUserDto(users));
    }
}
