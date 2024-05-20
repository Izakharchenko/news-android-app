package com.example.newsapp.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentArticleBinding
import com.google.android.material.appbar.CollapsingToolbarLayout

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private lateinit var articleViewModel: ArticleViewModel
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsId = arguments?.getString("newsId") ?: return

        articleViewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)

        articleViewModel.getArticle(newsId).observe(viewLifecycleOwner, Observer { article ->
            article?.let {
                binding.collapsingToolbar.title = it.title
                Glide.with(this).load(it.cover).into(binding.newsImageView)
                binding.newsContentTextView.text = it.body
                binding.newsAuthorTextView.text = getString(R.string.author, it.author)
                binding.newsSourceTextView.text = getString(R.string.source, it.source)

            }

        })
        articleViewModel.fetchArticle(newsId)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}