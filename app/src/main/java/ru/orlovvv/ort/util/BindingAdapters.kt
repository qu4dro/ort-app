package ru.orlovvv.ort.util

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import ru.orlovvv.ort.adapters.LocationAdapter
import ru.orlovvv.ort.adapters.ReviewAdapter
import ru.orlovvv.ort.models.LocationPreview
import ru.orlovvv.ort.models.Review

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