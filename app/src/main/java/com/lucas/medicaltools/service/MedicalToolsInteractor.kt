package com.lucas.medicaltools.service

import android.util.Log
import com.lucas.medical_equip.repository.MedicalTool
import com.lucas.medical_equip.service.RetrofitBuilder
import com.lucas.medicaltools.MainViewState
import com.lucas.medicaltools.repository.MedicalToolsRepository
import io.reactivex.Observable
import io.reactivex.functions.Predicate
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.lang.Exception

object MedicalToolsInteractor {
    private var medicalToolCache: List<MedicalTool>? = null
    lateinit var storeToolCache: MutableList<MedicalTool>
    lateinit var storeToolCacheCopy: MutableList<MedicalTool>

    fun getMedicalTools(filter: String): Observable<MainViewState> {
        return if (medicalToolCache == null ) {
            MedicalToolsRepository().getMedicalTools()
                .switchMap {
                    mapMedicalTools(it)
                }
                .subscribeOn(Schedulers.io())
        } else {
            mapMedicalToolsCache(medicalToolCache!!, filter)
        }
    }

    private fun mapMedicalTools(response: Response<List<MedicalTool>>): Observable<MainViewState> {
        val medicalTools = response.body()
        if (medicalTools != null) {
            medicalToolCache = medicalTools
        }
        return if (response.isSuccessful) {
            Observable.just(MainViewState.MedicalToolsState(medicalTools))
        } else {
            Observable.just(MainViewState.ErrorState)
        }
    }

    private fun mapMedicalToolsCache(medicalTools: List<MedicalTool>, filterString: String): Observable<MainViewState> {
        return Observable.just(MainViewState.MedicalToolsState(medicalTools))
    }

    fun mapFilteredMedicalTools(filterString: String): Observable<MainViewState> {
        return if (filterString.isNullOrBlank()) {
            Observable.just(MainViewState.MedicalToolsFilterState(medicalToolCache))
        } else {
            storeToolCacheCopy = medicalToolCache as MutableList<MedicalTool>
            storeToolCache = mutableListOf()
            for (tool in storeToolCacheCopy) {
                if (tool.description.contains(filterString)) {
                    storeToolCache.add(tool)
                }
            }
            Observable.just(MainViewState.MedicalToolsFilterState(storeToolCache))
        }
    }
}