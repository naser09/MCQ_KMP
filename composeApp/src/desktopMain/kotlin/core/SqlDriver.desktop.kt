package core

import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.example.MCQ_DB

actual class DriverFactory {
    actual suspend fun createDriver(): SqlDriver {
        val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        try {
            MCQ_DB.Schema.awaitCreate(driver)
        }catch (ex:Exception){
            println(ex.message)
        }
        return driver
    }
}