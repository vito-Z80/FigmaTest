package com.serdjuk.figmatest.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*

// Set of Material typography styles to start with
@OptIn(ExperimentalUnitApi::class)
val fonts = FontFamily(
    listOf(
        Font(com.serdjuk.figmatest.R.font.montserrat_medium, weight = FontWeight.Normal),
        Font(com.serdjuk.figmatest.R.font.montserrat_medium, weight = FontWeight.Medium),
        Font(com.serdjuk.figmatest.R.font.montserrat_medium, weight = FontWeight.Bold),
        Font(com.serdjuk.figmatest.R.font.montserrat_medium, weight = FontWeight.Light),
        Font(com.serdjuk.figmatest.R.font.montserrat_medium, weight = FontWeight.ExtraLight),
        Font(com.serdjuk.figmatest.R.font.montserrat_medium, weight = FontWeight.ExtraBold),
    )
)


val Typography = Typography(
//    body1 = TextStyle(
//        color = Color(0xFF161826),
//        fontFamily = fonts,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp
//    ),
    h1 = TextStyle(
//        color = Color.White,
        fontFamily = fonts,
        fontSize = 26f.sp,
        fontWeight = FontWeight(700),
//        lineHeight = 32f.sp,
        letterSpacing = (-0.30000001192092896).sp,
        textAlign = TextAlign.Center,
    ),
    /*
        font-family: Montserrat;
        font-size: 11px;
        font-weight: 500;
        line-height: 13px;
        letter-spacing: -0.30000001192092896px;
        text-align: center;

     */
    h2 = TextStyle(
        color = FontColor2,
        fontFamily = fonts,
        fontSize = 11f.sp,
        fontWeight = FontWeight(500),
        lineHeight = 13f.sp,
        letterSpacing = (-0.30000001192092896).sp,
        textAlign = TextAlign.Center
    ),
    h3 = TextStyle(
        color = ParagraphColor,
        fontFamily = fonts,
        fontSize = 11f.sp,
        fontWeight = FontWeight(500),
        lineHeight = 13f.sp,
        letterSpacing = (-0.30000001192092896).sp,
        textAlign = TextAlign.Center
    ),
    subtitle1 = TextStyle(
        color = ParagraphColor,
        fontFamily = fonts,
        fontSize = 26f.sp,
        fontWeight = FontWeight(600),
        lineHeight = 32f.sp,
        letterSpacing = (-0.30000001192092896).sp,
        textAlign = TextAlign.Center,
    ),
    subtitle2 = TextStyle(
        color = ParagraphColor,
        fontFamily = fonts,
        fontSize = 26f.sp,
        fontWeight = FontWeight(600),
        lineHeight = 32f.sp,
        letterSpacing = (-0.30000001192092896).sp,
        textAlign = TextAlign.Center,
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)


//font-family: Montserrat;
//font-size: 26px;
//font-weight: 600;
//line-height: 32px;
//letter-spacing: -0.30000001192092896px;
//text-align: center;
