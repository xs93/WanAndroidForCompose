package com.github.xs93.wanandroid.app.ui.screen

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.github.xs93.wanandroid.app.R
import kotlin.math.absoluteValue

/**
 * @author XuShuai
 * @version v1.0
 * @date 2025/3/18 13:42
 * @description Test Ui界面
 *
 */
@Composable
fun TestUiScreen() {
    val pageState = rememberPagerState(pageCount = { movieData.size })

    Crossfade(
        targetState = pageState.currentPage,
        animationSpec = tween(500),
        label = "background image cross fade"
    ) { currentPage ->
        Log.d("1111", "TestUiScreen: $currentPage,${pageState.currentPage}")
        val pageOffset = pageState.currentPageOffsetFraction
        Image(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = lerp(1f, 1.2f, pageOffset.absoluteValue)
                    scaleY = lerp(1f, 1.2f, pageOffset.absoluteValue)
                    translationY = lerp(0f, -20f, pageOffset.absoluteValue)
                }
                .drawWithCache {
                    val gradient = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.White),
                        startY = 0f,
                        endY = size.height / 1.5f
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(brush = gradient, blendMode = BlendMode.Lighten)
                    }
                },
            painter = painterResource(id = movieData[currentPage].resId),
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.TopCenter
        )
    }

    HorizontalPager(
        state = pageState,
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.Bottom,
        pageSpacing = 25.5f.dp,
        contentPadding = PaddingValues(horizontal = 57.5f.dp)
    ) { page ->
        val pageOffset = pageState.calculateCurrentOffsetForPage(page)
        MovieCard(
            modifier = Modifier
                .padding(
                    bottom = androidx.compose.ui.unit.lerp(
                        96.dp,
                        56.dp,
                        pageOffset.absoluteValue
                    )
                )
                .width(260.dp)
                .height(480.dp)
                .graphicsLayer {
                    clip = true
                    shape = RoundedCornerShape(130.dp)
                    shadowElevation = 30f
                    spotShadowColor = DefaultShadowColor.copy(alpha = 0.5f)
                    ambientShadowColor = DefaultShadowColor.copy(alpha = 0.5f)
                    scaleY = lerp(1.0f, 0.9f, pageOffset.absoluteValue)
                }
                .background(color = Color.White)
                .padding(top = 32.dp, start = 32.dp, end = 32.dp),
            page = page, movie = movieData[page]
        )
    }
}


@Composable
fun MovieCard(modifier: Modifier, page: Int, movie: MovieEntity) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = movie.resId),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(290.dp)
                    .clip(RoundedCornerShape(100.dp))
            )
            Text(
                text = movie.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        BookNow(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(Color.Black)
        )
    }
}

@Composable
fun BookNow(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "BOOK NOW",
            color = Color.White,
            fontSize = 12.sp,
        )
    }
}


fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun Preview() {
    TestUiScreen()
}

@Preview
@Composable
fun PreviewMovieCard() {
    MovieCard(
        modifier = Modifier
            .width(260.dp)
            .height(480.dp)
            .background(Color.White), page = 0, movie = movieData[0]
    )
}

data class MovieEntity(val name: String, val resId: Int, val description: String = "")

val movieData = arrayOf(
    MovieEntity(
        "Wicked: Part I",
        R.drawable.test_movie_1,
        "Elphaba, a young woman ridiculed for her green skin, and Galinda, a popular girl, become friends at Shiz University in the Land of Oz. After an encounter with the Wonderful Wizard of Oz, their friendship reaches a crossroads."
    ),
    MovieEntity(
        "Anora",
        R.drawable.test_movie_2,
        "A young escort from Brooklyn meets and impulsively marries the son of a Russian oligarch. Once the news reaches Russia, her fairy tale is threatened as his parents set out for New York to get the marriage annulled."
    ),
    MovieEntity(
        "Heretic",
        R.drawable.test_movie_3,
        "Two young religious women are drawn into a game of cat-and-mouse in the house of a strange man."
    ),
    MovieEntity(
        "Moana 2",
        R.drawable.test_movie_4,
        "After receiving an unexpected call from her wayfinding ancestors, Moana must journey to the far seas of Oceania and into dangerous, long-lost waters for an adventure unlike anything she's ever faced."
    ),
    MovieEntity(
        "Daredevil: Born Again",
        R.drawable.test_movie_5,
        "Matt Murdock finds himself on a collision course with Wilson Fisk when their past identities begin to emerge."
    )
)