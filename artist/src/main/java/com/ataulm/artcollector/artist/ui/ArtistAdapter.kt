package com.ataulm.artcollector.artist.ui

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ataulm.artcollector.artist.R
import com.ataulm.artcollector.artist.domain.Painting
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.itemview_artist_painting.view.*

internal class ArtistAdapter constructor(
        private val picasso: Picasso,
        private val onClick: (Painting) -> Unit
) : ListAdapter<Painting, ArtistAdapter.PaintingViewHolder>(PaintingDiffer) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaintingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemview_artist_painting, parent, false)
        return PaintingViewHolder(picasso, onClick, view)
    }

    override fun onBindViewHolder(viewHolder: PaintingViewHolder, position: Int) = viewHolder.bind(getItem(position))

    object PaintingDiffer : DiffUtil.ItemCallback<Painting>() {
        override fun areItemsTheSame(oldItem: Painting, newItem: Painting) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Painting, newItem: Painting) = oldItem == newItem
    }

    internal class PaintingViewHolder(
            private val picasso: Picasso,
            private val onClick: (Painting) -> Unit,
            view: View
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: Painting) {
            itemView.setOnClickListener { onClick(item) }
            picasso.load(item.imageUrl).into(itemView.imageView)
        }
    }
}