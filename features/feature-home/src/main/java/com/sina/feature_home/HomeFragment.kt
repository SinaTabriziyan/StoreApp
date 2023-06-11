package com.sina.feature_home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sina.common.responsestate.ResponseState
import com.sina.feature_home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val TAG = "HomeFragment"
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allMainProducts.collect { response ->
                    when (response) {
                        is ResponseState.Error -> {
                            Log.e(TAG, "onViewCreated: Error")
                        }

                        is ResponseState.Loading -> {
                            Log.e(TAG, "onViewCreated: Loading")
                        }

                        is ResponseState.Success -> {
                            Log.e(TAG, "onViewCreated: ${response.data}")
                            binding.tvTexdfsft.text = response.data.toString()
                        }
                    }
                }
            }
        }
    }
}