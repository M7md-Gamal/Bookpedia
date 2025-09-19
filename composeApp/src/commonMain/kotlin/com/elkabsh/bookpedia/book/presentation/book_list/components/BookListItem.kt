package com.elkabsh.bookpedia.book.presentation.book_list.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bookpedia.composeapp.generated.resources.Res
import bookpedia.composeapp.generated.resources.book_error_2
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.elkabsh.bookpedia.book.domain.Book
import com.elkabsh.bookpedia.core.presentation.LightBlue
import com.elkabsh.bookpedia.core.presentation.PulseAnimation
import com.elkabsh.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.painterResource
import kotlin.math.round

@Composable
fun BookListItem(
    book: Book, onBookClick: (Book) -> Unit, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().height(IntrinsicSize.Min)
            .background(LightBlue.copy(alpha = 0.2f), shape = RoundedCornerShape(32.dp))
            .clickable(
                onClick = {
                    onBookClick(book)
                    println("onclick")

                }
            ).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier.height(100.dp), contentAlignment = Alignment.Center
        ) {
            var imgLoadResult by remember {
                mutableStateOf<Result<Painter>?>(null)
            }

            val painter = rememberAsyncImagePainter(
                model = book.imgUrl, onSuccess = {
                    imgLoadResult =
                        if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.width > 1) {
                            Result.success(it.painter)
                        } else Result.failure(Exception("Invaled imge size"))
                }, onError = {
                    it.result.throwable.printStackTrace()
                    imgLoadResult = Result.failure(it.result.throwable)
                }
            )
            val painterState by painter.state.collectAsStateWithLifecycle()
            val transition by animateFloatAsState(
                targetValue = if (painterState is AsyncImagePainter.State.Success) 1f else 0f,
                animationSpec = tween(durationMillis = 800)
            )

            when (val result = imgLoadResult) {
                null -> {
                    PulseAnimation(modifier = Modifier.size(60.dp))
                }

                else -> {
                    Image(
                        painter = if (result.isSuccess) painter else painterResource(Res.drawable.book_error_2),
                        contentDescription = book.title,
                        contentScale = if (result.isSuccess) ContentScale.Crop else ContentScale.Fit,
                        modifier = Modifier
                            .aspectRatio(
                                ratio = 0.65f, matchHeightConstraintsFirst = true
                            )
                            .graphicsLayer {
                                rotationX = (1f - transition) * 30f
                                scaleX = 0.8f + (transition * 0.2f)
                                scaleY = 0.8f + (transition * 0.2f)
                            }

                    )
                }
            }

        }
        Column(
            modifier = Modifier.fillMaxHeight().weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            book.authors.firstOrNull()?.let { authorName ->
                Text(
                    text = authorName,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            book.averageRating?.let { rating ->
                Row {
                    val avg = round(rating * 10) / 10f
                    Text(
                        text = avg.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = SandYellow
                    )


                }
            }


        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier.size(36.dp)
        )


    }


}
