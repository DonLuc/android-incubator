package com.lucas.medicaltools

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.lucas.medical_equip.service.RetrofitBuilder
import com.lucas.medicaltools.service.GetUserUseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter: MviBasePresenter<MainView, MainViewState>() {
    override fun bindIntents() {

        /*val medicalToolsState: Observable<MainViewState> = intent(MainView::getMedicalToolsIntent)
            .subscribeOn(Schedulers.io())
            .switchMap { RetrofitBuilder.apiService.getMedicalEquipments() }
*/
        val userState: Observable<MainViewState> = intent(MainView::getUsersIntent)
            .subscribeOn(Schedulers.io())
            .switchMap { GetUserUseCase.getUserData() }
            .observeOn(AndroidSchedulers.mainThread())
    }

}