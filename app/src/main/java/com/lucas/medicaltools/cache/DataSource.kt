package com.lucas.medicaltools.cache

import com.lucas.medical_equip.repository.MedicalTool
import io.reactivex.Observable
import retrofit2.Response


class DataSource(private val memoryDataSource: MemoryDataSource,
                 private val diskDataSource: DiskDataSource,
                 private val networkDataSource: NetworkDataSource) {

    val dataFromMemory: Observable<Response<MutableList<MedicalTool>>>?
        get() = memoryDataSource.getData()

    val dataFromDisk: Observable<Response<MutableList<MedicalTool>>>?
        get() = diskDataSource.getMedicalTools().doOnNext { data: Response<MutableList<MedicalTool>> -> memoryDataSource.cacheInMemory(Observable.just(data)) }

    val dataFromNetwork: Observable<Response<MutableList<MedicalTool>>>?
        get() {
            return networkDataSource.getMedicalTools().doOnNext { data ->
                diskDataSource.saveToDisk(data.body())
                memoryDataSource.cacheInMemory(Observable.just(data))
            }
        }

}