package domain.use_cases

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class GetDataFromInternet(private val client: HttpClient) {
    suspend fun getKtorDocs():String{
        return client.get("https://ktor.io/docs/").bodyAsText()
    }
}