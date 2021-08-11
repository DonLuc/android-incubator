package com.lucas.medicaltools.service

import com.lucas.medical_equip.repository.MedicalTool
import com.lucas.medicaltools.MainViewState
import com.lucas.medicaltools.repository.MedicalToolsRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

object MedicalToolsInteractor {
    lateinit var medicalToolsFilterMap: List<MedicalTool>
    fun getMedicalTools(filter: String): Observable<MainViewState> {
        return MedicalToolsRepository.getMedicalTools()
            .switchMap {
                mapMedicalTools(it, filter)
            }
            .subscribeOn(Schedulers.io())
    }

    private fun mapMedicalTools(
        response: Response<List<MedicalTool>>,
        filter: String
    ): Observable<MainViewState> {
        val medicalTools = response.body()
        return if (response.isSuccessful) {
            if (medicalTools != null) {
                medicalToolsFilterMap = medicalTools.filter { (key, value) ->
                    value.toLowerCase()
                        .contains(filter.toLowerCase())
                }
            }
            Observable.just(MainViewState.MedicalToolsState(medicalToolsFilterMap))
        } else {
            Observable.just(MainViewState.ErrorState)
        }
    }
}