package com.pemmob1.h1d024061.responsi.ui.state

import java.io.IOException
import retrofit2.HttpException

sealed interface UiState<out T> {

    data object Loading : UiState<Nothing>

    data class Success<T>(val data: T) : UiState<T>

    data class Error(val message: String) : UiState<Nothing>
}

fun Throwable.toUserMessage(): String = when (this) {
    is IOException -> "Tidak dapat terhubung ke internet. Periksa koneksi lalu coba lagi."
    is HttpException -> "Server Digi-API menolak permintaan (kode ${code()}). Coba lagi nanti."
    else -> "Terjadi kesalahan saat memuat data Digimon."
}
