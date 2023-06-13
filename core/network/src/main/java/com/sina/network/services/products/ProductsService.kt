package com.sina.network.services.products

import com.sina.model.data.category_dto.CategoryDTOItem
import com.sina.model.data.product_dto.ProductDTOItem
import com.sina.model.data.products_dto.ProductsDTOItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsService {
    @GET("products/")
    suspend fun getProducts(
        @Query("page") page: Int,
        @Query("category") category: String,
    ): List<ProductsDTOItem>
    @GET("products")
    suspend fun getLatestProducts(
        @Query("page") page: Int,
        @Query("orderby") orderBy: String,
    ): List<ProductsDTOItem>

    @GET("products")
    suspend fun getMostVisitedProducts(
        @Query("page") page: Int,
        @Query("orderby") orderBy: String,
    ): List<ProductsDTOItem>

    @GET("products")
    suspend fun getTopRatedProductsService(
        @Query("page") page: Int = 1,
        @Query("orderby") orderBy: String,
    ): List<ProductsDTOItem>

    @GET("products/categories")
    suspend fun getCategories(): List<CategoryDTOItem>

    @GET("products")
    suspend fun getItem(
        @Query("include") id: Int,
    ): List<ProductDTOItem>


    @GET("products")
    suspend fun getProductsBySearch(
        @Query("page") page: Int,
        @Query("search") querySearch: String,
//        @Query("orderby") orderBy: String,
//        @Query("order") order: String
    ): List<ProductsDTOItem>
}