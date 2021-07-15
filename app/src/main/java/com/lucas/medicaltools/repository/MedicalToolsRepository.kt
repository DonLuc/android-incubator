package com.lucas.medicaltools.repository

import com.lucas.medical_equip.repository.MedicalTool
import com.lucas.medicaltools.cache.DataSource
import com.lucas.medicaltools.cache.DiskDataSource
import com.lucas.medicaltools.cache.MemoryDataSource
import com.lucas.medicaltools.cache.NetworkDataSource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class MedicalToolsRepository() {
    val dataSource: DataSource = DataSource(MemoryDataSource(), DiskDataSource(), NetworkDataSource())

    fun getMedicalTools(): Observable<Response<MutableList<MedicalTool>>>? {
        val memory: Observable<Response<MutableList<MedicalTool>>>? = dataSource.dataFromMemory
        val disk: Observable<Response<MutableList<MedicalTool>>>? = dataSource.dataFromDisk
        val network: Observable<Response<MutableList<MedicalTool>>>? = dataSource.dataFromNetwork

        return Observable.concat(memory, disk, network)
                .firstElement()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable()
    }


}
