package dev.sunabako.jdbc_template_example_kt

import dev.sunabako.jdbc_template_example_kt.customer.runForCustomer
import dev.sunabako.jdbc_template_example_kt.customer2.runForCustomer2
import dev.sunabako.jdbc_template_example_kt.customer3.runForCustomer3
import dev.sunabako.jdbc_template_example_kt.product.ProductRepository
import dev.sunabako.jdbc_template_example_kt.product.runForProduct
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

@SpringBootApplication
class JdbcTemplateExampleKtApplication(
    val jdbcTemplate: JdbcTemplate,
    val namedJdbcTemplate: NamedParameterJdbcTemplate,
    val productRepository: ProductRepository,
) : ApplicationRunner {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<JdbcTemplateExampleKtApplication>(*args)
        }
    }

    override fun run(args: ApplicationArguments) {
        val num = 3
        when (num) {
            0 -> runForCustomer(jdbcTemplate)
            1 -> runForCustomer2(namedJdbcTemplate)
            2 -> runForCustomer3(namedJdbcTemplate)
            else -> runForProduct(productRepository)
        }
    }
}