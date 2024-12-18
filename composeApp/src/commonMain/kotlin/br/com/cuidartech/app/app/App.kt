package br.com.cuidartech.app.app

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import br.com.cuidartech.app.ui.caseStudyList.CaseStudyListScreen
import br.com.cuidartech.app.ui.nursingProcess.NursingProcessListScreen
import br.com.cuidartech.app.ui.subjects.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            startDestination = Route.CuidarTechGraphRoute,
            navController = navController,
        ) {
            navigation<Route.CuidarTechGraphRoute>(
                startDestination = Route.HomeRoute,
            ){
                composable<Route.HomeRoute> {
                    HomeScreen(
                        goToNursingProcessList = { navController.navigate(Route.NursingProcessListRoute) },
                        goToCaseStudyList = { title, subjectId ->
                            navController.navigate(
                                Route.CaseStudyListRoute(title = title, subjectId = subjectId)
                            )
                        },
                        goToDiagnosticList = { subjectId ->
                            navController.navigate(
                                Route.DiagnosticListRoute(subjectId)
                            )
                        }
                    )
                }
                composable<Route.NursingProcessListRoute> {
                    NursingProcessListScreen()
                }
                composable<Route.CaseStudyListRoute> {
                    CaseStudyListScreen(
                        navigateBack = navController::navigateUp,
                        navigateToAnItem = {}
                    )
                }
                composable<Route.DiagnosticListRoute> {

                }
            }
        }
    }
}

