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
import br.com.cuidartech.app.ui.nursingDiagnostic.NursingDiagnosticScreen
import br.com.cuidartech.app.ui.nursingDiagnosticList.NursingDiagnosticListScreen
import br.com.cuidartech.app.ui.nursingProcess.NursingProcessScreen
import br.com.cuidartech.app.ui.nursingProcessList.NursingProcessListScreen
import br.com.cuidartech.app.ui.splash.SplashScreen
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
                startDestination = Route.SplashRoute,
            ) {
                composable<Route.SplashRoute> {
                    SplashScreen(onFinished = { _ ->
                        navController.navigate(Route.HomeRoute) {
                            popUpTo<Route.SplashRoute> { inclusive = true }
                            launchSingleTop = true
                        }
                    })
                }
                composable<Route.HomeRoute> {
                    HomeScreen(
                        goToNursingProcessList = { navController.navigate(Route.NursingProcessListRoute) },
                        goToNursingProcess = { processId ->
                            navController.navigate(Route.NursingProcessRoute(processId))
                        },
                        goToCaseStudyList = { subjectId, title ->
                            navController.navigate(
                                Route.CaseStudyListRoute(
                                    subjectId,
                                    title,
                                ),
                            )
                        },
                        goToCaseStudy = { caseStudyPath, title ->
                            navController.navigate(
                                Route.CaseStudyRoute(caseStudyPath, title),
                            )
                        },
                        goToDiagnosticList = { subjectId, title ->
                            navController.navigate(
                                Route.DiagnosticListRoute(subjectId, title),
                            )
                        },
                        goToDiagnostic = { diagnosticPath ->
                            navController.navigate(Route.DiagnosticRoute(diagnosticPath))
                        },
                    )
                }
                composable<Route.NursingProcessListRoute> {
                    NursingProcessListScreen(
                        navigateBack = navController::navigateUp,
                        navigateToItem = { processId ->
                            navController.navigate(
                                Route.NursingProcessRoute(processId),
                            )
                        },
                    )
                }
                composable<Route.CaseStudyListRoute> { entry ->
                    val args = entry.toRoute<Route.CaseStudyListRoute>()
                    CaseStudyListScreen(
                        title = args.title,
                        navigateBack = navController::navigateUp,
                        navigateToAnItem = { caseStudyPath, title ->
                            navController.navigate(
                                Route.CaseStudyRoute(caseStudyPath, title),
                            )
                        },
                    )
                }
                composable<Route.DiagnosticListRoute> { entry ->
                    val args = entry.toRoute<Route.DiagnosticListRoute>()

                    NursingDiagnosticListScreen(
                        title = args.title,
                        navigateBack = navController::navigateUp,
                        navigateToAnItem = { diagnosticPath ->
                            navController.navigate(
                                Route.DiagnosticRoute(diagnosticPath),
                            )
                        },
                    )
                }
                composable<Route.CaseStudyRoute> { entry ->
                    val args = entry.toRoute<Route.CaseStudyRoute>()

                    CaseStudyScreen(
                        title = args.title,
                        navigateBack = navController::navigateUp,
                    )
                }
                composable<Route.DiagnosticRoute> { entry ->
                    val args = entry.toRoute<Route.DiagnosticRoute>()

                    NursingDiagnosticScreen(
                        navigateBack = navController::navigateUp,
                    )
                }
                composable<Route.NursingProcessRoute> {
                    NursingProcessScreen(
                        navigateBack = navController::navigateUp,
                    )
                }
            }
        }
    }
}
