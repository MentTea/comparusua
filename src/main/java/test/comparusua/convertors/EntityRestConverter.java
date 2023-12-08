package test.comparusua.convertors;

import org.mapstruct.Mapper;
import test.comparusua.api.model.UserDto;
import test.comparusua.model.User;

import java.util.List;

@Mapper
public interface EntityRestConverter {

    List<UserDto> mapToUserDto(List<User> users);
}
