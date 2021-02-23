package com.example.rabbitcustom;

import com.example.constants.QueueNameConstant;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ActListen {

    @RabbitListener(queues = QueueNameConstant.QUEUE_TEST)
    public void receiver(String msg) {
        System.out.println(msg);
    }
}
