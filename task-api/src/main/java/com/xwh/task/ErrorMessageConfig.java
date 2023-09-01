package com.xwh.task;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xwh
 * @version 1.0
 * 2023/8/25
 */
@Configuration
public class ErrorMessageConfig {
    @Bean
    //创建一个名为error.direct的交换机
    public DirectExchange errorMessageExchange(){
        return new DirectExchange("error.direct");
    }

    @Bean
    //创建一个名为error.queue的队列
    public Queue errorQueue(){
        return new Queue("error.queue");
    }

    @Bean
    //将error.queue队列，绑定在error.direct交换机
    public Binding errorMessageBinding(){
        //bind方法的参数是队列，to方法的参数是交换机，with方法的参数是自定义routingkey
        return BindingBuilder.bind(errorQueue()).to(errorMessageExchange()).with("error");
    }

    @Bean
    //这个bean里面默认使用的是RejectAndDontRequeueRecoverer处理模式，
    //我们要修改为RepublishMessageRecoverer处理模式，也就是覆盖spring的默认配置
    public MessageRecoverer republishMessageRecoverer(RabbitTemplate rabbitTemplate){
        return new RepublishMessageRecoverer(rabbitTemplate,"error.direct","error");
        //上面那行的第一个参数是RabbitTemplate对象(需要注入)，第二个参数是交换机，第三个参数是routingkey
        //凡是失败的消息，都会被spring发送到error.direct交换机，由error.direct交换机发送到error.queue队列进行保存
    }
}
