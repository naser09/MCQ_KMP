package core

import android.content.Context
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.MCQ_DB

actual class DriverFactory(private val context:Context) {
    actual suspend fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(MCQ_DB.Schema.synchronous(), context, "test.db")
    }
}