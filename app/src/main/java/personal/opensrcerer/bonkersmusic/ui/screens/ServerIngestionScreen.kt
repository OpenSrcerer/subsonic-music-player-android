package personal.opensrcerer.bonkersmusic.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage
import personal.opensrcerer.bonkersmusic.R
import personal.opensrcerer.bonkersmusic.db.dto.SubsonicServer
import personal.opensrcerer.bonkersmusic.ui.dto.ServerField
import personal.opensrcerer.bonkersmusic.ui.dto.ServerIngestionFlow
import personal.opensrcerer.bonkersmusic.ui.models.ServerScreensModel
import personal.opensrcerer.bonkersmusic.ui.theme.*


@Composable
fun ServerIngestionScreen(
    hostnameContent: String = ServerScreensModel.getScreenModel().hostnameContent.value(),
    portContent: String = ServerScreensModel.getScreenModel().portContent.value(),
    usernameContent: String = ServerScreensModel.getScreenModel().usernameContent.value(),
    passwordContent: String = ServerScreensModel.getScreenModel().passwordContent.value(),
    server: SubsonicServer = ServerScreensModel.getScreenModel().currServer.value()!!,
    flow: ServerIngestionFlow,
) {
    when (flow) {
        ServerIngestionFlow.CONNECTING -> ConnectingScreen(server)
        ServerIngestionFlow.FAILED -> FailedToConnectScreen()
        ServerIngestionFlow.LOGIN -> LoginScreen(
            listOf(
                ServerField(
                    "Hostname (ex: google.com)",
                    "Hostname",
                    hostnameContent,
                    ServerField.Type.REGULAR
                ) { ServerScreensModel.getScreenModel().hostnameContent changeTo it },
                ServerField("Port", content = portContent, type = ServerField.Type.NUMBER)
                { ServerScreensModel.getScreenModel().portContent changeTo it },
                ServerField("Username", content = usernameContent, type = ServerField.Type.REGULAR)
                { ServerScreensModel.getScreenModel().usernameContent changeTo it },
                ServerField("Password", content = passwordContent, type = ServerField.Type.PASSWORD)
                { ServerScreensModel.getScreenModel().passwordContent changeTo it }
            )
        )
    }
}

@Composable
fun ConnectingScreen(
    server: SubsonicServer
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxHeight()
            .background(DarkerDeepBlue)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Connecting to server URL:",
                color = TextWhite,
                fontSize = 26.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 20.dp)
            )
            Text(
                text = "http://$server.host:$server.port",
                color = TextWhite,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
            )
            GlideImage(
                imageModel = R.raw.loading,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(250.dp)
            )
        }
    }
}

@Composable
fun FailedToConnectScreen() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxHeight()
            .background(DarkerDeepBlue)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Failed to connect to server",
                color = TextWhite,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 20.dp)
            )
            Row(
                modifier = Modifier
                    .width(250.dp)
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Try Again",
                    color = TextWhite,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable { }
                        .clip(RoundedCornerShape(10.dp))
                        .background(ButtonBlue)
                        .padding(vertical = 20.dp, horizontal = 15.dp)
                )
                Text(
                    text = "Cancel",
                    color = TextWhite,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable { }
                        .clip(RoundedCornerShape(10.dp))
                        .background(LightRed)
                        .padding(vertical = 20.dp, horizontal = 15.dp)
                )
            }
        }
    }
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.fillMaxHeight()
    ) {
        GlideImage(
            imageModel = R.raw.no,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(250.dp)
        )
    }
}

@Composable
fun LoginScreen(
    fields: List<ServerField>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkerDeepBlue)
    ) {
        Text(
            text = "Connect to a Subsonic server:",
            color = TextWhite,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 30.dp, bottom = 50.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 50.dp)
            ) {
                items(fields.size) {
                    FormField(fields[it])
                }
            }
        }
        Text(
            text = "Connect",
            color = TextWhite,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clickable { }
                .clip(RoundedCornerShape(10.dp))
                .background(ButtonBlue)
                .padding(vertical = 20.dp, horizontal = 15.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun FormField(
    serverField: ServerField
) {
    Column(
        Modifier.padding(bottom = 20.dp)
    ) {
        Text(
            text = serverField.title,
            color = TextWhite,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 5.dp)
        )
        TextField(
            placeholder = { serverField.hint },
            value = serverField.content,
            onValueChange = { updateStr ->
                if (
                    serverField.type == ServerField.Type.NUMBER &&
                    !(Regex("[0-9]*") matches updateStr)
                ) {
                    return@TextField
                }
                serverField.updateContent(updateStr)
            },
            textStyle = TextStyle(
                color = TextWhite,
                fontSize = 17.sp,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .border(
                    width = 2.dp,
                    color = BlueViolet1,
                    shape = RoundedCornerShape(20.dp)
                )
                .width(300.dp),
            singleLine = true,
            visualTransformation =
                if (serverField.type == ServerField.Type.PASSWORD)
                    VisualTransformation { text ->
                        TransformedText(
                            AnnotatedString('⬤'.repeat(text.length)), OffsetMapping.Identity
                        )
                    }
                else
                    VisualTransformation.None,
            keyboardOptions =
                when (serverField.type) {
                    ServerField.Type.NUMBER -> KeyboardOptions(keyboardType = KeyboardType.Number)
                    ServerField.Type.PASSWORD -> KeyboardOptions(keyboardType = KeyboardType.Password)
                    ServerField.Type.REGULAR -> KeyboardOptions.Default
                }
        )
    }
}

fun Char.repeat(count: Int): String = this.toString().repeat(count)