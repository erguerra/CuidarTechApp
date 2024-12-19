package br.com.cuidartech.app.data

import br.com.cuidartech.app.domain.model.CaseStudy
import br.com.cuidartech.app.domain.model.NursingDiagnostic
import br.com.cuidartech.app.domain.model.Subject
import br.com.cuidartech.app.domain.model.SubjectFeatures
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.FirebaseFirestore

class SubjectRepository(
    private val firebaseFirestore: FirebaseFirestore,
) {

    val subjectsReference by lazy {
        firebaseFirestore.collection("subjects")
    }

    suspend fun getSubjects(): Result<List<Subject>> = runCatching {
        subjectsReference.get().documents.map {
            it.data<Subject>().copy(
                id = it.reference.id,
                features = getSubjectsFeaturesById(it.reference.id).getOrNull()
            )
        }
    }

    private suspend fun getSubjectsFeaturesById(id: String): Result<Set<SubjectFeatures>> = runCatching {
        val features = mutableSetOf<SubjectFeatures>()
        SubjectFeatures.entries.forEach {
            val subCollection = subjectsReference
                .document(id)
                .collection(it.serializedName).get()

            if (subCollection.documents.isNotEmpty()) {
                features.add(it)
            }
        }
        features
    }

    suspend fun getCaseStudiesBySubject(subjectId: String): Result<List<CaseStudy>> = runCatching {
        subjectsReference.document(subjectId).collection(
            SubjectFeatures.CASE_STUDIES.serializedName,
        ).orderBy("id").get().documents.map { it.data<CaseStudy>().copy(remoteId = it.id) }
    }

    suspend fun getSubjectById(subjectId: String): Result<Subject> = runCatching {
        subjectsReference.document(subjectId).get().data()
    }

    suspend fun getNursingDiagnosticsBySubject(subjectId: String): Result<List<NursingDiagnostic>> =
        runCatching {
            subjectsReference.document(subjectId)
                .collection(SubjectFeatures.NURSING_DIAGNOSTICS.serializedName)
                .orderBy("title", Direction.ASCENDING)
                .get()
                .documents
                .map { it.data<NursingDiagnostic>().copy(remoteId = it.id) }
        }
}