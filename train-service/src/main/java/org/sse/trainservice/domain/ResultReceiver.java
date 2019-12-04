package org.sse.trainservice.domain;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.sse.trainservice.configuration.RabbitConfig;

/**
 * @version: 1.0
 * @author: usr
 * @className: ResultReceive
 * @packageName: org.sse.trainservice.domain
 * @description: send email
 * @data: 2019-12-04 15:49
 **/

@RabbitListener(queues = RabbitConfig.TASK_QUEUE_NAME)
public class ResultReceiver {
}
