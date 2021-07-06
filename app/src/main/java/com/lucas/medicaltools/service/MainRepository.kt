package com.lucas.medicaltools.service

import com.lucas.medical_equip.repository.MedicalTool
import com.lucas.medical_equip.service.RetrofitBuilder
import com.lucas.medicaltools.MainViewState
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.util.*
object MainRepository {

    private fun mapMedicalTools(response: Response<List<MedicalTool>>): Observable<MainViewState> {
        val medicalTools = response.body()
        return if (response.isSuccessful) {
            Observable.just(MainViewState.MedicalToolsState(medicalTools))
        } else {
            Observable.just(MainViewState.errorState)
        }
    }

    private fun mapFilteredMedicalTools(response: Response<List<MedicalTool>>): Observable<MainViewState> {
        val medicalTools = response.body()
        return if (response.isSuccessful) {
            Observable.just(MainViewState.MedicalToolsFilterState(medicalTools))
        } else {
            Observable.just(MainViewState.errorState)
        }
    }

    fun getMedicalTools(filter: String): Observable<out MainViewState>
    {
        if (filter.isNullOrBlank()) {
        val medTools = RetrofitBuilder.apiService
            return medTools.getMedicalEquipments()
                .switchMap {
                    mapMedicalTools(it)
                }
                    .startWith(MainViewState.loadingState)
            .subscribeOn(Schedulers.io())
        } else {
            val medTools = RetrofitBuilder.apiService
            return medTools.getMedicalEquipments()
                    .switchMap {
                        mapFilteredMedicalTools(it)
                    }
                    .startWith(MainViewState.loadingState)
                    .subscribeOn(Schedulers.io())
        }
    }
}
