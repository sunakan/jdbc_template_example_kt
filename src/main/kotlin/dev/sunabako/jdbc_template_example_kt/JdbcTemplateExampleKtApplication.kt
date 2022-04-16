package dev.sunabako.jdbc_template_example_kt

import org.springframework.boot.ApplicationArguments
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.jdbc.core.JdbcTemplate
import java.util.function.Consumer
import java.util.stream.Collectors

@SpringBootApplication
class JdbcTemplateExampleKtApplication(@Autowired val jdbcTemplate: JdbcTemplate) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        println("----------------[ 1 ]")
        //jdbcTemplate.execute("DROP TABLE customers IF EXISTS")

        println("----------------[ 2 ]")
        //jdbcTemplate.execute("CREATE TABLE customers(id SERIAL, last_name VARCHAR(255), first_name VARCHAR(255))")

        println("----------------[ 3 ]")
        val splitUpNames: List<Array<String>> = listOf("田中 太郎", "鈴木 一郎", "高橋 次郎", "田中 一郎")
            .stream()
            .map { name: String -> name.split(" ").toTypedArray() }
            .collect(Collectors.toList())

        println("----------------[ 4 ]")
        splitUpNames.forEach(Consumer { name: Array<String> ->
            println(String.format("INSERT 顧客 レコード for %s %s", name[0], name[1]))
        })

        println("----------------[ 5 ]")
        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames)

        println("----------------[ 6 ]")
        jdbcTemplate.queryForStream(
            "SELECT id, first_name, last_name FROM customers WHERE first_name = ?",
            CustomerRowMapper(),
            *arrayOf<Any>("田中")
        ).forEach { customer: Customer -> println(customer) }
        println("----------------[ 7 ]")
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<JdbcTemplateExampleKtApplication>(*args)
        }
    }
}