package com.lucas.medicaltools.service

import retrofit2.Response

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
class MedicalToolsRepositoryTest  {


    @Mock
    private lateinit var medicalToolsRepo: MedicalToolsRepository

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
        medicalToolsRepo = MedicalToolsRepository
    }

    @Test
    fun `medicalToolsListIsNotEmpty`() {
        val results = medicalToolsRepo.getMedicalTools()
            .blockingFirst()
        Assert.assertNotNull(results)
    }
    lateinit var results: Response<List<MedicalTool>>

    @Test
    fun medicalToolsListIsGreaterThanOne() {
        val results = medicalToolsRepo.getMedicalTools()
            .blockingFirst()
        Assert.assertTrue(results.body()?.size!! > 1)
    }

    @Test
    fun medicalToolsHaveDescription() {
        val results = medicalToolsRepo.getMedicalTools()
            .blockingFirst()
        // Assert.assertTrue(results.body()?.size!! > 1)
        for (medicalTool in results.body()!!) {
            Assert.assertNotNull(medicalTool.description)
        }
    }

    @Test
    fun medicalToolsHaveImage() {
        val results = medicalToolsRepo.getMedicalTools()
            .blockingFirst()
        // Assert.assertTrue(results.body()?.size!! > 1)
        for (medicalTool in results.body()!!) {
            Assert.assertNotNull(medicalTool.imageURL)
        }
    }

    @After
    fun tearDown() {
        //tearDown()
    }
}
