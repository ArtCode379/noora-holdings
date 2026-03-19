package shop.nooraholdings.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import shop.nooraholdings.app.ui.composable.approot.AppRoot
import shop.nooraholdings.app.ui.theme.ProductAppNORHDTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductAppNORHDTheme {
                AppRoot()
            }
        }
    }
}