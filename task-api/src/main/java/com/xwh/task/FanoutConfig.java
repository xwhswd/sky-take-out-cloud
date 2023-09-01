package com.xwh.task;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xwh
 * @version 1.0
 * 2023/8/25
 */
@Configuration
public class FanoutConfig {
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("pub.fanout");
    }

    @Bean("q1")
    public Queue fanoutQueue1(){
        return new Queue("fanout.q1");
    }

    @Bean
    public Binding fanoutBindingA(@Qualifier("q1") Queue fanoutQueue1, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }

    //声明队列2
    @Bean("q2")
    public Queue fanoutQueue2(){
        return  new Queue("fanout.q2");
    }

    //绑定队列2到交换机
    @Bean
    public Binding fanoutBindingB(@Qualifier("q2") Queue fanoutQueue2,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }
}
