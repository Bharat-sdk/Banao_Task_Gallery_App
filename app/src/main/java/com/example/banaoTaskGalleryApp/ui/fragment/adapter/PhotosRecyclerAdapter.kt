package com.example.banaoTaskGalleryApp.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.banaoTaskGalleryApp.data.remote.response.PhotoItem
import com.example.banaoTaskGalleryApp.databinding.ItemLoadingBinding
import com.example.banaoTaskGalleryApp.databinding.ItemPhotoBinding
import javax.inject.Inject

class PhotosRecyclerAdapter @Inject constructor(): PagingDataAdapter<PhotoItem, PhotosRecyclerAdapter.PhotosViewHolder>(
    Diff
)
{
    inner class PhotosViewHolder(val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PhotoItem) {
            Glide.with(binding.root.context).load(data.urlS).into(binding.ivPhoto)
        }
    }

    inner class LoadingViewHolder(val binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    object Diff : DiffUtil.ItemCallback<PhotoItem>() {
        override fun areItemsTheSame(
            oldItem: PhotoItem,
            newItem: PhotoItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PhotoItem,
            newItem: PhotoItem
        ): Boolean {
            return oldItem == newItem
        }
    }


    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotosViewHolder {
        return PhotosViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }




/*
    private var onClickListener: ((PhotoItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (PhotoItem) -> Unit) {
        onItemClickListener = listener
    }
*/
}