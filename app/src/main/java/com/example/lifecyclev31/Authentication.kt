package com.example.lifecyclev31

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom


class Authentication {
    companion object {
        fun stringToByteArray(pw: String): ByteArray {
            val pwCharred = pw.toCharArray()
            val tmpArr = ByteArray(pwCharred.size)
            for (i in pwCharred.indices) {
                tmpArr[i] = pwCharred[i].code.toByte()
            }
            return tmpArr
        }

        fun hashPw(pw: ByteArray, salt: ByteArray): ByteArray {
            return try {
                val md = MessageDigest.getInstance("SHA-256")
                md.update(pw)
                md.update(salt)
                md.digest()
            } catch (e: NoSuchAlgorithmException) {
                throw RuntimeException(e)
            }
        }

        fun generateSalt(): ByteArray {
            val rand = SecureRandom()
            val salt = ByteArray(8)
            rand.nextBytes(salt)
            return salt
        }

        fun comparePassword(providedPassword: ByteArray, storedPassword: ByteArray): Boolean {
            return MessageDigest.isEqual(providedPassword, storedPassword)
        }


    }
}