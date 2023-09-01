package com.xwh.task;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xwh
 * @version 1.0
 * 2023/8/25
 */
@Configuration
public class DelayConfig {
    @Bean("dq1")
    public Queue queue(){
        return QueueBuilder.durable("delay.queue")
                .lazy()
                .quorum()
                .build();
    }
    @Bean("de1")
    public DirectExchange fanoutExchange(){
        return ExchangeBuilder.directExchange("delay.direct")
                .delayed()
                .build();
    }
    @Bean
    public Binding bindingDelay(@Qualifier("dq1") Queue q1,@Qualifier("de1") DirectExchange directExchange){
        return BindingBuilder.bind(q1).to(directExchange).with("delay");
    }
}
