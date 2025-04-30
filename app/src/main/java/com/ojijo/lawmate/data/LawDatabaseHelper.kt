package com.ojijo.lawmate.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class LawDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "law_system.db"
        const val DATABASE_VERSION = 1

        // Table and column names for Laws and Cases
        const val TABLE_LAWS = "laws"
        const val COLUMN_ID = "_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_CONTENT = "content"

        const val TABLE_CASES = "cases"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_COURT_DATE = "court_date"
        const val COLUMN_STATUS = "status"

        // Table and columns for Users
        const val TABLE_USERS = "users"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_FULL_NAME = "full_name"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create tables for laws, cases, and users
        val createLawsTable = """
            CREATE TABLE $TABLE_LAWS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT,
                $COLUMN_CONTENT TEXT
            );
        """.trimIndent()

        val createCasesTable = """
            CREATE TABLE $TABLE_CASES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_COURT_DATE TEXT,
                $COLUMN_STATUS TEXT
            );
        """.trimIndent()

        val createUsersTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USERNAME TEXT UNIQUE,
                $COLUMN_PASSWORD TEXT,
                $COLUMN_EMAIL TEXT,
                $COLUMN_FULL_NAME TEXT
            );
        """.trimIndent()

        // Execute the SQL statements to create tables
        db.execSQL(createLawsTable)
        db.execSQL(createCasesTable)
        db.execSQL(createUsersTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_LAWS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CASES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    // Insert law into database
    fun insertLaw(title: String, content: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, title)
            put(COLUMN_CONTENT, content)
        }

        db.insert(TABLE_LAWS, null, values)
        db.close()
    }

    // Insert case into database
    fun insertCase(description: String, courtDate: String, status: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DESCRIPTION, description)
            put(COLUMN_COURT_DATE, courtDate)
            put(COLUMN_STATUS, status)
        }

        db.insert(TABLE_CASES, null, values)
        db.close()
    }

    // Insert user into users table
    fun insertUser(username: String, password: String, email: String, fullName: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_EMAIL, email)
            put(COLUMN_FULL_NAME, fullName)
        }

        // Check if the username already exists
        if (isUsernameTaken(username)) {
            return false // Username is taken, return false
        }

        val result = db.insert(TABLE_USERS, null, values)
        db.close()
        return result != -1L  // If insert fails, it returns -1
    }

    // Check if a username is taken
    fun isUsernameTaken(username: String): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS, arrayOf(COLUMN_USERNAME),
            "$COLUMN_USERNAME = ?", arrayOf(username),
            null, null, null
        )

        val isTaken = cursor.count > 0
        cursor.close()
        db.close()
        return isTaken
    }

    // Validate user credentials for login
    fun validateUser(username: String, password: String): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS, arrayOf(COLUMN_USERNAME, COLUMN_PASSWORD),
            "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?", arrayOf(username, password),
            null, null, null
        )

        val isValid = cursor.count > 0
        cursor.close()
        db.close()
        return isValid
    }

    // Fetch all laws from database
    @SuppressLint("Range")
    fun getAllLaws(): List<Pair<String, String>> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_TITLE, $COLUMN_CONTENT FROM $TABLE_LAWS", null)
        val laws = mutableListOf<Pair<String, String>>()

        if (cursor.moveToFirst()) {
            val titleColumnIndex = cursor.getColumnIndex(COLUMN_TITLE)
            val contentColumnIndex = cursor.getColumnIndex(COLUMN_CONTENT)

            if (titleColumnIndex != -1 && contentColumnIndex != -1) {
                do {
                    val title = cursor.getString(titleColumnIndex)
                    val content = cursor.getString(contentColumnIndex)
                    laws.add(title to content)
                } while (cursor.moveToNext())
            } else {
                Log.e("DatabaseError", "Columns not found in the result set.")
            }
        } else {
            Log.e("DatabaseError", "No data found for laws.")
        }

        cursor.close()
        db.close()
        return laws
    }

    // Fetch all cases from database
    fun getAllCases(): List<Triple<String, String, String>> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_DESCRIPTION, $COLUMN_COURT_DATE, $COLUMN_STATUS FROM $TABLE_CASES", null)
        val cases = mutableListOf<Triple<String, String, String>>()

        if (cursor.moveToFirst()) {
            val descriptionColumnIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION)
            val courtDateColumnIndex = cursor.getColumnIndex(COLUMN_COURT_DATE)
            val statusColumnIndex = cursor.getColumnIndex(COLUMN_STATUS)

            if (descriptionColumnIndex != -1 && courtDateColumnIndex != -1 && statusColumnIndex != -1) {
                do {
                    val description = cursor.getString(descriptionColumnIndex)
                    val courtDate = cursor.getString(courtDateColumnIndex)
                    val status = cursor.getString(statusColumnIndex)
                    cases.add(Triple(description, courtDate, status))
                } while (cursor.moveToNext())
            } else {
                Log.e("DatabaseError", "Columns not found in the result set.")
            }
        } else {
            Log.e("DatabaseError", "No data found for cases.")
        }

        cursor.close()
        db.close()
        return cases
    }
}
