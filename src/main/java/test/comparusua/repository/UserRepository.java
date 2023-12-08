package test.comparusua.repository;

import test.comparusua.config.property.DataSourceInfo;
import test.comparusua.model.User;

import java.util.List;

public interface UserRepository {

    List<User> getAllUsersByDatasourceName(DataSourceInfo dataSourceInfo);
}
