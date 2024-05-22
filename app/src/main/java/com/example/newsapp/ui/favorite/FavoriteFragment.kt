package com.example.newsapp.ui.favorite

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentFavoriteBinding
import com.example.newsapp.db.AppDatabase
import com.example.newsapp.repository.FavoriteRepositoryImpl
import com.example.newsapp.ui.adapters.FavoriteAdapter

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private lateinit var adapter: FavoriteAdapter

    private val binding get() = _binding!!

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        val dao = AppDatabase.getDatabase(requireContext()).favoriteDao()
        FavoriteViewModelFactory(FavoriteRepositoryImpl(dao))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FavoriteAdapter { newsId ->
            val bundle = Bundle().apply {
                putString("newsId", newsId)
            }
            findNavController().navigate(R.id.action_favoriteFragment_to_articleFragment, bundle)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        favoriteViewModel.favoriteNews.observe(viewLifecycleOwner, Observer { favorite ->
            if (favorite.isNullOrEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.emptyView.visibility = View.VISIBLE
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                binding.emptyView.visibility = View.GONE
                favorite.let { adapter.setFavorite(favorite) }
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}