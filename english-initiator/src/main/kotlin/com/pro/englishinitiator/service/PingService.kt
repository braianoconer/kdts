package com.pro.englishinitiator.service

import com.pro.englishinitiator.client.GermanProcessor
import com.pro.englishinitiator.client.ItalianProcessor
import com.pro.englishinitiator.client.SpanishProcessor
import org.springframework.stereotype.Service


interface PingService {

    fun pingSpanishProcessor(): String

    fun pingItalianProcessor(): String

    fun pingGermanProcessor(): String

}


@Service
class PingServiceImpl(private val spanishProcessor: SpanishProcessor,
                      private val italianProcessor: ItalianProcessor,
                      private val germanProcessor: GermanProcessor): PingService {

    override fun pingSpanishProcessor(): String {
        return spanishProcessor.process()
    }

    override fun pingItalianProcessor(): String {
        return italianProcessor.process()
    }

    override fun pingGermanProcessor(): String {
        return germanProcessor.process()
    }

}