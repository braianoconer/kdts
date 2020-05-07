package com.pro.englishinitiator.service

import com.pro.englishinitiator.config.ServiceConfig
import com.pro.englishinitiator.config.logger
import com.pro.englishinitiator.messaging.MsgPublisher
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.stream.IntStream
import kotlin.math.abs

interface TaskExecutor {

    fun processSync()

    fun processAsync(batchSize: Int)

    fun stop()

    fun setBatchSize(batchSize: Int)

    fun getRandomInput(): String
}


@Service
class TaskExecutorImpl(private val config: ServiceConfig,
                       private val msgPublisher: MsgPublisher<Long, String>,
                       private val batchSize: AtomicInteger = AtomicInteger(100),
                       private val keepSending: AtomicBoolean = AtomicBoolean(false)) : TaskExecutor {

    private val log = logger()
    private val rnd = Random()

    private val english = listOf("hello", "goodbye", "good morning",
            "good night", "how are you?", "what is your name?",
            "my tailor is rich", "my house is your house")


    override fun processSync() {
        msgPublisher.publish(getRandomInput() + "-")
    }

    @Async
    override fun processAsync(batchSize: Int) {

        log.debug("Start method invoked with size {}", batchSize)

        if (keepSending.compareAndSet(false, true)) {
            setBatchSize(batchSize)

            while (keepSending.get()) {

                IntStream.range(0, this.batchSize.get()).forEach {

                    log.trace("Sending batch. Processing iteration $it")

                    val start = Instant.now()
                    msgPublisher.publish(getRandomInput() + "-")

                    //TODO micrometer.Timer
                    log.debug("TODO micrometer {}",Duration.between(start, Instant.now()))
                }

                try {
                    Thread.sleep(config.delayMillis)
                } catch (e: InterruptedException) {
                    log.error(e.message)
                }
            }
        }
    }

    override fun stop() {
        log.debug("Stop method invoked...")
        keepSending.compareAndSet(true, false)
    }

    override fun setBatchSize(batchSize: Int) {
        log.debug("SetBatch method invoked with size {}", batchSize)
        if (keepSending.get()) {
            this.batchSize.getAndSet(batchSize)
        }
    }

    override fun getRandomInput(): String {
        return english[abs(rnd.nextInt()) % (english.size - 1)]
    }

}