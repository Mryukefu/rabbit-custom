package com.example.constants;


import lombok.ToString;

@ToString
public enum ExQueBindEnum {

   // RABBITMQ_TEST(ExchangeNameConstant.EXCHANGE_TEST, QueueNameConstant.QUEUE_TEST, RuoTingKey.KEY_TEST),
    RABBITMQ_DELAY_TEST(ExchangeNameConstant.EXCHANGE_DELAY_TEST,QueueNameConstant.QUEUE_DELAY_TEST,RuoTingKey.KEY_DELAY_TEST,true);

    private String queueName;
    private String exchangeName;
    private String RoutingKey;
    private Boolean isDelayQueue=false;

    public String getQueueName() {
        return queueName;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public String getRoutingKey() {
        return RoutingKey;
    }

    public Boolean getDelayQueue() {
        return isDelayQueue;
    }

    ExQueBindEnum(String exchangeName, String queueName) {
        this.queueName = queueName;
        this.exchangeName = exchangeName;
    }

    ExQueBindEnum( String exchangeName,String queueName, String routingKey) {
        this.queueName = queueName;
        this.exchangeName = exchangeName;
        RoutingKey = routingKey;
    }

    ExQueBindEnum( String exchangeName,String queueName, Boolean isDelayQueue) {
        this.queueName = queueName;
        this.exchangeName = exchangeName;
        this.isDelayQueue = isDelayQueue;
    }
    ExQueBindEnum(String queueName, String exchangeName, String routingKey, Boolean isDelayQueue) {
        this.queueName = queueName;
        this.exchangeName = exchangeName;
        RoutingKey = routingKey;
        this.isDelayQueue = isDelayQueue;
    }


}
