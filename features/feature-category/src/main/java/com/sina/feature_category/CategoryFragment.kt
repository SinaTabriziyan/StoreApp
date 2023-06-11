package com.sina.feature_category

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sina.common.responsestate.ResponseState
import com.sina.feature_category.databinding.FragmentCategoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : Fragment(R.layout.fragment_category) {
    private val TAG = "CategoryFragment"
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CategoryViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCategoryBinding.bind(view)
        implRecycler()
        observes()
    }

    private fun implRecycler() {
        categoryAdapter=CategoryAdapter {

        }
        binding.rvCategoryItems.apply {
            layoutManager = LinearLayoutManager(binding.root.context)
            adapter = categoryAdapter
        }
    }

    private fun observes() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categoryProducts.collect { response ->
                    when (response) {
                        is ResponseState.Error -> {}
                        is ResponseState.Loading -> {}
                        is ResponseState.Success -> {
                            categoryAdapter.submitList(response.data)
                        }
                    }
                }
            }
        }
    }
}