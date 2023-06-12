package com.sina.feature_home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sina.feature_home.databinding.FragmentHomeBinding
import com.sina.feature_item.ItemActivity
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        implRecyclerView()
        observers()
    }

    private fun implRecyclerView() {
        mainHomeAdapter = MainHomeAdapter(onClick = {
            val intent = Intent(requireActivity(), ItemActivity::class.java)
            intent.putExtra("productId", it)
            startActivity(intent)
        }, onReachedEndOfList = {

        })
        binding.rvMainProducts.adapter = mainHomeAdapter
        binding.rvMainProducts.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allMainProducts.collectLatest {
                    mainHomeAdapter.submitList(it)
                }
            }
        }
    }
}