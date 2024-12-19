package br.com.cuidartech.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class NursingProcess(
    val remoteId: String,
    val title: String,
    val body: String,
    val references: List<Reference>,
)
