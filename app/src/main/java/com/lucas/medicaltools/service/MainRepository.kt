package com.lucas.medicaltools.service

import com.lucas.medicaltools.MainViewState
import io.reactivex.Observable
import java.util.*

object MainRepository {
    fun loadUserData(): Observable<String> = Observable.just(getRandomMessage())

    private fun getRandomMessage(): String {
        val users = listOf("Tom", "Guy", "Harry")
        return users[Random().nextInt(users.size)]
    }
}
object GetUserUseCase {
    fun getUserData(): Observable<MainViewState> {
        return MainRepository.loadUserData()
            .map<MainViewState> { MainViewState.userData(it) }
            .startWith(MainViewState.loadingState)
            .onErrorReturn { MainViewState.errorState(it) }
    }
}
