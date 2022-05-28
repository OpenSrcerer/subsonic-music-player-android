package personal.opensrcerer.bonkersmusic.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import personal.opensrcerer.bonkersmusic.audio.AudioPlayerService
import personal.opensrcerer.bonkersmusic.ui.common.BottomBar
import personal.opensrcerer.bonkersmusic.ui.common.TopBar
import personal.opensrcerer.bonkersmusic.ui.dto.IngestionPageType
import personal.opensrcerer.bonkersmusic.ui.models.HomeScreenModel
import personal.opensrcerer.bonkersmusic.ui.models.ServerScreensModel
import personal.opensrcerer.bonkersmusic.ui.theme.ButtonBlue
import personal.opensrcerer.bonkersmusic.ui.theme.DeepBlue
import personal.opensrcerer.bonkersmusic.ui.theme.LightRed
import personal.opensrcerer.bonkersmusic.ui.theme.TextWhite

@Composable
fun ServerScreen(
    navigator: NavController
) {
    Scaffold(
        topBar = { TopBar(navigator = navigator) },
        content = {
            Box(
                modifier = Modifier
                    .background(DeepBlue)
                    .fillMaxSize()
            ) {
                LoggedInServerView(navigator = navigator)
            }
        },
        bottomBar = { BottomBar(navigator = navigator) }
    )
}

@Composable
fun LoggedInServerView(
    navigator: NavController
) {
    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxHeight()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            val server = ServerScreensModel.getScreenModel().currServer.value()
            val serverRes = ServerScreensModel.getScreenModel().serverResponse.value()
            val messages = listOf(
                "Status: ${serverRes?.status}",
                "Server Version: ${serverRes?.version}"
            )
            Text(
                text = "You are currently connected to:",
                color = TextWhite,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = server?.host ?: "Unknown Server",
                color = TextWhite,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 15.dp)
            )
            Divider(
                color = TextWhite,
                modifier = Modifier.padding(start = 15.dp, end = 15.dp)
            )
            Text(
                buildAnnotatedString {
                    messages.forEach {
                        withStyle(style = ParagraphStyle(
                            textIndent = TextIndent(restLine = 12.sp)
                        )) {
                            withStyle(style = SpanStyle(
                                color = TextWhite,
                                fontWeight = FontWeight.Normal,
                                fontSize = 20.sp,
                                letterSpacing = (-0.5).sp
                            )
                            ) {
                                append("\u2022")
                                append("\t\t")
                                append(it)
                            }
                        }
                    }
                },
                modifier = Modifier.
                        padding(top = 20.dp, bottom = 20.dp)
            )
            Divider(
                color = TextWhite,
                modifier = Modifier.padding(start = 15.dp, end = 15.dp)
            )
            Row(
                modifier = Modifier
                    .width(250.dp)
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Edit",
                    color = TextWhite,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable {
                            AudioPlayerService.stopPlayer()
                            val screensModel = ServerScreensModel.getScreenModel()
                            screensModel.dumpServerDataToForms()
                            screensModel.removeServer(context)
                            screensModel.pageType changeTo IngestionPageType.LOGIN
                            navigator.navigate("ingestion")
                            screensModel.serverResponse changeTo null
                            HomeScreenModel.getHomeModel().songLoaded changeTo false
                        }
                        .clip(RoundedCornerShape(10.dp))
                        .background(ButtonBlue)
                        .padding(vertical = 20.dp, horizontal = 15.dp)
                )
                Text(
                    text = "Disconnect",
                    color = TextWhite,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable {
                            AudioPlayerService.stopPlayer()
                            val screensModel = ServerScreensModel.getScreenModel()
                            screensModel.clearFormData()
                            screensModel.removeServer(context)
                            screensModel.pageType changeTo IngestionPageType.LOGIN
                            navigator.navigate("ingestion")
                            screensModel.serverResponse changeTo null
                            HomeScreenModel.getHomeModel().songLoaded changeTo false
                        }
                        .clip(RoundedCornerShape(10.dp))
                        .background(LightRed)
                        .padding(vertical = 20.dp, horizontal = 15.dp)
                )
            }
        }
    }
}