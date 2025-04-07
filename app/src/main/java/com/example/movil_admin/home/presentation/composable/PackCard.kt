package com.example.movil_admin.home.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movil_admin.core.data.model.entities.Pack
import com.example.movil_admin.ui.theme.FifthRed
import com.example.movil_admin.ui.theme.FourthLightSkyBlue
import com.example.movil_admin.ui.theme.NewBlue
import com.example.movil_admin.ui.theme.SecondaryBlue
import com.example.movil_admin.ui.theme.White
import java.text.NumberFormat
import java.util.Locale

@Composable
fun PackCard(
    pack: Pack,
    onDelete: (id: Int) -> Unit
) {
    val formattedPrice = NumberFormat.getCurrencyInstance(Locale.US).format(pack.price)

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = FourthLightSkyBlue // Light gray background
        )
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // Package label
            Text(
                text = "Paquete",
                color = SecondaryBlue, // Grayish blue color
                style = MaterialTheme.typography.labelSmall
            )

            // Package name
            Text(
                text = pack.name,
                color = NewBlue, // Dark blue color
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )

            // Package description
            Text(
                text = pack.description,
                color = SecondaryBlue, // Grayish blue color
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Package details
            Text(
                text = pack.details,
                color = SecondaryBlue, // Grayish blue color
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Delete button and price in row
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Delete button
                Button(
                    onClick = { onDelete(pack.id) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = FifthRed // Red color
                    ),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "Eliminar",
                        color = White,
                        fontSize = 16.sp
                    )
                }

                // Price
                Text(
                    text = formattedPrice,
                    color = SecondaryBlue, // Grayish blue color
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}