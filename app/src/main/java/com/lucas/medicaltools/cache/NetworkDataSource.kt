package com.lucas.medicaltools.cache

import com.lucas.medical_equip.repository.MedicalTool
import com.lucas.medical_equip.service.RetrofitBuilder
import io.reactivex.Observable
import retrofit2.Response

class NetworkDataSource {
    fun getMedicalTools(): Observable<Response<MutableList<MedicalTool>>> {
        var medToolsService = RetrofitBuilder.apiService
        return medToolsService.getMedicalEquipments()
    }
}