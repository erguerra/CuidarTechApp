package br.com.cuidartech.app.di

import br.com.cuidartech.app.data.NursingProcessRepository
import br.com.cuidartech.app.data.SubjectRepository
import br.com.cuidartech.app.ui.caseStudy.CaseStudyViewModel
import br.com.cuidartech.app.ui.caseStudyList.CaseStudyListViewModel
import br.com.cuidartech.app.ui.nursingDiagnostics.NursingDiagnosticViewModel
import br.com.cuidartech.app.ui.nursingProcess.NursingProcessListViewModel
import br.com.cuidartech.app.ui.subjects.HomeViewModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val dataModule = module {
    single {
        Firebase.firestore
    }
    singleOf(::NursingProcessRepository)
    singleOf(::SubjectRepository)
    viewModelOf(::NursingProcessListViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::CaseStudyListViewModel)
    viewModelOf(::NursingDiagnosticViewModel)
    viewModelOf(::CaseStudyViewModel)
}