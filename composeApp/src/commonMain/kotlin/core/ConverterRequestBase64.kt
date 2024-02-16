package core

import io.ktor.utils.io.core.toByteArray
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
fun <T : Any> T.base64encode(serializer: KSerializer<T>): String {
    val data = Json.encodeToString(serializer, this)
    return Base64.encode(data.toByteArray())
}
@OptIn(ExperimentalEncodingApi::class)
fun <T : Any> String.base64decode(serializer: KSerializer<T>): T {
    val decodedBytes = Base64.decode(this)
    return Json.decodeFromString(serializer, decodedBytes.decodeToString())
}