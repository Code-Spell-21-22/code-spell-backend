package pt.ua.deti.codespell.codespellbackend.rabbitmq;

import lombok.Getter;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Getter
public class RabbitMQHandler {

    private final String topicExchangeName = "code-spell-launcher-exchange";

    private final String codeReceiverQueue = "code-spell-launcher-receiver-queue";

    private final String codeAnalysisResultsQueue = "code-spell-launcher-analysis-results-queue";

    private final String routingKey = "code_spell.launcher.#";

    @Bean
    Queue launcherReceiverQueue() {
        return new Queue(codeReceiverQueue, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    public String getRoutingKeyWithTopic(String topic) {
        return routingKey.replace("#", topic);
    }

}
