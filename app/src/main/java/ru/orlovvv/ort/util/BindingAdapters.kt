package ru.orlovvv.ort.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.orlovvv.ort.adapters.LocationAdapter
import ru.orlovvv.ort.models.LocationPreview

@BindingAdapter("locationsPreviewList")
fun bindLocationsPreviewRecyclerView(recyclerView: RecyclerView, data: List<LocationPreview>) {
    val adapter = recyclerView.adapter as LocationAdapter
    adapter.submitList(data)
}