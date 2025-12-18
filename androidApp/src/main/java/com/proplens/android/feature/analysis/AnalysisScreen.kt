package com.proplens.android.feature.analysis

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.drawBehind
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.proplens.android.R

data class AnalysisUiState(
    val verdict: String,
    val propertyName: String,
    val propertyLocation: String,
    val score: Int
)

@Composable
fun AnalysisRoute(
    onBack: () -> Unit,
    onSave: () -> Unit,
    onOpenTransactions: () -> Unit
) {
    val state = AnalysisUiState(
        verdict = stringResource(id = R.string.analysis_verdict_underpriced),
        propertyName = stringResource(id = R.string.analysis_property_name),
        propertyLocation = stringResource(id = R.string.analysis_property_location),
        score = 88
    )

    AnalysisScreen(
        state = state,
        onBack = onBack,
        onSave = onSave,
        onOpenTransactions = onOpenTransactions
    )
}

@Composable
fun AnalysisScreen(
    state: AnalysisUiState,
    onBack: () -> Unit,
    onSave: () -> Unit,
    onOpenTransactions: () -> Unit,
    modifier: Modifier = Modifier
) {
    val primary = colorResource(id = R.color.proplens_primary_blue)
    val background = colorResource(id = R.color.proplens_background_light)
    val surface = colorResource(id = R.color.proplens_card_surface)
    val textPrimary = colorResource(id = R.color.proplens_text_primary)
    val textMuted = colorResource(id = R.color.proplens_text_muted)
    val border = colorResource(id = R.color.proplens_border_light)
    val success = colorResource(id = R.color.proplens_positive_green)
    val error = colorResource(id = R.color.proplens_error_red)
    val warning = colorResource(id = R.color.proplens_warning_amber)
    val heroBase = colorResource(id = R.color.proplens_slate_850)

    Scaffold(
        modifier = modifier,
        containerColor = background,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(background)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .clickable(onClick = onBack)
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = textPrimary
                    )
                }
                Text(
                    text = stringResource(id = R.string.analysis_title),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = textPrimary,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(id = R.string.analysis_save),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = primary,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable(onClick = onSave)
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                HeroCard(
                    state = state,
                    primary = primary,
                    success = success,
                    heroBase = heroBase,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            item {
                StatsGrid(
                    primary = primary,
                    success = success,
                    error = error,
                    textPrimary = textPrimary,
                    textMuted = textMuted,
                    surface = surface,
                    border = border
                )
            }
            item {
                MarketComparisonCard(
                    primary = primary,
                    success = success,
                    textPrimary = textPrimary,
                    textMuted = textMuted,
                    surface = surface,
                    border = border
                )
            }
            item {
                InsightsSection(
                    primary = primary,
                    success = success,
                    warning = warning,
                    textPrimary = textPrimary,
                    textMuted = textMuted,
                    surface = surface,
                    border = border
                )
            }
            item {
                TransactionsButton(
                    onClick = onOpenTransactions,
                    primary = primary,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            item {
                BenchmarkAccordion(
                    primary = primary,
                    warning = warning,
                    textPrimary = textPrimary,
                    textMuted = textMuted,
                    surface = surface,
                    border = border
                )
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun HeroCard(
    state: AnalysisUiState,
    primary: Color,
    success: Color,
    heroBase: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        heroBase,
                        heroBase.copy(alpha = 0.6f),
                        heroBase.copy(alpha = 0.2f)
                    )
                )
            )
            .height(210.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(primary.copy(alpha = 0.9f))
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Verified,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = stringResource(id = R.string.analysis_confidence),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(success)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.analysis_verdict_label),
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                }
                Text(
                    text = state.verdict,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                    color = success
                )
                Text(
                    text = "${state.propertyName} • ${state.propertyLocation}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.85f)
                )
            }
            ScoreRing(score = state.score, success = success)
        }
    }
}

