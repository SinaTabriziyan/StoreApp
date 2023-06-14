package com.sina.feature_customer.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.sina.feature_customer.R
import com.sina.feature_customer.databinding.FragmentCustomerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerFragment : Fragment(R.layout.fragment_customer) {
    private val TAG = "CategoryFragment"
    private var _binding: FragmentCustomerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CustomerViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCustomerBinding.bind(view)


        with(binding) {
            viewModel.createUser(
                userName = etCustomerName.text.toString(),
                firstName = etCustomerEmail.text.toString(),
                lastName = etCustomerEmail.text.toString(),
                email = etCustomerEmail.text.toString(),
                avatar = etCustomerEmail.text.toString()
            )
        }
    }

}