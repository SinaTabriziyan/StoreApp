package com.sina.feature_customer.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sina.feature_customer.databinding.FragmentUserBinding
import com.sina.ui_components.BaseFragment
import com.sina.ui_components.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    private val viewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnCreateCustomer.setOnClickListener {
                viewModel.createUser(
                    "", "", "", "", ""
                )
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