package di

import core.TestDatabase
import domain.use_cases.GetDataFromInternet
import io.ktor.client.HttpClient
import org.koin.dsl.module

val commonModule = module {
    single { HttpClient() }
    single <TestDatabase>{
        TestDatabase(get())
    }
    single { GetDataFromInternet(get()) }
}