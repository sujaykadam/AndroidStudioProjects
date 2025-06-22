package com.example.sujayscolorpicker

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ColorPicker(){
    var red = rememberSaveable { mutableFloatStateOf(0f) }
    var blue = rememberSaveable { mutableFloatStateOf(0f) }
    var green = rememberSaveable { mutableFloatStateOf(0f) }

    val hex = String.format("#%02X%02X%02X", red.floatValue.toInt(), blue.floatValue.toInt(), green.floatValue.toInt())
    val color = Color(red.floatValue/255f, green.floatValue/255f, blue.floatValue/255f)
    val context =  LocalContext.current
    val clipBoard = LocalClipboardManager.current
    val orientation = LocalConfiguration.current.orientation
    val isPortrait = orientation == Configuration.ORIENTATION_PORTRAIT

    Box(modifier = Modifier.fillMaxSize()){

        if (isPortrait){
            Column(
                modifier = Modifier
                    .padding((16.dp))
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(48.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(color = color, shape = RoundedCornerShape(16.dp))
                )
                Spacer(Modifier.height(64.dp))
                RgbSlider(
                    label = "Red",
                    value = red.floatValue,
                    {red.floatValue = it}
                )
                RgbSlider(
                    label = "Green",
                    value = green.floatValue,
                    {green.floatValue = it}
                )
                RgbSlider(
                    label = "Blue",
                    value = blue.floatValue,
                    {blue.floatValue = it}
                )
                Spacer(Modifier.height(48.dp))

                Text(
                    text = hex,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                )

                Button(onClick = {
                    clipBoard.setText(AnnotatedString(hex))
                    Toast.makeText(context, "Copied Color to ClipBoard", Toast.LENGTH_LONG).show()
                }) {
                    Text("Copy Color")
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .padding((16.dp))
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(48.dp))
                Row {
                        Box(
                            modifier = Modifier
                                .width(180.dp)
                                .height(180.dp)
                                .background(color = color, shape = RoundedCornerShape(16.dp)),
                        )
                    Spacer(Modifier.height(64.dp))
                    Column(
                        modifier = Modifier
                            .padding(start = 32.dp, end = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RgbSlider(
                            label = "Red",
                            value = red.floatValue,
                            {red.floatValue = it}
                        )
                        RgbSlider(
                            label = "Green",
                            value = green.floatValue,
                            {green.floatValue = it}
                        )
                        RgbSlider(
                            label = "Blue",
                            value = blue.floatValue,
                            {blue.floatValue = it}
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = hex,
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp
                        )

                        Button(onClick = {
                            clipBoard.setText(AnnotatedString(hex))
                            Toast.makeText(context, "Copied Color to ClipBoard", Toast.LENGTH_LONG).show()
                        }) {
                            Text("Copy Color")
                        }
                    }

                }

            }
        }
    }
}

@Composable
fun RgbSlider(label: String, value: Float, onValueChange : (Float) -> Unit){
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label)
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..255f,
            steps = 254

        )
    }
}