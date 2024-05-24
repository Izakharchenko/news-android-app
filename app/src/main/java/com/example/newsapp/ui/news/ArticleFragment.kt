package com.example.newsapp.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentArticleBinding
import com.example.newsapp.db.AppDatabase
import com.example.newsapp.model.Favorite
import com.example.newsapp.repository.FavoriteRepositoryImpl
import com.example.newsapp.repository.NewsRepositoryImpl
import com.example.newsapp.service.ServiceCreator
import com.example.newsapp.service.NewsService
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private lateinit var articleViewModel: ArticleViewModel
    private lateinit var currentNews: Favorite
    private var isFavorite = false

    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)

        val newsDao = AppDatabase.getDatabase(requireContext()).favoriteDao()
        val favoriteRepositoryImpl = FavoriteRepositoryImpl(newsDao)
        val repository = NewsRepositoryImpl(ServiceCreator.create<NewsService>())
        val factory = ArticleViewModelFactory(repository, favoriteRepositoryImpl)
        articleViewModel = ViewModelProvider(this, factory).get(ArticleViewModel::class.java)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsId = arguments?.getString("newsId") ?: return



        lifecycleScope.launch {
            isFavorite = articleViewModel.isNewsFavorite(newsId.toInt())
            setFavoriteIcon(isFavorite)
        }

        articleViewModel.getArticle(newsId).observe(viewLifecycleOwner) { article ->
            article?.let {
                binding.collapsingToolbar.title = it.title
                Glide.with(this).load(it.cover).into(binding.newsImageView)
                binding.newsCategoryTextView.text = it.categoryTitle
                binding.newsContentTextView.text = it.body
                binding.newsAuthorTextView.text = getString(R.string.author, it.author)
                binding.newsSourceTextView.text = getString(R.string.source, it.source)
                currentNews = Favorite(
                    it.id!!, it.categoryId, it.categoryTitle, it.title, it.cover, it.body, it.source, it.author
                )
            }

        }

        articleViewModel.fetchArticle(newsId)

        articleViewModel.incrementViewCount(newsId.toInt())

        articleViewModel.viewCount.observe(viewLifecycleOwner) { viewCount ->
            if (viewCount != -1) {
                binding.newsViewCountTextView.text = getString(R.string.views, "$viewCount")
            }
            binding.newsViewCountTextView.visibility = View.GONE
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.newsFavorite.setOnClickListener {
            isFavorite = !isFavorite
            setFavoriteIcon(isFavorite)
            if (isFavorite) {
                articleViewModel.addNewsToFavorites(currentNews)
            } else {
                articleViewModel.removeNewsFromFavorites(currentNews)
            }
        }

        binding.newsFavorite.setOnClickListener {
            isFavorite = !isFavorite
            setFavoriteIcon(isFavorite)
            if (isFavorite) {
                articleViewModel.addNewsToFavorites(currentNews)
                Snackbar.make(view, "Article added to favorite", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        Toast.makeText(context, "Removed from favorite", Toast.LENGTH_LONG).show()
                        articleViewModel.removeNewsFromFavorites(currentNews)
                        isFavorite = false
                        setFavoriteIcon(isFavorite)
                    }
                    .show()
            } else {
                articleViewModel.removeNewsFromFavorites(currentNews)
            }
        }

    }
    private fun setFavoriteIcon(isFavorite: Boolean) {
        val favoriteItem = binding.newsFavorite
        if (isFavorite) {
            favoriteItem.setImageResource(R.drawable.baseline_favorite_24)
        } else {
            favoriteItem.setImageResource(R.drawable.baseline_favorite_border_24)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}