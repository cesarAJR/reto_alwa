package com.cesar.reto_alwa.component

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat.startActivity
import com.cesar.domain.model.App

@Composable
fun ItemApp(item: App,context:Context) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 5.dp)
            .clickable(onClick = {

            })
    ) {
        ConstraintLayout(
            Modifier.fillMaxWidth()
        ) {
            val (text1,text2,image) = createRefs()
            Text(
                text = item.name,
                style = TextStyle(fontSize = 18.sp),
                modifier = Modifier
                    .constrainAs(text1) {
                        bottom.linkTo(parent.bottom)
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(text2.start)
                        width = Dimension.fillToConstraints
                    }
                    .padding(end = 20.dp),

                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier.constrainAs(text2){
                    bottom.linkTo(parent.bottom)
                    top.linkTo(parent.top)
                    end.linkTo(image.start)
                    width= Dimension.fillToConstraints
                }.padding(end = 20.dp),
                text = item.timeUsed,
                style = TextStyle(fontSize = 18.sp)
            )


            Image(
                imageVector = Icons.Filled.Settings ,
                contentDescription = "",
                modifier = Modifier.clickable {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.parse("package:" + item.packageName)
                    context.startActivity(intent)
                 }.constrainAs(image){
                    bottom.linkTo(parent.bottom)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    width= Dimension.value(40.dp)
                }
            )

        }
    }
}