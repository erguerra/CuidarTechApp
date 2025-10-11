package br.com.cuidartech.app.di

import br.com.cuidartech.app.data.NursingProcessRepository
import br.com.cuidartech.app.data.SubjectRepository
import br.com.cuidartech.app.data.SubjectsPrefetchStore
import br.com.cuidartech.app.mappers.NursingDiagnosticUIModelMapper
import br.com.cuidartech.app.ui.caseStudy.CaseStudyViewModel
import br.com.cuidartech.app.ui.caseStudyList.CaseStudyListViewModel
import br.com.cuidartech.app.ui.nursingDiagnostic.NursingDiagnosticViewModel
import br.com.cuidartech.app.ui.nursingDiagnosticList.NursingDiagnosticListViewModel
import br.com.cuidartech.app.ui.nursingProcess.NursingProcessViewModel
import br.com.cuidartech.app.ui.nursingProcessList.NursingProcessListViewModel
import br.com.cuidartech.app.ui.subjects.HomeViewModel
import br.com.cuidartech.app.ui.splash.SplashViewModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val dataModule =
    module {
        single {
            Firebase.firestore
        }
        singleOf(::NursingProcessRepository)
        singleOf(::SubjectRepository)
        singleOf(::SubjectsPrefetchStore)
        singleOf(::NursingDiagnosticUIModelMapper)
        viewModelOf(::NursingProcessListViewModel)
        viewModelOf(::HomeViewModel)
        viewModelOf(::CaseStudyListViewModel)
        viewModelOf(::NursingDiagnosticListViewModel)
        viewModelOf(::CaseStudyViewModel)
        viewModelOf(::NursingDiagnosticViewModel)
        viewModelOf(::NursingProcessViewModel)
        viewModelOf(::SplashViewModel)
    }
