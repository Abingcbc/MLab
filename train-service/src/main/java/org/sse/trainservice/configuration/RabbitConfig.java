package org.sse.trainservice.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sse.trainservice.domain.TaskReceiver;

/**
 * @version: 1.0
 * @author: usr
 * @className: DirectRabbitConfig
 * @packageName: org.sse.trainservice.configuration
 * @description: RabbitMQ Config
 * @data: 2019-12-03 18:18
 **/
@Configuration
public class RabbitConfig {

    public static final String TASK_QUEUE_NAME="TaskQueue";
    public static final String TASK_EXCHANGE_NAME="TaskExchange";
    public static final String TASK_ROUTING_NAME="TaskRouting";

    public static final String RESULT_QUEUE_NAME="ResultQueue";
    public static final String RESULT_EXCHANGE_NAME="ResultExchange";
    public static final String RESULT_ROUTING_NAME="ResultRouting";

    @Bean
    public Queue taskQueue() {
        return new Queue(TASK_QUEUE_NAME,true);  //true 是否持久
    }


    @Bean
    DirectExchange taskExchange() {
        return new DirectExchange(TASK_EXCHANGE_NAME);
    }


    @Bean
    Binding bindingTask() {
        return BindingBuilder.bind(taskQueue()).to(taskExchange()).with(TASK_ROUTING_NAME);
    }

    @Bean
    public Queue resultQueue() {
        return new Queue(RESULT_QUEUE_NAME,true);  //true 是否持久
    }


    @Bean
    DirectExchange resultExchange() {
        return new DirectExchange(RESULT_EXCHANGE_NAME);
    }

    @Bean
    public TaskReceiver taskReceiver1(){return new TaskReceiver("1");}

    @Bean
    public TaskReceiver taskReceiver2(){return new TaskReceiver("2");}

    @Bean
    public TaskReceiver taskReceiver3(){return new TaskReceiver("3");}

    @Bean
    Binding bindingresult() {
        return BindingBuilder.bind(taskQueue()).to(taskExchange()).with(RESULT_ROUTING_NAME);
    }
}
