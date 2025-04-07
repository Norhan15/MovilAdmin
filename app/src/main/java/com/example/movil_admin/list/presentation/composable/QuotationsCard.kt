package com.example.movil_admin.list.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movil_admin.core.data.model.entities.Quote
import com.example.movil_admin.ui.theme.FifthRed
import com.example.movil_admin.ui.theme.NewBlue
import com.example.movil_admin.ui.theme.PrimaryBlue
import com.example.movil_admin.ui.theme.SecondaryBlue
import com.example.movil_admin.ui.theme.White
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QuotationsCard(
    quote: Quote,
    onApprove: () -> Unit,
    onContact: () -> Unit,
    onReject: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Colores
    val cardBackground = SecondaryBlue
    val approveButtonColor = NewBlue
    val contactButtonColor = White
    val rejectButtonColor = FifthRed
    val textColor = White

    // Formatear fecha
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd 'de' MMMM yyyy", Locale("es", "ES"))
    val date = try {
        dateFormat.parse(quote.created_at)?.let { outputFormat.format(it) } ?: "Fecha no disponible"
    } catch (e: Exception) {
        "Fecha no disponible"
    }

    // Preparar texto de extras
    val extras = buildString {
        append("Extras: ")
        val hasExtras = mutableListOf<String>()

        if (quote.custom_template) hasExtras.add("Diseño")
        if (quote.logo_design) hasExtras.add("Logo")
        if (quote.technical_support) hasExtras.add("Soporte")

        append(hasExtras.joinToString(", "))
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(cardBackground)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Fecha
            Text(
                text = date, color = textColor, style = MaterialTheme.typography.labelSmall
            )

            // Nombre del paquete
            Text(
                text = quote.pack.name,
                color = textColor,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(top = 4.dp)
            )

            // Extras
            Text(
                text = extras,
                color = textColor,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(top = 16.dp)
            )

            // Cliente
            Text(
                text = "Cliente: ${quote.buyer_name}",
                color = textColor,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
            )

            if (quote.status != "pending") {
                Text(
                    text = "Estado: ${

                        if (quote.status === "accepted") {
                            "Aceptado"
                        } else {
                            "Rechazado"
                        }

                    }",
                    color = textColor,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
                )
            }

            // Botones

            if (quote.status == "pending") {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Botón Aprobar
                    Button(
                        onClick = onApprove,
                        colors = ButtonDefaults.buttonColors(containerColor = approveButtonColor),
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        Text("Aprobar", color = Color.White, fontSize = 12.sp)
                    }

                    // Botón Rechazar
                    Button(
                        onClick = onReject,
                        colors = ButtonDefaults.buttonColors(containerColor = rejectButtonColor),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    ) {
                        Text("Rechazar", color = Color.White, fontSize = 12.sp)
                    }

                    // Botón Contactar
                    Button(
                        onClick = onContact,
                        colors = ButtonDefaults.buttonColors(containerColor = contactButtonColor),
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                    ) {
                        Text("Contactar", color = Color.DarkGray, fontSize = 12.sp)
                    }
                }
            }

        }
    }
}
