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
            .subscribeOn(Schedulers.io())
            .onErrorReturn { MainViewState.ErrorState }

        val allIntents = Observable
            .mergeArray(medicalToolsState)
            .startWith(MainViewState.LoadingState)
            .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(allIntents, MainView::render)
    }
}