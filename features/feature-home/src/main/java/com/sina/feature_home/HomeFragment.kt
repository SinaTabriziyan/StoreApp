package com.sina.feature_home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sina.feature_customer.CustomerActivity
import com.sina.feature_home.adapter.HomeSliderAdapter
import com.sina.feature_home.adapter.MainHomeAdapter
import com.sina.feature_home.databinding.FragmentHomeBinding
import com.sina.feature_item.ItemActivity
import com.sina.feature_search.SearchActivity
import com.sina.ui_components.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val TAG = "HomeFragment"
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var mainHomeAdapter: MainHomeAdapter
    private lateinit var homeSliderAdapter: HomeSliderAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        observeUiEvents()
        implRecyclerView()
        implHomeSlider()
        observers()
        uiEvenet()
    }

    private fun observeUiEvents() {
        binding.imgCustomerAvatar.setOnClickListener {
            val intent = Intent(requireActivity(), CustomerActivity::class.java)
            startActivity(intent)
        }
    }

    private fun implHomeSlider() {
        homeSliderAdapter = HomeSliderAdapter()
        // TODO: impl event handle
        binding.rvSliderHome.apply {
            adapter = homeSliderAdapter
            set3DItem(true)
            setInfinite(true)
//            setFlat(true)
        }
    }

    private fun uiEvenet() {
        with(binding) {
            btnSearchHome.setOnClickListener {
                // TODO: use default navigation later
                startActivity(Intent(requireActivity(), SearchActivity::class.java))
            }
        }
    }

    private fun implRecyclerView() {
        mainHomeAdapter = MainHomeAdapter(onClick = {
            val intent = Intent(requireActivity(), ItemActivity::class.java)
            intent.putExtra("productId", it)
            startActivity(intent)
        }, onReachedEndOfList = {

        })
        binding.rvMainProducts.apply {
            adapter = mainHomeAdapter
            layoutManager = LinearLayoutManager(binding.root.context)
        }
    }

    private fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allMainProducts.collectLatest {
                    mainHomeAdapter.submitList(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sliderImages.collectLatest { homeSliderAdapter.submitList(it) }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    animationStatus(it)
                }
            }
        }
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.sliderProducts.collectLatest {
//                    when (it) {
//                        is InteractState.Error -> {}
//                        is InteractState.Loading -> {}
//                        is InteractState.Success -> {
//                            homeSliderAdapter.submitList(it.data[0].images)
//                            Log.e(TAG, "observers: ${it.data[0].images}")
//                        }
//                    }
//                }
//            }
//        }
    }

    private fun animationStatus(state: BaseViewModel.UiState) {
        binding.lottieLayer.lottie.isVisible = when (state) {
            BaseViewModel.UiState.Success -> false
            BaseViewModel.UiState.Loading -> true
        }
    }

}