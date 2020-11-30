package com.bonaventurajason.consumerapp.helper

import android.content.ContentValues
import android.database.Cursor
import com.bonaventurajason.consumerapp.data.model.User
import com.bonaventurajason.consumerapp.helper.Constant.AVATAR_URL
import com.bonaventurajason.consumerapp.helper.Constant.EVENTS_URL
import com.bonaventurajason.consumerapp.helper.Constant.FOLLOWERS_URL
import com.bonaventurajason.consumerapp.helper.Constant.FOLLOWING_URL
import com.bonaventurajason.consumerapp.helper.Constant.GISTS_URL
import com.bonaventurajason.consumerapp.helper.Constant.GRAVATAR_ID
import com.bonaventurajason.consumerapp.helper.Constant.HTML_URL
import com.bonaventurajason.consumerapp.helper.Constant.ID
import com.bonaventurajason.consumerapp.helper.Constant.LOGIN
import com.bonaventurajason.consumerapp.helper.Constant.NODE_ID
import com.bonaventurajason.consumerapp.helper.Constant.ORGANIZATIONS_URL
import com.bonaventurajason.consumerapp.helper.Constant.RECEIVED_EVENTS_URL
import com.bonaventurajason.consumerapp.helper.Constant.REPOS_URL
import com.bonaventurajason.consumerapp.helper.Constant.SCORE
import com.bonaventurajason.consumerapp.helper.Constant.SITE_ADMIN
import com.bonaventurajason.consumerapp.helper.Constant.STARRED_URL
import com.bonaventurajason.consumerapp.helper.Constant.SUBSCRIPTIONS_URL
import com.bonaventurajason.consumerapp.helper.Constant.TYPE
import com.bonaventurajason.consumerapp.helper.Constant.URL

object Utils {
    fun ContentValues.toUser(): User {
        return User(
            avatar_url = getAsString(AVATAR_URL),
            events_url = getAsString(EVENTS_URL),
            followers_url = getAsString(FOLLOWERS_URL),
            following_url = getAsString(FOLLOWING_URL),
            gists_url = getAsString(GISTS_URL),
            gravatar_id = getAsString(GRAVATAR_ID),
            html_url = getAsString(HTML_URL),
            id = getAsInteger(ID),
            login = getAsString(LOGIN),
            node_id = getAsString(NODE_ID),
            organizations_url = getAsString(ORGANIZATIONS_URL),
            received_events_url = getAsString(RECEIVED_EVENTS_URL),
            repos_url = getAsString(REPOS_URL),
            score = getAsDouble(SCORE),
            site_admin = getAsBoolean(SITE_ADMIN),
            starred_url = getAsString(STARRED_URL),
            subscriptions_url = getAsString(SUBSCRIPTIONS_URL),
            type = getAsString(TYPE),
            url = getAsString(URL)

        )
    }

    fun User.toContentValues(): ContentValues =
        ContentValues().apply {
            put(AVATAR_URL, avatar_url)
            put(EVENTS_URL, events_url)
            put(FOLLOWERS_URL, followers_url)
            put(FOLLOWING_URL, following_url)
            put(GISTS_URL, gists_url)
            put(GRAVATAR_ID, gravatar_id)
            put(HTML_URL, html_url)
            put(ID, id)
            put(LOGIN, login)
            put(NODE_ID, node_id)
            put(ORGANIZATIONS_URL, organizations_url)
            put(RECEIVED_EVENTS_URL, received_events_url)
            put(REPOS_URL, repos_url)
            put(SCORE, score)
            put(SITE_ADMIN, site_admin)
            put(STARRED_URL, starred_url)
            put(SUBSCRIPTIONS_URL, subscriptions_url)
            put(TYPE, type)
            put(URL, url)
        }

    fun Cursor.toListUser(): ArrayList<User>{
        val userList = ArrayList<User>()

        apply {
            while (moveToNext()){
                userList.add(
                    toUser()
                )
            }
        }
        return userList

    }

    fun Cursor.toUser(): User =
        User(
            getString(getColumnIndexOrThrow(AVATAR_URL)),
            getString(getColumnIndexOrThrow(EVENTS_URL)),
            getString(getColumnIndexOrThrow(FOLLOWERS_URL)),
            getString(getColumnIndexOrThrow(FOLLOWING_URL)),
            getString(getColumnIndexOrThrow(GISTS_URL)),
            getString(getColumnIndexOrThrow(GRAVATAR_ID)),
            getString(getColumnIndexOrThrow(HTML_URL)),
            getInt(getColumnIndexOrThrow(ID)),
            getString(getColumnIndexOrThrow(LOGIN)),
            getString(getColumnIndexOrThrow(NODE_ID)),
            getString(getColumnIndexOrThrow(ORGANIZATIONS_URL)),
            getString(getColumnIndexOrThrow(RECEIVED_EVENTS_URL)),
            getString(getColumnIndexOrThrow(REPOS_URL)),
            getDouble(getColumnIndexOrThrow(SCORE)),
            (getInt(getColumnIndexOrThrow(SITE_ADMIN)) > 0),
            getString(getColumnIndexOrThrow(STARRED_URL)),
            getString(getColumnIndexOrThrow(SUBSCRIPTIONS_URL)),
            getString(getColumnIndexOrThrow(TYPE)),
            getString(getColumnIndexOrThrow(URL))
        )


}