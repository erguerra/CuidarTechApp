package br.com.cuidartech.app.data

import br.com.cuidartech.app.domain.model.NursingProcess
import dev.gitlive.firebase.firestore.FirebaseFirestore

class NursingProcessRepository(
    private val firebaseFirestore: FirebaseFirestore,
) {

    suspend fun getNursingProcessList(): Result<List<NursingProcess>> = runCatching {
        val processResponse = firebaseFirestore.collection("nursing_processes").get()
        processResponse.documents.map { it.data() }
    }

}