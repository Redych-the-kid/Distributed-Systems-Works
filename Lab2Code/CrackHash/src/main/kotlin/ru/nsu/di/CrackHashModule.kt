package ru.nsu.di

import org.koin.dsl.module
import ru.nsu.data.MongoDBUtil
import ru.nsu.messages.crackMessages.RabbitMQSender
import ru.nsu.messages.util.RabbitMQConfig
import ru.nsu.messages.util.RabbitMQConnection
import ru.nsu.messages.updateMessages.RabbitMQWorker

val UtilModule = module(createdAtStart = true) {
    single {
        RabbitMQConfig(
            host = "rabbitmq",
            port = 5672,
            username = "guest",
            password = "guest",
            sendQueueName = "worker_queue",
            sendExchangeName = "worker_exchange",
            sendRoutingKey = "worker_routing",
            receiveQueueName = "update_queue",
            receiveExchangeName = "update_exchange",
            receiveRoutingKey = "update_routing"
        )
    }
    single{
        MongoDBUtil()
    }
    single { RabbitMQConnection(get()) }
    single { RabbitMQWorker(get<RabbitMQConnection>().getSendChannel(), get<MongoDBUtil>()) }
    single { RabbitMQSender(get<RabbitMQConnection>().getSendChannel()) }
}