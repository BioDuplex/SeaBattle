import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import kotlin.math.*
import kotlin.random.Random

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop!"
        }) {
            Text(text)
        }
    }
}

//composable("main") { MainScreen(field = fieldPlayer, navController = navController)}
//composable("second") { SecondScreen(field = fieldPlayer,fieldComp, navController)}
//composable("last") { LastScreen(navController)}
//composable("lastComp") { LastCompScreen(navController)}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(width = 600.dp, height = 700.dp)
    ) {
//        App()
        val fieldComp = fieldCreate()
        val fieldPlayer = remember {
            mutableStateListOf(
                mutableStateListOf(Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue),
                mutableStateListOf(Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue),
                mutableStateListOf(Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue),
                mutableStateListOf(Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue),
                mutableStateListOf(Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue),
                mutableStateListOf(Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue),
                mutableStateListOf(Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue),
                mutableStateListOf(Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue),
                mutableStateListOf(Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue),
                mutableStateListOf(Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue)
            )
        }
        val fieldComp2 = remember {
            mutableListOf(
                MutableList<Int>(10){0},
                MutableList<Int>(10){0},
                MutableList<Int>(10){0},
                MutableList<Int>(10){0},
                MutableList<Int>(10){0},
                MutableList<Int>(10){0},
                MutableList<Int>(10){0},
                MutableList<Int>(10){0},
                MutableList<Int>(10){0},
                MutableList<Int>(10){0}
            )
        }
        val statut = remember { Statut() }
        Column(){
            when (statut.screenNumber) {
                0 -> MainScreen(statut = statut, onReadyClick = { statut.screenNumber++ })
                1 -> SecondScreen(statut = statut, fieldComp)
                2 -> LastScreen()
                3 -> LastCompScreen()

            }
//                Text(text = fieldComp.joinToString())
        }
    }
}

//fun iComp(
//statut: Statut,
//) {
//    var shot = false
//    var win = true
//    do {
//        val x = Random.nextInt(0, 10)
//        val y = Random.nextInt(0, 10)
//        if (statut.fieldPlayer[x][y] != Color.Red) {
//            shot = true
//            statut.fieldPlayer[x][y] = Color.Red
//        }
//    } while (!shot)
//    for (i in 0..9) {
//        for (j in 0..9) {
//            if (statut.fieldPlayer[i][j] == Color.Gray) win = false
//        }
//    }
//    if (win) statut.screenNumber = 3
//}


fun fieldCreate(): MutableList<MutableList<Int>> {
    val field = mutableListOf(
        MutableList(10) {0},
        MutableList(10) {0},
        MutableList(10) {0},
        MutableList(10) {0},
        MutableList(10) {0},
        MutableList(10) {0},
        MutableList(10) {0},
        MutableList(10) {0},
        MutableList(10) {0},
        MutableList(10) {0}
    )
    for (i in 3 downTo 0) {
        repeat(4 - i){
            var good = false
            a@ while (!good) {
                var a = true
                val x = Random.nextInt(0, 10)
                val y = Random.nextInt(0, 10)
                val orient = Random.nextInt(0, 2)
                when (orient) {
                    0 -> {
                        if (x + i < 10) {
                            for (j in 0..i) {
                                if (field[x + j][y] != 0) {
                                    a = false
                                }
                            }
                            if (a) {
                                for (j in 0..i) {
                                    field[x + j][y] = 1
                                }
                                good = true
                            }
                        }
                    }
                    else -> {
                        if (y + i < 10) {
                            for (j in 0..i) {
                                if (field[x][y + j] != 0) {
                                    a = false
                                }
                            }
                            if (a) {
                                for (j in 0..i) {
                                    field[x][y + j] = 1
                                }
                                good = true
                            }
                        }
                    }
                }
            }
            fieldShip(field)
        }
    }
    return field
}
fun fieldShip(field: MutableList<MutableList<Int>>) {
    for (i in 0..9) {
        for (j in 0..9) {
            if (field[i][j] == 1) {
                if (j + 1 != 10 && field[i][j + 1] == 0) field[i][j + 1] = 2
                if (j - 1 != -1 && field[i][j - 1] == 0) field[i][j - 1] = 2
                if (i + 1 != 10 && field[i + 1][j] == 0) field[i + 1][j] = 2
                if (i - 1 != -1 && field[i - 1][j] == 0) field[i -1 ][j] = 2
                if (j + 1 != 10 && i + 1 != 10 && field[i + 1][j + 1] == 0) field[i + 1][j + 1] = 2
                if (j + 1 != 10 && i - 1 != -1 && field[i - 1][j + 1] == 0) field[i - 1][j + 1] = 2
                if (j - 1 != -1 && i + 1 != 10 && field[i + 1][j - 1] == 0) field[i + 1][j - 1] = 2
                if (j - 1 != -1 && i - 1 != -1 && field[i - 1][j - 1] == 0) field[i - 1][j - 1] = 2
            }
        }
    }
}