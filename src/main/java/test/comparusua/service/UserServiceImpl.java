package test.comparusua.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import test.comparusua.config.property.DataSourceProperties;
import test.comparusua.model.User;
import test.comparusua.repository.UserRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final DataSourceProperties dataSourceProperties;
    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        val futures = dataSourceProperties.getDataSources().stream()
                .map(dataSourceInfo -> CompletableFuture.supplyAsync(() ->
                        userRepository.getAllUsersByDatasourceName(dataSourceInfo)))
                .toList();

        return futures.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .toList();
    }
}
