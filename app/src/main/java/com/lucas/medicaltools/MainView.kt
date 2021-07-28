package com.lucas.medicaltools

import android.content.Intent
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.lucas.medical_equip.repository.MedicalTool
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

interface MainView: MvpView {
    val onScreenLoadIntent: PublishSubject<String>
    val onCreateHappenedIntent: PublishSubject<String>
    val onToolsFiltered: PublishSubject<String>
    fun render(viewState: MainViewState)
}