@Composable
private fun ScoreRing(score: Int, success: Color) {
    val stroke = 6.dp
    Box(
        modifier = Modifier.size(72.dp),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = Color.White.copy(alpha = 0.3f),
                style = Stroke(width = stroke.toPx())
            )
            val sweep = (score.coerceIn(0, 100) / 100f) * 360f
            drawArc(
                color = success,
                startAngle = -90f,
                sweepAngle = sweep,
                useCenter = false,
                style = Stroke(width = stroke.toPx(), cap = androidx.compose.ui.graphics.StrokeCap.Round)
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = score.toString(),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
            Text(
                text = stringResource(id = R.string.analysis_score_label),
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
private fun StatsGrid(
    primary: Color,
    success: Color,
    error: Color,
    textPrimary: Color,
    textMuted: Color,
    surface: Color,
    border: Color
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = stringResource(id = R.string.analysis_financial_snapshot),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = textPrimary,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                StatCard(
                    icon = Icons.Filled.Payments,
                    iconTint = error,
                    iconBackground = error.copy(alpha = 0.1f),
                    label = stringResource(id = R.string.analysis_cashflow),
                    value = stringResource(id = R.string.analysis_cashflow_value),
                    unit = stringResource(id = R.string.analysis_cashflow_unit),
                    valueColor = error,
                    note = stringResource(id = R.string.analysis_cashflow_note),
                    surface = surface,
                    border = border,
                    labelColor = textMuted,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    icon = Icons.Filled.ShowChart,
                    iconTint = success,
                    iconBackground = success.copy(alpha = 0.1f),
                    label = stringResource(id = R.string.analysis_gross_yield),
                    value = stringResource(id = R.string.analysis_gross_yield_value),
                    unit = null,
                    valueColor = textPrimary,
                    note = stringResource(id = R.string.analysis_gross_yield_note),
                    noteColor = success,
                    surface = surface,
                    border = border,
                    labelColor = textMuted,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                StatCard(
                    icon = Icons.Filled.AccountBalanceWallet,
                    iconTint = primary,
                    iconBackground = primary.copy(alpha = 0.1f),
                    label = stringResource(id = R.string.analysis_net_yield),
                    value = stringResource(id = R.string.analysis_net_yield_value),
                    unit = null,
                    valueColor = textPrimary,
                    note = stringResource(id = R.string.analysis_net_yield_note),
                    noteColor = textMuted,
                    surface = surface,
                    border = border,
                    labelColor = textMuted,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    icon = Icons.Filled.SquareFoot,
                    iconTint = textMuted,
                    iconBackground = border,
                    label = stringResource(id = R.string.analysis_price_per_sqft),
                    value = stringResource(id = R.string.analysis_price_sqft_value),
                    unit = stringResource(id = R.string.analysis_price_sqft_unit),
                    valueColor = textPrimary,
                    note = stringResource(id = R.string.analysis_price_sqft_note),
                    noteColor = success,
                    surface = surface,
                    border = border,
                    labelColor = textMuted,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun StatCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    iconBackground: Color,
    label: String,
    value: String,
    unit: String?,
    valueColor: Color,
    note: String,
    noteColor: Color = valueColor,
    surface: Color,
    border: Color,
    labelColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(surface)
            .border(width = 1.dp, color = border, shape = RoundedCornerShape(14.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(iconBackground),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(16.dp))
            }
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                color = labelColor
            )
        }
        Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = valueColor
            )
            if (unit != null) {
                Text(
                    text = unit,
                    style = MaterialTheme.typography.labelMedium,
                    color = valueColor
                )
            }
        }
        Text(
            text = note,
            style = MaterialTheme.typography.labelSmall,
            color = noteColor
        )
    }
}

@Composable
private fun MarketComparisonCard(
    primary: Color,
    success: Color,
    textPrimary: Color,
    textMuted: Color,
    surface: Color,
    border: Color
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(surface)
            .border(width = 1.dp, color = border, shape = RoundedCornerShape(14.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.analysis_market_comparison),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = textPrimary
        )
        ComparisonBlock(
            title = stringResource(id = R.string.analysis_price_vs_market),
            delta = stringResource(id = R.string.analysis_price_delta),
            badge = stringResource(id = R.string.analysis_price_badge),
            avgValue = stringResource(id = R.string.analysis_market_avg_price),
            description = stringResource(id = R.string.analysis_price_context),
            success = success,
            textPrimary = textPrimary,
            textMuted = textMuted,
            markerPosition = 0.7f,
            fillFraction = 0.55f
        )
        ComparisonBlock(
            title = stringResource(id = R.string.analysis_yield_vs_market),
            delta = stringResource(id = R.string.analysis_yield_delta),
            badge = stringResource(id = R.string.analysis_yield_badge),
            avgValue = stringResource(id = R.string.analysis_market_avg_yield),
            description = stringResource(id = R.string.analysis_yield_context),
            success = success,
            textPrimary = textPrimary,
            textMuted = textMuted,
            markerPosition = 0.4f,
            fillFraction = 0.65f
        )
    }
}

@Composable
private fun ComparisonBlock(
    title: String,
    delta: String,
    badge: String,
    avgValue: String,
    description: String,
    success: Color,
    textPrimary: Color,
    textMuted: Color,
    markerPosition: Float,
    fillFraction: Float
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = title, style = MaterialTheme.typography.bodyMedium, color = textMuted)
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = delta,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = success
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(success.copy(alpha = 0.1f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = badge,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = success
                        )
                    }
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = stringResource(id = R.string.analysis_market_avg), style = MaterialTheme.typography.labelSmall, color = textMuted)
                Text(text = avgValue, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold), color = textPrimary)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(textMuted.copy(alpha = 0.2f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fillFraction)
                    .height(10.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(success)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(markerPosition)
                    .height(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .width(2.dp)
                        .height(10.dp)
                        .background(textMuted)
                )
            }
        }
        Text(text = description, style = MaterialTheme.typography.labelSmall, color = textMuted)
    }
}

