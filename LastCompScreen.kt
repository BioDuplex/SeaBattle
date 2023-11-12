import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LastCompScreen(
//    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red),
        contentAlignment = Alignment.Center) {
        Card(
            modifier = Modifier
                .background(Color.Red),
            shape = RoundedCornerShape(3.dp)
        ) {
            Text(
                text = "GAME OVER!\n Comp Won!",
                fontSize = 24.sp,
                fontFamily = FontFamily.Cursive
            )
        }
    }
}