package ru.nsu.messages.util

import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.Connection
import com.rabbitmq.client.Channel

class RabbitMQConnection(private val config: RabbitMQConfig) {

    private var connection: Connection? = null
    private var channel: Channel? = null
    init{
        connect()
    }
    private fun connect() {
        val factory = ConnectionFactory()
        factory.host = config.host
        factory.port = config.port
        factory.username = config.username
        factory.password = config.password

        connection = factory.newConnection()
        channel = connection?.createChannel()
        channel?.exchangeDeclare(config.sendExchangeName, "direct")
        channel?.exchangeDeclare(config.receiveExchangeName, "direct")
        channel?.queueDeclare(config.sendQueueName, true, false, false, null)
        channel?.queueDeclare(config.receiveQueueName, true, false, false, null)
        channel?.queueBind(config.sendQueueName, config.sendExchangeName, config.sendRoutingKey)
        channel?.queueBind(config.receiveQueueName, config.receiveExchangeName, config.receiveRoutingKey)
    }

    fun getSendChannel(): Channel? {
        return channel
    }
}