package ru.orlovvv.ort.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.textview.MaterialTextView
import ru.orlovvv.ort.R
import ru.orlovvv.ort.adapters.LocationAdapter
import ru.orlovvv.ort.adapters.ReviewAdapter
import ru.orlovvv.ort.models.LocationPreview
import ru.orlovvv.ort.models.Review
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@BindingAdapter("locationsPreviewList")
fun bindLocationsPreviewRecyclerView(recyclerView: RecyclerView, data: List<LocationPreview>?) {
    val adapter = recyclerView.adapter as LocationAdapter
    adapter.submitList(data)
}

@BindingAdapter("reviewsList")
fun bindReviewsRecyclerView(recyclerView: RecyclerView, data: List<Review>?) {
    val adapter = recyclerView.adapter as ReviewAdapter
    adapter.submitList(data)
}

@BindingAdapter("ratingColor")
fun bindLocationRatingColor(chip: Chip, value: Int) {
    when (value) {
        Rating.FIVE.value -> chip.setChipBackgroundColorResource(R.color.rating_5)
        Rating.FOUR.value -> chip.setChipBackgroundColorResource(R.color.rating_4)
        Rating.THREE.value -> chip.setChipBackgroundColorResource(R.color.rating_3)
        Rating.TWO.value -> chip.setChipBackgroundColorResource(R.color.rating_2)
        Rating.ONE.value -> chip.setChipBackgroundColorResource(R.color.rating_1)
        else -> {
            chip.setChipBackgroundColorResource(R.color.blue_50)
            chip.setTextColor(ContextCompat.getColor(chip.context, R.color.black_900))
            chip.text = ":("
        }
    }
}

@BindingAdapter("rangeBeautify")
fun bindRangeBeautiful(chip: Chip, value: Double) {
    if (value < 1000) {
        val rounded = value.toBigDecimal().setScale(0, RoundingMode.UP)
        chip.text = chip.context.getString(R.string.meters, rounded)
    } else {
        val rounded = (value / 1000.0).toBigDecimal().setScale(1, RoundingMode.UP)
        chip.text = chip.context.getString(R.string.kilometers, rounded)
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("formattedDate")
fun bindRangeBeautiful(textView: MaterialTextView, value: String) {
    val dateFormatted: String =
        LocalDateTime.parse(
            value,
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
        ).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")).toString()

    textView.text = dateFormatted

}