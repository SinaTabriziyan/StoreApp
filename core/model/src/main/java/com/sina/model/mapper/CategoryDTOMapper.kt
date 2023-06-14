package com.sina.model.mapper

import com.sina.model.data.category_dto.CategoryDTOItem
import com.sina.model.ui.category_item.CategoryItem

fun mapCategoryDTOToCategory(dto: CategoryDTOItem): CategoryItem = with(dto) {
    CategoryItem(description, id, image, name)
}