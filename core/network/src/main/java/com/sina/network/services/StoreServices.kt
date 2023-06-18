package com.sina.network.services

import com.sina.model.data.category_dto.CategoryDTOItem
import com.sina.model.data.customer_dto.CustomerDTO
import com.sina.model.data.product_details_dto.ProductDetailsDto
import com.sina.model.data.products_dto.ProductsDTOItem
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface StoreServices {

    @GET("products")
    suspend fun getProductsList(
        @Query(PARAMS.PAGE) page: Int = 1,
        @Query("orderby") orderBy: String,
    ): List<ProductsDTOItem>

    @GET("products/{id}")
    suspend fun getProductDetails(
        @Path("id") id: Int,
    ): ProductDetailsDto


    @GET("products/")
    suspend fun getProductsByCategory(
        @Query(PARAMS.PAGE) page: Int,
        @Query("category") category: String,
    ): List<ProductsDTOItem>

    @GET("products/categories")
    suspend fun getCategories(): List<CategoryDTOItem>

    //    @GET("products")
//    suspend fun getProductsBySearch(
//        @Query(PARAMS.PAGE) page: Int,
//        @Query("search") query: String,
//        @Query("orderby") orderBy: String,
////        @Query("order") order: String
//    ): List<ProductsDTOItem>
// orderBy:,title,price,popularity,rating,date
// order:asc ,desc
    @GET("products")
    suspend fun getProductsBySearch(
        @Query(PARAMS.PAGE) page: Int,
        @Query("search") query: String,
        @QueryMap filters: Map<String, String>
//        @Query("order") order: String
    ): List<ProductsDTOItem>
// orderBy:,title,price,popularity,rating,date
// order:asc ,desc

    @POST(Route.CUSTOMERS)
    suspend fun createCustomer(
        @Body customerNetwork: CustomerDTO,
    ): CustomerDTO


    private object Route {
        private const val PREFIX = "/wp-json/wc/v3"
        const val PRODUCT_DETAILS = "$PREFIX/products/{id}"
        const val PRODUCTS = "$PREFIX/products"
        const val PRODUCTS_CATEGORIES = "$PREFIX/products/categories"

        /* customer */
        const val CUSTOMERS = "$PREFIX/customers"
        const val CUSTOMERS_ID = "$PREFIX/customers/{id}"

        /* Orders */
        const val ORDERS = "$PREFIX/orders"
        const val ORDERS_ID = "$PREFIX/orders/{id}"

        /* Reviews */
        const val REVIEWS = "$PREFIX/products/reviews"

        /* Coupons */
        const val COUPONS = "$PREFIX/coupons"
    }

    private object PARAMS {
        const val PRODUCT_ID = "id"
        const val PAGE = "page"
        const val PER_PAGE = "per_page"

    }
}