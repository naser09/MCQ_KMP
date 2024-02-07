package core

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.db.SqlDriver
import com.example.HockeyPlayer
import com.example.MCQ_DB

expect class DriverFactory {
    suspend fun createDriver(): SqlDriver
}
class TestDatabase(val driverFactory: DriverFactory){
    suspend fun get():List<HockeyPlayer>{
        val query = MCQ_DB(driverFactory.createDriver()).hovkeyQueries
        return query.get_hokkey().awaitAsList()
    }
}