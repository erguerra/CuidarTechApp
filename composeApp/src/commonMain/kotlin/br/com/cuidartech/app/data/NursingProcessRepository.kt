package br.com.cuidartech.app.data

import br.com.cuidartech.app.domain.model.NursingProcess
import dev.gitlive.firebase.firestore.FirebaseFirestore

class NursingProcessRepository(
    private val firebaseFirestore: FirebaseFirestore,
) {

    private val nursingProcessCollectionReference by lazy {
        firebaseFirestore.collection("nursing_processes")
    }

    suspend fun getNursingProcessList(): Result<List<NursingProcess>> = runCatching {
        nursingProcessCollectionReference.get().documents.map { it.data() }
    }

    suspend fun getNursingProcessById(id: String): Result<NursingProcess> = runCatching {
        nursingProcessCollectionReference.document(id).get().data()
    }

}