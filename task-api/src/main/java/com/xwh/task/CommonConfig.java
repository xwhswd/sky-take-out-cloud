package com.xwh.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**编写全局Callback
 * @author xwh
 * @version 1.0
 * 2023/8/25
 */
@Configuration
@Slf4j
public class CommonConfig implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
        rabbitTemplate.setReturnCallback(
                (message, replyCode, replyText, exchange, routingKey) -> {
                    if (message.getMessageProperties().getReceivedDelay()>0){//延迟队列
                        return;
                    }
                    log.error("消息发送到队列失败，响应码是:{}, 失败的原因是:{}, 交换机的名字:{}, routingkey是:{}, 消息是:{}",
                        replyCode,replyText,exchange,routingKey,message.toString());
                }
        );
    }
}
