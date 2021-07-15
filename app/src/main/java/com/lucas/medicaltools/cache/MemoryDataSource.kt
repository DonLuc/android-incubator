package com.lucas.medicaltools.cache

import com.lucas.medical_equip.repository.MedicalTool
import io.reactivex.Observable
import retrofit2.Response

class MemoryDataSource {
    private var data: Observable<Response<MutableList<MedicalTool>>>? = null

    fun getData(): Observable<Response<MutableList<MedicalTool>>>?  {
        return Observable.create({ emitter ->
            if (data != null) {
                emitter.onNext(data)
            }
            emitter.onComplete()
        })
    }

    fun cacheInMemory(data: Observable<Response<MutableList<MedicalTool>>>?) {
        this.data.clear()
        this.data = data
    }
}
