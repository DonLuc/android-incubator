package com.lucas.medicaltools

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.lucas.medical_equip.repository.MedicalTool
import com.lucas.medicaltools.databinding.MedicalToolItemBinding
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class MedicalToolAdapter(private var medicalTools: List<MedicalTool>) : RecyclerView.Adapter<MedicalToolAdapter.ViewHolder>() {
    var medicalToolsFiltered = ArrayList<MedicalTool>()
    private val selectedMedicalTool: PublishSubject<MedicalTool> = PublishSubject.create<MedicalTool>()
    private val clickSubject = PublishSubject.create<MedicalTool>()
    init {
        medicalToolsFiltered = medicalTools as ArrayList<MedicalTool>
    }

    val clickEvent: Observable<MedicalTool> = clickSubject
    inner class ViewHolder(val binding: MedicalToolItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickSubject.onNext(medicalToolsFiltered[layoutPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = MedicalToolItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return medicalToolsFiltered.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Get data model based on position
        val medicalTool: MedicalTool = medicalToolsFiltered.get(position)
        with(holder) {
            if(medicalTool.isAvailable) {
                binding.medicalToolAvail.setText(R.string.available)
                binding.medicalToolAvail.setTextColor(Color.GREEN)
            } else {
                binding.medicalToolAvail.setText(R.string.not_available)
                binding.medicalToolAvail.setTextColor(Color.RED)
            }
            binding.medicalToolText.setText(medicalTool.description)
            Picasso.get().load(medicalTool.imageURL).into(binding.medicalToolImage)
        }
    }

}
