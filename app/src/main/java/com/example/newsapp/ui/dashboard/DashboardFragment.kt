package com.example.newsapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var dashboardViewModel: DashboardViewModel
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        dashboardViewModel.totalNewsCount.observe(viewLifecycleOwner) { count ->
            binding.totalNewsCount.text = "Total News: $count"
        }
        dashboardViewModel.totalCategoryCount.observe(viewLifecycleOwner) { count ->
            binding.totalCategoryCount.text = "Total Categories: $count"
        }

        dashboardViewModel.popArticle.observe(viewLifecycleOwner) { article ->
            binding.mostPopularNewsTitle.text = "Most popular: ${article.title}"
            binding.mostPopularNewsViews.text = "Views: ${article.views}"
        }

        setupSwipeToRefresh()

        return root
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            refreshDashboardStats()
        }
    }

    private fun refreshDashboardStats() {
        dashboardViewModel.refreshDashboardStats()
        binding.swipeRefreshLayout.isRefreshing = false
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}