package com.xwh.task;

import com.xwh.task.minio.MinIOUtil;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**此处使用Scheduled解决定时任务
 * 可以使用mq实现(订单状态查询+状态修改)
 * @author xwh
 * @version 1.0
 * 2023/8/3
 */
@SpringBootTest
@Slf4j
public class OrderTask {

    @Autowired
    private RabbitTemplate rabbitTemplate;//在pom.xmp引入spring-boot-starter-amqp依赖之后就能注入这个模板


    @Test
    public void testSendMessageSimpleQueue(){
        String queueName = "simple.queue2";//自定义队列名称
        String message = "我是使用amqp模板，往队列里面发消息，你接收到了吗";
        rabbitTemplate.convertAndSend(queueName,message);//调用convertAndSend实现发送
    }

    @Test
    public void testSendMessageWorkQueue() throws InterruptedException {
        String queueName = "simple.q1";//自定义队列名称
        String message = "我正在往队列里面发送消息";
        for (int i = 0; i <= 50; i++) {
            rabbitTemplate.convertAndSend(queueName,message+" 消息"+i);//调用convertAndSend实现发送
            Thread.sleep(20);//每隔20毫秒发送一次，共需要1秒才能发完50条消息
        }
    }

    @Test
    public void testSendFanoutExchange(){

        //往哪个交换机发送消息(必须是配置类中的交换机)
        String xxexchangeName = "pub.fanout";

        //要发送什么消息
        String yymessage = "来自生产者向你发送的消息fanout";

        //发送
        rabbitTemplate.convertAndSend(xxexchangeName,"",yymessage);
    }

    @Test
    public void testSendDirectExchange(){

        //往哪个交换机发送消息
        String xxexchangeName = "pub.direct";

        //要发送什么消息
        String yymessage = "来自生产者向你发送的消息blue";

        //发送，需要指定key，例如发送到交换机的消息带了一个key为blue的消息，只有对应的队列才能从交换机获取这个消息
        rabbitTemplate.convertAndSend(xxexchangeName,"blue",yymessage);
    }


    @Test
    public void testSendTopicExchange(){

        //往哪个交换机发送消息
        String xxexchangeName = "pub.topic";

        //要发送什么消息
        String yymessage = "来自生产者向你发送的消息blue";

        //发送，需要指定key，例如发送到交换机的消息带了一个key为blue的消息，只有对应的队列才能从交换机获取这个消息
        rabbitTemplate.convertAndSend(xxexchangeName,"news.abc",yymessage);
    }

    @Test
    public void testSendMessage2SimpleQueue() throws InterruptedException {
        String msg = "abcde";
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        correlationData.getFuture().addCallback(result -> {
            if (result.isAck()){
                log.debug("消息成功发送到交换机! 消息ID是: {}",correlationData.getId());
            }else {
                log.error("消息发送到交换机失败了! 消息ID是: {}",correlationData.getId());
            }
        },ex -> {
            log.error("发送失败");
        });
        //第一个参数是往哪个交换机发，第二个参数是具备什么routingKey的交换机，第三个参数是具体的消息，第四个参数是消息的确认机制
        rabbitTemplate.convertAndSend("simple.topic","",msg,correlationData);
    }

    @Test
    public void testDurableMessage(){
        //要发送的具体消息
        Message message = MessageBuilder.withBody("我是发送的消息，重启后还能看到吗".getBytes(StandardCharsets.UTF_8))
                //PERSISTENT表示让这条消息有持久化的功能
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                //构建
                .build();

        //向simple.queue队列发送消息
        rabbitTemplate.convertAndSend("simple.q1",message);
    }


    @Test
    public void testTTLMsg() {
        // 创建消息
        Message message = MessageBuilder
                .withBody("hello, ttl message".getBytes(StandardCharsets.UTF_8))
                .setExpiration("5000")
                .build();
        // 消息ID，需要封装到CorrelationData中
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        // 发送消息
        rabbitTemplate.convertAndSend("publisher.fanout", "publisher", message, correlationData);
    }



    @Resource
    MinioClient minioClient;
    @Test
    public void test() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket("public")
                        .object("2.jpg")
                        .filename("C:\\Users\\Administrator\\Documents\\1.jpg")
                        .build()
        );
    }
    @Test
    public void testDownload() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.downloadObject(DownloadObjectArgs.builder()
                        .bucket("public")
                        .object("credentials.json")
                        .filename("C:\\Users\\Administrator\\Documents\\credentials1.json")
                .build());
    }


    @Test
    public void testMinio() throws Exception {
//       minioClient.makeBucket(MakeBucketArgs.builder().bucket("abc").build());
//        minioClient.putObject(PutObjectArgs.builder().bucket("abc").object("test").stream(
//                new FileInputStream("C:\\Users\\Administrator\\Documents\\credentials.json"),107,-1).build()
//        );
//        minioClient.removeObject(RemoveObjectArgs.builder().bucket("abc").object("test").build());
        String presignedObjectUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket("abc").object("test")
                .expiry(5, TimeUnit.MINUTES)
                .method(Method.GET)
                .build()
        );
        System.out.println(presignedObjectUrl);
    }

    @Test
    public void testSendDelayMessage() throws InterruptedException {
        //要发送什么消息
        Message message = MessageBuilder
                .withBody("测试延迟队列插件来实现消息延时".getBytes(StandardCharsets.UTF_8))
                //PERSISTENT表示让这条消息有持久化的功能
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                //重点在这里，添加消息头，才能让这条消息实现延时。第一个参数是消息头(固定写法)，第二个参数是延时的时间
                .setHeader("x-delay",50000)
                //构建
                .build();
        //消息确认的id
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        //发送消息，第一个参数是往哪个交换机发，第二个参数是具备什么routingKey的交换机，第三个参数是具体的消息，第四个参数是消息的确认机制
        rabbitTemplate.convertAndSend("delay.direct", "delay", message,correlationData);
        log.info("发送消息成功");
    }

    @Test
    public void test1(){
        Flux.just("a", "b", "c").buffer(2).subscribe(System.out::println);
        Flux.range(1,10).bufferUntil(i->i>5).subscribe(System.out::println);
        Flux.merge(Flux.just("123"),Flux.just("567")).subscribe(System.out::println);

        Mono.just("a").and(Mono.just("abc")).subscribe(System.out::println);
    }

}
