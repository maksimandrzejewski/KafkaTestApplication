package maksim.andrzejewski.KafkaTestApplication.config;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app.properties")
@Data
public class AppProperties {

    @NotEmpty
    private String kafkaBootstrapServerUrl;

    @NotEmpty
    private String topicName;

    @NotEmpty
    private String groupId;

}
