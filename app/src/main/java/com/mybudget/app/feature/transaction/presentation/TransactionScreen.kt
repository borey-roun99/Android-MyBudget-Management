package com.mybudget.app.feature.transaction.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mybudget.app.core.designsystem.component.EmptyState
import com.mybudget.app.core.util.formatMinorUnits
import com.mybudget.app.domain.model.Transaction
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TransactionScreen(viewModel: TransactionViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)) {
            Text("Transactions", style = MaterialTheme.typography.headlineMedium)
            Text(
                "Detected bank payments and assigned categories.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (state.transactions.isEmpty()) {
            EmptyState(
                icon = Icons.Outlined.History,
                title = "No transactions yet",
                description = "Your spending records will appear here."
            )
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(state.transactions, key = { it.id }) { transaction ->
                    TransactionCard(
                        transaction = transaction,
                        categoryName = state.categories.firstOrNull { it.id == transaction.categoryId }?.name,
                        categories = state.categories,
                        onAssignCategory = { categoryId ->
                            viewModel.assignCategory(transaction.id, categoryId)
                        }
                    )
                }
                item { Spacer(Modifier.padding(bottom = 12.dp)) }
            }
        }
    }
}

@Composable
private fun TransactionCard(
    transaction: Transaction,
    categoryName: String?,
    categories: List<com.mybudget.app.domain.model.Category>,
    onAssignCategory: (Long?) -> Unit
) {
    var menuExpanded by remember(transaction.id) { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(transaction.description, style = MaterialTheme.typography.titleSmall)
                Text(
                    SimpleDateFormat("dd MMM yyyy, h:mm a", Locale.getDefault())
                        .format(Date(transaction.transactionDate)),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    categoryName ?: "Uncategorized",
                    style = MaterialTheme.typography.labelMedium,
                    color = if (categoryName == null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "-${formatMinorUnits(transaction.amountMinor, transaction.currencyCode)}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.error
                )
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(Icons.Outlined.Category, contentDescription = "Assign category")
                }
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Uncategorized") },
                        onClick = {
                            onAssignCategory(null)
                            menuExpanded = false
                        }
                    )
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text("${category.iconEmoji}  ${category.name}") },
                            onClick = {
                                onAssignCategory(category.id)
                                menuExpanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}
