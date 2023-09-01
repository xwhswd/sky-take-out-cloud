package com.xwh.task;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xwh
 * @version 1.0
 * 2023/8/25
 */
@Configuration
public class TopicConfig {
    @Bean("topicEx")
    public TopicExchange topicExchange(){
        return new TopicExchange("pub.topic");
    }

    @Bean("tq1")
    public Queue topicQ1(){
        return new Queue("topic.q1");
    }

    @Bean
    public Binding bindingQ1Exchange(@Qualifier("tq1") Queue q1,@Qualifier("topicEx") TopicExchange topicExchange){
        return BindingBuilder.bind(q1).to(topicExchange).with("news.#");
    }

    //添加信息转换，以便于传输对象而不乱码---》发送和接收的convertor必须相同
    @Bean
    //这里只添加这个方法
    public MessageConverter messageConverter(){
        //使用Jackson消息转换工具，将消息转换为JSON格式数据
        return new Jackson2JsonMessageConverter();
    }
}
