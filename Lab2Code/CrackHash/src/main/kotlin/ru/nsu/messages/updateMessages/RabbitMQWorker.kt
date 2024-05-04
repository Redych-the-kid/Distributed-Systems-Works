package ru.nsu.messages.updateMessages

import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Channel
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import kotlinx.serialization.json.Json
import ru.nsu.data.MongoDBUtil

class RabbitMQWorker(private val channel: Channel?, private val dbUtil: MongoDBUtil) {
    fun startConsuming(queueName: String) {
        channel?.basicConsume(queueName, true, object : DefaultConsumer(channel) {
            override fun handleDelivery(consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties?, body: ByteArray?) {
                println("Received message!")
                val message = Json.decodeFromString<UpdateDTO>(String(body!!, Charsets.UTF_8))
                dbUtil.updateDocument(message.toDocument())
            }
        })
    }
}