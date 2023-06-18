package com.sina.feature_category

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sina.domain_main.interactor.InteractState
import com.sina.feature_category.databinding.FragmentCategoryBinding
import com.sina.feature_products.ProductsActivity
import com.sina.ui_components.BaseFragment
import com.sina.ui_components.BaseViewModel
import com.sina.ui_components.BaseViewModel.UiState.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(FragmentCategoryBinding::inflate) {
    private val TAG = "CategoryFragment"

    private val viewModel: CategoryViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        implRecycler()
        observes()
    }

    override fun setupViews() {

    }

    override fun playAnimate() {
        binding.lottie.lottie.playAnimation()
    }

    override fun cancelAnimate() {
        binding.lottie.lottie.cancelAnimation()
    }

    override fun animationStatus(state: BaseViewModel.UiState) {
        binding.lottie.lottie.isVisible = when (state) {
            Success -> {
                cancelAnimate()
                false
            }

            Loading -> {
                playAnimate()
                true
            }

            Error -> {
                cancelAnimate()
                false
            }
        }
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
                viewModel.categoriseProductsList.collectLatest {
                    when (it) {
                        is InteractState.Error -> {
                            showToast(it.errorMessage)
                            viewModel.uiState.value = Error
                        }

                        is InteractState.Loading -> viewModel.uiState.value = Loading
                        is InteractState.Success -> {
                            viewModel.uiState.value = Success
                            categoryAdapter.submitList(it.data)
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    when (it) {
                        Success -> animationStatus(it)
                        Loading -> animationStatus(it)
                        Error -> {
                            animationStatus(it)
                            showToast(it.name)
                        }
                    }
                }
            }
        }

    }
}