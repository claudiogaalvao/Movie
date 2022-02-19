package com.claudiogalvaodev.moviemanager.data.bd.callback

import android.content.Context
import android.content.res.Resources
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.data.bd.CineSeteDatabase
import com.claudiogalvaodev.moviemanager.data.bd.entity.MyList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MyListsDatabaseCallback(
    private val context: Context,
    private val scope: CoroutineScope?,
    private val resources: Resources?
): RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        scope?.launch {
            CineSeteDatabase.getInstance(context).myListsDao.populate(prePopulateMyListsDatabase())
        }
    }

    private fun prePopulateMyListsDatabase() = listOf(
        MyList(0, resources?.getString(R.string.my_lists_favorites) ?: ""),
        MyList(0, resources?.getString(R.string.my_lists_watch_later) ?: "")
    )

}