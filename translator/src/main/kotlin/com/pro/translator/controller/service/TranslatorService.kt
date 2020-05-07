package com.pro.translator.controller.service

import org.springframework.stereotype.Service
import java.util.*


enum class TargetLanguage {
    ES, IT, DE
}


@Service
class Dictionary {

    private val english = listOf("hello", "goodbye", "good morning",
            "good night", "how are you?", "what is your name?",
            "my tailor is rich", "my house is your house")

    private val ENG_SPA: MutableMap<String, String> = HashMap()
    private val ENG_ITA: MutableMap<String, String> = HashMap()
    private val ENG_DEU: MutableMap<String, String> = HashMap()

    init {

        ENG_SPA[english[0]] = "hola"
        ENG_SPA[english[1]] = "adios"
        ENG_SPA[english[2]] = "buenos dias"
        ENG_SPA[english[3]] = "buenas noches"
        ENG_SPA[english[4]] = "como estas?"
        ENG_SPA[english[5]] = "como te llamas?"
        ENG_SPA[english[6]] = "mi sastre es rico"
        ENG_SPA[english[7]] = "mi casa es tu casa"

        ENG_ITA[english[0]] = "ciao"
        ENG_ITA[english[1]] = "arrivederci"
        ENG_ITA[english[2]] = "buon giorno"
        ENG_ITA[english[3]] = "buonanotte"
        ENG_ITA[english[4]] = "come stai?"
        ENG_ITA[english[5]] = "come ti chiami?"
        ENG_ITA[english[6]] = "il mio sartro e ricco"
        ENG_ITA[english[7]] = "la mia casa e anche tua"

        ENG_DEU[english[0]] = "hallo"
        ENG_DEU[english[1]] = "tschuss"
        ENG_DEU[english[2]] = "guten morgen"
        ENG_DEU[english[3]] = "guten nacht"
        ENG_DEU[english[4]] = "wie geht's dir?"
        ENG_DEU[english[5]] = "wie heisst du?"
        ENG_DEU[english[6]] = "mein schneider ist reich"
        ENG_DEU[english[7]] = "mein haus ist dein haus"
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


