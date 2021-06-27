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
            var error = Exception(response.errorBody()?.string())

            Observable.just(MainViewState.ErrorState(error))
        }
    }

    fun getMedicalTools(filter: String): Observable<out MainViewState>
    {
        val medTools = RetrofitBuilder.apiService
            return medTools.getMedicalEquipments()
                .switchMap {
                    mapMedicalTools(it)
                }
            .subscribeOn(Schedulers.io())
    }
}
