package com.pro.translator.controller.service

import org.springframework.stereotype.Service
import java.util.*


object InputWords {

    private val ENGLISH: MutableList<String> = ArrayList()

    init {
        ENGLISH.add("Hello".toLowerCase())
        ENGLISH.add("Goodbye".toLowerCase())
        ENGLISH.add("Good morning".toLowerCase())
        ENGLISH.add("Good night".toLowerCase())
        ENGLISH.add("How are you?".toLowerCase())
        ENGLISH.add("What is your name?".toLowerCase())
        ENGLISH.add("My tailor is rich".toLowerCase())
        ENGLISH.add("My house is your house".toLowerCase())
    }

    val englishWordsLowerCase: List<String>
        get() = ENGLISH
}


enum class TargetLanguage {
    ES, IT, DE
}


@Service
class Dictionary {

    private val ENG_SPA: MutableMap<String, String> = HashMap()
    private val ENG_ITA: MutableMap<String, String> = HashMap()
    private val ENG_DEU: MutableMap<String, String> = HashMap()

    init {

        val english: List<String> = InputWords.englishWordsLowerCase

        ENG_SPA[english[0]] = "Hola"
        ENG_SPA[english[1]] = "Adios"
        ENG_SPA[english[2]] = "Buenos dias"
        ENG_SPA[english[3]] = "Buenas noches"
        ENG_SPA[english[4]] = "Como estas?"
        ENG_SPA[english[5]] = "Como te llamas?"
        ENG_SPA[english[6]] = "Mi sastre es rico"
        ENG_SPA[english[7]] = "Mi casa es tu casa"

        ENG_ITA[english[0]] = "Ciao"
        ENG_ITA[english[1]] = "Arrivederci"
        ENG_ITA[english[2]] = "Buon giorno"
        ENG_ITA[english[3]] = "Buonanotte"
        ENG_ITA[english[4]] = "Come stai?"
        ENG_ITA[english[5]] = "Come ti chiami?"
        ENG_ITA[english[6]] = "Il mio sartro e ricco"
        ENG_ITA[english[7]] = "La mia casa e anche tua"

        ENG_DEU[english[0]] = "Hallo"
        ENG_DEU[english[1]] = "Tschuss"
        ENG_DEU[english[2]] = "Guten Morgen"
        ENG_DEU[english[3]] = "Guten Nacht"
        ENG_DEU[english[4]] = "Wie geht's dir?"
        ENG_DEU[english[5]] = "Wie heisst du?"
        ENG_DEU[english[6]] = "Mein Schneider ist reich"
        ENG_DEU[english[7]] = "Mein Haus ist dein Haus"
    }

    fun translateTo(targetLang: TargetLanguage, word: String): String {

        val default = "N/A"

        return when (targetLang) {
            TargetLanguage.ES -> ENG_SPA.getOrDefault(word.toLowerCase(), default)
            TargetLanguage.IT -> ENG_ITA.getOrDefault(word.toLowerCase(), default)
            TargetLanguage.DE -> ENG_DEU.getOrDefault(word.toLowerCase(), default)
        }
    }

}


