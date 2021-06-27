package com.lucas.medicaltools

import android.content.Intent
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.jakewharton.rxrelay2.Relay
import com.lucas.medical_equip.repository.MedicalTool
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

interface MainView: MvpView {
    fun getMedicalToolsIntent(): Observable<MedicalTool>
    fun getUsersIntent(): Observable<String>
    fun render(viewState: MainViewState)
}
