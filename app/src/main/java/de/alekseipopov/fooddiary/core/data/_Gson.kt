package de.alekseipopov.fooddiary.core.data

import android.net.Uri
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException

val gsonInstance: Gson
    get() = GsonHolder.instance

private object GsonHolder {
    val instance: Gson by lazy { GsonBuilder().create() }
}

inline fun <reified T> T.toEncodedJson(): String {
    return Uri.encode(gsonInstance.toJson(this, T::class.java))
}

inline fun <reified T> T.toJson(): String {
    return gsonInstance.toJson(this, T::class.java)
}

inline fun <reified T> String.fromJson(): T? {
    return try {
        gsonInstance.fromJson(this, T::class.java)
    } catch (e: JsonSyntaxException) {
        null
    }
}
