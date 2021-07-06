package com.lucas.medicaltools

import com.lucas.medical_equip.repository.MedicalTool

sealed class MainViewState() {
    object loadingState: MainViewState()
    object errorState: MainViewState()
    data class MedicalToolsState(val medicalTools: List<MedicalTool>?): MainViewState()
    data class MedicalToolsFilterState(val medicalTools: List<MedicalTool>?): MainViewState()
}