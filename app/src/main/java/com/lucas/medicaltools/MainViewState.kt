package com.lucas.medicaltools

import com.lucas.medical_equip.repository.MedicalTool

sealed class MainViewState() {
    object LoadingState: MainViewState()
    object ErrorState: MainViewState()
    data class MedicalToolsState(val medicalTools: List<MedicalTool>?): MainViewState()
    data class MedicalToolsFilterState(val medicalTools: List<MedicalTool>?): MainViewState()
}