package test.comparusua.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import test.comparusua.config.DataSourceContextHolder;
import test.comparusua.config.property.DataSourceInfo;
import test.comparusua.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static test.comparusua.util.ExceptionConstants.GETTING_USERS_FROM_DB_EXCEPTION_TEMPLATE;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JdbcUserRepositoryImpl implements UserRepository {

    public static final String SELECT_FIELDS_FROM_TABLE_TEMPLATE = "SELECT %s FROM %s";
    public static final String AS = " AS ";
    public static final String COMA_AND_SPACE = ", ";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<User> getAllUsersByDatasourceName(DataSourceInfo dataSourceInfo) {
        List<User> users = new ArrayList<>();

        try {
            DataSourceContextHolder.setDataSourceKey(dataSourceInfo.getName());

            val sql = buildSqlQuery(dataSourceInfo);
            users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
        } catch (Exception e) {
            log.error(GETTING_USERS_FROM_DB_EXCEPTION_TEMPLATE, dataSourceInfo.getName(), e);
        } finally {
            DataSourceContextHolder.clearDataSourceKey();
        }
        return users;
    }

    private String buildSqlQuery(DataSourceInfo dataSourceInfo) {
        val selectFields = new StringBuilder();
        for (Map.Entry<String, String> entry : dataSourceInfo.getMapping().entrySet()) {
            selectFields.append(entry.getValue()).append(AS).append(entry.getKey()).append(COMA_AND_SPACE);
        }
        selectFields.deleteCharAt(selectFields.length() - COMA_AND_SPACE.length());

        return String.format(SELECT_FIELDS_FROM_TABLE_TEMPLATE, selectFields, dataSourceInfo.getTable());
    }
}
