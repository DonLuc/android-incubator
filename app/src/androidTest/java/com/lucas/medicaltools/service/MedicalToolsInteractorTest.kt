package com.lucas.medicaltools.service

import com.lucas.medical_equip.repository.MedicalTool
import com.lucas.medical_equip.service.APIService
import com.lucas.medical_equip.service.RetrofitBuilder
import com.lucas.medicaltools.MainViewState
import com.lucas.medicaltools.repository.MedicalToolsRepository
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(JUnit4::class)
class MedicalToolsInteractorTest {


    @Mock
    private lateinit var medicalToolsInteractor: MedicalToolsInteractor

    @Mock
    private lateinit var dataState: MainViewState.MedicalToolsState
    @Mock
    private lateinit var errorState: MainViewState.MedicalToolsState

    companion object {
        @Throws(Exception::class)
        fun init() {
            // Tell RxAndroid to not use android main ui thread scheduler
            MockitoAnnotations.initMocks(this)
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        }

        @Throws(Exception::class)
        fun tearDown() {
            RxAndroidPlugins.reset()
            RxJavaPlugins.reset()
        }
    }


    @Before
    fun setUp() {
        init()
        //MockitoAnnotations.initMocks(this)
        medicalToolsInteractor = MedicalToolsInteractor
    }

    @After
    fun tearDown() {
        //tearDown()

    }

    @Test
    fun testGetMedicalToolsSuccess() {
        val filterString = ""
        val viewState = medicalToolsInteractor.getMedicalTools(filterString)
            .blockingFirst()
        Assert.assertTrue(!viewState.equals(MainViewState.LoadingState))
        Assert.assertTrue(!viewState.equals(MainViewState.ErrorState))
    }

    @Test
    fun `medicalToolsListIsNotEmpty`() {
        var filter: String = ""
        Assert.assertNotNull(filter)
    }

    @Test
    fun getMedicalToolsFailure() {
    }

    @Test
    fun setMedicalToolsFilterMap() {
    }

    @Test
    fun getMedicalTools() {
    }
}