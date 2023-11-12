import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun MainScreen(
    statut: Statut,
//    field: SnapshotStateList<SnapshotStateList<Color>>,
//               navController: NavController
    onReadyClick: () -> Unit
) {
    var offsetXBig by remember { mutableStateOf(0f) }
    var offsetYBig by remember { mutableStateOf(0f) }
    Box(modifier = Modifier){
        Column() {
            Box() {
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    for (i in 0..9) {
                        Column(verticalArrangement = Arrangement.SpaceEvenly) {
                            for (j in 0..9) {
                                Card(
                                    shape = RoundedCornerShape(4.dp),
                                    modifier = Modifier.padding(1.dp)
                                ) {
//                                var col = remember{ mutableStateOf(if (field[i][j] == 0)Color.Blue else Color.Gray) }
//                                var col = remember{ mutableStateOf(field[i][j]) }
                                    Box(
                                        modifier = Modifier
                                            .size(30.dp)
                                            .background(statut.fieldPlayer[i][j])
                                            .clickable(false) {
                                                if (statut.fieldPlayer[i][j] == Color.Gray) statut.fieldPlayer[i][j] = Color.Blue
                                                else statut.fieldPlayer[i][j] = Color.Gray
                                            }
                                    ) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Text(
                statut.warning,
                color = Color.Red
                )
            Button(onClick = {
//            navController.navigate("second")
                if (statut.readyCheck0()) {
//                    println(statut.readyCheck())
                    if (!statut.readyCheck()) statut.warning = "Корабли не должны касаться друг друга даже углами"
                        else statut.onReadyClick()

                }
                else statut.warning = "Не все корабли на поле боя"
            }) {
                Text(text = "Ready!")
            }
            val radioOptions = listOf("EASY", "NORMAL", "HARD")
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[1] ) }
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = {
                                statut.difficulty = when (text) {
                                    "EASY" -> { DIFFICULTY.EASY }
                                    "NORMAL" -> DIFFICULTY.NORMAL
                                    "HARD" -> DIFFICULTY.HARD
                                    else -> DIFFICULTY.HARD
                                }
                                onOptionSelected(text)
                            }
                        )
                        .padding(horizontal = 16.dp)
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) }
                    )
                    Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                        Text(
                            text = text,
//                        style = MaterialTheme.typography.body1.merge(),
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        }
        oneShip(i = 4) { statut.shipPositioning(0, it) }
        Box {
            oneShip(i = 3) { statut.shipPositioning(1, it) }
            oneShip(i = 3) { statut.shipPositioning(2, it) }
        }
        Box {
             oneShip(i = 2) { statut.shipPositioning(3, it) }
             oneShip(i = 2) { statut.shipPositioning(4, it) }
             oneShip(i = 2) { statut.shipPositioning(5, it) }
        }
        Box {
            oneShip(i = 1) { statut.shipPositioning(6, it) }
            oneShip(i = 1) { statut.shipPositioning(7, it) }
            oneShip(i = 1) { statut.shipPositioning(8, it) }
            oneShip(i = 1) { statut.shipPositioning(9, it) }
        }
    }
}
@Composable
fun ship() {
    Card( elevation = 5.dp,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.padding(1.dp)
//            .draggable(orientation = )
    ) {
        Box(
            Modifier
                .background(Color.Green)
                .size(30.dp))
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun oneShip(i: Int, onPosition: (ShipPosition) -> Unit): List<Int> {
    var offsetX by remember { mutableStateOf(400f) }
    var offsetY by remember { mutableStateOf(0f + i * 35) }
    var hoverColorMine by remember { mutableStateOf(false) }
    var angle by remember { mutableStateOf(false) }
    var canClick by remember { mutableStateOf(false) }
    Box (
        Modifier
            .absoluteOffset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .padding(2.dp)
            .onClick(enabled = canClick, matcher = PointerMatcher.mouse(PointerButton.Secondary), onClick = {
                angle = !angle
                if (
                    offsetY.toInt() !in -15..300 && !angle
                    || offsetY.toInt() > (210 + (4 - i) * 35) && angle
                    || offsetX.toInt() > 300 && angle
                    || offsetX.toInt() !in -15..(210 + (4 - i) * 35) && !angle
                ) {
                    offsetX = 400F
                    offsetY = 0f + i * 35
                    angle = false
                    canClick = false
                } else {
                    when {
                        offsetX.toInt() < 15 -> {
                            offsetX = 0F
                        }
                        offsetX.toInt() > 270 && angle -> {
                            offsetX = 288F
                        }
                        offsetX.toInt() > (175 + (4 - i) * 32) && !angle -> {
                            offsetX = (192 + (4 - i) * 32).toFloat()
                        }
                        else -> {
                            offsetX = (((offsetX.toInt() + 16) / 32) * 32).toFloat()
                        }

                    }
                    when {
                        offsetY.toInt() < 15 -> {
                            offsetY = 0F
                        }
                        offsetY.toInt() > (175 + (4 - i) * 32) && angle -> {
                            offsetY = (192 + (4 - i) * 32).toFloat()
                        }
                        offsetY.toInt() > 270 && !angle -> {
                            offsetY = 288F
                        }
                        else -> { offsetY = ((offsetY.toInt() + 16) / 32 * 32).toFloat() }
                    }
                    canClick = true
                }

                onPosition(ShipPosition(x = offsetX.toInt() / 32, y = offsetY.toInt() / 32, rotate = angle, length = i))
            })
//            .rotate(if (angle) 90F else 0F)
//            .background( if (hoverColorMine) Color.Red else Color.Cyan)
//            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }


//            .draggable(
//                orientation = Orientation.Vertical,
//                state = rememberDraggableState { delta ->
//                    offsetX += delta
//                    offsetY += delta
//                }
//            )
            .onPointerEvent(PointerEventType.Enter) { hoverColorMine = true}
//            .onPointerEvent(PointerEventType.Move) { canClick = true}
            .onPointerEvent(PointerEventType.Exit) { hoverColorMine = false}
            .onPointerEvent(PointerEventType.Release) {
                if (
                    offsetY.toInt() !in -15..300 && !angle
                    || offsetY.toInt() > (210 + (4 - i) * 35) && angle
                    || offsetX.toInt() > 300 && angle
                    || offsetX.toInt() !in -15..(210 + (4 - i) * 35) && !angle
                    ) {
                    offsetX = 400F
                    offsetY = 0f + i * 35
                    angle = false
                    canClick = false
//                    onPosition(ShipPosition(x = -1, y = -1, rotate = false, length = 1))
                } else {
                    when {
                        offsetX.toInt() < 15 -> {
                            offsetX = 0F
                        }
                        offsetX.toInt() > 270 && angle -> {
                            offsetX = 288F
                        }
                        offsetX.toInt() > (175 + (4 - i) * 32) && !angle -> {
                            offsetX = (192 + (4 - i) * 32).toFloat()
                        }
                        else -> {
                            offsetX = (((offsetX.toInt() + 16) / 32) * 32).toFloat()
                        }

                    }
                    when {
                        offsetY.toInt() < 15 -> {
                            offsetY = 0F
                        }
                        offsetY.toInt() > (175 + (4 - i) * 32) && angle -> {
                            offsetY = (192 + (4 - i) * 32).toFloat()
                        }
                        offsetY.toInt() > 270 && !angle -> {
                            offsetY = 288F
                        }
                        else -> { offsetY = ((offsetY.toInt() + 16) / 32 * 32).toFloat() }
                    }
                    canClick = true
                }
                onPosition(ShipPosition(x = offsetX.toInt() / 32, y = offsetY.toInt() / 32, rotate = angle, length = i))
            }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
//                awaitPointerEventScope {
//                    val event = awaitPointerEvent(PointerEventPass.Initial)
//                    if (event.buttons.isSecondaryPressed) {
//                        angle = !angle
//                    }
//                }
            }
//            .onPointerEvent(eventType = PointerEventType.Release,)
    ) {
        if (!angle){

            Row() {
                repeat(i) {
                    ship()
                }
                Column() {
//                    Text(text = offsetX.toInt().toString())
//                    Text(text = offsetY.toInt().toString())
                }
            }
        } else {
            Column() {
                repeat(i) {
                    ship()
                }
                Column() {
//                    Text(text = offsetX.toInt().toString())
//                    Text(text = offsetY.toInt().toString())
                }
            }
        }
        Column (){
            Text(text = offsetX.toInt().toString())
            Text(text = offsetY.toInt().toString())
        }
    }

    return listOf(offsetX.toInt(), offsetY.toInt())
}
@Composable
fun twoShip() {
    ship()
    ship()
}
/**
fun onOffSet(i: Int, angle: Boolean, x: Float, y: Float, canClick: (Boolean) -> Unit, changeAngle: (Boolean) -> Unit): (Float, Float, Boolean) {
    var offsetX = x
    var offsetY = y
    if (
        offsetY.toInt() !in -15..300 && !angle
        || offsetY.toInt() > (210 + (4 - i) * 35) && angle
        || offsetX.toInt() > 300 && angle
        || offsetX.toInt() !in -15..(210 + (4 - i) * 35) && !angle
    ) {
        offsetX = 400F
        offsetY = 0f + i * 35
        changeAngle(false)
        canClick(false)
    } else {
        when {
            offsetX.toInt() < 15 -> {
                offsetX = 0F
            }
            offsetX.toInt() > 270 && angle -> {
                offsetX = 288F
            }
            offsetX.toInt() > (175 + (4 - i) * 32) && !angle -> {
                offsetX = (192 + (4 - i) * 32).toFloat()
            }
            else -> {
                offsetX = (((offsetX.toInt() + 16) / 32) * 32).toFloat()
            }

        }
        when {
            offsetY.toInt() < 15 -> {
                offsetY = 0F
            }
            offsetY.toInt() > (175 + (4 - i) * 32) && angle -> {
                offsetY = (192 + (4 - i) * 32).toFloat()
            }
            offsetY.toInt() > 270 && !angle -> {
                offsetY = 288F
            }
            else -> { offsetY = ((offsetY.toInt() + 16) / 32 * 32).toFloat() }
        }
        canClick(true)
    }
    return (offsetX, offsetY, angle)
}
**/