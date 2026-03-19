package shop.nooraholdings.app.ui.composable.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import shop.nooraholdings.app.R
import shop.nooraholdings.app.data.model.Product
import shop.nooraholdings.app.data.model.ProductCategory
import shop.nooraholdings.app.ui.composable.shared.DataBasedContainer
import shop.nooraholdings.app.ui.composable.shared.DataEmptyContent
import shop.nooraholdings.app.ui.state.DataUiState
import shop.nooraholdings.app.ui.viewmodel.ProductViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = koinViewModel(),
    onNavigateToProductDetails: (productId: Int) -> Unit,
) {
    val productsState by viewModel.productsState.collectAsState()

    HomeContent(
        productsState = productsState,
        modifier = modifier,
        onNavigateToProductDetails = onNavigateToProductDetails,
        onAddProductToCart = viewModel::addToCart,
    )
}

@Composable
private fun HomeContent(
    productsState: DataUiState<List<Product>>,
    modifier: Modifier = Modifier,
    onNavigateToProductDetails: (productId: Int) -> Unit,
    onAddProductToCart: (productId: Int) -> Unit,
) {
    var selectedCategory by remember { mutableStateOf<ProductCategory?>(null) }

    Column(modifier = modifier) {
        DataBasedContainer(
            dataState = productsState,
            modifier = Modifier.fillMaxSize(),

            dataPopulated = {
                val products = (productsState as DataUiState.Populated).data
                val featuredProducts = products.take(4)
                val filteredProducts = if (selectedCategory == null) {
                    products
                } else {
                    products.filter { it.category == selectedCategory }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(0.dp),
                    modifier = Modifier.fillMaxSize(),
                ) {
                    item(span = { GridItemSpan(2) }) {
                        HeroCarousel(
                            products = featuredProducts,
                            onProductClick = onNavigateToProductDetails,
                        )
                    }

                    item(span = { GridItemSpan(2) }) {
                        CategoryChipsRow(
                            selectedCategory = selectedCategory,
                            onCategorySelected = { selectedCategory = it },
                        )
                    }

                    items(
                        items = filteredProducts,
                        key = { it.id },
                    ) { product ->
                        ProductCard(
                            product = product,
                            onClick = { onNavigateToProductDetails(product.id) },
                            modifier = Modifier.padding(horizontal = 8.dp),
                        )
                    }
                }
            },

            dataEmpty = {
                DataEmptyContent(
                    primaryText = stringResource(R.string.products_state_empty_primary_text),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}

@Composable
private fun HeroCarousel(
    products: List<Product>,
    onProductClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(pageCount = { products.size })

    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
        ) { page ->
            val product = products[page]
            HeroCard(
                product = product,
                onClick = { onProductClick(product.id) },
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(products.size) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .size(if (isSelected) 8.dp else 5.dp)
                        .background(
                            color = if (isSelected) Color(0xFFC9A961) else Color(0x80FFFFFF),
                            shape = CircleShape,
                        )
                )
            }
        }
    }
}

@Composable
private fun HeroCard(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .clickable(onClick = onClick),
    ) {
        Image(
            painter = painterResource(product.imageRes),
            contentDescription = product.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xCC000000)),
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
        ) {
            Text(
                text = product.title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = "£%.2f".format(product.price),
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFC9A961),
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp)
                .background(color = Color(0xFFC9A961), shape = RoundedCornerShape(4.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp),
        ) {
            Text(
                text = stringResource(R.string.featured_label),
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF1A1A1A),
            )
        }
    }
}

@Composable
private fun CategoryChipsRow(
    selectedCategory: ProductCategory?,
    onCategorySelected: (ProductCategory?) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(vertical = 4.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FilterChip(
            selected = selectedCategory == null,
            onClick = { onCategorySelected(null) },
            label = { Text(stringResource(R.string.category_all)) },
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.primary,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
            ),
        )
        ProductCategory.entries.forEach { category ->
            FilterChip(
                selected = selectedCategory == category,
                onClick = { onCategorySelected(if (selectedCategory == category) null else category) },
                label = { Text(stringResource(category.titleRes)) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                ),
            )
        }
    }
}

@Composable
private fun ProductCard(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column {
            Image(
                painter = painterResource(product.imageRes),
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop,
            )

            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "£%.2f".format(product.price),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}
