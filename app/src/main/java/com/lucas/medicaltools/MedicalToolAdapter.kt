package com.lucas.medicaltools

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.lucas.medical_equip.repository.MedicalTool
import com.lucas.medicaltools.databinding.MedicalToolItemBinding
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class MedicalToolAdapter(private var medicalTools: List<MedicalTool>) : RecyclerView.Adapter<MedicalToolAdapter.ViewHolder>() {
    var medicalToolsFiltered = ArrayList<MedicalTool>()

    init {
        medicalToolsFiltered = medicalTools as ArrayList<MedicalTool>
    }
    inner class ViewHolder(val binding: MedicalToolItemBinding) : RecyclerView.ViewHolder(binding.root)

        // val medicalToolImage = itemView.findViewById<ImageView>(R.id.medical_tool_image)
        // val medicalToolDescription = itemView.findViewById<TextView>(R.id.medical_tool_text)
        // val isAvailable = itemView.findViewById<TextView>(R.id.medical_tool_avail)




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

    private fun onItemClicked(medicalTool: MedicalTool, context: View) {
        Snackbar.make(context, medicalTool.description, Snackbar.LENGTH_LONG).show()
    }
}
