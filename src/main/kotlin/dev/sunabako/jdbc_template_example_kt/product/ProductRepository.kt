package dev.sunabako.jdbc_template_example_kt.product

import arrow.core.Either
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Repository

interface ProductRepository {
    fun create(product: Product): Either<Error, SavedProduct> = Either.Left(Error.NotImplemented)
    sealed class Error : FaultEvent {
        data class DataAccess(override val cause: DataAccessException, override val err: DbClient.Error) : Error(), FaultEvent.Intermediate
        //data class SqlError(override val cause: SQLException, override val err: DbClient.Error) : Error(), FaultEvent.Intermediate
        data class Unexpected(override val cause: Throwable, override val err: DbClient.Error) : Error(), FaultEvent.Intermediate
        object NotImplemented : Error()
    }
}

@Repository
class PostgresqlProductRepository(
    val dbClient: DbClient
) : ProductRepository {
    val insertQuery = """
        INSERT INTO products
            (code, name, price, cost)
        VALUES
            (:code, :name, :price, :cost)
        RETURNING *
    """.trimIndent()
    data class InsertParams(
        val code: String,
        val name: String,
        val price: Int,
        val cost: Int,
    )

    override fun create(product: Product): Either<ProductRepository.Error, SavedProduct> {
        val params = InsertParams(
            product.code,
            product.name,
            product.price,
            product.cost,
        )
        val result = dbClient.query(
            insertQuery,
            params,
            SavedProduct::class
        )

        return when (result) {
            is Either.Right -> Either.Right(result.value.first())
            is Either.Left -> when(val fault = result.value) {
                is DbClient.Error.DataAccess -> Either.Left(ProductRepository.Error.DataAccess(fault.cause, fault))
                is DbClient.Error.BeanInstantiation -> Either.Left(ProductRepository.Error.Unexpected(fault.cause, fault))
                is DbClient.Error.Query -> Either.Left(ProductRepository.Error.Unexpected(fault.cause, fault))
                is DbClient.Error.NotImplemented -> Either.Left(ProductRepository.Error.NotImplemented)
            }
        }
    }
}
