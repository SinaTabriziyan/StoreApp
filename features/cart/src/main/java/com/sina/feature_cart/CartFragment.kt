package com.sina.feature_cart

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
import androidx.lifecycle.viewModelScope
import com.sina.domain_main.usecase.AddItemUseCase
import com.sina.feature_cart.databinding.FragmentCartBinding
import com.sina.ui_components.BaseFragment
import com.sina.ui_components.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {

    private val viewModel: CartViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getItemsLocal.collectLatest {
                    Timber.e("______--------------_______$it")
                }
            }
        }
    }

    override fun setupViews() {
    }

    override fun playAnimate() {
    }

    override fun cancelAnimate() {
    }

    override fun animationStatus(state: BaseViewModel.UiState) {
    }

}