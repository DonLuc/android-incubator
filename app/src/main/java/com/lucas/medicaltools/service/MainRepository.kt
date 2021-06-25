package com.lucas.medicaltools.service

import android.view.View
import com.lucas.medical_equip.repository.MedicalTool
import com.lucas.medical_equip.service.RetrofitBuilder
import com.lucas.medicaltools.MainViewState
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.util.*
object MainRepository {
    fun loadUserData(): Observable<String> = Observable.just(getRandomMessage())

    private fun getRandomMessage(): String {
        val users = listOf("Tom", "Guy", "Harry")
        return users[Random().nextInt(users.size)]
    }
    private fun mapMedicalTools(response: Response<List<MedicalTool>>): Observable<MainViewState> {
        val medicalTools = response.body()
        return if (response.isSuccessful) {
            Observable.just(MainViewState.medicalToolsState(medicalTools))
        } else {
            var error = Exception(response.errorBody()?.string())

            Observable.just(MainViewState.errorState(error))
        }
    }

    fun getMedicalTools(): Observable<MainViewState>?
    {
        val medTools = RetrofitBuilder.apiService
            return medTools.getMedicalEquipments()
                .switchMap {
                    mapMedicalTools(it)
                }
            .subscribeOn(Schedulers.io())
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
