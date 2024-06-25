package com.github.xs93.wanandroid.app.router

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.xs93.wanandroid.app.ui.screen.ArticleDetailsScreen
import com.github.xs93.wanandroid.app.ui.screen.MainScreen

/**
 *
 *
 * @author XuShuai
 * @version v1.0
 * @date 2024/3/12 17:37
 * @email 466911254@qq.com
 */

object AppNavHost {
    @SuppressLint("StaticFieldLeak")
    lateinit var navController: NavHostController
}


@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    AppNavHost.navController = navController
    val pageAnimDuration = 350
    NavHost(navController = navController, startDestination = RouteConfig.ROUTE_MAIN,
        enterTransition = {
            slideIn(animationSpec = tween(pageAnimDuration)) {
                IntOffset(it.width, 0)
            }
        },
        exitTransition = {
            slideOut {
                IntOffset(0, 0)
            }
        },
        popEnterTransition = {
            slideIn {
                IntOffset(0, 0)
            }
        },
        popExitTransition = {
            slideOut(animationSpec = tween(pageAnimDuration)) {
                IntOffset(it.width, 0)
            }
        }

    ) {
        composable(RouteConfig.ROUTE_MAIN) {
            MainScreen()
        }

        composable(route = "${RouteConfig.ROUTE_ARTICLE_DETAIL}/{articleId}/{title}/{url}",
            arguments = listOf(
                navArgument("articleId") {
                    type = NavType.LongType
                },

                navArgument("title") {
                    type = NavType.StringType
                },
                navArgument("url") {
                    type = NavType.StringType
                }
            )

        ) {
            val articleId = it.arguments?.getLong("articleId") ?: 0
            val title = it.arguments?.getString("title") ?: ""
            val url = it.arguments?.getString("url") ?: ""
            ArticleDetailsScreen(articleId, title, url)
        }
    }
}