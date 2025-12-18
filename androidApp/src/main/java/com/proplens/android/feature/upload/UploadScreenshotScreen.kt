package com.proplens.android.feature.upload

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DocumentScanner
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.proplens.android.R

enum class UploadSource { PhotoLibrary, Clipboard }

data class UploadScreenshotUiState(
    val selectedSource: UploadSource = UploadSource.PhotoLibrary
)

sealed interface UploadScreenshotAction {
    data object Close : UploadScreenshotAction
    data class SelectSource(val source: UploadSource) : UploadScreenshotAction
    data object UploadTapped : UploadScreenshotAction
    data object ScanAndPrefill : UploadScreenshotAction
    data object EnterManually : UploadScreenshotAction
}

@Composable
fun UploadScreenshotRoute(
    onAction: (UploadScreenshotAction) -> Unit = {}
) {
    var state by remember { mutableStateOf(UploadScreenshotUiState()) }

    UploadScreenshotScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is UploadScreenshotAction.SelectSource -> state = state.copy(selectedSource = action.source)
                else -> Unit
            }
            onAction(action)
        }
    )
}

@Composable
fun UploadScreenshotScreen(
    state: UploadScreenshotUiState,
    onAction: (UploadScreenshotAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val primary = colorResource(id = R.color.proplens_primary_blue)
    val surface = colorResource(id = R.color.proplens_card_surface)
    val border = colorResource(id = R.color.proplens_border_light)
    val textPrimary = colorResource(id = R.color.proplens_text_primary)
    val textMuted = colorResource(id = R.color.proplens_text_muted)

    Scaffold(
        modifier = modifier,
        containerColor = Color.White,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable { onAction(UploadScreenshotAction.Close) }
                        .background(surface),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(id = R.string.upload_close),
                        tint = textPrimary
                    )
                }
                Text(
                    text = stringResource(id = R.string.upload_analyze_property),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = textPrimary,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(40.dp))
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { onAction(UploadScreenshotAction.ScanAndPrefill) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primary,
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.DocumentScanner,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.upload_scan_prefill),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
                Text(
                    text = stringResource(id = R.string.upload_enter_manually),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = textMuted,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable { onAction(UploadScreenshotAction.EnterManually) }
                        .padding(vertical = 4.dp)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(id = R.string.upload_title),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = textPrimary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.upload_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = textMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            UploadSourceToggle(
                selected = state.selectedSource,
                primary = primary,
                surface = surface,
                border = border,
                textPrimary = textPrimary,
                textMuted = textMuted,
                onSelect = { onAction(UploadScreenshotAction.SelectSource(it)) }
            )
            Spacer(modifier = Modifier.height(24.dp))
            UploadDropArea(
                primary = primary,
                border = border,
                surface = surface,
                textPrimary = textPrimary,
                textMuted = textMuted,
                onClick = { onAction(UploadScreenshotAction.UploadTapped) }
            )
        }
    }
}

@Composable
private fun UploadSourceToggle(
    selected: UploadSource,
    primary: Color,
    surface: Color,
    border: Color,
    textPrimary: Color,
    textMuted: Color,
    onSelect: (UploadSource) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(surface)
            .border(width = 1.dp, color = border, shape = RoundedCornerShape(12.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        ToggleChip(
            label = stringResource(id = R.string.upload_source_photo_library),
            selected = selected == UploadSource.PhotoLibrary,
            primary = primary,
            textPrimary = textPrimary,
            textMuted = textMuted,
            onClick = { onSelect(UploadSource.PhotoLibrary) },
            modifier = Modifier.weight(1f)
        )
        ToggleChip(
            label = stringResource(id = R.string.upload_source_clipboard),
            selected = selected == UploadSource.Clipboard,
            primary = primary,
            textPrimary = textPrimary,
            textMuted = textMuted,
            onClick = { onSelect(UploadSource.Clipboard) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ToggleChip(
    label: String,
    selected: Boolean,
    primary: Color,
    textPrimary: Color,
    textMuted: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .background(if (selected) Color.White else Color.Transparent)
            .border(
                width = 1.dp,
                color = if (selected) primary.copy(alpha = 0.3f) else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
            ),
            color = if (selected) primary else textMuted
        )
    }
}

@Composable
private fun UploadDropArea(
    primary: Color,
    border: Color,
    surface: Color,
    textPrimary: Color,
    textMuted: Color,
    onClick: () -> Unit
) {
    val dashEffect = remember { PathEffect.dashPathEffect(floatArrayOf(12f, 10f), 0f) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 320.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(surface)
            .drawBehind {
                drawRoundRect(
                    color = border,
                    cornerRadius = CornerRadius(20.dp.toPx()),
                    style = androidx.compose.ui.graphics.drawscope.Stroke(
                        width = 2.dp.toPx(),
                        pathEffect = dashEffect
                    )
                )
            }
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(primary.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.AddPhotoAlternate,
                    contentDescription = null,
                    tint = primary,
                    modifier = Modifier.size(40.dp)
                )
            }
            Text(
                text = stringResource(id = R.string.upload_tap_to_upload),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = textPrimary,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(id = R.string.upload_preview_hint),
                style = MaterialTheme.typography.bodySmall,
                color = textMuted,
                textAlign = TextAlign.Center
            )
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .border(width = 1.dp, color = border, shape = RoundedCornerShape(50))
                .background(Color.White)
                .padding(horizontal = 18.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.upload_choose_screenshot),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = primary
            )
        }
    }
}
