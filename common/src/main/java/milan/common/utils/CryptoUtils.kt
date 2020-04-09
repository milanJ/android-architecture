package milan.common.utils

import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


fun md5Hash(s: String): String {
    var m: MessageDigest?

    try {
        m = MessageDigest.getInstance("MD5")
    } catch (e: NoSuchAlgorithmException) {
        throw RuntimeException("MD5 should be supported?", e)
    }

    m!!.update(s.toByteArray(), 0, s.length)
    return BigInteger(1, m.digest()).toString(16)
}

fun md5Hash(byteArray: ByteArray): String {
    var m: MessageDigest?

    try {
        m = MessageDigest.getInstance("MD5")
    } catch (e: NoSuchAlgorithmException) {
        throw RuntimeException("MD5 should be supported?", e)
    }

    m!!.update(byteArray)
    return BigInteger(1, m.digest()).toString(16)
}
