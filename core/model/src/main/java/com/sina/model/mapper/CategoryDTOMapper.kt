package com.sina.model.mapper

import com.sina.model.data.category_dto.CategoryDTOItem
import com.sina.model.ui.category_item.CategoryItem

fun mapCategoryDTOToCategory(categoryDTO: CategoryDTOItem): CategoryItem {
    return CategoryItem(
        description = categoryDTO.description,
        id = categoryDTO.id,
        image = categoryDTO.image,
        name = categoryDTO.name
    )
}