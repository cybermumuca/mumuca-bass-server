package com.mumuca.mumucabass.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AOFCompactionJob {

    private static final Logger logger = LoggerFactory.getLogger(AOFCompactionJob.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Scheduled(cron = "0 0 0 * * ?")
    public void execute() {
        logger.info("Starting AOF compression in Redis...");

        try {
            redisTemplate.getConnectionFactory()
                    .getConnection()
                    .execute("BGREWRITEAOF");
            logger.info("AOF compression completed.");
        } catch (Exception e) {
            logger.error("Error compressing AOF: ", e);
        }
    }
}
