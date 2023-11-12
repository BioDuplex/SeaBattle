import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SecondScreen(
    statut: Statut,
//    field: SnapshotStateList<SnapshotStateList<Color>>,
    fieldComp: MutableList<MutableList<Int>>,
//                 navController: NavController
) {
    Column(verticalArrangement = Arrangement.Center) {
        Box() {
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                for (i in 0..9) {
                    Column(verticalArrangement = Arrangement.SpaceEvenly) {
                        for (j in 0..9) {
                            Card(
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier.padding(1.dp)
                            ) {
                                var col = remember{ mutableStateOf(
                                    if (statut.fieldComp[i][j] == 0 || statut.fieldComp[i][j] == 1|| statut.fieldComp[i][j] == 2) Color.Cyan
                                    else if (statut.fieldComp[i][j] == 3) Color.Blue
                                    else if (statut.fieldComp[i][j] == 4) Color.Gray
                                    else { Color.Gray}) }
                                var clicked = remember { mutableStateOf(true) }
                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .background(col.value)
                                        .clickable (enabled = clicked.value){
                                            var secondChanse = false
                                            if (statut.fieldComp[i][j] == 0 || statut.fieldComp[i][j] == 3|| statut.fieldComp[i][j] == 2) {
                                                col.value = Color.Blue
                                                statut.fieldComp[i][j] = 3
                                            } else {
                                                col.value = Color.Gray
                                                statut.fieldComp[i][j] = 4
                                                secondChanse = true
                                            }
                                            var win = true
                                            for (i in 0..9) {
                                                for (j in 0..9) {
                                                    if (statut.fieldComp[i][j] == 1) win = false
                                                }
                                            }
//                                            if (win) navController.navigate("last")
                                            if (win) statut.screenNumber = 2
                                            clicked.value = !clicked.value
                                            if (!secondChanse) iComp(statut = statut)
//                                            iComp(fieldPlayer = field, navController)
//                                            field[x][y] = 5
                                        }
                                ) {
//                                    Text(text = fieldComp[i][j].toString())
                                }
                            }
                        }
                    }
                }
            }
        }
        Box() {
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                for (i in 0..9) {
                    Column(verticalArrangement = Arrangement.SpaceEvenly) {
                        for (j in 0..9) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .background(statut.fieldPlayer[i][j])
                            ) {
                                Text(statut.fieldPlayerSecretMatrix[i][j].toString())
                            }
                        }
                    }
                }
            }
        }

    }

}