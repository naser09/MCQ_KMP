package core

import app.cash.sqldelight.async.coroutines.await
import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import com.example.MCQ_DB
import org.koin.core.component.getScopeId
import org.w3c.dom.Worker

actual class DriverFactory {
    actual suspend fun createDriver(): SqlDriver {

        val worker = Worker(
            js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)""")
            //js("""new URL("sqlite.worker.js", import.meta.url)""").unsafeCast<String>()
        )
        val driver: SqlDriver = WebWorkerDriver(worker)
//        val driver = WebWorkerDriver(
//            Worker(
//                //js("""new URL("sqlite.worker.js", import.meta.url)""").unsafeCast<String>()
//                js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)""")
//            )
//        )
        console.error(worker.toString())
        console.error(driver.toString())
        console.error(driver.getScopeId())
        // TODO: Chagne back for first start .
        try {
            MCQ_DB.Schema.create(driver).await()
        }catch (ex:Exception){
            console.error("NASER")
            console.error("exception ${ex.message}")
        }
        return driver
    }
}