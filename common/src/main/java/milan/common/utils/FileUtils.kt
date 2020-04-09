package milan.common.utils

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

fun figureOutNonExistingFilenameInDirectory(parentDirectory: File, filename: String): String {
    var retFilename = filename
    var counter = 0
    var file = File(parentDirectory, filename)

    while (file.exists()) {
        counter++;
        retFilename = StringBuilder(filename.length + 3)
                .append(filename.substring(0, filename.length - 4))
                .append('(')
                .append(counter)
                .append(')')
                .append(filename.substring(filename.length - 4))
                .toString()


        file = File(parentDirectory, retFilename)
    }

    return retFilename
}

fun deleteFile(file: File): Boolean {
    var success = true
    if (file.isDirectory) {
        val files = file.listFiles()
        if (files != null) {
            for (f in files) {
                success = success and deleteFile(f)
            }
        }
        success = success and file.delete()
    } else {
        success = file.delete()
    }
    return success
}

fun readTextFile(file: File): String? {
    val text = StringBuilder()
    var br: BufferedReader? = null
    try {
        br = BufferedReader(FileReader(file))
        var line = br.readLine()
        while (line != null) {
            text.append(line)
            text.append('\n')
            line = br.readLine()
        }
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    } finally {
        try {
            br?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
    return text.toString()
}
