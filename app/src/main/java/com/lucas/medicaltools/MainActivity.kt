package com.lucas.medicaltools

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.lucas.medical_equip.repository.MedicalTool
import com.lucas.medicaltools.databinding.ActivityMainBinding
import io.reactivex.subjects.PublishSubject
import mu.KotlinLogging
import timber.log.Timber

private val logger = KotlinLogging.logger {}

class MainActivity() : MviActivity<MainView, MainPresenter>(), MainView {

    private lateinit var medicalTools: List<MedicalTool>
    private lateinit var medicalAdapter: MedicalToolAdapter
    private lateinit var searchView: SearchView
    private lateinit var binding: ActivityMainBinding
    private val onCreateHappenedSubject = PublishSubject.create<String>()
    private val useCapturedFilteringTextSubject = PublishSubject.create<String>()

    private var filterKey: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("ACTIVITY: IN ON CREATE")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //onScreenLoadIntent.onNext("")
    }

    override fun onResume() {
        super.onResume()
        binding.btnSearch.setOnClickListener {
            this.handleSearch()
        }

        onCreateHappenedSubject.onNext("")
    }

    override fun createPresenter() = MainPresenter()

    override val onScreenLoadIntent: PublishSubject<String>
        get() = onScreenLoadIntent
    override val onCreateHappenedIntent: PublishSubject<String>
        get() = onCreateHappenedSubject

    override val onToolsFiltered: PublishSubject<String>
        get() = useCapturedFilteringTextSubject


    override fun render(viewState: MainViewState) {
        when (viewState) {
            is MainViewState.LoadingState -> renderLoadingState()
            is MainViewState.MedicalToolsState -> renderOnCreateHappenedState(viewState.medicalTools)
            //is MainViewState.MedicalToolsFilterState -> renderFilterState(viewState.medicalTools)
            //is MainViewState.errorState -> renderErrorState()
            else -> return
        }
    }

    private fun renderOnCreateHappenedState(medicalTools: List<MedicalTool>?) {
        filterKey = ""
        renderRecyclerView(medicalTools)
        onCreateHappenedSubject.onNext(filterKey)
        dismissSpinner()
    }

    private fun renderFilterState(medicalTools: List<MedicalTool>?) {
        renderRecyclerView(medicalTools)
        useCapturedFilteringTextSubject.onNext(filterKey)
    }

    private fun renderLoadingState() {
        showSpinner()
    }

    private fun showSpinner() {
        binding.progressBar1.visibility = View.VISIBLE
    }

    private fun dismissSpinner() {
        binding.progressBar1.visibility = View.GONE
    }

    private fun renderErrorState() {
        Toast.makeText(this, resources.getString(R.string.error_state), Toast.LENGTH_SHORT).show()
    }

    private fun handleSearch() {
        filterKey = binding.editTextSearch.text.toString()
        useCapturedFilteringTextSubject.onNext(filterKey)
    }

    private fun renderRecyclerView(medicalTools: List<MedicalTool>?) {
        //medicalAdapter = medicalTools?.let { MedicalToolAdapter(it) }!!
        if (medicalTools !== null) {
            medicalAdapter = MedicalToolAdapter(medicalTools)
            binding.medicalToolRecyclerView.adapter = medicalAdapter
            //Set the layout manager to position the item
            binding.medicalToolRecyclerView.layoutManager = LinearLayoutManager(this)
        }
    }
}