package com.example.newsapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.model.News

class NewsAdapter(val context: Context) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var newsList: List<News> = listOf()
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val newsImageView: ImageView = itemView.findViewById(R.id.newsImageView)
    }

    fun setNews(news: List<News>) {
        this.newsList = news
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        val viewHolder = ViewHolder(view)

//        viewHolder.itemView.setOnClickListener {
//            val position = viewHolder.adapterPosition // adapterPosition
//            val post = posts[position]
//
//            val postIntent = Intent("com.example.grudapi.START_POST");
//            postIntent.putExtra("id", post.id)
//            postIntent.putExtra("title", post.title)
//            postIntent.putExtra("position", position)
//
//            view.context.startActivity(postIntent);
//        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = newsList[position]
        holder.titleTextView.text = article.title
        Glide.with(context).load(article.cover).into(holder.newsImageView)
    }

    override fun getItemCount() = newsList.size

}