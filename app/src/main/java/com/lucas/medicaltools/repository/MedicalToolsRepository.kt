package com.lucas.medicaltools.repository

import com.lucas.medical_equip.repository.MedicalTool
import com.lucas.medical_equip.service.RetrofitBuilder
import io.reactivex.Observable
import retrofit2.Response

class MedicalToolsRepository() {
    fun getMedicalTools(): Observable<Response<List<MedicalTool>>> {
        var medToolsService = RetrofitBuilder.apiService
        return medToolsService.getMedicalEquipments()
    }
}
