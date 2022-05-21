package personal.opensrcerer.bonkersmusic.server.db.contracts

import android.provider.BaseColumns

internal object SubsonicServerContract {
    object ServerEntry : BaseColumns {
        const val TABLE_NAME = "servers"
        const val SERVER_ID = "srv_id"
        const val SERVER_HOST = "srv_host"
        const val SERVER_PORT = "srv_port"
        const val SERVER_USERNAME = "srv_user"
        const val SERVER_PASSWORD = "srv_pass"
        const val SERVER_VERSION = "srv_version"
    }

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${ServerEntry.TABLE_NAME} (" +
                "${ServerEntry.SERVER_ID} INTEGER PRIMARY KEY," +
                "${ServerEntry.SERVER_HOST} TEXT," +
                "${ServerEntry.SERVER_PORT} INTEGER," +
                "${ServerEntry.SERVER_USERNAME} TEXT," +
                "${ServerEntry.SERVER_PASSWORD} TEXT," +
                "${ServerEntry.SERVER_VERSION} VERSION)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${ServerEntry.TABLE_NAME}"
}