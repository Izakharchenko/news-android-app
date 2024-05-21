package com.example.newsapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ItemCategoryBinding
import com.example.newsapp.model.Category
import com.example.newsapp.model.News

class CategoryAdapter(private val onCategoryClick: (Int) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var categories: List<Category> = listOf()
    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.categoryTitle.text = category.title
            binding.root.setOnClickListener {
                onCategoryClick(category.id!!)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount() = categories.size

    fun updateCategories(newCategoryList: List<Category>) {
        this.categories = newCategoryList
        notifyDataSetChanged()
    }
    fun setNews(news: List<Category>) {
        this.categories = news
        notifyDataSetChanged()
    }


}
