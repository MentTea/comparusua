package test.comparusua.repository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import test.comparusua.config.property.DataSourceInfo;
import test.comparusua.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static test.comparusua.util.StubModelHelper.stubDataSourceInfo;
import static test.comparusua.util.StubModelHelper.stubUsers;

class JdbcUserRepositoryImplTest {

    private final JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);
    private final JdbcUserRepositoryImpl userRepository = new JdbcUserRepositoryImpl(jdbcTemplate);

    @Test
    void getAllUsersByDatasourceName_ok() {
        DataSourceInfo dataSourceInfo = stubDataSourceInfo();
        List<User> expectedUsers = stubUsers();

        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class))).thenReturn(expectedUsers);

        List<User> actualUsers = userRepository.getAllUsersByDatasourceName(dataSourceInfo);

        assertEquals(expectedUsers, actualUsers);

        verify(jdbcTemplate).query(anyString(), any(BeanPropertyRowMapper.class));
    }
}