package com.xwh.task.redisLock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.redisson.api.RedissonClient;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author xwh
 * @version 1.0
 * 2023/8/25
 */
@Slf4j
@SpringBootTest
public class RedisLockTest {
    @Resource
    private RedisTemplate redisTemplate;
    @Test
    public void testSetNX(){
        Boolean flag = redisTemplate.opsForValue().setIfAbsent("lock", "true");
        if (!flag){
            log.info("获取共享锁失败");
            throw new RuntimeException("获取共享锁失败");
        }else {
            log.info("完成业务代码");
//            redisTemplate.delete("lock");
            log.info("释放共享锁");
        }
    }


    @Resource
    RedissonClient redissonClient;
    @Test
    public void testRedisson() {
        boolean b = false;
        RLock genLock = redissonClient.getLock("genLock");
        try {
            b = genLock.tryLock(1, TimeUnit.SECONDS);
            if (b) {
                log.info("完成业务代码");
            } else {
                log.info("获取锁失败");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (b) {
//                genLock.unlock();
            }
        }
    }
}
