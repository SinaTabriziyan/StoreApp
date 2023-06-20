package com.sina.feature_category

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sina.domain_main.interactor.InteractState
import com.sina.feature_category.databinding.FragmentCategoryBinding
import com.sina.feature_products.ProductsActivity
import com.sina.network.networkListener.NetworkListener
import com.sina.ui_components.BaseFragment
import com.sina.ui_components.BaseViewModel
import com.sina.ui_components.BaseViewModel.UiState.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(FragmentCategoryBinding::inflate) {
    private val TAG = "CategoryFragment"

    private val viewModel: CategoryViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter

    @Inject
    lateinit var networkListener: NetworkListener
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observes()
    }

    override fun setupViews() {
        implRecycler()
        with(binding) {
            rvCategoryItems.apply {
                layoutManager = LinearLayoutManager(binding.root.context)
                adapter = categoryAdapter
            }
        }
    }

    override fun playAnimate() = binding.lottie.lottie.playAnimation()
    override fun cancelAnimate() = binding.lottie.lottie.cancelAnimation()

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
    }

    private fun observes() {
        viewLifecycleOwner.launchWhenStarted {
            viewModel.categoryList.collectLatest { categoryAdapter.submitList(it) }
        }
        viewLifecycleOwner.launchWhenStarted {
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

        viewLifecycleOwner.launchWhenStarted {
            networkListener.checkNetworkAvailability().collectLatest {
                viewModel.networkStatus = it
                showNetworkStatue()
            }
        }
    }


    private fun showNetworkStatue() {
        if (!viewModel.networkStatus) {
            showToast("No Internet conection")
            viewModel.saveBackOnline(true)
        } else if (viewModel.networkStatus) {
            if (viewModel.backOnline) {
                showToast("Wer back online")
                viewModel.saveBackOnline(false)
            }
        }
    }
}

