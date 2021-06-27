package com.lucas.medicaltools

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import com.lucas.medical_equip.repository.MedicalTool
import com.lucas.medical_equip.service.RetrofitBuilder
import com.lucas.medicaltools.databinding.ActivityMainBinding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import mu.KotlinLogging
import java.util.*

private val logger = KotlinLogging.logger {}
class MainActivity : MviActivity<MainView, MainPresenter>(), MainView {

    private lateinit var medicalTools: List<MedicalTool>
    private lateinit var medicalAdapter: MedicalToolAdapter
    private lateinit var searchView: SearchView
    private lateinit var binding: ActivityMainBinding
    private val onCreateHappenedSubject = PublishSubject.create<String>()
    private val useCapturedFilteringTextSubject = PublishSubject.create<String>()
    private val onScreenLoadRelay = PublishRelay.create<Intent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //binding.btnSearch.setOnClickListener{
        //}
    }

    override fun onResume() {
        super.onResume()
        onScreenLoadRelay.accept(intent)
    }

    override fun createPresenter() = MainPresenter()
    override val onScreenLoadIntent: Relay<Intent>
        get() = onScreenLoadRelay

    override val onCreateHappenedIntent: PublishSubject<String>
        get() = onCreateHappenedSubject

    override val useCapturedFilteringTextIntent: PublishSubject<String>
        get() = useCapturedFilteringTextSubject


    override fun render(viewState: MainViewState) {
        when (viewState) {
            is MainViewState.MedicalToolsState -> handleOnCreateHappened()
            is MainViewState.MedicalToolsFilterState -> handleFilter()
        }
    }

    private fun handleOnCreateHappened() {
        val filterKey = ""
        onCreateHappenedSubject.onNext(filterKey)
    }

    private fun handleFilter() {
        val filterKey = ""
        useCapturedFilteringTextSubject.onNext(filterKey)
    }
}