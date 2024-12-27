package br.com.cuidartech.app.mappers

import br.com.cuidartech.app.domain.model.NursingDiagnostic
import br.com.cuidartech.app.ui.model.NursingDiagnosticCategoryDivider
import br.com.cuidartech.app.ui.model.NursingDiagnosticItemUiModel
import br.com.cuidartech.app.ui.model.NursingDiagnosticListItem

class NursingDiagnosticUIModelMapper {

    private fun transformToUiModel(nursingDiagnostic: NursingDiagnostic): NursingDiagnosticItemUiModel =
        with(nursingDiagnostic) {
            NursingDiagnosticItemUiModel(
                id = id,
                remoteId = remoteId,
                title = title,
                category = category,
            )
        }

    fun transformToCategorizedList(nursingDiagnosticList: List<NursingDiagnostic>): List<NursingDiagnosticListItem> {
        val categorizedList = mutableListOf<NursingDiagnosticListItem>()
        nursingDiagnosticList.forEachIndexed { index, nursingDiagnostic ->
            when(index) {
                0 -> {
                    categorizedList.add(NursingDiagnosticCategoryDivider(title = nursingDiagnostic.category))
                    categorizedList.add(transformToUiModel(nursingDiagnostic))
                }
                else -> {
                    if(nursingDiagnostic.category != nursingDiagnosticList[index-1].category) {
                        categorizedList.add(NursingDiagnosticCategoryDivider(title = nursingDiagnostic.category))
                        categorizedList.add(transformToUiModel(nursingDiagnostic))
                    } else {
                        categorizedList.add(transformToUiModel(nursingDiagnostic))
                    }
                }
            }
        }

        return categorizedList
    }
}