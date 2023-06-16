package com.sina.feature_category

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
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
import com.sina.domain_main.interactor.InteractState
import com.sina.feature_category.databinding.FragmentCategoryBinding
import com.sina.feature_products.ProductsActivity
import com.sina.ui_components.BaseFragment
import com.sina.ui_components.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(FragmentCategoryBinding::inflate) {
    private val TAG = "CategoryFragment"
    override fun setupViews() {

    }

    override fun animationStatus(state: BaseViewModel.UiState) {

    }

    private val viewModel: CategoryViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        implRecycler()
        observes()
    }

    private fun implRecycler() {
        categoryAdapter = CategoryAdapter {
            val intent = Intent(requireActivity(), ProductsActivity::class.java)
            intent.putExtra("categoryId", it)
            startActivity(intent)
        }
        binding.rvCategoryItems.apply {
            layoutManager = LinearLayoutManager(binding.root.context)
            adapter = categoryAdapter
        }
    }

    private fun observes() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.categoryProducts.collectLatest {
//                    categoryAdapter.submitList(it)
//                }
                val categoriseProductsList = viewModel.categoriseProductsList

                viewModel.categoriseProductsList.collectLatest {
                    when (it) {
                        is InteractState.Error -> {
                            Log.e(TAG, "observes: ${it.errorMessage}")
                        }

                        is InteractState.Loading -> {
                            Log.e(TAG, "observes: loading")
                        }

                        is InteractState.Success -> {
                            categoryAdapter.submitList(it.data)
                        }
                    }
                }
            }
        }
    }
}