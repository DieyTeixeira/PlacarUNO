package br.com.dieyteixeira.placaruno.di

import androidx.room.Room
import br.com.dieyteixeira.placaruno.authentication.FirebaseAuthRepository
import br.com.dieyteixeira.placaruno.database.PlacarUNODatabase
import br.com.dieyteixeira.placaruno.repositories.PlayersRepository
import br.com.dieyteixeira.placaruno.ui.viewmodels.AppViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.MenuViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.PlayerFormViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.PlayersListViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.SignInViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.SignUpViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MenuViewModel)
    viewModelOf(::PlayerFormViewModel)
    viewModelOf(::PlayersListViewModel)
    viewModelOf(::SignInViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::AppViewModel)
}

val storageModule = module {
    singleOf(::PlayersRepository)
    singleOf(::FirebaseAuthRepository)
    single {
        Room.databaseBuilder(
            androidContext(),
            PlacarUNODatabase::class.java, "placar-uno.db"
        ).build()
    }
    single {
        get<PlacarUNODatabase>().playerDao()
    }
}

val firebaseModule = module {
    single {
        Firebase.auth
    }
}