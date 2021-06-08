package com.lucas.medicaltools

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lucas.medical_equip.repository.MedicalTool
import com.lucas.medical_equip.service.RetrofitBuilder
import com.lucas.medicaltools.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
class MainActivity : AppCompatActivity() {
    private lateinit var medicalTools: List<MedicalTool>
    private lateinit var medicalAdapter: MedicalToolAdapter
    private lateinit var searchView: SearchView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityMainBinding.inflate(layoutInflater)
        getMedicalTools()
        setContentView(binding.root)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        if (menu != null) {
            searchView = menu.findItem(R.id.action_search).actionView as SearchView
        }
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                medicalAdapter.filter.filter(newText)
                return false
            }
        })

        return true
    }

    @SuppressLint("CheckResult")
    fun getMedicalTools() {
        binding.progressBar1.visibility = View.VISIBLE
        val medTools = RetrofitBuilder.apiService.getMedicalEquipments()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({toolsResponse ->
                medicalTools = toolsResponse
                medicalAdapter = MedicalToolAdapter(medicalTools)
                binding.medicalToolRecyclerView.adapter = medicalAdapter
                //Set the layout manager to position the item
                binding.medicalToolRecyclerView.layoutManager = LinearLayoutManager(this)
                logger.debug { toolsResponse }
                binding.progressBar1.visibility = View.GONE
            }, {error ->
                logger.error { error }
                binding.progressBar1.visibility = View.GONE
            })
    }
}