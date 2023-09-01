package com.xwh.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author xwh
 * @version 1.0
 * 2023/8/24
 */
@Component
@Slf4j
public class SpringRabbitListener {
    @RabbitListener(queues = {"simple.queue2"})
    public void listenQ2(String msg){
        System.out.println("测试队列"+msg);
    }

    @RabbitListener(queues = {"simple.q1"})
    public void listenQ1(String msg) throws InterruptedException {
        System.out.println("Q1a:"+msg);
        Thread.sleep(20);
    }
    @RabbitListener(queues = {"simple.q1"})
    public void listenQ12(String msg) throws InterruptedException {
        System.out.println("Q1b:"+msg);
        Thread.sleep(100);
    }

    //接收fanout.queue1队列的消息
    @RabbitListener(queues = "fanout.q1")
    public void listenFanoutQueue1(String msg){
        System.out.println("消费者接收到了fanout.queue1队列的消息是【"+msg+"】");
    }

    //接收fanout.queue2队列的消息
    @RabbitListener(queues = "fanout.q2")
    public void listenFanoutQueue2(String msg){
        System.out.println("消费者接收到了fanout.queue1队列的消息是【"+msg+"】");
    }


    @RabbitListener(queues = "direct.q1")
    public void listenDirectQueue1(String msg){
        System.out.println("direct.queue1队列的消息是【"+msg+"】");
    }


    @RabbitListener(queues = "direct.q2")
    public void listenDirectQueue2(String msg){
        System.out.println("direct.queue2队列的消息是【"+msg+"】");
    }

    //    创建方法2
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(name = "direct.q3"),
//            exchange = @Exchange(),
//            key = {"red","blue"},
//            arguments = @Argument(name = "x-queue-mode" , value = "lazy")
//    ))
//    public void listenDirectQueue3(String msg){
//        System.out.println("direct.queue3队列的消息是【"+msg+"】");
//    }

    @RabbitListener(queues = "topic.q1")
    public void listenTopicQ1(String msg){
        System.out.println("topic.queue1队列的消息是【"+msg+"】");
    }

    @RabbitListener(queues = "delay.queue")
    public void listenDelayQueue(String msg){
        System.out.println("delay.queue1队列的消息是【"+msg+"】");
    }
}
