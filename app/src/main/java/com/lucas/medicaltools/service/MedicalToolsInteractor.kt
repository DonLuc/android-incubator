package com.lucas.medicaltools.service

import com.lucas.medical_equip.repository.MedicalTool
import com.lucas.medical_equip.service.RetrofitBuilder
import com.lucas.medicaltools.MainViewState
import com.lucas.medicaltools.repository.MedicalToolsRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

object MedicalToolsInteractor {
    lateinit var medicalToolCache: Response<List<MedicalTool>>
    fun getMedicalTools(filter: String): Observable<MainViewState> {
            return MedicalToolsRepository().getMedicalTools()
                    .switchMap {
                        mapMedicalTools(it)
                    }
                    .subscribeOn(Schedulers.io())
    }

    private fun mapMedicalTools(response: Response<List<MedicalTool>>): Observable<MainViewState> {
        val medicalTools = response.body()
        return if (response.isSuccessful) {
            Observable.just(MainViewState.MedicalToolsState(medicalTools))
        } else {
            Observable.just(MainViewState.ErrorState)
        }
    }

    private fun mapFilteredMedicalTools(response: Response<MutableList<MedicalTool>>): Observable<MainViewState> {
        val medicalTools = response.body()
        return if (response.isSuccessful) {
            Observable.just(MainViewState.MedicalToolsFilterState(medicalTools))
        } else {
            Observable.just(MainViewState.ErrorState)
        }
    }
}