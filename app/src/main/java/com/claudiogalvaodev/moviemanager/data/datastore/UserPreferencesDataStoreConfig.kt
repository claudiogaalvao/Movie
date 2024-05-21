package com.claudiogalvaodev.moviemanager.data.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.claudiogalvaodev.moviemanager.UserPreferences
import com.google.protobuf.InvalidProtocolBufferException
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object UserPreferencesSerializer : Serializer<UserPreferences> {
    override val defaultValue: UserPreferences
        get() = UserPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserPreferences {
        try {
            return UserPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        } catch (e: IOException) {
            e.printStackTrace()
            throw e
        }
    }

    override suspend fun writeTo(t: UserPreferences, output: OutputStream) {
        t.writeTo(output)
    }

}

private val Context.userPreferencesDataStore by dataStore(
    fileName = "userPreferences.pb",
    serializer = UserPreferencesSerializer
)

fun provideProtoDataStore(context: Context) = context.userPreferencesDataStore
