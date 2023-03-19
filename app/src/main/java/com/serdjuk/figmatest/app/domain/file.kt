package com.serdjuk.figmatest.app.domain

import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.net.toUri
import com.serdjuk.figmatest.app.data.AVATAR_FILE
import com.serdjuk.figmatest.avatarBitmap
import java.io.File

//  https://ngengesenior.medium.com/pick-image-from-gallery-in-jetpack-compose-5fa0d0a8ddaf
fun getAvatarBitmap(context: Context, uri: Uri?) {
    // TODO добавлять к сохраненной картинке имя пользвотеля, что бы при старте приложения фото соответствовало аккаунту
    val file = File(context.filesDir, AVATAR_FILE)
    if (file.exists()) {
        if (Build.VERSION.SDK_INT < 28) {
            avatarBitmap.value = MediaStore.Images
                .Media.getBitmap(
                    context.contentResolver,
                    uri ?: file.toUri()
                )
        } else {
            avatarBitmap.value = ImageDecoder
                .createSource(
                    context.contentResolver,
                    uri ?: file.toUri()
                ).let {
                    ImageDecoder.decodeBitmap(it)
                }
        }
    }
}