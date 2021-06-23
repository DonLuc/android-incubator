package com.lucas.medicaltools

import com.lucas.medical_equip.repository.MedicalTool

sealed class MainViewState() {
    object loadingState: MainViewState()
    data class errorState(val error: Throwable): MainViewState()
    data class medicalToolsData(val medicalTools: List<MedicalTool>): MainViewState()
    data class userData(val user: String): MainViewState()
}