package test.comparusua.config.property;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@NoArgsConstructor
@ConfigurationProperties(prefix = "core")
public class DataSourceProperties {

    private List<DataSourceInfo> dataSources;
}
