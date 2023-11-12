import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

class Statut {
    var difficulty by mutableStateOf(DIFFICULTY.NORMAL)
    var lastShot by mutableStateOf(LastShot(-1, -1))
    var predLastShot by mutableStateOf(LastShot(-1, -1))
    var battlelastShot by mutableStateOf(LastShot(-1, -1)) /**first shot to battleship**/
    var huntModeOn by mutableStateOf(false)
    var huntFor3 by mutableStateOf(false)
    var huntForBattleship by mutableStateOf(false)
    var targetHorizontalShip by mutableStateOf(true)
    var warning by mutableStateOf("")
    var screenNumber by mutableStateOf(0)
    var playersShipPosition by mutableStateOf(MutableList(10) {ShipPosition()})
    var fieldPlayerMatrix by mutableStateOf(MutableList(10) { MutableList(10) {0} })
    var fieldPlayerSecretMatrix by mutableStateOf(MutableList(10) { MutableList(10) {0} })
    var fieldPlayer = mutableStateListOf(
        mutableStateListOf(
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue),
        mutableStateListOf(
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue),
        mutableStateListOf(
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue),
        mutableStateListOf(
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue),
        mutableStateListOf(
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue),
        mutableStateListOf(
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue),
        mutableStateListOf(
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue),
        mutableStateListOf(
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue),
        mutableStateListOf(
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue),
        mutableStateListOf(
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue,
            Color.Blue)
    )
    var fieldComp = fieldCreate()
    fun onReadyClick() {
        allShipPositioning(true)
        screenNumber  ++
    }
    fun shipPositioning(
        i: Int,
        position: ShipPosition
    ) {
        playersShipPosition[i] = position
    }
    private fun allShipPositioning(finalCheck: Boolean = false) {
        fieldPlayerSecretMatrix = MutableList(10) { MutableList(10) {0} }
        for (i in 0..9) {
            for (j in 0 until playersShipPosition[i].length){
                if (playersShipPosition[i].rotate) {
                    if (finalCheck) fieldPlayer[playersShipPosition[i].x][playersShipPosition[i].y + j] = Color.Gray
                    fieldPlayerSecretMatrix[playersShipPosition[i].x][playersShipPosition[i].y + j] = playersShipPosition[i].length
                }
                else {
                    if (finalCheck) fieldPlayer[playersShipPosition[i].x + j][playersShipPosition[i].y] = Color.Gray
                    fieldPlayerSecretMatrix[playersShipPosition[i].x + j][playersShipPosition[i].y] = playersShipPosition[i].length
                }
            }
        }
    }
    fun targetZone(x: Int, y: Int) {
        for (i in -1..1){
            for (j in -1..1) {
                try {
                    if (fieldPlayerMatrix[x + i][y + j] == 0) fieldPlayerMatrix[x + i][y + j] = 3
                } catch (e: Exception) {
                    println("booms")
                }
            }
        }
        println("barash x:$x y:$y")
    }
    fun readyCheck0(): Boolean {
        print("chek0:")
        var a = true
        for (i in 0..9) {
            print(playersShipPosition[i].x)
            print(",")
            if (playersShipPosition[i].x == -1 || playersShipPosition[i].x == 12) {
                a = false
            }
        }
        println(a)
        return a
    }

    fun readyCheck(): Boolean {
        print("readyCheck")
        allShipPositioning()
        var counter = 0
        var counterShips = 0
        for (i in 0..9) {
            for (j in 0..9) {
                if (fieldPlayerSecretMatrix[i][j] != 0) {
                    if (j + 1 != 10 && fieldPlayerSecretMatrix[i][j + 1] != 0) counter++
                    if (j - 1 != -1 && fieldPlayerSecretMatrix[i][j - 1] != 0) counter++
                    if (i + 1 != 10 && fieldPlayerSecretMatrix[i + 1][j] != 0) counter++
                    if (i - 1 != -1 && fieldPlayerSecretMatrix[i - 1][j] != 0) counter++
                    if (j + 1 != 10 && i + 1 != 10 && fieldPlayerSecretMatrix[i + 1][j + 1] != 0) counter++
                    if (j + 1 != 10 && i - 1 != -1 && fieldPlayerSecretMatrix[i - 1][j + 1] != 0) counter++
                    if (j - 1 != -1 && i + 1 != 10 && fieldPlayerSecretMatrix[i + 1][j - 1] != 0) counter++
                    if (j - 1 != -1 && i - 1 != -1 && fieldPlayerSecretMatrix[i - 1][j - 1] != 0) counter++
                    counterShips ++
                }
            }
        }
        println(counter)
        return counter == 20 && counterShips == 20
    }
}