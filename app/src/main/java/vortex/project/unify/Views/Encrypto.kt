package vortex.project.unify.Views

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class Encrypto {

    private val KEY_STORE_TYPE = "AndroidKeyStore"
    private val CRYPTO_KEY = "crypto_key"

    private val ALGOR = "AES"
    private val CBC = KeyProperties.BLOCK_MODE_CBC
    private val PKCS7 = KeyProperties.ENCRYPTION_PADDING_PKCS7
    private val CRYPTO_SIZE = 16
    private val INITIAL_VECTOR = ByteArray(CRYPTO_SIZE)

    val keySotre = KeyStore.getInstance(KEY_STORE_TYPE).apply {load(null)}

    fun getSecretKey(): SecretKey? {
        var key: SecretKey? = null

        if (keySotre.containsAlias((CRYPTO_KEY))){

            val entry = keySotre.getEntry(CRYPTO_KEY,null) as? KeyStore.SecretKeyEntry

            key = entry?.secretKey

        } else {
            val builder = KeyGenParameterSpec.Builder(CRYPTO_KEY, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            builder.setKeySize(256)
            builder.setBlockModes(CBC)
            builder.setEncryptionPaddings(PKCS7)

            val keySpec = builder.build()
            val keyGenerator = KeyGenerator.getInstance(ALGOR, KEY_STORE_TYPE)

            keyGenerator.init(keySpec)

            key = keyGenerator.generateKey()
        }
        return key
    }

    fun cipher(message: String, key: SecretKey? = getSecretKey()): ByteArray {
        if (key != null) {
            Cipher.getInstance("$ALGOR/$CBC/$PKCS7").run {
                init(Cipher.ENCRYPT_MODE, key)
                val value = doFinal(message.toByteArray())
                iv.copyInto(INITIAL_VECTOR, 0, 0, CRYPTO_SIZE)
                return INITIAL_VECTOR + value
            }
        }
        return byteArrayOf()
    }

    fun cipher(message: String): ByteArray {
        val key = getSecretKey()
        return cipher(message, key)
    }

    fun decipher(crypto: ByteArray, key: SecretKey?): String {
        if (key != null){
            Cipher.getInstance("$ALGOR/$CBC/$PKCS7").run {
                val value = ByteArray(crypto.size-CRYPTO_SIZE)
                crypto.copyInto(value, 0, CRYPTO_SIZE, crypto.size)
                crypto.copyInto(INITIAL_VECTOR, 0, 0, CRYPTO_SIZE)
                init(Cipher.DECRYPT_MODE, key, IvParameterSpec(INITIAL_VECTOR))

                return String(doFinal(value))
            }
        }
        return ""
    }

    fun decipher(crypto: ByteArray): String {
        val key = getSecretKey()
        return decipher(crypto, key)
    }

    fun md5Hash(message: String): String {
        val messageDigest = MessageDigest.getInstance("MD5")
        return Base64.encodeToString(messageDigest.digest(message.toByteArray()), Base64.DEFAULT).trimEnd()
    }
}