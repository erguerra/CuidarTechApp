package br.com.cuidartech.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Reference(
    val reference: String,
    val url: String?,
)
