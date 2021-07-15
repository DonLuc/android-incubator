package com.lucas.medicaltools.cache

import com.lucas.medical_equip.repository.MedicalTool
import io.reactivex.Observable
import retrofit2.Response

class DiskDataSource {
    private var data: Response<MutableList<MedicalTool>>? = null
    fun getMedicalTools(): Observable<Response<MutableList<MedicalTool>>> {
        return Observable.create({ emitter ->
            if (data != null) {
                emitter.onNext(data!!)
            }
            emitter.onComplete()
        })
    }

    fun saveToDisk(data: MutableList<MedicalTool>?) {
        this.data?.clear()
        this.data = data
    }
}