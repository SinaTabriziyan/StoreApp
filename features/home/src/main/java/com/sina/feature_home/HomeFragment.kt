package com.sina.feature_home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sina.feature_home.adapter.HomeSliderAdapter
import com.sina.feature_home.adapter.MainHomeAdapter
import com.sina.feature_home.databinding.FragmentHomeBinding
import com.sina.feature_item.ItemActivity
import com.sina.feature_search.SearchActivity
import com.sina.network.networkListener.NetworkListener
import com.sina.ui_components.BaseFragment
import com.sina.ui_components.BaseViewModel
import com.sina.ui_components.BaseViewModel.UiState.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    @Inject
    lateinit var networkListener: NetworkListener

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var mainHomeAdapter: MainHomeAdapter
    private lateinit var homeSliderAdapter: HomeSliderAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observers()
    }

    override fun setupViews() {
        implRecyclerView()
        with(binding) {
            searchLayer.btnSearchHome.setOnClickListener {
                startActivity(Intent(requireActivity(), SearchActivity::class.java))
            }
            rvSliderHome.apply {
                adapter = homeSliderAdapter
                set3DItem(true)
                setInfinite(true)
            }
            rvMainProducts.apply {
                adapter = mainHomeAdapter
                layoutManager = LinearLayoutManager(binding.root.context)
            }
        }
    }

    override fun playAnimate() = binding.lottie.lottie.playAnimation()
    override fun cancelAnimate() = binding.lottie.lottie.cancelAnimation()
    private fun implRecyclerView() {
        mainHomeAdapter = MainHomeAdapter(onClick = {
            val intent = Intent(requireActivity(), ItemActivity::class.java)
            intent.putExtra("productId", it)
            startActivity(intent)
        }, onReachedEndOfList = {})
        homeSliderAdapter = HomeSliderAdapter()
    }

    private fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allMainProducts.collectLatest {
                    mainHomeAdapter.submitList(it)
                }
            }
        }
        viewLifecycleOwner.launchWhenStarted {
            viewModel.sliderImages.collectLatest { homeSliderAdapter.submitList(it) }

        }
        viewLifecycleOwner.launchWhenStarted {
            viewModel.uiState.collectLatest {
                animationStatus(it)
            }
        }

        viewLifecycleOwner.launchWhenStarted {
            networkListener.checkNetworkAvailability().collectLatest {
                viewModel.networkStatus = it
                showNetworkStatue()
            }
        }
        viewLifecycleOwner.launchWhenStarted {
            viewModel.readBackOnline.collectLatest {
                viewModel.backOnline = it
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

    override fun animationStatus(state: BaseViewModel.UiState) {
        binding.lottie.lottie.isVisible = when (state) {
            Success -> {
                cancelAnimate();false
            }

            Loading -> {
                playAnimate();true
            }

            Error -> {
                cancelAnimate()
                false
            }
        }
    }
}