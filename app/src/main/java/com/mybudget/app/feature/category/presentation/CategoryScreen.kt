package com.mybudget.app.feature.category.presentation

import android.graphics.Color as AndroidColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mybudget.app.core.designsystem.component.EmptyState
import com.mybudget.app.core.util.formatMinorUnits
import com.mybudget.app.domain.model.Category
import com.mybudget.app.domain.model.CategorySpending

private val categoryEmojis = listOf("🍜", "☕", "👕", "🚕", "🛍️", "🧾", "💊", "🏠", "🎮", "✨")

@Composable
fun CategoryScreen(viewModel: CategoryViewModel = hiltViewModel()) {
    val categorySpendings by viewModel.categorySpendings.collectAsState(initial = emptyList())
    var showEditor by rememberSaveable { mutableStateOf(false) }
    var editingCategory by remember { mutableStateOf<Category?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Categories", style = MaterialTheme.typography.headlineMedium)
                Text(
                    "Organize your spending your way.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Button(onClick = {
                editingCategory = null
                showEditor = true
            }) {
                Icon(Icons.Outlined.Add, contentDescription = null)
                Text("Add")
            }
        }

        if (categorySpendings.isEmpty()) {
            EmptyState(
                icon = Icons.Outlined.LocalOffer,
                title = "No categories yet",
                description = "Your categories will appear here."
            )
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(start = 16.dp, end = 16.dp, bottom = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(categorySpendings, key = { it.category.id }) { spending ->
                    CategoryCard(
                        spending = spending,
                        onEdit = {
                            editingCategory = spending.category
                            showEditor = true
                        },
                        onDelete = { viewModel.deleteCategory(spending.category.id) }
                    )
                }
            }
        }
    }

    if (showEditor) {
        CategoryEditorDialog(
            initialCategory = editingCategory,
            onDismiss = { showEditor = false },
            onSave = { name, emoji ->
                val existing = editingCategory
                if (existing == null) viewModel.addCategory(name, emoji)
                else viewModel.updateCategory(existing, name, emoji)
                showEditor = false
            }
        )
    }
}

@Composable
private fun CategoryCard(
    spending: CategorySpending,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val fallbackColor = MaterialTheme.colorScheme.primary
    val categoryColor = remember(spending.category.colorHex, fallbackColor) {
        runCatching { Color(AndroidColor.parseColor(spending.category.colorHex)) }
            .getOrDefault(fallbackColor)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                spending.category.iconEmoji,
                modifier = Modifier
                    .background(categoryColor.copy(alpha = 0.13f), CircleShape)
                    .padding(12.dp),
                style = MaterialTheme.typography.titleLarge
            )
            Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                Text(spending.category.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    "${spending.transactionCount} transactions",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )
                if (spending.totals.isNotEmpty()) {
                    Text(
                        spending.totals.joinToString("  ·  ") {
                            formatMinorUnits(it.amountMinor, it.currencyCode)
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            IconButton(onClick = onEdit) {
                Icon(Icons.Outlined.Edit, contentDescription = "Edit category")
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Outlined.DeleteOutline, contentDescription = "Delete category")
            }
        }
    }
}

@Composable
private fun CategoryEditorDialog(
    initialCategory: Category?,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var name by remember(initialCategory?.id) { mutableStateOf(initialCategory?.name.orEmpty()) }
    var emoji by remember(initialCategory?.id) { mutableStateOf(initialCategory?.iconEmoji ?: "✨") }
    var error by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (initialCategory == null) "Add category" else "Edit category") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it; error = null },
                    label = { Text("Category name") },
                    singleLine = true
                )
                Text("Choose an icon", style = MaterialTheme.typography.labelLarge)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(categoryEmojis) { option ->
                        FilterChip(
                            selected = emoji == option,
                            onClick = { emoji = option },
                            label = { Text(option) }
                        )
                    }
                }
                if (error != null) {
                    Text(error.orEmpty(), color = MaterialTheme.colorScheme.error)
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (name.isBlank()) error = "Enter a category name."
                else onSave(name.trim(), emoji)
            }) { Text("Save") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}
