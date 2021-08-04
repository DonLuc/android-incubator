package com.lucas.medicaltools.repository

import com.lucas.medical_equip.repository.MedicalTool
import com.lucas.medical_equip.service.RetrofitBuilder
import io.reactivex.Observable
import retrofit2.Response

object MedicalToolsRepository {
    var medicalToolCache: Response<List<MedicalTool>>? = null

    fun getMedicalTools(): Observable<Response<List<MedicalTool>>> {
        return if (medicalToolCache === null) {
            var medToolsService = RetrofitBuilder.apiService
            val medToolServiceResponse = medToolsService.getMedicalEquipments()
            medToolServiceResponse.doOnNext {
                medicalToolCache = it
            }
        } else {
            Observable.just(medicalToolCache)
        }
    }
}
