package dev.sunabako.jdbc_template_example_kt.customer3

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource

fun runForCustomer3(jdbcTemplate: NamedParameterJdbcTemplate) {
    println("----------------[ 1 ]")
    val insertQuery: String = "INSERT INTO customers(first_name, last_name) VALUES (:first_name, :last_name) RETURNING *"
    val sqlParams: SqlParameterSource = MapSqlParameterSource()
        .addValue("first_name", "田中")
        .addValue("last_name", "Taro")
    val rowMapper = CustomerRowMapper3()
    // RETURNING句を使ってINSERTと同時にやっちゃう(返り値があるため、queryで処理)
    jdbcTemplate.query(insertQuery, sqlParams, rowMapper)

    println("----------------[ 2 ]")
    val sqlParams2: SqlParameterSource = MapSqlParameterSource()
        .addValue("hoge", "田中")
    jdbcTemplate.queryForStream(
        "SELECT id, first_name, last_name FROM customers WHERE first_name = :hoge",
        sqlParams2,
        CustomerRowMapper3()
    ).forEach { customer: Customer3 -> println(customer) }
}