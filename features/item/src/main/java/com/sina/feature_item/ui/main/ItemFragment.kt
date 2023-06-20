package com.sina.feature_item.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.sina.domain_main.interactor.InteractState
import com.sina.feature_details.databinding.FragmentItemBinding
import com.sina.model.ui.product_details_item.ProductDetails
import com.sina.ui_components.BaseFragment
import com.sina.ui_components.BaseViewModel
import com.sina.ui_components.BaseViewModel.UiState.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class ItemFragment : BaseFragment<FragmentItemBinding>(FragmentItemBinding::inflate) {
    private val TAG = "ProductFragment"

    private val viewModel: ItemViewModel by viewModels()
    private lateinit var itemAdapter: ImageItemAdapter
    override fun setupViews() {
        with(binding) {
            rvItemImages.adapter = itemAdapter
        }
    }

    override fun playAnimate() = binding.lottie.lottie.playAnimation()
    override fun cancelAnimate() = binding.lottie.lottie.cancelAnimation()

    override fun animationStatus(state: BaseViewModel.UiState) {
        binding.lottie.lottie.isVisible = when (state) {
            Success -> {
                cancelAnimate();
                viewStatus(true)
                false
            }

            Loading -> {
                playAnimate();
                viewStatus(false)
                true
            }

            Error -> {
                cancelAnimate();
                viewStatus(false)
                false
            }
        }
    }

    private fun viewStatus(state: Boolean) {
        binding.btnAddToCart.isVisible = state
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        itemAdapter = ImageItemAdapter()
        setupViews()
        observers()
    }

    private fun observers() {
        viewLifecycleOwner.launchWhenStarted {
            viewModel.productDetails.collectLatest {
                when (it) {
                    is InteractState.Error -> {
                        Timber.d(it.errorMessage)
                        viewModel.uiState.value = Error
                    }

                    is InteractState.Loading -> viewModel.uiState.value = Loading
                    is InteractState.Success -> {
                        viewModel.uiState.value = Success
                        implUi(it.data)
                    }
                }
            }
        }

        viewLifecycleOwner.launchWhenStarted {
            viewModel.uiState.collectLatest {
                animationStatus(it)
            }
        }

        viewLifecycleOwner.launchWhenStarted {

            viewModel.itemAdded.collectLatest {
                when (it) {
                    true -> {
                        binding.btnAddToCart.text = "برو به سبد خرید"
                    }

                    false -> {
                        binding.btnAddToCart.text = "خرید"
                    }
                }
            }
        }

    }

    private fun implUi(data: ProductDetails) {
        with(binding) {
            itemAdapter.submitList(data.images)
            tvItemDescription.text = HtmlCompat.fromHtml(data.description.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
            tvItemPrice.text = data.price
            tvItemTitle.text = data.name
            btnAddToCart.setOnClickListener { viewModel.addItemToCart(data.id) }
        }
    }
}