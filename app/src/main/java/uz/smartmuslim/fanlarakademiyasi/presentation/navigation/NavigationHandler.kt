package uz.smartmuslim.fanlarakademiyasi.presentation.navigation

import androidx.navigation.NavController
import kotlinx.coroutines.flow.Flow

typealias NavigationArgs = NavController.() -> Unit

interface NavigationHandler {
    val navigationStack: Flow<NavigationArgs>
}