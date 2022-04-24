package dev.sunabako.jdbc_template_example_kt.customer2

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class CustomerRowMapper2: RowMapper<Customer2> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Customer2 {
        return Customer2(
            rs.getLong("id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
        )
    }
}