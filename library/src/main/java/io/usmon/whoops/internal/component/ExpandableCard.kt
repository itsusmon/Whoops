package io.usmon.whoops.internal.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.usmon.whoops.internal.util.ColorUtils.applyAlpha

/**
 * A composable function that creates an expandable elevated card.
 *
 * @param icon The icon to be displayed on the card.
 * @param title The title of the card.
 * @param subtitle The subtitle of the card.
 * @param modifier The modifier to be applied to the card.
 * @param isExpanded The initial expansion state of the card.
 * @param content The content to be displayed inside the expanded card.
 */
@Composable
internal fun ExpandableCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false,
    content: @Composable () -> Unit,
) {
    var expanded by rememberSaveable { mutableStateOf(isExpanded) }

    val animatedDegree = animateFloatAsState(targetValue = if (expanded) 0f else -180f, label = "Button Rotation")

    ElevatedCard(
        modifier = modifier, onClick = { expanded = !expanded }, shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.weight(0.1f),
                imageVector = icon,
                contentDescription = "Device information"
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                    .weight(1f),
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.applyAlpha(0.62f),
                    fontWeight = FontWeight.Normal
                )
            }
            FilledTonalIconButton(modifier = Modifier
                .padding()
                .size(24.dp),
                onClick = { expanded = !expanded }) {
                Icon(
                    Icons.Outlined.ExpandLess,
                    null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.rotate(animatedDegree.value)
                )
            }
        }
        AnimatedVisibility(visible = expanded) {
            content()
        }
    }
}