@Composable
private fun InsightsSection(
    primary: Color,
    success: Color,
    warning: Color,
    textPrimary: Color,
    textMuted: Color,
    surface: Color,
    border: Color
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(id = R.string.analysis_insights),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = textPrimary
        )
        InsightRow(
            icon = Icons.Filled.CheckCircle,
            iconTint = success,
            text = stringResource(id = R.string.analysis_insight_1),
            surface = surface,
            border = border,
            textPrimary = textPrimary
        )
        InsightRow(
            icon = Icons.Filled.TrendingUp,
            iconTint = success,
            text = stringResource(id = R.string.analysis_insight_2),
            surface = surface,
            border = border,
            textPrimary = textPrimary
        )
        InsightRow(
            icon = Icons.Filled.Warning,
            iconTint = warning,
            text = stringResource(id = R.string.analysis_insight_3),
            surface = surface,
            border = border,
            leadingBorder = warning,
            textPrimary = textPrimary
        )
        InsightRow(
            icon = Icons.Filled.Apartment,
            iconTint = primary,
            text = stringResource(id = R.string.analysis_insight_4),
            surface = surface,
            border = border,
            textPrimary = textPrimary
        )
        InsightRow(
            icon = Icons.Filled.Info,
            iconTint = textMuted,
            text = stringResource(id = R.string.analysis_insight_5),
            surface = surface,
            border = border,
            textPrimary = textPrimary
        )
    }
}

@Composable
private fun InsightRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    text: String,
    surface: Color,
    border: Color,
    textPrimary: Color,
    leadingBorder: Color? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(surface)
            .border(
                width = 1.dp,
                color = border,
                shape = RoundedCornerShape(14.dp)
            )
            .drawBehind {
                if (leadingBorder != null) {
                    drawLine(
                        color = leadingBorder,
                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end = androidx.compose.ui.geometry.Offset(0f, size.height),
                        strokeWidth = 6.dp.toPx()
                    )
                }
            }
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = iconTint)
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
            color = textPrimary
        )
    }
}

@Composable
private fun BenchmarkAccordion(
    primary: Color,
    warning: Color,
    textPrimary: Color,
    textMuted: Color,
    surface: Color,
    border: Color
) {
    var expanded by remember { mutableStateOf(false) }
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(14.dp))
                .background(surface)
                .border(width = 1.dp, color = border, shape = RoundedCornerShape(14.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Filled.Analytics, contentDescription = null, tint = textMuted)
                    Text(
                        text = stringResource(id = R.string.analysis_benchmark_title),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = textPrimary
                    )
                }
                Icon(
                    imageVector = Icons.Filled.ExpandMore,
                    contentDescription = null,
                    tint = textMuted,
                    modifier = Modifier.size(18.dp)
                )
            }
            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(width = 1.dp, color = border, shape = RoundedCornerShape(14.dp))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = stringResource(id = R.string.analysis_benchmark_sales_label),
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = textMuted
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Filled.Apartment, contentDescription = null, tint = primary)
                            Text(
                                text = stringResource(id = R.string.analysis_benchmark_sales_value),
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = textPrimary
                            )
                        }
                        Text(
                            text = stringResource(id = R.string.analysis_benchmark_sales_source),
                            style = MaterialTheme.typography.labelSmall,
                            color = textMuted
                        )
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = stringResource(id = R.string.analysis_benchmark_rent_label),
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = textMuted
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Filled.TrendingUp, contentDescription = null, tint = warning)
                            Text(
                                text = stringResource(id = R.string.analysis_benchmark_rent_value),
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = textPrimary
                            )
                        }
                        Text(
                            text = stringResource(id = R.string.analysis_benchmark_rent_source),
                            style = MaterialTheme.typography.labelSmall,
                            color = textMuted
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(warning.copy(alpha = 0.1f))
                                .border(width = 1.dp, color = warning.copy(alpha = 0.2f), shape = RoundedCornerShape(10.dp))
                                .padding(8.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.analysis_benchmark_note),
                                style = MaterialTheme.typography.labelSmall,
                                color = textPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TransactionsButton(
    onClick: () -> Unit,
    primary: Color,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(26.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = primary,
            contentColor = Color.White
        )
    ) {
        Icon(
            imageVector = Icons.Filled.AccountBalanceWallet,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = R.string.analysis_view_transactions),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
    }
}
