package com.proplens.android.feature.paste

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentPasteGo
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.RealEstateAgent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.proplens.android.R

sealed interface PasteListingAction {
    data object Close : PasteListingAction
    data object Paste : PasteListingAction
    data object Extract : PasteListingAction
    data object UseDetected : PasteListingAction
}

@Composable
fun PasteListingRoute(
    onAction: (PasteListingAction) -> Unit = {}
) {
    var url by remember { mutableStateOf("") }
    PasteListingScreen(
        url = url,
        onUrlChange = { url = it },
        onAction = onAction
    )
}

@Composable
fun PasteListingScreen(
    url: String,
    onUrlChange: (String) -> Unit,
    onAction: (PasteListingAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val primary = colorResource(id = R.color.proplens_primary_blue)
    val surface = colorResource(id = R.color.proplens_card_surface)
    val textPrimary = colorResource(id = R.color.proplens_text_primary)
    val textMuted = colorResource(id = R.color.proplens_text_muted)
    val border = colorResource(id = R.color.proplens_border_light)

    Scaffold(
        modifier = modifier,
        containerColor = Color.White,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    tint = textPrimary,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .clickable { onAction(PasteListingAction.Close) }
                        .padding(12.dp)
                )
                Text(
                    text = stringResource(id = R.string.paste_new_analysis_title),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = textPrimary,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(48.dp))
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(surface)
                    .border(width = 1.dp, color = border, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.RealEstateAgent,
                    contentDescription = null,
                    tint = primary,
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(id = R.string.paste_analyze_title),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = textPrimary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.paste_analyze_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = textMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            TextField(
                value = url,
                onValueChange = onUrlChange,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Link,
                        contentDescription = null,
                        tint = textMuted
                    )
                },
                placeholder = {
                    Text(
                        text = "https://...",
                        color = textMuted.copy(alpha = 0.5f)
                    )
                },
                trailingIcon = {
                    Text(
                        text = stringResource(id = R.string.paste_button_paste),
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = primary,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .border(width = 1.dp, color = border, shape = RoundedCornerShape(10.dp))
                            .clickable { onAction(PasteListingAction.Paste) }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    focusedContainerColor = surface,
                    unfocusedContainerColor = surface,
                    disabledContainerColor = surface,
                    cursorColor = primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .border(width = 1.dp, color = border, shape = RoundedCornerShape(14.dp))
            )
            Spacer(modifier = Modifier.height(12.dp))
            SupportedPlatformsRow(
                textPrimary = textPrimary,
                textMuted = textMuted,
                border = border
            )
            Spacer(modifier = Modifier.height(20.dp))
            DetectedLinkCard(
                primary = primary,
                textPrimary = textPrimary,
                textMuted = textMuted,
                border = border,
                onUse = { onAction(PasteListingAction.UseDetected) }
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { onAction(PasteListingAction.Extract) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.AutoAwesome,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.paste_extract_prefill),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.paste_terms),
                style = MaterialTheme.typography.labelSmall,
                color = textMuted.copy(alpha = 0.6f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Composable
private fun SupportedPlatformsRow(
    textPrimary: Color,
    textMuted: Color,
    border: Color
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.paste_supported_platforms),
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = textMuted
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PlatformChip(label = "Dubizzle", color = Color(0xFFE60000), border = border, textPrimary = textPrimary)
            PlatformChip(label = "Property Finder", color = Color(0xFFEF5E4E), border = border, textPrimary = textPrimary)
            PlatformChip(label = "Bayut", color = Color(0xFF00A651), border = border, textPrimary = textPrimary)
        }
    }
}

@Composable
private fun PlatformChip(
    label: String,
    color: Color,
    border: Color,
    textPrimary: Color
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .border(width = 1.dp, color = border, shape = RoundedCornerShape(10.dp))
            .background(colorResource(id = R.color.proplens_card_surface))
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
            color = textPrimary
        )
    }
}

@Composable
private fun DetectedLinkCard(
    primary: Color,
    textPrimary: Color,
    textMuted: Color,
    border: Color,
    onUse: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .border(width = 1.dp, color = border, shape = RoundedCornerShape(14.dp))
            .background(colorResource(id = R.color.proplens_card_surface))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(primary.copy(alpha = 0.1f))
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ContentPasteGo,
                    contentDescription = null,
                    tint = primary
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = stringResource(id = R.string.paste_link_detected),
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    color = textPrimary
                )
                Text(
                    text = "https://www.propertyfinder.ae/en/pl...",
                    style = MaterialTheme.typography.labelSmall,
                    color = textMuted,
                    maxLines = 1
                )
            }
        }
        Text(
            text = stringResource(id = R.string.paste_button_use),
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
            color = primary,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(primary.copy(alpha = 0.1f))
                .clickable(onClick = onUse)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}
