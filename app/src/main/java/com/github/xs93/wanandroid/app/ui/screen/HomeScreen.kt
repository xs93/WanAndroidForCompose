package com.github.xs93.wanandroid.app.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.github.xs93.wanandroid.app.R
import com.github.xs93.wanandroid.app.model.HomeTab
import com.github.xs93.wanandroid.app.ui.theme.AppTheme
import kotlinx.coroutines.launch

/**
 *
 *
 * @author XuShuai
 * @version v1.0
 * @date 2023/8/14 13:55
 * @email 466911254@qq.com
 */

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(0, 0f) {
        HomeTab.entries.size
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {

            val (menuRef, tabRowRef, dividerRef) = remember {
                createRefs()
            }

            HorizontalDivider(
                modifier = Modifier.constrainAs(dividerRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            )

            PrimaryTabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = Color.Transparent,
                modifier = Modifier.constrainAs(tabRowRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.value(190.dp)
                },
                divider = {
                    HorizontalDivider(color = Color.Transparent)
                }
            ) {
                HomeTab.entries.forEachIndexed { index, homeTab ->
                    Tab(
                        selected = index == pagerState.currentPage,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                text = stringResource(id = homeTab.tabNameStringResId),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    )
                }
            }

            val menuInteractionSource = remember {
                MutableInteractionSource()
            }

            Image(
                painter = painterResource(id = R.drawable.main_ic_menu_24dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .constrainAs(menuRef) {
                        start.linkTo(parent.start, margin = 16.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .size(40.dp)
                    .padding(8.dp)
                    .clickable(
                        interactionSource = menuInteractionSource,
                        indication = null,
                        onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                    ),
                contentDescription = null
            )
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize(),
            beyondViewportPageCount = HomeTab.entries.size - 1
        ) { page ->
            when (page) {
                0 -> ExploreScreen()
                1 -> SquareScreen()
                2 -> AnswerScreen()
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = false)
@Composable
fun HomeScreenPreview() {
    AppTheme {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        HomeScreen(drawerState)
    }
}