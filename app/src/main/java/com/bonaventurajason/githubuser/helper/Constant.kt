package com.bonaventurajason.githubuser.helper

object Constant {
    const val BASE_URL = "https://api.github.com/"

    //bundle
    const val ARG_USERNAME = "username"
    const val ARG_POSITION = "position"

    //reminder
    const val REMINDER_TITLE = "title"
    const val REMINDER_MESSAGE = "message"
    const val REMINDER_CHANNEL_ID = "reminder_channel"
    const val REMINDER_CHANNEL_NAME = "Reminder"
    const val REMINDER_ID_REPEATING = 101

    //db
    const val DB_NAME = "github_user.db"
    const val DB_USER_TABLE_NAME = "user"

    //content provider (db)
    const val PROVIDER_AUTHORITY = "com.bonaventurajason.githubuser"
    const val PROVIDER_SCHEME = "content"
    const val PROVIDER_CONTENT_URI = "$PROVIDER_SCHEME://$PROVIDER_AUTHORITY"

    //content provider (table)
    const val PROVIDER_USER_CONTENT_URI = "$PROVIDER_CONTENT_URI/$DB_USER_TABLE_NAME"

    //user
    const val AVATAR_URL = "avatar_url"
    const val EVENTS_URL = "events_url"
    const val FOLLOWERS_URL = "followers_url"
    const val FOLLOWING_URL = "following_url"
    const val GISTS_URL = "gists_url"
    const val GRAVATAR_ID = "gravatar_id"
    const val HTML_URL = "html_url"
    const val ID = "id"
    const val LOGIN = "login"
    const val NODE_ID = "node_id"
    const val ORGANIZATIONS_URL = "organizations_url"
    const val RECEIVED_EVENTS_URL = "received_events_url"
    const val REPOS_URL = "repos_url"
    const val SCORE = "score"
    const val SITE_ADMIN = "site_admin"
    const val STARRED_URL = "starred_url"
    const val SUBSCRIPTIONS_URL = "subscriptions_url"
    const val TYPE = "type"
    const val URL = "url"

}