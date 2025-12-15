package com.petproject.workflow.presentation.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.petproject.workflow.R
import com.petproject.workflow.databinding.ItemAnnouncementInfoBinding
import com.petproject.workflow.domain.entities.Announcement
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

class AnnouncementAdapter(
    private val requestManager: RequestManager,
) : ListAdapter<Announcement, AnnouncementAdapter.AnnouncementInfoViewHolder>(
    AnnouncementDiffItemCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementInfoViewHolder {
        return AnnouncementInfoViewHolder.inflateFrom(parent, requestManager)
    }

    override fun onBindViewHolder(holder: AnnouncementInfoViewHolder, position: Int) {
        val announcement = getItem(position)
        holder.bind(announcement)
    }

    class AnnouncementInfoViewHolder(
        val binding: ItemAnnouncementInfoBinding,
        private val requestManager: RequestManager
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object{

            fun inflateFrom(viewGroup: ViewGroup, requestManager: RequestManager): AnnouncementInfoViewHolder {
                val inflater = LayoutInflater.from(viewGroup.context)
                val binding = ItemAnnouncementInfoBinding.inflate(inflater, viewGroup, false)
                return AnnouncementInfoViewHolder(binding, requestManager)
            }
        }

        fun bind(
            announcement: Announcement
        ) {
            val context = binding.root.context
            binding.announcement = announcement
            binding.postDataTextView.text = formatDateTime(context, announcement.createdAt)
            setAnnouncementPoster(announcement)
        }

        private fun setAnnouncementPoster(announcement: Announcement) {
            if (announcement.photoUrl != null) {
                requestManager
                    .load(announcement.photoUrl)
                    .into(binding.posterImageView)
            } else {
                binding.posterImageView.setImageResource(R.drawable.app_logo2)
            }
        }

        private fun formatDateTime(context: Context, localDateTime: LocalDateTime): String {
            return android.text.format.DateFormat.getMediumDateFormat(context).format(
                Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
            ) + " " + android.text.format.DateFormat.getTimeFormat(context).format(
                Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
            )
        }
    }
}