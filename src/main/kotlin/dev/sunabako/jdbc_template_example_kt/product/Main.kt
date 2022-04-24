package dev.sunabako.jdbc_template_example_kt.product

fun runForProduct(repository: ProductRepository) {
    val product = Product(
        "aaaaaaaaaaaaaa",
        "bbbbbbbbbbbbbb",
        123,
        456,
    )
    val result = repository.create(product)
    println("runForProductの結果")
    println(result)
}