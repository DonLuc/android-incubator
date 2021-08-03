package com.lucas.medicaltools.service

import com.lucas.medical_equip.repository.MedicalTool
import com.lucas.medicaltools.MainViewState
import com.lucas.medicaltools.repository.MedicalToolsRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

object MedicalToolsInteractor {
    lateinit var tempToolsArray: MutableList<MedicalTool>

    fun getMedicalTools(filter: String): Observable<MainViewState> {
            return MedicalToolsRepository.getMedicalTools()
                .switchMap {
                    mapMedicalTools(it, filter)
                }
                .subscribeOn(Schedulers.io())
    }

    private fun mapMedicalTools(response: Response<List<MedicalTool>>, filter: String): Observable<MainViewState> {
        val medicalTools = response.body()

        return if (response.isSuccessful) {
            tempToolsArray = mutableListOf()
            if (medicalTools != null) {
                if (!filter.isNullOrBlank()) {
                    for (tool in medicalTools) {
                        if (tool.description.toLowerCase().contains(filter.toLowerCase())) {
                            tempToolsArray.add(tool)
                        }
                    }
                } else {
                    tempToolsArray = medicalTools as MutableList<MedicalTool>
                }
            } else {
                tempToolsArray = medicalTools as MutableList<MedicalTool>
            }
            Observable.just(MainViewState.MedicalToolsState(tempToolsArray))
        } else {
            Observable.just(MainViewState.ErrorState)
        }
    }
}