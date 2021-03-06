package com.pro.genericprocessor.messaging

import com.pro.genericprocessor.client.Translator
import com.pro.genericprocessor.config.ServiceConfig
import com.pro.genericprocessor.config.logger
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct


interface MsgConsumer<K, V> : AutoCloseable {
    fun consume(msg: V)
}


@Service
class SpringKafkaMsgConsumer(private val msgPublisher: MsgPublisher<Long, String>,
                             private val config: ServiceConfig,
                             private val translator: Translator) : MsgConsumer<Long, String> {

    private val log = logger()


    @PostConstruct
    private fun init() {
        createInTopic()
    }

    @Bean
    fun createInTopic(): NewTopic {
        return NewTopic(config.getKafkaInTopicName(), config.getKafkaInTopicPartitions(), config.getKafkaInTopicReplicationFactor())
    }

    @KafkaListener(topics = ["\${service.kafka-in-topic}"], groupId = "\${spring.kafka.consumer.group-id}")
    override fun consume(msg: String) {

        log.info("--> KAFKA [$msg]")
        val englishToTranslate = extractWord(msg)

        try {
            val translation = translator.translate(target = config.translatorTarget, word = englishToTranslate)
            msgPublisher.publish("$msg$translation")
        } catch (e: Exception) {
            log.error("Received a translation error for msg {}. Error: {}", msg, e.message)
        }
    }

    override fun close() {
        TODO("Not yet implemented")
    }

    private fun extractWord(input: String): String {
        val beginIdx = input.indexOf(":") + 1
        val endIdx = input.indexOf("-")
        return input.substring(beginIdx, endIdx)
    }

}