package com.lucas.medicaltools.repository

import com.lucas.medical_equip.repository.MedicalTool
import com.lucas.medical_equip.service.RetrofitBuilder
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.Response

object MedicalToolsRepository {
    var medicalToolCache: Observable<Response<List<MedicalTool>>>? = null
    //lateinit var medicalToolCache2: Response<List<MedicalTool>>


    fun getMedicalTools(): Observable<Response<List<MedicalTool>>> {
        return if (medicalToolCache === null) {
            var medToolsService = RetrofitBuilder.apiService
            val medToolServiceResponse = medToolsService.getMedicalEquipments()
            cacheMedicalTools2(medToolServiceResponse)
            medToolServiceResponse
        } else {
            medicalToolCache!!
        }
    }

    private fun cacheMedicalTools(tools: Observable<Response<List<MedicalTool>>>) {
        medicalToolCache = tools
    }

    private fun cacheMedicalTools2(tools: Observable<Response<List<MedicalTool>>>) {
        //medicalToolCache2 = tools.blockingFirst()
        /*tools.switchMap {
          if (it.isSuccessful) {
              medicalToolCache2 = it
          }
        }*/
        //medicalToolCache2
    }

}
