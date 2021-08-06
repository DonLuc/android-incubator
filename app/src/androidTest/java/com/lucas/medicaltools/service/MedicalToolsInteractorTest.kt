package com.lucas.medicaltools.service

import com.lucas.medical_equip.service.APIService
import com.lucas.medical_equip.service.RetrofitBuilder
import com.lucas.medicaltools.repository.MedicalToolsRepository
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MedicalToolsInteractorTest {


    @Mock
    private lateinit var medicalToolsRepo: MedicalToolsRepository

    @Mock
    private lateinit var retrofitBuilder: RetrofitBuilder
    @Mock
    private lateinit var clientService: APIService

    @Mock
    private lateinit var medicalToolsInteractor: MedicalToolsInteractor
    companion object {
        @Throws(Exception::class)
        fun init() {
            // Tell RxAndroid to not use android main ui thread scheduler
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
        MockitoAnnotations.initMocks(this)
        medicalToolsInteractor = MedicalToolsInteractor

    }

    @After
    fun tearDown() {
        tearDown()
    }

    @Test
    fun getMedicalToolsFilterMap() {
    }

    @Test
    fun setMedicalToolsFilterMap() {
    }

    @Test
    fun getMedicalTools() {
    }
}