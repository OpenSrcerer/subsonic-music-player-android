package personal.opensrcerer.bonkersmusic.server.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import personal.opensrcerer.bonkersmusic.server.db.dto.SubsonicServer
import personal.opensrcerer.bonkersmusic.server.db.contracts.SubsonicServerContract.ServerEntry

class ServerDbClient(
    context: Context,
    private val writable: Boolean = false
) : AutoCloseable {

    private val database: SQLiteDatabase

    init {
        val dbHelper = ServerDbHelper(context)
        database = if (writable)
            dbHelper.writableDatabase
        else
            dbHelper.readableDatabase
    }

    fun getServer(): SubsonicServer {
        if (writable) {
            throw IllegalAccessException("Database is write-only!")
        }

        // Filter results WHERE "SERVER_ID" = '1'
        // Always one result
        val selection = "${ServerEntry.SERVER_ID} = ?"
        val selectionArgs = arrayOf("1")

        val cursor = database.query(
            ServerEntry.TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        cursor.moveToNext()

        var server: SubsonicServer
        with(cursor) {
            server = SubsonicServer(
                getInt(getColumnIndexOrThrow(ServerEntry.SERVER_ID)),
                getString(getColumnIndexOrThrow(ServerEntry.SERVER_HOST)),
                getInt(getColumnIndexOrThrow(ServerEntry.SERVER_PORT)),
                getString(getColumnIndexOrThrow(ServerEntry.SERVER_USERNAME)),
                getString(getColumnIndexOrThrow(ServerEntry.SERVER_PASSWORD)),
                getString(getColumnIndexOrThrow(ServerEntry.SERVER_VERSION))
            )
        }
        cursor.close()
        return server
    }

    fun replaceServer(server: SubsonicServer) {
        this.removeServer()

        val serverToStore = ContentValues().apply {
            put(ServerEntry.SERVER_ID, server.id)
            put(ServerEntry.SERVER_HOST, server.host)
            put(ServerEntry.SERVER_PORT, server.port)
            put(ServerEntry.SERVER_USERNAME, server.username)
            put(ServerEntry.SERVER_PASSWORD, server.password)
            put(ServerEntry.SERVER_VERSION, server.version)
        }

        database.insert(ServerEntry.TABLE_NAME, null, serverToStore)
    }

    fun removeServer() {
        if (!writable) {
            throw IllegalAccessException("Database is read-only!")
        }
        val selection = "${ServerEntry.SERVER_ID} = ?"
        val args = arrayOf("1")
        database.delete(ServerEntry.TABLE_NAME, selection, args)
    }

    override fun close() {
        database.close()
    }
}