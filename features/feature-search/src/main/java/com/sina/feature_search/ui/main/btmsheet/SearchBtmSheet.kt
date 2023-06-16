package com.sina.feature_search.ui.main.btmsheet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.sina.common.constants.Constants.Companion.DEFAULT_SEARCH_TYPE
import com.sina.common.constants.Constants.Companion.DEFAULT_SEARCH_TYPE_POSITION
import com.sina.feature_search.R
import com.sina.feature_search.databinding.SearchBtmSheetBinding
import com.sina.feature_search.ui.main.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class SearchBtmSheet : BottomSheetDialogFragment(R.layout.search_btm_sheet) {
    private var searchTypeChip = DEFAULT_SEARCH_TYPE
    private var searchTypeIdChip = DEFAULT_SEARCH_TYPE_POSITION

    private var _binding: SearchBtmSheetBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = SearchBtmSheetBinding.bind(view)
        with(binding) {
            orderByTypeChipGroup.setOnCheckedChangeListener { group, checkedId ->
                val chip = group.findViewById<Chip>(checkedId)
                val selectedMealType = chip.text.toString().lowercase(Locale.ROOT)
                searchTypeChip = selectedMealType
                searchTypeIdChip = checkedId
            }

            btnApply.setOnClickListener {
                searchViewModel.saveSearchType(searchTypeChip, searchTypeIdChip)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}