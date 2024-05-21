package com.example.newsapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.ui.news.NewsAdapter
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var homeViewModel: HomeViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        newsAdapter = NewsAdapter { newsId ->
            val bundle = Bundle().apply {
                putString("newsId", newsId)
            }
            findNavController().navigate(R.id.action_homeFragment_to_articleFragment, bundle)
        }
        recyclerView.adapter = newsAdapter

        binding.swipeRefresh.setOnRefreshListener {
            refreshData()
        }

        homeViewModel.news.observe(viewLifecycleOwner, Observer { news ->
            if (news.isEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.emptyView.visibility = View.VISIBLE
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                binding.emptyView.visibility = View.GONE
                newsAdapter.setNews(news)
            }
            binding.swipeRefresh.isRefreshing = false
        })

        homeViewModel.fetchNews()

        return root
    }

    private fun refreshData() {
        homeViewModel.refreshFavoriteNews()
        binding.swipeRefresh.isRefreshing = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}