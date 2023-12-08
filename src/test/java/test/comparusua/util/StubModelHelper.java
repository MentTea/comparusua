package test.comparusua.util;

import lombok.val;
import test.comparusua.api.model.UserDto;
import test.comparusua.config.property.DataSourceInfo;
import test.comparusua.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class StubModelHelper {

    private static final Integer LENGTH_TO_RANDOM = 10;

    private StubModelHelper() {
    }

    public static DataSourceInfo stubDataSourceInfo() {
        val dataSourceInfo = new DataSourceInfo();
        dataSourceInfo.setName(randomAlphabetic(LENGTH_TO_RANDOM));
        dataSourceInfo.setStrategy(randomAlphabetic(LENGTH_TO_RANDOM));
        dataSourceInfo.setUrl(randomAlphabetic(LENGTH_TO_RANDOM));
        dataSourceInfo.setTable(randomAlphabetic(LENGTH_TO_RANDOM));
        dataSourceInfo.setUser(randomAlphabetic(LENGTH_TO_RANDOM));
        dataSourceInfo.setPassword(randomAlphabetic(LENGTH_TO_RANDOM));

        Map<String, String> mapping = new HashMap<>();
        mapping.put("id", randomAlphabetic(LENGTH_TO_RANDOM));
        mapping.put("username", randomAlphabetic(LENGTH_TO_RANDOM));
        mapping.put("name", randomAlphabetic(LENGTH_TO_RANDOM));
        mapping.put("surname", randomAlphabetic(LENGTH_TO_RANDOM));

        dataSourceInfo.setMapping(mapping);

        return dataSourceInfo;
    }

    public static List<User> stubUsers() {
        val user1 = new User();
        user1.setId(randomAlphabetic(LENGTH_TO_RANDOM));
        user1.setUsername(randomAlphabetic(LENGTH_TO_RANDOM));
        user1.setName(randomAlphabetic(LENGTH_TO_RANDOM));
        user1.setSurname(randomAlphabetic(LENGTH_TO_RANDOM));

        val user2 = new User();
        user2.setId(randomAlphabetic(LENGTH_TO_RANDOM));
        user2.setUsername(randomAlphabetic(LENGTH_TO_RANDOM));
        user2.setName(randomAlphabetic(LENGTH_TO_RANDOM));
        user2.setSurname(randomAlphabetic(LENGTH_TO_RANDOM));

        return List.of(user1, user2);
    }

    public static List<UserDto> stubUserDtoList() {
        val userDto1 = new UserDto();
        userDto1.setId(randomAlphabetic(LENGTH_TO_RANDOM));
        userDto1.setUsername(randomAlphabetic(LENGTH_TO_RANDOM));
        userDto1.setName(randomAlphabetic(LENGTH_TO_RANDOM));
        userDto1.setSurname(randomAlphabetic(LENGTH_TO_RANDOM));

        val userDto2 = new UserDto();
        userDto2.setId(randomAlphabetic(LENGTH_TO_RANDOM));
        userDto2.setUsername(randomAlphabetic(LENGTH_TO_RANDOM));
        userDto2.setName(randomAlphabetic(LENGTH_TO_RANDOM));
        userDto2.setSurname(randomAlphabetic(LENGTH_TO_RANDOM));

        return List.of(userDto1, userDto2);
    }

    public static List<DataSourceInfo> stubDataSourceInfoList() {
        val dataSourceInfo1 = new DataSourceInfo();
        dataSourceInfo1.setName("dataSource1");
        val dataSourceInfo2 = new DataSourceInfo();
        dataSourceInfo2.setName("dataSource2");

        return List.of(dataSourceInfo1, dataSourceInfo2);
    }
}
