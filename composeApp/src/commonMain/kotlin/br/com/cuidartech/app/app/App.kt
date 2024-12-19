package br.com.cuidartech.app.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import br.com.cuidartech.app.app.navigation.Route
import br.com.cuidartech.app.ui.caseStudy.CaseStudyScreen
import br.com.cuidartech.app.ui.caseStudyList.CaseStudyListScreen
import br.com.cuidartech.app.ui.nursingDiagnostics.NursingDiagnosticListScreen
import br.com.cuidartech.app.ui.nursingProcess.NursingProcessListScreen
import br.com.cuidartech.app.ui.subjects.HomeScreen
import br.com.cuidartech.app.ui.theme.CuidardTechTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    CuidardTechTheme {
        val navController = rememberNavController()
        NavHost(
            startDestination = Route.CuidarTechGraphRoute,
            navController = navController,
        ) {
            navigation<Route.CuidarTechGraphRoute>(
                startDestination = Route.HomeRoute,
            ) {
                composable<Route.HomeRoute> {
                    HomeScreen(
                        goToNursingProcessList = { navController.navigate(Route.NursingProcessListRoute) },
                        goToCaseStudyList = { subjectId, title, primaryColor ->
                            navController.navigate(
                                Route.CaseStudyListRoute(subjectId, title, primaryColorLong = primaryColor)
                            )
                        },
                        goToDiagnosticList = { subjectId, title, primaryColor ->
                            navController.navigate(
                                Route.DiagnosticListRoute(subjectId, title, primaryColor)
                            )
                        }
                    )
                }
                composable<Route.NursingProcessListRoute> {
                    NursingProcessListScreen(
                        navigateBack = navController::navigateUp
                    )
                }
                composable<Route.CaseStudyListRoute> { entry ->
                    val args = entry.toRoute<Route.CaseStudyListRoute>()
                    CaseStudyListScreen(
                        title = args.title,
                        primaryColorLong = args.primaryColorLong,
                        navigateBack = navController::navigateUp,
                        navigateToAnItem = { caseStudyPath, title, primaryColorLong ->
                            navController.navigate(
                                Route.CaseStudyRoute(caseStudyPath, title, primaryColorLong)
                            )
                        }
                    )
                }
                composable<Route.DiagnosticListRoute> { entry ->
                    val args = entry.toRoute<Route.DiagnosticListRoute>()

                    NursingDiagnosticListScreen(
                        title = args.title,
                        primaryColorLong = args.primaryColorLong,
                        navigateBack = navController::navigateUp,
                        navigateToAnItem = {}
                    )
                }
                composable<Route.CaseStudyRoute> { entry ->
                    val args = entry.toRoute<Route.CaseStudyRoute>()

                    CaseStudyScreen(
                        title = args.title,
                        primaryColorLong = args.primaryColorLong,
                        navigateBack = navController::navigateUp,
                    )
                }
            }
        }
    }
}

