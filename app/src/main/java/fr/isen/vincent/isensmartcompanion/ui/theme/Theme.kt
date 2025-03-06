package fr.isen.vincent.isensmartcompanion.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import fr.isen.vincent.isensmartcompanion.R


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun ISENSmartCompanionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    // Couleurs du mode clair
    val lightColors = lightColorScheme(
        primary = colorResource(id = R.color.light_primary),
        secondary = colorResource(id = R.color.light_secondary),
        //surface = colorResource(id = R.color.light_surface),
        surfaceVariant = colorResource(id = R.color.light_surface_variant),
        onSurface = colorResource(id = R.color.light_on_surface),
        onSurfaceVariant = colorResource(id = R.color.light_on_surface_variant),

        background = colorResource(id = R.color.light_background),
        surface = colorResource(id = R.color.light_bottom_nav)
    )

    // Couleurs du mode sombre
    val darkColors = darkColorScheme(
        primary = colorResource(id = R.color.dark_primary),
        secondary = colorResource(id = R.color.dark_secondary),
        //surface = colorResource(id = R.color.dark_surface),
        surfaceVariant = colorResource(id = R.color.dark_surface_variant),
        onSurface = colorResource(id = R.color.dark_on_surface),
        onSurfaceVariant = colorResource(id = R.color.dark_on_surface_variant),

        background = colorResource(id = R.color.dark_background),
        surface = colorResource(id = R.color.dark_bottom_nav)
    )

    val colors = if (darkTheme) darkColors else lightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        //shapes = Shapes,
        content = content
    )
}