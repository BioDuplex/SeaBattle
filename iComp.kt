import androidx.compose.ui.graphics.Color
import kotlin.random.Random

fun iComp(
    statut: Statut,
) {
    var shot = false
    var win = true
    do {
        var x = Random.nextInt(0, 10)
        var y = Random.nextInt(0, 10)
        if (statut.difficulty == DIFFICULTY.EASY && statut.fieldPlayerMatrix[x][y] == 0) {
            if (statut.fieldPlayer[x][y] != Color.Gray) shot = true
            statut.fieldPlayer[x][y] = Color.Red
            statut.fieldPlayerMatrix[x][y] = 1
        }
        if (statut.difficulty == DIFFICULTY.NORMAL && statut.fieldPlayerMatrix[x][y] == 0) {
            if (statut.fieldPlayer[x][y] != Color.Gray) shot = true
            else {
                if (x < 9 && y < 9) statut.fieldPlayerMatrix[x + 1][y + 1] = 3
                if (x > 0 && y < 9) statut.fieldPlayerMatrix[x - 1][y + 1] = 3
                if (x > 0 && y > 0) statut.fieldPlayerMatrix[x - 1][y - 1] = 3
                if (x < 9 && y > 0) statut.fieldPlayerMatrix[x + 1][y - 1] = 3
            }
            statut.fieldPlayer[x][y] = Color.Red
            statut.fieldPlayerMatrix[x][y] = 1
        }
        /** for matrix: 0 - no info, 1 - shot, 3 - here no ship**/
        if (statut.difficulty == DIFFICULTY.HARD && statut.fieldPlayerMatrix[x][y] == 0) {
            if (statut.huntModeOn) {
                if (statut.lastShot.x > 0 && statut.fieldPlayerMatrix[statut.lastShot.x - 1][statut.lastShot.y] == 0) {
                    x = statut.lastShot.x - 1
                    y = statut.lastShot.y
                }else if (statut.lastShot.x < 9 && statut.fieldPlayerMatrix[statut.lastShot.x + 1][statut.lastShot.y] == 0) {
                    x = statut.lastShot.x + 1
                    y = statut.lastShot.y
                }else if (statut.lastShot.y > 0 && statut.fieldPlayerMatrix[statut.lastShot.x][statut.lastShot.y - 1] == 0) {
                    x = statut.lastShot.x
                    y = statut.lastShot.y - 1
                }else if (statut.lastShot.y < 9 && statut.fieldPlayerMatrix[statut.lastShot.x][statut.lastShot.y + 1] == 0) {
                    x = statut.lastShot.x
                    y = statut.lastShot.y + 1
                }else if (
                    statut.huntFor3 && statut.lastShot.x > 1
                    && statut.fieldPlayerMatrix[statut.lastShot.x - 1][statut.lastShot.y] == 1
                    && statut.fieldPlayerMatrix[statut.lastShot.x - 2][statut.lastShot.y] == 0
                    ) {
                    x = statut.lastShot.x - 2
                    y = statut.lastShot.y
                }else if (
                    statut.huntFor3 && statut.lastShot.x < 8
                    && statut.fieldPlayerMatrix[statut.lastShot.x + 1][statut.lastShot.y] == 1
                    && statut.fieldPlayerMatrix[statut.lastShot.x + 2][statut.lastShot.y] == 0
                    ) {
                    x = statut.lastShot.x + 2
                    y = statut.lastShot.y
                }else if (
                    statut.huntFor3 && statut.lastShot.y > 1
                    && statut.fieldPlayerMatrix[statut.lastShot.x][statut.lastShot.y - 1] == 1
                    && statut.fieldPlayerMatrix[statut.lastShot.x][statut.lastShot.y - 2] == 0
                    ) {
                    x = statut.lastShot.x
                    y = statut.lastShot.y - 2
                }else if (
                    statut.huntFor3 && statut.lastShot.y < 8
                    && statut.fieldPlayerMatrix[statut.lastShot.x][statut.lastShot.y + 1] == 1
                    && statut.fieldPlayerMatrix[statut.lastShot.x][statut.lastShot.y + 2] == 0
                    ) {
                    x = statut.lastShot.x
                    y = statut.lastShot.y + 2
                }else if (
                    statut.huntForBattleship && statut.lastShot.x > 2
                    && statut.fieldPlayerMatrix[statut.lastShot.x - 2][statut.lastShot.y] == 1
                    && statut.fieldPlayerMatrix[statut.lastShot.x - 3][statut.lastShot.y] == 0
                ) {
                    x = statut.lastShot.x - 3
                    y = statut.lastShot.y
                }else if (
                    statut.huntFor3 && statut.lastShot.x < 7
                    && statut.fieldPlayerMatrix[statut.lastShot.x + 2][statut.lastShot.y] == 1
                    && statut.fieldPlayerMatrix[statut.lastShot.x + 3][statut.lastShot.y] == 0
                ) {
                    x = statut.lastShot.x + 3
                    y = statut.lastShot.y
                }else if (
                    statut.huntFor3 && statut.lastShot.y > 2
                    && statut.fieldPlayerMatrix[statut.lastShot.x][statut.lastShot.y - 2] == 1
                    && statut.fieldPlayerMatrix[statut.lastShot.x][statut.lastShot.y - 3] == 0
                ) {
                    x = statut.lastShot.x
                    y = statut.lastShot.y - 3
                }else if (
                    statut.huntFor3 && statut.lastShot.y < 7
                    && statut.fieldPlayerMatrix[statut.lastShot.x][statut.lastShot.y + 2] == 1
                    && statut.fieldPlayerMatrix[statut.lastShot.x][statut.lastShot.y + 3] == 0
                ) {
                    x = statut.lastShot.x
                    y = statut.lastShot.y + 3
                }
            }
            if (statut.fieldPlayer[x][y] != Color.Gray) {
                statut.fieldPlayerMatrix[x][y] = 3
                shot = true
            }
            else {
                statut.fieldPlayerMatrix[x][y] = 1
                if (statut.fieldPlayerSecretMatrix[x][y] == 1) {
                    statut.targetZone(x = x, y = y)
                } else if (statut.huntModeOn && statut.fieldPlayerSecretMatrix[x][y] == 2) {
                    statut.targetZone(x = statut.lastShot.x, y = statut.lastShot.y)
                    statut.targetZone(x = x, y = y)
                    statut.huntModeOn = false
                } else if (statut.huntModeOn && !statut.huntFor3) {
                    statut.huntFor3 = true
                    statut.targetHorizontalShip = statut.lastShot.x - x != 0
                    statut.predLastShot = statut.lastShot
                    statut.lastShot = LastShot(x, y)
                } else if (statut.huntModeOn && statut.huntFor3 && statut.fieldPlayerSecretMatrix[x][y] == 3) {
                    statut.targetZone(x = statut.lastShot.x, y = statut.lastShot.y)
                    statut.targetZone(x = statut.predLastShot.x, y = statut.predLastShot.y)
                    statut.targetZone(x = x, y = y)
                    statut.huntModeOn = false
                    statut.huntFor3 = false
                } else if (statut.huntModeOn && statut.huntFor3 && !statut.huntForBattleship) {
                    statut.huntForBattleship = true
                    statut.battlelastShot = statut.predLastShot
                    statut.predLastShot = statut.lastShot
                    statut.lastShot = LastShot(x, y)
                } else if (statut.huntModeOn && statut.huntFor3 && statut.huntForBattleship && statut.fieldPlayerSecretMatrix[x][y] == 4) {
                    statut.targetZone(x = statut.lastShot.x, y = statut.lastShot.y)
                    statut.targetZone(x = statut.predLastShot.x, y = statut.predLastShot.y)
                    statut.targetZone(x = statut.battlelastShot.x, y = statut.battlelastShot.y)
                    statut.targetZone(x = x, y = y)
                    statut.huntForBattleship = false
                    statut.huntModeOn = false
                    statut.huntFor3 = false
                } else {
                    statut.huntModeOn = true
                    statut.lastShot = LastShot(x, y)
                }
                if (x < 9 && y < 9) statut.fieldPlayerMatrix[x + 1][y + 1] = 3
                if (x > 0 && y < 9) statut.fieldPlayerMatrix[x - 1][y + 1] = 3
                if (x > 0 && y > 0) statut.fieldPlayerMatrix[x - 1][y - 1] = 3
                if (x < 9 && y > 0) statut.fieldPlayerMatrix[x + 1][y - 1] = 3
//                if (x < 9 && statut.fieldPlayerMatrix[x + 1][y] == 1) {
//                    if (y < 9) {
//                        statut.fieldPlayerMatrix[x][y + 1] = 3
//                        statut.fieldPlayerMatrix[x + 1][y + 1] = 3
//                    }
//                    if (y > 0) {
//                        statut.fieldPlayerMatrix[x][y - 1] = 3
//                        statut.fieldPlayerMatrix[x + 1][y - 1] = 3
//                    }
//                }
//                if (x > 0 && statut.fieldPlayerMatrix[x - 1][y] == 1) {
//                    if (y < 9) {
//                        statut.fieldPlayerMatrix[x][y + 1] = 3
//                        statut.fieldPlayerMatrix[x - 1][y + 1] = 3
//                    }
//                    if (y > 0) {
//                        statut.fieldPlayerMatrix[x][y - 1] = 3
//                        statut.fieldPlayerMatrix[x - 1][y - 1] = 3
//                    }
//                }
//                if (y < 9 && statut.fieldPlayerMatrix[x][y + 1] == 1) {
//                    if (x < 9) {
//                        statut.fieldPlayerMatrix[x + 1][y] = 3
//                        statut.fieldPlayerMatrix[x + 1][y + 1] = 3
//                    }
//                    if (x > 0) {
//                        statut.fieldPlayerMatrix[x - 1][y] = 3
//                        statut.fieldPlayerMatrix[x - 1][y + 1] = 3
//                    }
//                }
//                if (y > 0 && statut.fieldPlayerMatrix[x][y - 1] == 1) {
//                    if (x < 9) {
//                        statut.fieldPlayerMatrix[x + 1][y] = 3
//                        statut.fieldPlayerMatrix[x + 1][y - 1] = 3
//                    }
//                    if (x > 0) {
//                        statut.fieldPlayerMatrix[x - 1][y] = 3
//                        statut.fieldPlayerMatrix[x - 1][y - 1] = 3
//                    }
//                }

            }
            statut.fieldPlayer[x][y] = Color.Red
        }

    } while (!shot)
    println(statut.fieldPlayerMatrix.joinToString())
    for (i in 0..9) {
        for (j in 0..9) {
            if (statut.fieldPlayer[i][j] == Color.Gray) {
                win = false
            }
        }
    }
    if (win) statut.screenNumber = 3
}