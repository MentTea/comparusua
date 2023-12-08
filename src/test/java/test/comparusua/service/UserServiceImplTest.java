package test.comparusua.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import test.comparusua.config.property.DataSourceInfo;
import test.comparusua.config.property.DataSourceProperties;
import test.comparusua.model.User;
import test.comparusua.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static test.comparusua.util.StubModelHelper.stubDataSourceInfoList;
import static test.comparusua.util.StubModelHelper.stubUsers;

class UserServiceImplTest {

    private final DataSourceProperties dataSourceProperties = Mockito.mock(DataSourceProperties.class);
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);

    private final UserServiceImpl userService = new UserServiceImpl(dataSourceProperties, userRepository);

    @Test
    void getAllUsers_ok() {
        List<DataSourceInfo> dataSourceInfos = stubDataSourceInfoList();
        List<User> mockUsers1 = stubUsers();
        List<User> mockUsers2 = stubUsers();

        when(dataSourceProperties.getDataSources()).thenReturn(dataSourceInfos);
        when(userRepository.getAllUsersByDatasourceName(dataSourceInfos.get(0))).thenReturn(mockUsers1);
        when(userRepository.getAllUsersByDatasourceName(dataSourceInfos.get(1))).thenReturn(mockUsers2);

        List<User> result = userService.getAllUsers();

        verify(userRepository, times(1)).getAllUsersByDatasourceName(dataSourceInfos.get(0));
        verify(userRepository, times(1)).getAllUsersByDatasourceName(dataSourceInfos.get(1));

        assertEquals(4, result.size());
    }
}