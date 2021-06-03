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
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class MedicalToolAdapter(private var medicalTools: List<MedicalTool>) : RecyclerView.Adapter<MedicalToolAdapter.ViewHolder>(), Filterable {
    var medicalToolsFiltered = ArrayList<MedicalTool>()

    init {
        medicalToolsFiltered = medicalTools as ArrayList<MedicalTool>
    }
    inner class ViewHolder(medicalToolItemView: View) : RecyclerView.ViewHolder(medicalToolItemView) {
        val medicalToolImage = itemView.findViewById<ImageView>(R.id.medical_tool_image)
        val medicalToolDescription = itemView.findViewById<TextView>(R.id.medical_tool_text)
        val isAvailable = itemView.findViewById<TextView>(R.id.medical_tool_avail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        //Inflate the view item
        val medicalToolView = inflater.inflate(R.layout.medical_tool_item, parent, false)
        return ViewHolder(medicalToolView)
    }

    override fun getItemCount(): Int {
        return medicalToolsFiltered.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Get data model based on position
        val medicalTool: MedicalTool = medicalToolsFiltered.get(position)

        //Set the items views based on your views data model
        val medicalToolImage = holder.medicalToolImage
        val medicalToolDescription = holder.medicalToolDescription
        val isMedicalAvail = holder.isAvailable
        holder.itemView.setOnClickListener{onItemClicked(medicalTool, holder.medicalToolImage)}

        if(medicalTool.isAvailable) {
            isMedicalAvail.setText("AVAILABLE")
            isMedicalAvail.setTextColor(Color.GREEN)
        } else {
            isMedicalAvail.setText("NOT AVAILABLE")
            isMedicalAvail.setTextColor(Color.RED)
        }
        Picasso.get().load(medicalTool.imageURL).into(medicalToolImage)
        medicalToolDescription.setText(medicalTool.description)
    }

    private fun onItemClicked(medicalTool: MedicalTool, context: View) {
        Snackbar.make(context, "Medical Tool: " + medicalTool.description, Snackbar.LENGTH_LONG).show()
    }

    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                if(charString.isEmpty()) {
                    medicalToolsFiltered = medicalTools as ArrayList<MedicalTool>
                } else {
                    val resultList = ArrayList<MedicalTool>()
                    for(medicalItem in medicalTools) {
                        if(medicalItem.description.toLowerCase(Locale.ROOT).contains(charString.toLowerCase(Locale.ROOT))) {
                            resultList.add(medicalItem)
                        }
                    }
                    medicalToolsFiltered = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = medicalToolsFiltered
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                medicalToolsFiltered = results?.values as ArrayList<MedicalTool>
                notifyDataSetChanged()
            }
        }
    }
}
