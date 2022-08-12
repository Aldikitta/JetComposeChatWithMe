package com.example.chatwithme.presentation.bottomnavigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.chatwithme.presentation.auth.signIn.SignInScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class)
@Composable
fun BottomNavActivity(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    keyboardController: SoftwareKeyboardController
) {
    AnimatedNavHost(navController, startDestination = BottomNavItem.SignIn.fullRoute) {
        //SIGN IN SCREEN
        composable(
            BottomNavItem.SignIn.fullRoute,
            arguments = listOf(
                navArgument("emailFromSignUp") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            ),
            enterTransition = {
                when (initialState.destination.route) {
                    BottomNavItem.SignUp.fullRoute ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    else -> null
                }

            }, exitTransition = {

                when (targetState.destination.route) {
                    BottomNavItem.SignUp.fullRoute -> slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    )
                    else -> null
                }
            }
        ) {
            val emailFromSignUp = remember {
                it.arguments?.getString("emailFromSignUp")
            }

            SignInScreen(
                emailFromSignUp = emailFromSignUp ?: "",
                navController = navController,
                snackbarHostState = snackbarHostState,
                keyboardController = keyboardController
            )
        }

        composable(
            BottomNavItem.SignUp.fullRoute,
            arguments = listOf(
                navArgument("emailFromSignIn") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            ),
            enterTransition = {
                when (initialState.destination.route) {
                    BottomNavItem.SignIn.fullRoute ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )
                    else -> null
                }

            }, exitTransition = {
                when (targetState.destination.route) {
                    BottomNavItem.SignIn.fullRoute ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    else -> null
                }
            }
        ) {
            val emailFromSignIn = remember{
                it.arguments?.getString("emailFromSignIn")
            }

        }
    }
}