package com.example.postapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.postapp.PostDetail
import com.example.postapp.R
import com.example.postapp.model.Post

class PostsAdapter(
    private val posts: List<Post>,
    private val context: Context
) : RecyclerView.Adapter<PostsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.post_item, parent, false)
        return PostsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.title.text = posts[position].title
        holder.itemView.setOnClickListener {
            val intent = Intent(context, PostDetail::class.java)
            intent.putExtra("postID", posts[position].id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}

class PostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById<TextView>(R.id.post_item_title)
}
