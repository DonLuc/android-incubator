package com.lucas.medicaltools

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.lucas.medical_equip.repository.MedicalTool
import io.reactivex.Observable

interface MainView: MvpView {
    fun getMedicalToolsIntent(): Observable<MedicalTool>
    fun getUsersIntent(): Observable<String>
    fun render(viewState: MainViewState)
}
