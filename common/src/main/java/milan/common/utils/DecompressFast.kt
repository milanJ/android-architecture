package milan.common.utils

import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class DecompressFast {

    private val zipFileInputStream: InputStream
    private val extractToFile: File

    @Throws(FileNotFoundException::class)
    constructor(zipFile: File, extractToFile: File) {
        this.zipFileInputStream = FileInputStream(zipFile)
        this.extractToFile = extractToFile

        if (!extractToFile.exists()) {
            throw IllegalArgumentException("Extraction folder doesn't exist.")
        }
    }

    constructor(zipFileInputStream: InputStream, extractToFile: File) {
        this.zipFileInputStream = zipFileInputStream
        this.extractToFile = extractToFile

        if (!extractToFile.exists()) {
            throw IllegalArgumentException("Extraction folder doesn't exist.")
        }
    }

    @Throws(IOException::class)
    fun unzip() {
        val extractToFilePath = extractToFile.canonicalPath
        val zin = ZipInputStream(zipFileInputStream)
        var ze: ZipEntry?
        while (true) {
            ze = zin.nextEntry
            if (ze == null) {
                break
            }

            val extractFile = File(extractToFile, ze.name)

            if (!extractFile.canonicalPath.startsWith(extractToFilePath)) {
                throw SecurityException("Zip Path Traversal attack detected!")
            }

            if (ze.isDirectory) {
                extractFile.mkdirs()
            } else {
                if (!extractFile.parentFile.exists()) {
                    extractFile.parentFile.mkdirs()
                }

                val bufout = BufferedOutputStream(FileOutputStream(extractFile))
                val buffer = ByteArray(1024)
                var read: Int
                while (true) {
                    read = zin.read(buffer)
                    if (read == -1) {
                        break
                    }

                    bufout.write(buffer, 0, read)
                }

                bufout.close()
                zin.closeEntry()
            }
        }
        zin.close()
    }
}
