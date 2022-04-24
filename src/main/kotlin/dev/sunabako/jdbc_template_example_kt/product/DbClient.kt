package dev.sunabako.jdbc_template_example_kt.product

import arrow.core.Either
import org.springframework.beans.BeanInstantiationException
import org.springframework.dao.DataAccessException
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

interface DbClient {
    sealed class Error : FaultEvent {
        data class DataAccess(override val cause: DataAccessException) : Error(), FaultEvent.Primary.External
        data class BeanInstantiation(override val cause: BeanInstantiationException) : Error(), FaultEvent.Primary.External
        data class Query(override val cause: Throwable) : Error(), FaultEvent.Primary.External
        object NotImplemented : Error(), FaultEvent.Primary.Undeveloped
    }

    fun <T : Any> query(query: String, klass: KClass<T>) : Either<Error, List<T>> =
        Either.Left(Error.NotImplemented)

    fun <T : Any> query(query: String, params: Any, klass: KClass<T>): Either<Error, List<T>> =
        Either.Left(Error.NotImplemented)
}

@Component
class DbClientImpl(val jdbcTemplate: NamedParameterJdbcTemplate) : DbClient {
    override fun <T : Any> query(query: String, klass: KClass<T>): Either<DbClient.Error, List<T>> {
        return try {
            val rowMapper = BeanPropertyRowMapper<T>(klass.java)
            Either.Right(jdbcTemplate.query(query, rowMapper))
        } catch (ex: Throwable) {
            Either.Left(DbClient.Error.Query(ex))
        }
    }

    override fun <T : Any> query(query: String, params: Any, klass: KClass<T>): Either<DbClient.Error, List<T>> {
        return try {
            val sqlParams = BeanPropertySqlParameterSource(params)
            val rowMapper = BeanPropertyRowMapper<T>(klass.java)
            Either.Right(jdbcTemplate.query(query, sqlParams, rowMapper))
        } catch(ex: DataAccessException) {
            Either.Left(DbClient.Error.DataAccess(ex))
        } catch (ex: BeanInstantiationException) {
            Either.Left(DbClient.Error.BeanInstantiation(ex))
        } catch (ex: Throwable) {
            Either.Left(DbClient.Error.Query(ex))
        }
    }
}