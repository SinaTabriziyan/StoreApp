package com.sina.ui_components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseFragment<VB : ViewBinding>(private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB) : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!
//    protected val viewModel: BaseViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun setupViews()
    abstract fun playAnimate()
    abstract fun cancelAnimate()


    abstract fun animationStatus(state: BaseViewModel.UiState)

    protected fun showToast(message: String) = Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
    fun <T> LifecycleOwner.launchWhenStarted(block: suspend CoroutineScope.() -> T) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                block()
            }
        }
    }
//    private fun showNetworkStatue() {
//        if (!viewModel.networkStatus) {
//            showToast("No Internet conection")
//            viewModel.saveBackOnline(true)
//        } else if (viewModel.networkStatus) {
//            if (viewModel.backOnline) {
//                showToast("Wer back online")
//                viewModel.saveBackOnline(false)
//            }
//        }
//    }
}
