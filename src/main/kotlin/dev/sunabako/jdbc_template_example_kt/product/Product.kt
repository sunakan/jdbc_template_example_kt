package dev.sunabako.jdbc_template_example_kt.product

data class Product(
    val code: String,
    val name: String,
    val price: Int,
    val cost: Int,
)

data class SavedProduct(
    val id: Int,
    val code: String,
    val name: String,
    val price: Double,
    val costa: Double,
)