package di

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import core.TestDatabase
import data.repository.RegistrationRepositoryImpl
import data.repository.UserAuthenticationRepositoryImpl
import domain.repository.RegistrationRepository
import domain.repository.UserAuthenticationRepository
import domain.use_cases.GetDataFromInternet
import domain.use_cases.RegistrationUseCase
import io.ktor.client.HttpClient
import org.koin.dsl.module
import presentaion.registration_screen.RegistrationModel

val commonModule = module {
    single { HttpClient() }
    single <TestDatabase>{
        TestDatabase(get())
    }
    single { Settings() }
    single { GetDataFromInternet(get()) }
    single <RegistrationRepository>{ RegistrationRepositoryImpl(get()) }
    single <UserAuthenticationRepository>{ UserAuthenticationRepositoryImpl(get(),get()) }

    factory { RegistrationUseCase(get()) }
    factory { RegistrationModel(get(),get()) }
}