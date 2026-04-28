package com.sergioozzon.sergei_smirnov_interval_timer.ui.searchworkout.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ProgressIndicatorDefaults.CircularDeterminateStrokeCap
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sergioozzon.sergei_smirnov_interval_timer.R
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.Border
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.DisabledBg
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.DisabledText
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.Error
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.IntervalTheme
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.Primary
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.PrimaryLight
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.TextPrimary
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.TextSecondary
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.TextTertiary


@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    color: Color = MaterialTheme.colorScheme.primary,
    @DrawableRes iconResId: Int = 0,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            width = 0.5.dp, brush = SolidColor(if (enabled) color else Primary)
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isLoading) PrimaryLight else color,
            contentColor = Color.White,
            disabledContainerColor = PrimaryLight,
            disabledContentColor = Primary
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 2.dp,
                strokeCap = CircularDeterminateStrokeCap
            )
        }
        if (iconResId != 0) {
            Icon(
                painter = painterResource(iconResId),
                contentDescription = null,
                modifier = Modifier.padding(end = IntervalTheme.spacing.s),
                tint = Color.White
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = IntervalTheme.spacing.m)
        )
    }
}

@Composable
fun GhostButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    borderColor: Color = Error,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp),
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        border = ButtonDefaults.outlinedButtonBorder(enabled).copy(
            width = 0.5.dp, brush = SolidColor(if (enabled) borderColor else DisabledBg)
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = if (borderColor == Error) Error else TextPrimary,
            disabledContentColor = DisabledText
        )
    ) {
        Text(
            text = text, style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun SearchWorkoutInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    label: String? = null,
    errorText: String? = null,
    isEnabled: Boolean = false,
    keyboardAction: KeyboardActions = KeyboardActions { KeyboardActions.Default },
) {
    Column(modifier = modifier) {
        if (label != null) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                modifier = Modifier.padding(bottom = IntervalTheme.spacing.xs)
            )
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = isEnabled,
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextTertiary
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Border,
                disabledBorderColor = Border,
                errorBorderColor = Error
            ),
            textStyle = MaterialTheme.typography.bodyLarge,
            isError = errorText != null,
            modifier = Modifier
                .height(52.dp)
                .fillMaxWidth(),
            keyboardActions = keyboardAction,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search, keyboardType = KeyboardType.Number
            )
        )
        if (errorText != null) {
            Row(
                modifier = Modifier.padding(top = IntervalTheme.spacing.s)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_alert),
                    tint = Error,
                    contentDescription = "",
                    modifier = Modifier
                        .size(12.dp)
                        .align(Alignment.CenterVertically),
                )
                Text(
                    text = errorText,
                    color = Error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = IntervalTheme.spacing.s)
                )
            }
        }
    }
}
