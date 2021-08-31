package com.example.phunapp.adapters

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.phunapp.DetailActivity.DetailActivity
import com.example.phunapp.PhunModel.PhunModelItem
import com.example.phunapp.R
import com.example.phunapp.utilities.Utilities

class PhunListAdapter : ListAdapter<PhunModelItem, PhunListAdapter.ChildViewHolder> (ChildComparator()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChildViewHolder {
        return ChildViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
        //CLICK LISTENER TO START DETAIL ACTIVITY
        holder.itemView.setOnClickListener {
            if(current != null){
                //SENDS ID TO NEW ACTIVITY TO RETRIEVE PHUNMODELITEM FROM DATABASE
                val i = Intent(holder.itemView.context, DetailActivity::class.java).apply{
                    putExtra("id", current.id)
                }
                //SHARED ELEMENTS TRANSITION FROM IMAGE
                val options = ActivityOptions.makeSceneTransitionAnimation(holder.itemView.context as Activity, holder.itemView.findViewById(R.id.image), "backgroundImage")
                holder.itemView.context.startActivity(i, options.toBundle())
            }
        }
    }

    class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val headline: TextView = itemView.findViewById(R.id.headline)
        private val time: TextView = itemView.findViewById(R.id.time)
        private val location: TextView = itemView.findViewById(R.id.location)
        private val description: TextView = itemView.findViewById(R.id.description)
        private val image: ImageView = itemView.findViewById(R.id.image)
        fun bind(item: PhunModelItem?) {
            headline.text = item?.title
            time.text = "${Utilities.parseDate(item?.date)} at ${Utilities.parseTime(item?.date)}"
            location.text = "${item?.locationline1}, ${item?.locationline2}"
            description.text = item?.description

            //USES GLIDE LIBRARY TO LOAD IMAGES
                itemView.apply {
                    Glide.with(this)
                        .load(item?.image)
                        .placeholder(R.drawable.placeholder_nomoon)
                        .apply( //HELPS PREVENT REDRAWING ISSUES WITH TRANSITIONS
                            RequestOptions().dontTransform()
                        )
                        .into(image)
                }
        }

        companion object {
            fun create(parent: ViewGroup): ChildViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return ChildViewHolder(view)
            }
        }
    }

    class ChildComparator : DiffUtil.ItemCallback<PhunModelItem>() {

        override fun areItemsTheSame(oldItem: PhunModelItem, newItem: PhunModelItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PhunModelItem, newItem: PhunModelItem): Boolean {
            return oldItem.id == newItem.id
        }
    }
}


