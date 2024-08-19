package br.com.dieyteixeira.placaruno.di

import androidx.room.Room
import br.com.dieyteixeira.placaruno.firebase.FirebaseAuthRepository
import br.com.dieyteixeira.placaruno.database.PlacarUNODatabase
import br.com.dieyteixeira.placaruno.repositories.GamesRepository
import br.com.dieyteixeira.placaruno.repositories.PlayersRepository
import br.com.dieyteixeira.placaruno.repositories.TeamsRepository
import br.com.dieyteixeira.placaruno.repositories.UsersRepository
import br.com.dieyteixeira.placaruno.ui.viewmodels.AppViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.GameViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.UsersListViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.MenuViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.PlayersEditViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.PlayersListViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.PontuationViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.ScoreboardEditViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.ScoreboardListViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.SignInViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.SignUpViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.TeamsEditViewModel
import br.com.dieyteixeira.placaruno.ui.viewmodels.TeamsListViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MenuViewModel)
    viewModelOf(::PlayersEditViewModel)
    viewModelOf(::PlayersListViewModel)
    viewModelOf(::TeamsEditViewModel)
    viewModelOf(::TeamsListViewModel)
    viewModelOf(::GameViewModel)
    viewModelOf(::ScoreboardListViewModel)
    viewModelOf(::ScoreboardEditViewModel)
    viewModelOf(::PontuationViewModel)
    viewModelOf(::UsersListViewModel)
    viewModelOf(::SignInViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::AppViewModel)
}

val storageModule = module {
    singleOf(::PlayersRepository)
    singleOf(::TeamsRepository)
    singleOf(::GamesRepository)
    singleOf(::UsersRepository)
    singleOf(::FirebaseAuthRepository)
    single {
        Room.databaseBuilder(
            androidContext(),
            PlacarUNODatabase::class.java, "placar-uno.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val firebaseModule = module {
    single {
        Firebase.auth
    }
}