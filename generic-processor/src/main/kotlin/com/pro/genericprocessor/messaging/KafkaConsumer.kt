package com.pro.genericprocessor.messaging

import com.pro.genericprocessor.client.Translator
import com.pro.genericprocessor.config.ServiceConfig
import com.pro.genericprocessor.config.logger
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

interface MsgConsumer<K, V> : AutoCloseable {
    fun consume(msg: V)
}

@Service
class SpringKafkaMsgConsumer(val msgPublisher: MsgPublisher<Long, String>,
                             val config: ServiceConfig,
                             val translator: Translator) : MsgConsumer<Long, String> {

    private val log = logger()

    @KafkaListener(topics = ["\${service.kafka-in-topic}"], groupId = "\${spring.kafka.consumer.group-id}")
    override fun consume(msg: String) {
        log.info("received from Kafka [$msg]")
        val englishToTranslate = extractWord(msg)
        val translation = translator.translate(target = config.translatorTarget, word = englishToTranslate)
        msgPublisher.publish("$msg$translation")
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