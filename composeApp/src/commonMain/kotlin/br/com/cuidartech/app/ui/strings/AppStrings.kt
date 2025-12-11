package br.com.cuidartech.app.ui.strings

object AppStrings {
    const val errorGeneric = "Ocorreu um erro inesperado."

    object Home {
        const val welcomeTitle = "Bem-vindo(a)!"
        const val welcomeSubtitle = "Organize seus estudos e encontre que precisa com poucos toques."
        const val exploreButton = "Explorar"
        const val homeSectionTitle = "Assuntos"
        const val heroTitle = "Processo de enfermagem"
        const val heroSubtitle = "Siga cada etapa com confiança, do histórico à avaliação final."
        const val heroButton = "Acessar agora"
        const val featureCaseStudies = "Estudos de Caso"
        const val featureNursingDiagnostics = "Diagnósticos"
        const val searchSheetTitle = "Pesquisar conteúdo"
        const val searchPlaceholder = "Buscar"
        const val searchFilterType = "Filtrar por tipo"
        const val searchFilterSubject = "Filtrar por assunto"
        const val searchFilterAllSubjects = "Todos"
        const val searchIdleHint = "Digite para pesquisar por qualquer conteúdo disponível."
        const val searchError = "Não foi possível carregar os resultados no momento."
        fun searchNoResults(query: String) = "Nenhum resultado para \"$query\"."
        const val searchChipProcess = "Processos"
        const val searchChipDiagnostic = "Diagnósticos"
        const val searchChipCaseStudy = "Estudos de caso"
        const val overlayTitle = "Explorar conteúdo"
        const val overlayDismiss = "Fechar busca"
    }

    object SubjectDescription {
        const val caseAndDiagnostic = "Estudos de caso e diagnósticos disponíveis."
        const val caseOnly = "Estudos de caso disponíveis."
        const val diagnosticOnly = "Diagnósticos disponíveis."
    }

    object CaseStudyList {
        const val topBarTitle = "Estudos de Caso"
        const val headerDescription = "Leia atentamente as informações do Estudo de Caso e selecione uma das opções de diagnóstico apresentadas. Caso a opção esteja incorreta, você visualizará uma dica para a seleção do diagnóstico adequado."
        fun cardTitle(id: Int) = "Estudo de Caso $id"
        const val cardContentDescription = "Estudo de Caso"
    }

    object CaseStudyDetail {
        const val scenarioTitle = "Cenário"
        const val fallbackQuestion = "Escolha uma das alternativas abaixo"
    }

    object NursingDiagnosticList {
        const val topBarTitle = "Diagnósticos"
        const val searchLabel = "Buscar diagnósticos"
        const val searchPlaceholder = "Digite um termo ou categoria"
        const val emptyMessage = "Nenhum diagnóstico encontrado"
        const val itemContentDescription = "Diagnóstico"
    }

    object NursingDiagnosticDetail {
        const val topBarTitle = "Diagnósticos"
        const val interventionsTitle = "Intervenções"
        const val interventionContentDescription = "Intervenção"
        fun categoryLabel(category: String) = "Categoria: $category"
    }

    object NursingProcessList {
        const val topBarTitle = "Processos de Enfermagem"
        const val headerTitle = "Processos de Enfermagem"
        const val itemContentDescription = "Processo de Enfermagem"
    }

    object NursingProcessDetail {
        const val topBarTitle = "Processo de Enfermagem"
        const val iconContentDescription = "Processo de Enfermagem"
        const val referenceContentDescription = "Abrir referência"
    }

    object Splash {
        const val title = "CuidarTech"
        const val errorMessage = "Não conseguimos carregar os conteúdos."
        const val retryButton = "Tentar novamente"
    }

    object CaseStudyFeedback {
        const val wrongTitle = "Resposta Errada"
        const val rightTitle = "Resposta Certa!"
        const val wrongDismiss = "Tentar Novamente"
        const val rightDismiss = "Concluir"
    }

    object Accessibility {
        const val back = "Voltar"
        const val expand = "Expandir"
    }
}
