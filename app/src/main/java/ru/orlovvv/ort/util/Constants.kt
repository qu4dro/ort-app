package ru.orlovvv.ort.util

class Constants {

    companion object {

        const val BASE_URL = "https://protected-beach-00413.herokuapp.com/"

        const val REQUEST_CODE_LOCATION_PERMISSION = 0

        const val LOCATION_UPDATE_INTERVAL = 60000L
        const val FASTEST_LOCATION_INTERVAL = LOCATION_UPDATE_INTERVAL / 4
    }
}

enum class Rating(val value: Int) {
    FIVE(5), FOUR(4), THREE(3), TWO(2), ONE(1)
}