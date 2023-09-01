package com.xwh.task;

import org.apache.commons.lang.enums.Enum;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xwh
 * @version 1.0
 * 2023/8/25
 */
//@Configuration
public class DirectConfig {
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("pub.direct");
    }

    @Bean("q11")
    public Queue directQueue1(){
//        return QueueBuilder.durable("direct.q1")
//                .deadLetterExchange("dl.direct")
//                .ttl(10000)
//                .build();
        return new Queue("direct.q1");
    }

    @Bean
    public Binding bindingQ1Exchange(@Qualifier("q11") Queue q1, DirectExchange exchange){
        return BindingBuilder.bind(q1).to(exchange).with("red");
    }

    @Bean("q22")
    public Queue directQueue2(){
        return new Queue("direct.q2");
    }

    @Bean
    public Binding bindingQ2Exchange(@Qualifier("q22") Queue q2, DirectExchange exchange){
        return BindingBuilder.bind(q2).to(exchange).with("blue");
    }
}
