package com.proplens.android.feature.profile

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RealEstateAgent
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

data class ProfileUiState(
    val name: String,
    val plan: String,
    val role: String,
    val email: String,
    val phone: String,
    val primaryRole: String,
    val version: String
)

sealed interface ProfileAction {
    data object Edit : ProfileAction
    data object ChangePassword : ProfileAction
    data object NotificationSettings : ProfileAction
    data object Subscription : ProfileAction
    data object Logout : ProfileAction
    data object Avatar : ProfileAction
    data object Camera : ProfileAction
    data object NavDeals : ProfileAction
    data object NavAnalyze : ProfileAction
    data object NavSaved : ProfileAction
    data object NavProfile : ProfileAction
    data object Back : ProfileAction
}

@Composable
fun ProfileRoute(
    onAction: (ProfileAction) -> Unit = {}
) {
    val state = ProfileUiState(
        name = stringResource(id = R.string.profile_name),
        plan = stringResource(id = R.string.profile_plan),
        role = stringResource(id = R.string.profile_role),
        email = stringResource(id = R.string.profile_email),
        phone = stringResource(id = R.string.profile_phone),
        primaryRole = stringResource(id = R.string.profile_primary_role),
        version = stringResource(id = R.string.profile_version)
    )

    ProfileScreen(
        state = state,
        onAction = onAction
    )
}

@Composable
fun ProfileScreen(
    state: ProfileUiState,
    onAction: (ProfileAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val primary = colorResource(id = R.color.proplens_primary_blue)
    val background = colorResource(id = R.color.proplens_background_light)
    val surface = colorResource(id = R.color.proplens_card_surface)
    val textPrimary = colorResource(id = R.color.proplens_text_primary)
    val textMuted = colorResource(id = R.color.proplens_text_muted)
    val border = colorResource(id = R.color.proplens_border_light)

    Scaffold(
        modifier = modifier,
        containerColor = background,
        bottomBar = {
            ProfileBottomNav(
                primary = primary,
                muted = textMuted,
                onSelect = onAction
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.profile_title),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = textPrimary
                    )
                    Text(
                        text = stringResource(id = R.string.profile_edit),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = primary,
                        modifier = Modifier.clickable { onAction(ProfileAction.Edit) }
                    )
                }
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(112.dp)
                            .clip(CircleShape)
                            .background(surface)
                            .border(width = 4.dp, color = Color.White, shape = CircleShape)
                            .clickable { onAction(ProfileAction.Avatar) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = null,
                            tint = textMuted,
                            modifier = Modifier.size(48.dp)
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(primary)
                                .border(width = 2.dp, color = Color.White, shape = CircleShape)
                                .clickable { onAction(ProfileAction.Camera) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CameraAlt,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    Text(
                        text = state.name,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = textPrimary
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(primary.copy(alpha = 0.1f))
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Verified,
                                contentDescription = null,
                                tint = primary,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = state.plan,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = primary
                            )
                        }
                        Text(
                            text = state.role,
                            style = MaterialTheme.typography.bodyMedium,
                            color = textMuted
                        )
                    }
                }
            }
            item {
                SectionTitle(text = stringResource(id = R.string.profile_section_personal))
            }
            item {
                ProfileInfoCard(
                    state = state,
                    textPrimary = textPrimary,
                    textMuted = textMuted,
                    border = border,
                    surface = surface
                )
            }
            item {
                SectionTitle(text = stringResource(id = R.string.profile_section_settings))
            }
            item {
                SettingsCard(
                    primary = primary,
                    textPrimary = textPrimary,
                    textMuted = textMuted,
                    border = border,
                    surface = surface,
                    onAction = onAction
                )
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .border(width = 1.dp, color = Color(0xFFFFE4E6), shape = RoundedCornerShape(14.dp))
                            .background(surface)
                            .clickable { onAction(ProfileAction.Logout) }
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Logout,
                            contentDescription = null,
                            tint = Color(0xFFEF4444)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = stringResource(id = R.string.profile_logout),
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFFEF4444)
                        )
                    }
                    Text(
                        text = state.version,
                        style = MaterialTheme.typography.labelSmall,
                        color = textMuted,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(72.dp))
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
        color = colorResource(id = R.color.proplens_text_muted),
        modifier = Modifier.padding(horizontal = 20.dp)
    )
}

