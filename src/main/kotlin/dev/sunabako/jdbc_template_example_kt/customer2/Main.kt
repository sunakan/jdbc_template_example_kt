package dev.sunabako.jdbc_template_example_kt.customer2

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource

fun runForCustomer2(jdbcTemplate: NamedParameterJdbcTemplate) {
    println("----------------[ 1 ]")
    val insertQuery: String = "INSERT INTO customers(first_name, last_name) VALUES (:first_name, :last_name)"
    val sqlParams: SqlParameterSource = MapSqlParameterSource()
        .addValue("first_name", "田中")
        .addValue("last_name", "Taro")
    // SELECT系はquery
    // INSERT, UPDATE, DELETE系はupdate
    jdbcTemplate.update(insertQuery, sqlParams)


    println("----------------[ 2 ]")
    val sqlParams2: SqlParameterSource = MapSqlParameterSource()
        .addValue("hoge", "田中")
    jdbcTemplate.queryForStream(
        "SELECT id, first_name, last_name FROM customers WHERE first_name = :hoge",
        sqlParams2,
        CustomerRowMapper2()
    ).forEach { customer: Customer2 -> println(customer) }
}