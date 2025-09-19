package com.elkabsh.bookpedia.book.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bookpedia.composeapp.generated.resources.Res
import bookpedia.composeapp.generated.resources.favorites
import bookpedia.composeapp.generated.resources.no_favorite_books
import bookpedia.composeapp.generated.resources.no_search_results
import bookpedia.composeapp.generated.resources.search_hint
import com.elkabsh.bookpedia.book.domain.Book
import com.elkabsh.bookpedia.book.presentation.book_list.components.BookList
import com.elkabsh.bookpedia.book.presentation.book_list.components.BookSearchBar
import com.elkabsh.bookpedia.core.presentation.DarkBlue
import com.elkabsh.bookpedia.core.presentation.DesertWhite
import com.elkabsh.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun BookListRoot(
    viewModel: BookListViewModel = koinViewModel(),
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier

) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    BookListContent(
        state = state,
        onAction = { action ->
            when (action) {
                is BookListAction.OnBookClick -> {onBookClick(action.book)}
                else -> Unit
            }
            println("testttttt: $action")
            viewModel.onAction(action)
        }
    )
}
@Composable
private fun BookListContent(
    state: BookListState,
    onAction: (BookListAction) -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    val pagerState = rememberPagerState { 2 }

    val searchResultListScrollState = rememberLazyListState()
    val favoriteListScrollState = rememberLazyListState()


    LaunchedEffect(state.searchResult) {
        searchResultListScrollState.animateScrollToItem(0)
    }
    LaunchedEffect(state.selectedTapIndex) {
        pagerState.animateScrollToPage(state.selectedTapIndex)
    }
    LaunchedEffect(pagerState.currentPage) {
        onAction(BookListAction.OnTabSelected(pagerState.currentPage))
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        BookSearchBar(
            query = state.searchQuery,
            onQueryChange = { query ->
                onAction(BookListAction.OnSearchQueryChange(query))
            },
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp),
            onImeSearch = {
                keyboardController?.hide()
            }
        )
        Surface(
            modifier = Modifier.fillMaxWidth().weight(1f),
            color = DesertWhite,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)

        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TabRow(
                    selectedTabIndex = state.selectedTapIndex,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .widthIn(max = 700.dp)
                        .fillMaxWidth(),
                    containerColor = DesertWhite,
                    contentColor = SandYellow,
                    indicator = {
                        TabRowDefaults.SecondaryIndicator(
                            color = SandYellow,
                            modifier = Modifier.tabIndicatorOffset(it[state.selectedTapIndex])
                        )
                    }
                ) {
                    Tab(
                        selected = state.selectedTapIndex == 0,
                        onClick = { onAction(BookListAction.OnTabSelected(0)) },
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = stringResource(Res.string.search_hint),
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                    Tab(
                        selected = state.selectedTapIndex == 1,
                        onClick = { onAction(BookListAction.OnTabSelected(1)) },
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = stringResource(Res.string.favorites),
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth().weight(1f),
                ) { pageIndex ->
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        when (pageIndex) {
                            0 -> {
                                when {
                                    state.isLoading -> CircularProgressIndicator()
                                    state.error != null -> {
                                        Text(
                                            text = state.error.asString(),
                                            style = MaterialTheme.typography.headlineSmall,
                                            color = MaterialTheme.colorScheme.error
                                        )
                                    }

                                    state.searchResult.isEmpty() -> {
                                        Text(
                                            text = stringResource(Res.string.no_search_results),
                                            style = MaterialTheme.typography.headlineSmall,
                                            color = MaterialTheme.colorScheme.error
                                        )
                                    }

                                    else -> {
                                        BookList(
                                            books = state.searchResult,
                                            onBookClick = { onAction(BookListAction.OnBookClick(it)) },
                                            modifier = Modifier.fillMaxSize(),
                                            scrollState = searchResultListScrollState
                                        )
                                    }
                                }
                            }

                            1 -> {
                                if (state.favoriteBooks.isEmpty()) {
                                    Text(
                                        text = stringResource(Res.string.no_favorite_books),
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = MaterialTheme.colorScheme.error
                                    )

                                } else {
                                    BookList(
                                        books = state.favoriteBooks,
                                        onBookClick = { onAction(BookListAction.OnBookClick(it)) },
                                        modifier = Modifier.fillMaxSize(),
                                        scrollState = favoriteListScrollState
                                    )


                                }

                            }
                        }

                    }


                }
            }


        }
    }

}