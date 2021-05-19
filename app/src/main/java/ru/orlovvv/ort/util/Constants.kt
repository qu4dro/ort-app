package ru.orlovvv.ort.util

class Constants {
    companion object {
        const val BASE_URL = "https://protected-beach-00413.herokuapp.com/"
    }
}

enum class Rating(val value: Int) {
    FIVE(5), FOUR(4), THREE(3), TWO(2), ONE(1)
}