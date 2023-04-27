
package org.zotero.android.screens.sortpicker

import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import org.zotero.android.uicomponents.navigation.ZoteroNavHost
import org.zotero.android.uicomponents.singlepicker.SinglePickerScreen

@Composable
internal fun SortPickerNavigation() {
    val navController = rememberAnimatedNavController()
    val dispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val navigation = remember(navController) {
        SortPickerNavigation(navController, dispatcher)
    }

    ZoteroNavHost(
        navController = navController,
        startDestination = SortPickerDestinations.SORT_PICKER,
        modifier = Modifier.navigationBarsPadding(), // do not draw behind nav bar
    ) {
        sortPickerScreen(
            onBack = navigation::onBack,
            navigateToSinglePickerScreen = navigation::toSinglePickerScreen,
        )
        singlePickerScreen(onBack = navigation::onBack)
    }
}

private fun NavGraphBuilder.sortPickerScreen(
    navigateToSinglePickerScreen: () -> Unit,
    onBack: () -> Unit,
) {
    composable(
        route = SortPickerDestinations.SORT_PICKER,
        arguments = listOf(),
    ) {
        SortPickerScreen(
            onBack = onBack,
            navigateToSinglePickerScreen = navigateToSinglePickerScreen,
        )
    }
}

private fun NavGraphBuilder.singlePickerScreen(
    onBack: () -> Unit,
) {
    composable(
        route = SortPickerDestinations.SINGLE_PICKER_SCREEN,
        arguments = listOf(),
    ) {
        SinglePickerScreen(onCloseClicked = onBack)
    }
}

private object SortPickerDestinations {
    const val SORT_PICKER = "sortPicker"
    const val SINGLE_PICKER_SCREEN = "singlePickerScreen"
}

@SuppressWarnings("UseDataClass")
private class SortPickerNavigation(
    private val navController: NavHostController,
    private val onBackPressedDispatcher: OnBackPressedDispatcher?,
) {
    fun onBack() = onBackPressedDispatcher?.onBackPressed()

    fun toSortPicker() {
        navController.navigate(SortPickerDestinations.SORT_PICKER)
    }

    fun toSinglePickerScreen() {
        navController.navigate(SortPickerDestinations.SINGLE_PICKER_SCREEN)
    }
}
