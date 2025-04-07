package com.example.movil_admin.add.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun FormInput(
    label: String,
    placeholder: String,
    textArea: Boolean = false,
    numeric: Boolean = false,
    value: String =  "",
    onChange: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp),
        )

        if (textArea && !numeric) {
            TextField(
                value = value,
                onValueChange = { value: String -> onChange(value) },
                placeholder = { Text(placeholder) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White, focusedContainerColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),

                )
        } else if (numeric && !textArea) {
            TextField(
                value = value,
                onValueChange = { value: String ->
                    if (value.isEmpty() || value.matches(Regex("^\\d*\\.?\\d*$"))) {
                        onChange(value)
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                ),
                placeholder = { Text(placeholder) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White, focusedContainerColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            )
        } else {
            TextField(
                value = value,
                onValueChange = { value: String -> onChange(value) },
                placeholder = { Text(placeholder) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White, focusedContainerColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            )
        }
    }
}