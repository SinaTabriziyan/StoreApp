package com.sina.network.services.products

import com.sina.model.data.category_dto.CategoryDTOItem
import com.sina.model.data.products_dto.ProductsDTOItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsService {

    @GET("products")
    suspend fun getLatestProducts(
        @Query("page") page: Int = 1,
        @Query("orderby") orderBy: String = "date",
    ): List<ProductsDTOItem>

    @GET("products")
    suspend fun getMostVisitedProducts(
        @Query("page") page: Int = 1,
        @Query("orderby") orderBy: String = "popularity",
    ): List<ProductsDTOItem>

    @GET("products")
    suspend fun getTopRatedProductsService(
        @Query("page") page: Int = 1,
        @Query("orderby") orderBy: String,
    ): List<ProductsDTOItem>

    @GET("products/categories")
    suspend fun getCategories(): List<CategoryDTOItem>
}