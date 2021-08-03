package com.lucas.medicaltools

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.lucas.medical_equip.repository.MedicalTool
import com.lucas.medicaltools.databinding.ActivityMainBinding
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class MainActivity() : MviActivity<MainView, MainPresenter>(), MainView {

    private lateinit var medicalAdapter: MedicalToolAdapter
    private lateinit var binding: ActivityMainBinding
    private val onCreateHappenedSubject = PublishSubject.create<String>()
    private val useCapturedFilteringTextSubject = PublishSubject.create<String>()
    private var subscribe: Disposable? = null

    private var filterKey: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener {
            handleSearch()
        }
    }

    override fun onResume() {
        super.onResume()
        onCreateHappenedSubject.onNext("")
    }

    override fun createPresenter() = MainPresenter()

    override val onCreateHappenedIntent: PublishSubject<String>
        get() = onCreateHappenedSubject


    override fun render(viewState: MainViewState) {
        when (viewState) {
            is MainViewState.MedicalToolsState -> renderOnCreateHappenedState(viewState.medicalTools)
            is MainViewState.ErrorState -> renderErrorState()
            else -> return
        }
    }

    private fun renderOnCreateHappenedState(medicalTools: List<MedicalTool>?) {
        renderRecyclerView(medicalTools)
    }

    private fun renderFilterState(medicalTools: List<MedicalTool>?) {
        renderRecyclerView(medicalTools)
    }

    private fun showSpinner() {
        binding.progressBar1.visibility = View.VISIBLE
    }

    private fun dismissSpinner() {
        binding.progressBar1.visibility = View.GONE
    }

    private fun renderErrorState() {
        Toast.makeText(this, resources.getString(R.string.error_state), Toast.LENGTH_LONG).show()
    }

    private fun handleSearch() {
        filterKey = binding.editTextSearch.text.toString()
        onCreateHappenedSubject.onNext(filterKey)
    }

    private fun renderRecyclerView(medicalTools: List<MedicalTool>?) {
        showSpinner()
        if (medicalTools !== null) {
            medicalAdapter = MedicalToolAdapter(medicalTools)
            binding.medicalToolRecyclerView.adapter = medicalAdapter
            //Set the layout manager to position the item
            binding.medicalToolRecyclerView.layoutManager = LinearLayoutManager(this)
            onMedicalItemClick()
            dismissSpinner()
        } else {
            // TODO: An alert to show make a user aware that the data was returned
            dismissSpinner()
        }
    }

    private fun onMedicalItemClick() {
        var that = this
        subscribe = medicalAdapter.clickEvent
            .subscribe {
                Toast.makeText(that, it.description, Toast.LENGTH_LONG).show()
                AlertDialog.Builder(that)
                    .setTitle(getString(R.string.dialog_title, it.description))
                    .setMessage(getString(R.string.dialog_message, it.description))
                    .setPositiveButton(R.string.button_positive, DialogInterface.OnClickListener{dialog, id ->
                        Toast.makeText(that, it.description, Toast.LENGTH_LONG).show()
                    })
                    .setNegativeButton(R.string.button_negative, DialogInterface.OnClickListener{dialog, id ->
                        //TODO
                    })
                    .show()
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        subscribe?.dispose()
    }
}