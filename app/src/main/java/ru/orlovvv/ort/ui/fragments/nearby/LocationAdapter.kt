package ru.orlovvv.ort.ui.fragments.nearby

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.orlovvv.ort.databinding.ItemLocationPreviewBinding
import ru.orlovvv.ort.models.LocationPreview

class LocationAdapter :
    ListAdapter<LocationPreview, LocationAdapter.LocationViewHolder>(LocationCallBack()) {

    class LocationViewHolder(private val binding: ItemLocationPreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(locationPreview: LocationPreview) {
            binding.location = locationPreview
            binding.executePendingBindings()
        }
    }

    class LocationCallBack : DiffUtil.ItemCallback<LocationPreview>() {
        override fun areItemsTheSame(oldItem: LocationPreview, newItem: LocationPreview): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: LocationPreview,
            newItem: LocationPreview
        ): Boolean {
            return oldItem._id == newItem._id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder(
            ItemLocationPreviewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val locationPreview = getItem(position)

        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(locationPreview)
            }
        }

        holder.bind(locationPreview)

    }

    private var onItemClickListener: ((LocationPreview) -> Unit)? = null

    fun setOnItemClickListener(listener: (LocationPreview) -> Unit) {
        onItemClickListener = listener
    }

}