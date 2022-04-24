package dev.sunabako.jdbc_template_example_kt.customer3

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class CustomerRowMapper3: RowMapper<Customer3> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Customer3 {
        return Customer3(
            rs.getLong("id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
        )
    }
}