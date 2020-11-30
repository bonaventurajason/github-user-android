package com.bonaventurajason.githubuser.data.db.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.core.net.toUri
import com.bonaventurajason.githubuser.data.db.UserDatabase
import com.bonaventurajason.githubuser.helper.Constant.DB_USER_TABLE_NAME
import com.bonaventurajason.githubuser.helper.Constant.PROVIDER_AUTHORITY
import com.bonaventurajason.githubuser.helper.Constant.PROVIDER_USER_CONTENT_URI
import com.bonaventurajason.githubuser.helper.Utils.toUser

class FavouriteProvider : ContentProvider() {


    companion object {
        private const val USER = 1
        private const val USER_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    }

    init {
        sUriMatcher.addURI(
            PROVIDER_AUTHORITY,
            DB_USER_TABLE_NAME,
            USER
        )

        sUriMatcher.addURI(
            PROVIDER_AUTHORITY,
            "$DB_USER_TABLE_NAME/#",
            USER_ID
        )
    }


    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)){
            USER -> context?.let { UserDatabase.invoke(it).getUserDao().getAllUsers() }
            USER_ID -> uri.lastPathSegment?.toInt()?.let { context?.let { it1 ->
                UserDatabase.invoke(
                    it1
                ).getUserDao().getUserById(it)
            } }
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val insertUser: Long = when (USER){
            sUriMatcher.match(uri) -> values?.toUser()?.let {
                context?.let { it1 ->
                    UserDatabase.invoke(it1).getUserDao().insertUser(
                        it
                    )
                }
            } ?: 0
            else -> 0
        }
        context?.contentResolver?.notifyChange(PROVIDER_USER_CONTENT_URI.toUri(), null)
        return Uri.parse("$PROVIDER_USER_CONTENT_URI/$insertUser")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
       val deleteUser: Int = when(USER_ID){
           sUriMatcher.match(uri) -> uri.lastPathSegment?.toInt()?.let {
               context?.let { it1 ->
                   UserDatabase.invoke(it1).getUserDao().deleteUser(
                       it
                   )
               }
           } ?: 0
           else -> 0
       }
        context?.contentResolver?.notifyChange(PROVIDER_USER_CONTENT_URI.toUri(), null)
        return deleteUser
    }
}