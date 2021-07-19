package com.lucas.medicaltools

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.lucas.medicaltools.service.MedicalToolsInteractor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.util.concurrent.TimeUnit

class MainPresenter: MviBasePresenter<MainView, MainViewState>() {
    override fun bindIntents() {

        val medicalToolsState: Observable<MainViewState> = intent(MainView::onCreateHappenedIntent)
            .switchMap { MedicalToolsInteractor.getMedicalTools(it)}
        val medicalToolsFilteredState: Observable<MainViewState> = intent(MainView::onToolsFiltered)
            .switchMap { MedicalToolsInteractor.getMedicalTools(it)}
            .debounce(400, TimeUnit.MILLISECONDS)

        val allIntents = Observable
            .mergeArray(medicalToolsState, medicalToolsFilteredState)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(allIntents, MainView::render)
    }
}