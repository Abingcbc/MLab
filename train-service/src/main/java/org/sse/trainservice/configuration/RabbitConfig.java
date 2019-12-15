package org.sse.trainservice.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sse.trainservice.service.PredictReceiver;
import org.sse.trainservice.service.ResultReceiver;
import org.sse.trainservice.service.TaskReceiver;

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

    public static final String PREDICT_QUEUE_NAME="PredictQueue";
    public static final String PREDICT_EXCHANGE_NAME="PredictExchange";
    public static final String PREDICT_ROUTING_NAME="PredictRouting";
    @Bean
    public Queue taskQueue() {
        return new Queue(TASK_QUEUE_NAME,true);
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
        return new Queue(RESULT_QUEUE_NAME,true);
    }


    @Bean
    DirectExchange resultExchange() {
        return new DirectExchange(RESULT_EXCHANGE_NAME);
    }

    @Bean
    Binding bindingResult() { return BindingBuilder.bind(resultQueue()).to(resultExchange()).with(RESULT_ROUTING_NAME);}

    @Bean
    public Queue predictQueue(){return new Queue(PREDICT_QUEUE_NAME,true);}

    @Bean
    DirectExchange predictExchange() {
        return new DirectExchange(PREDICT_EXCHANGE_NAME);
    }

    @Bean
    Binding bindingPredict() { return BindingBuilder.bind(predictQueue()).to(predictExchange()).with(PREDICT_ROUTING_NAME); }

    @Bean
    public TaskReceiver taskReceiver1(){return new TaskReceiver("1");}

    /**
    @Bean
    public TaskReceiver taskReceiver2(){return new TaskReceiver("2");}

    @Bean
    public TaskReceiver taskReceiver3(){return new TaskReceiver("3");}
     **/

    @Bean
    public PredictReceiver predictReceiver1(){return new PredictReceiver("1");}

    /**
    @Bean
    public PredictReceiver predictReceiver2(){return new PredictReceiver("2");}

    @Bean
    public PredictReceiver predictReceiver3(){return new PredictReceiver("3");}
     **/

    @Bean
    public ResultReceiver resultReceiver(){return new ResultReceiver();}


}
