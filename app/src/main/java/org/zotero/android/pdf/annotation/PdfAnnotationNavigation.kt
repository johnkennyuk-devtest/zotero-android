package org.zotero.android.pdf.annotation

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.zotero.android.architecture.navigation.ZoteroNavigation
import org.zotero.android.screens.tagpicker.TagPickerScreen
import org.zotero.android.uicomponents.navigation.ZoteroNavHost

@Composable
internal fun PdfAnnotationNavigation() {
    val navController = rememberNavController()
    val dispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val navigation = remember(navController) {
        ZoteroNavigation(navController, dispatcher)
    }
    ZoteroNavHost(
        navController = navController,
        startDestination = PdfAnnotationDestinatiosn.PDF_ANNOTATION_SCREEN,
        modifier = Modifier.navigationBarsPadding(), // do not draw behind nav bar
    ) {
        pdfAnnotationNavScreens(navigation = navigation)
    }
}

internal fun NavGraphBuilder.pdfAnnotationNavScreens(
    navigation: ZoteroNavigation,
) {
    pdfAnnotationScreen(onBack = navigation::onBack, navigateToTagPicker = navigation::toTagPicker)
    tagPickerScreen(onBack = navigation::onBack)
}

private fun NavGraphBuilder.pdfAnnotationScreen(
    onBack: () -> Unit,
    navigateToTagPicker: () -> Unit,
) {
    composable(
        route = PdfAnnotationDestinatiosn.PDF_ANNOTATION_SCREEN,
        arguments = listOf(),
    ) {
        PdfAnnotationScreen(onBack = onBack, navigateToTagPicker = navigateToTagPicker)
    }
}

private fun NavGraphBuilder.tagPickerScreen(
    onBack: () -> Unit,
) {
    composable(
        route = PdfAnnotationDestinatiosn.TAG_PICKER_SCREEN,
    ) {
        TagPickerScreen(onBack = onBack)
    }
}

private object PdfAnnotationDestinatiosn {
    const val PDF_ANNOTATION_SCREEN = "pdfAnnotationScreen"
    const val TAG_PICKER_SCREEN = "tagPickerScreen"
}

fun ZoteroNavigation.toPdfAnnotationScreen() {
    navController.navigate(PdfAnnotationDestinatiosn.PDF_ANNOTATION_SCREEN)
}

private fun ZoteroNavigation.toTagPicker() {
    navController.navigate(PdfAnnotationDestinatiosn.TAG_PICKER_SCREEN)
}
