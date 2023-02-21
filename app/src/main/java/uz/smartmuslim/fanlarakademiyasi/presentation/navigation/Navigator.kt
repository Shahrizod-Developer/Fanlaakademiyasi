package uz.smartmuslim.fanlarakademiyasi.presentation.navigation

import androidx.navigation.NavDirections

typealias Direction = NavDirections

interface Navigator {
    suspend fun navigateTo(direction: Direction)

}