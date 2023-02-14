package com.example.primeraclaseandroid.data.contentprovider

import android.content.*
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import com.example.primeraclaseandroid.utils.Constants

//el content provider sirve para poder compartir informacion con otras aplicaciones

class MovieProvider : ContentProvider() {

    private var dbHelper : DBHelper? = null
    private var uriMatcher = UriMatcher(UriMatcher.NO_MATCH)//se inicializa y es para poder utilixar la urll
    private var columnsMovie = mutableMapOf<String, String>()
    private val uriContentProvider: Uri = Uri.parse("content://$AUTHORITY/movie")

    init {
    //se ejecuta primero antes que nada dentro de esta clase
        uriMatcher.addURI(AUTHORITY, MOVIE_TABLE_NAME,URI_MOVIES)
        uriMatcher.addURI(AUTHORITY, "$MOVIE_TABLE_NAME/$",URI_MOVIES)
        columnsMovie[Constants.COLUMN_ID] = Constants.COLUMN_ID
        columnsMovie[Constants.COLUMN_NAME] = Constants.COLUMN_NAME
    }

    override fun onCreate(): Boolean {
        dbHelper = DBHelper(context,DATABASE_NAME, null,DATABASE_VERSION)
        return true
    }

    override fun query(
        uri: Uri,
        columns: Array<out String>?,
        where: String?,
        args: Array<out String>?,
        sort: String?
    ): Cursor? {
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = MOVIE_TABLE_NAME
        queryBuilder.projectionMap = columnsMovie
        var whereFinal = where
        if (uriMatcher.match(uri) != URI_MOVIES) {
            if (uriMatcher.match(uri) == URI_MOVIE)
                whereFinal = where + "_id = " + uri.lastPathSegment
            else
                throw IllegalArgumentException("Unknown URI $uri")
        }
        val database = dbHelper?.readableDatabase
        val cursor = queryBuilder.query(database, columns, whereFinal, args, null, null, sort)
        cursor.setNotificationUri(context?.contentResolver, uri)
        return cursor
    }

    override fun getType(uri: Uri): String? {
        if (uriMatcher.match(uri) == URI_MOVIES)
            return Constants.CONTENT_TYPE
        else
            throw IllegalArgumentException("URI Desconocida $uri")
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        if (uriMatcher.match(uri) != URI_MOVIES) {
            throw IllegalArgumentException("URI Desconocida $uri")
        }

        val contentValuesFinal: ContentValues = if (contentValues != null) {
            ContentValues(contentValues)
        } else {
            ContentValues()
        }

        val database = dbHelper?.writableDatabase
        val rowId = database?.insert(MOVIE_TABLE_NAME, Constants.COLUMN_NAME, contentValuesFinal)

        if (rowId != null) {
            if (rowId > 0) {
                val uriProvider = ContentUris.withAppendedId(uriContentProvider, rowId)
                context?.contentResolver?.notifyChange(uriProvider, null)
                return uriProvider
            }
        }else {
            throw SQLException("No se pudo insertar el registro en $uri")
        }
        return null
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        return 1
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        return 1
    }

    inner class DBHelper(
        context: Context?,
        name: String?,
        factory : SQLiteDatabase.CursorFactory?,
        version : Int
    ) : SQLiteOpenHelper(context,name,factory,version){

        override fun onCreate(db : SQLiteDatabase?) {
            db?.execSQL(
                "CREATE TABLE " +
                        MOVIE_TABLE_NAME +
                        " (" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," + Constants.COLUMN_NAME + " VARCHAR(20)" + ")"
            )
        }

        override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
            db?.execSQL("DROP TABLE IF EXISTS $MOVIE_TABLE_NAME")
            onCreate(db)
        }

    }
    companion object{
         private const val MOVIE_TABLE_NAME = "movie"
         private const val DATABASE_NAME = "aulamovies.db"
         private const val DATABASE_VERSION = 1
         private const val AUTHORITY = "com.example.primeraclaseandroid.providers"
         private const val URI_MOVIES = 1
        private const val URI_MOVIE = 2
    }

}