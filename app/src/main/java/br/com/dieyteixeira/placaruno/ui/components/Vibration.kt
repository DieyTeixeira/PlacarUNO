package br.com.dieyteixeira.placaruno.ui.components

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi

@SuppressLint("MissingPermission")
@RequiresApi(Build.VERSION_CODES.O)
fun vibration(context: Context) {
    // Obter o serviço de Vibrator
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    // Verificar se o dispositivo suporta vibração
    if (vibrator.hasVibrator()) {
        // Definir um padrão de vibração (opcional)
        val pattern = longArrayOf(0, 400) // 0 ms de espera, 400 ms de vibração

        // Definir o efeito de vibração
        val vibrationEffect = VibrationEffect.createWaveform(pattern, -1)

        // Vibrar com o efeito especificado
        vibrator.vibrate(vibrationEffect)
    }
}