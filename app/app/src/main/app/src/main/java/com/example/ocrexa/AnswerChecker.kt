package com.example.ocrexa

object AnswerChecker {

    // ✍️ ПРАВИЛЬНІ ВІДПОВІДІ (можеш змінювати)
    private val correctAnswers = listOf(
        "Kyiv",
        "London",
        "Paris",
        "Berlin",
        "Rome"
    )

    fun check(text: String): Result {
        val lowerText = text.lowercase()
        val found = mutableListOf<String>()
        val notFound = mutableListOf<String>()

        for (answer in correctAnswers) {
            if (lowerText.contains(answer.lowercase())) {
                found.add(answer)
            } else {
                notFound.add(answer)
            }
        }
        return Result(found, notFound)
    }

    data class Result(
        val found: List<String>,
        val missing: List<String>
    )
}