@Composable
private fun ProfileInfoCard(
    state: ProfileUiState,
    textPrimary: Color,
    textMuted: Color,
    border: Color,
    surface: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(surface)
            .border(width = 1.dp, color = border, shape = RoundedCornerShape(16.dp))
    ) {
        ProfileInfoRow(label = stringResource(id = R.string.profile_full_name), value = state.name, textPrimary = textPrimary, textMuted = textMuted)
        DividerLine(border = border)
        ProfileInfoRow(label = stringResource(id = R.string.profile_email_label), value = state.email, textPrimary = textPrimary, textMuted = textMuted)
        DividerLine(border = border)
        ProfileInfoRow(label = stringResource(id = R.string.profile_phone_label), value = state.phone, textPrimary = textPrimary, textMuted = textMuted)
        DividerLine(border = border)
        ProfileInfoRow(label = stringResource(id = R.string.profile_primary_role_label), value = state.primaryRole, textPrimary = textPrimary, textMuted = textMuted)
    }
}

@Composable
private fun ProfileInfoRow(
    label: String,
    value: String,
    textPrimary: Color,
    textMuted: Color
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = textMuted
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
            color = textPrimary
        )
    }
}

@Composable
private fun SettingsCard(
    primary: Color,
    textPrimary: Color,
    textMuted: Color,
    border: Color,
    surface: Color,
    onAction: (ProfileAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(surface)
            .border(width = 1.dp, color = border, shape = RoundedCornerShape(16.dp))
    ) {
        SettingsRow(
            icon = Icons.Filled.Lock,
            label = stringResource(id = R.string.profile_change_password),
            onClick = { onAction(ProfileAction.ChangePassword) },
            primary = primary,
            textPrimary = textPrimary,
            textMuted = textMuted,
            border = border
        )
        DividerLine(border = border)
        SettingsRow(
            icon = Icons.Filled.Notifications,
            label = stringResource(id = R.string.profile_notifications),
            onClick = { onAction(ProfileAction.NotificationSettings) },
            primary = primary,
            textPrimary = textPrimary,
            textMuted = textMuted,
            border = border
        )
        DividerLine(border = border)
        SettingsRow(
            icon = Icons.Filled.CreditCard,
            label = stringResource(id = R.string.profile_subscription),
            sublabel = stringResource(id = R.string.profile_subscription_subtitle),
            onClick = { onAction(ProfileAction.Subscription) },
            primary = primary,
            textPrimary = textPrimary,
            textMuted = textMuted,
            border = border
        )
    }
}

@Composable
private fun SettingsRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
    primary: Color,
    textPrimary: Color,
    textMuted: Color,
    border: Color,
    sublabel: String? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = primary)
            }
            Column {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    color = textPrimary
                )
                if (sublabel != null) {
                    Text(
                        text = sublabel,
                        style = MaterialTheme.typography.labelSmall,
                        color = textMuted
                    )
                }
            }
        }
        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = null,
            tint = textMuted
        )
    }
}

@Composable
private fun DividerLine(border: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(border)
    )
}

@Composable
private fun ProfileBottomNav(
    primary: Color,
    muted: Color,
    onSelect: (ProfileAction) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.proplens_card_surface))
            .border(width = 1.dp, color = colorResource(id = R.color.proplens_border_light))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem(
            icon = Icons.Filled.RealEstateAgent,
            label = stringResource(id = R.string.profile_nav_deals),
            selected = false,
            primary = primary,
            muted = muted,
            onClick = { onSelect(ProfileAction.NavDeals) }
        )
        BottomNavItem(
            icon = Icons.Filled.Analytics,
            label = stringResource(id = R.string.profile_nav_analyze),
            selected = false,
            primary = primary,
            muted = muted,
            onClick = { onSelect(ProfileAction.NavAnalyze) }
        )
        BottomNavItem(
            icon = Icons.Filled.Favorite,
            label = stringResource(id = R.string.profile_nav_saved),
            selected = false,
            primary = primary,
            muted = muted,
            onClick = { onSelect(ProfileAction.NavSaved) }
        )
        BottomNavItem(
            icon = Icons.Filled.Person,
            label = stringResource(id = R.string.profile_nav_profile),
            selected = true,
            primary = primary,
            muted = muted,
            onClick = { onSelect(ProfileAction.NavProfile) }
        )
    }
}

@Composable
private fun BottomNavItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    selected: Boolean,
    primary: Color,
    muted: Color,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (selected) primary else muted,
            modifier = Modifier.size(26.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
            ),
            color = if (selected) primary else muted
        )
    }
}
