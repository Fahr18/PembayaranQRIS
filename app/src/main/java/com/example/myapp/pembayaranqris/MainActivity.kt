package com.example.myapp.pembayaranqris

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material.icons.Icons
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.pembayaranqris.model.Saldo
import com.example.myapp.pembayaranqris.ui.theme.PembayaranQRISTheme
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.example.myapp.pembayaranqris.util.CameraPermissionManager
import com.journeyapps.barcodescanner.CaptureActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PembayaranQRISTheme {
                Surface {
                    HalamanUtama()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanUtama() {
    // Contoh data saldo awal
    val saldoAwal = Saldo(
        id = "12345",
        bankSumber = "BNI",
        idTransaksi = "ID12345678",
        namaMerchant = "MERCHANT MOCK TEST",
        nominalTransaksi = 50000
    )

    // State untuk menyimpan hasil scanning QR code
    var qrResult by remember { mutableStateOf("") }

    val context = LocalContext.current
    val cameraPermissionManager = CameraPermissionManager(context)

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            val scannerIntent = Intent(context, CaptureActivity::class.java)
            context.startActivity(scannerIntent)
        } else {
            qrResult = "Izin kamera tidak diberikan"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Saldo Dompet Kamu",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(android.graphics.Color.parseColor("#50FAF4")))
                .wrapContentSize()
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "Saldo Awal",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Rp ${saldoAwal.nominalTransaksi}",
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Bank Sumber: ${saldoAwal.bankSumber}",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "ID Transaksi: ${saldoAwal.idTransaksi}",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "Merchant: ${saldoAwal.namaMerchant}",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Button untuk meminta izin membuka kamera
                Button(
                    onClick = {
                        if (cameraPermissionManager.isCameraPermissionGranted()) {
                            val scannerIntent = Intent(context, CaptureActivity::class.java)
                            context.startActivity(scannerIntent)
                        } else {
                            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Buka Kamera untuk Scanning QR")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Hasil scanning QR code akan ditampilkan disini
                Text(
                    text = qrResult,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
