package milan.common.utils

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.json.JSONObject

fun loadTextFile(applicationContext: Context, absolutePath: String): String {
    return applicationContext.assets.open(absolutePath)
            .bufferedReader()
            .use { it.readText() }
}

fun loadJsonObject(applicationContext: Context, absolutePath: String): JSONObject {
    return JSONObject(loadTextFile(applicationContext, absolutePath))
}

fun <T> loadObject(applicationContext: Context, absolutePath: String, moshi: Moshi, c: Class<T>): T? {
    val jsonAdapter: JsonAdapter<T> = moshi.adapter<T>(c)
    return jsonAdapter.fromJson(loadTextFile(applicationContext, absolutePath))
}
