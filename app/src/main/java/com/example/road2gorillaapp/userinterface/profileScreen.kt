package com.example.road2gorillaapp.userinterface

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.road2gorillaapp.R

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    // spremenljivka za profilno sliko
    var profileSlika: Painter = painterResource(id = R.drawable.gorillapfp)

    // profil
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // profilna slika
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color.LightGray, shape = CircleShape)
                .clickable {
                    // dodajanje nove slike ..
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = profileSlika,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(110.dp)
                    .background(Color.White, shape = CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // username
        Text(text = "HRIBCEK NA TRENU", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(24.dp))

        // drugo
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Column(
                horizontalAlignment = Alignment.Start,
                //verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Friends", fontSize = 18.sp)
                Text(text = "Weight:", fontSize = 18.sp)
                Text(text = "Height:", fontSize = 18.sp)
            }

            Column(
                horizontalAlignment = Alignment.Start,
                //verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "(3)", fontSize = 18.sp)
                Text(text = "100 kg", fontSize = 18.sp)
                Text(text = "175 cm", fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Sign out in Delete Account gumba
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    // odjava
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 4.dp)
            ) {
                Text("Sign Out", color = Color.White)
            }

            Button(
                onClick = {
                    // brisanje računa
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 4.dp)
            ) {
                Text("Delete Account", color = Color.White)
            }
        }
    }
}
