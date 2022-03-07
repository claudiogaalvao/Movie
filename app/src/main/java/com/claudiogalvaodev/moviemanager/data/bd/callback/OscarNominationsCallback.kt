package com.claudiogalvaodev.moviemanager.data.bd.callback

import android.content.Context
import android.content.res.Resources
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.data.bd.CineSeteDatabase
import com.claudiogalvaodev.moviemanager.data.bd.entity.OscarNomination
import com.claudiogalvaodev.moviemanager.utils.enums.OscarCategory
import com.claudiogalvaodev.moviemanager.utils.enums.ItemType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OscarNominationsCallback(
    private val context: Context,
    private val scope: CoroutineScope?,
    private val resources: Resources?
): RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        scope?.launch {
            CineSeteDatabase.getInstance(context).oscarNominationsDao
                .populate(populateOscarNominationsDatabase())
        }
    }

//    override fun onOpen(db: SupportSQLiteDatabase) {
//        super.onOpen(db)
//        scope?.launch {
//            val oscarNominationDao = CineSeteDatabase.getInstance(context).oscarNominationsDao
//            val result = oscarNominationDao.getAll()
//            if(result.isEmpty()) {
//                oscarNominationDao.populate(populateOscarNominationsDatabase())
//            }
//        }
//    }

    private fun populateOscarNominationsDatabase() = listOf(
        OscarNomination(id = 0, itemId = 777270,
            type = ItemType.MOVIE,
            title = "Belfast",
            subtitle = "Down To Joy",
            imageUrl = "https://image.tmdb.org/t/p/w500/3mInLZyPOVLsZRsBwNHi3UJXXnm.jpg",
            releaseDate = "2021-11-11",
            categories = listOf(
                OscarCategory.BEST_PICTURE,
                OscarCategory.BEST_ORIGINAL_SCREENPLAY,
                OscarCategory.BEST_SOUND_MIXING,
                OscarCategory.BEST_ORIGINAL_SONG)),
        OscarNomination(id = 0, itemId = 646380,
            type = ItemType.MOVIE,
            title = resources?.getString(R.string.oscar_best_picture_dont_look_up) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/6Sc7Tjt7aPsdghYK32mDMFeZkqJ.jpg",
            releaseDate = "2021-12-07",
            categories = listOf(
                OscarCategory.BEST_PICTURE,
                OscarCategory.BEST_ORIGINAL_SCREENPLAY,
                OscarCategory.BEST_ORIGINAL_SCORE,
                OscarCategory.BEST_EDITING)),
        OscarNomination(id = 0, itemId = 438631,
            type = ItemType.MOVIE,
            title = resources?.getString(R.string.oscar_best_picture_dune) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/uzERcfV2rSHNhW5eViQiO9hNiA7.jpg",
            releaseDate = "2021-09-15",
            categories = listOf(
                OscarCategory.BEST_PICTURE,
                OscarCategory.BEST_ADAPTED_SCREENPLAY,
                OscarCategory.BEST_COSTUME_DESIGN,
                OscarCategory.BEST_ORIGINAL_SCORE,
                OscarCategory.BEST_SOUND_MIXING,
                OscarCategory.BEST_MAKEUP_AND_HAIRSTYLING,
                OscarCategory.BEST_VISUAL_EFFECTS,
                OscarCategory.BEST_CINEMATOGRAPHY,
                OscarCategory.BEST_EDITING,
                OscarCategory.BEST_PRODUCTION_DESIGN)
        ),
        OscarNomination(id = 0, itemId = 718032,
            type = ItemType.MOVIE,
            title = "Licorice Pizza",
            imageUrl = "https://image.tmdb.org/t/p/w500/q405QKpOc7hsxuxgQAF5QV7OFO9.jpg",
            releaseDate = "2021-11-26",
            categories = listOf(
                OscarCategory.BEST_PICTURE,
                OscarCategory.BEST_ORIGINAL_SCREENPLAY)),
        OscarNomination(id = 0, itemId = 600583,
            type = ItemType.MOVIE,
            title = resources?.getString(R.string.oscar_best_power_dog) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/pTieUAFyDbC22uq0p7uMT1wBYax.jpg",
            releaseDate = "2021-11-17",
            categories = listOf(
                OscarCategory.BEST_PICTURE,
                OscarCategory.BEST_ADAPTED_SCREENPLAY,
                OscarCategory.BEST_ORIGINAL_SCORE,
                OscarCategory.BEST_SOUND_MIXING,
                OscarCategory.BEST_CINEMATOGRAPHY,
                OscarCategory.BEST_EDITING,
                OscarCategory.BEST_PRODUCTION_DESIGN)),
        OscarNomination(id = 0, itemId = 776503,
            type = ItemType.MOVIE,
            title = resources?.getString(R.string.oscar_best_coda) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/rnEcFnQYgZ6mmxeeaEeLNSZdgD8.jpg",
            releaseDate = "2021-08-11",
            categories = listOf(
                OscarCategory.BEST_PICTURE,
                OscarCategory.BEST_ADAPTED_SCREENPLAY)),
        OscarNomination(id = 0, itemId = 758866,
            type = ItemType.MOVIE,
            title = "Drive My Car",
            subtitle = resources?.getString(R.string.country_japan) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/6BkE8l4NK4fWw0PLrE9oGY6feuH.jpg",
            releaseDate = "2021-08-18",
            categories = listOf(
                OscarCategory.BEST_PICTURE,
                OscarCategory.BEST_FOREIGN_LANGUAGE_FILM,
                OscarCategory.BEST_ADAPTED_SCREENPLAY)),
        OscarNomination(id = 0, itemId = 614917,
            type = ItemType.MOVIE,
            title = resources?.getString(R.string.oscar_king_richard) ?: "",
            subtitle = "Be Alive",
            imageUrl = "https://image.tmdb.org/t/p/w500/1GzzYMqCUbx0RMY3yAdrKdKVonh.jpg",
            releaseDate = "2021-11-18",
            categories = listOf(
                OscarCategory.BEST_PICTURE,
                OscarCategory.BEST_ORIGINAL_SCREENPLAY,
                OscarCategory.BEST_ORIGINAL_SONG,
                OscarCategory.BEST_EDITING)),
        OscarNomination(id = 0, itemId = 597208,
            type = ItemType.MOVIE,
            title = resources?.getString(R.string.oscar_nightmare_alley) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/9HnTq2Kg0R4YpdB1B5r91mFt0S2.jpg",
            releaseDate = "2021-12-02",
            categories = listOf(
                OscarCategory.BEST_PICTURE,
                OscarCategory.BEST_COSTUME_DESIGN,
                OscarCategory.BEST_CINEMATOGRAPHY,
                OscarCategory.BEST_PRODUCTION_DESIGN)),
        OscarNomination(id = 0, itemId = 511809,
            type = ItemType.MOVIE,
            title = resources?.getString(R.string.oscar_west_side_story) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/4XvvbOFlnC5czMBJd0l7ysMMV4z.jpg",
            releaseDate = "2021-12-08",
            categories = listOf(
                OscarCategory.BEST_PICTURE,
                OscarCategory.BEST_COSTUME_DESIGN,
                OscarCategory.BEST_SOUND_MIXING,
                OscarCategory.BEST_CINEMATOGRAPHY,
                OscarCategory.BEST_PRODUCTION_DESIGN)),
        OscarNomination(id = 0, itemId = 680813,
            type = ItemType.MOVIE,
            title = resources?.getString(R.string.oscar_flee) ?: "",
            subtitle = resources?.getString(R.string.country_denmark) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/eIaG0veFgAxOy9iMRohPItTmscO.jpg",
            releaseDate = "2021-06-17",
            categories = listOf(
                OscarCategory.BEST_FOREIGN_LANGUAGE_FILM,
                OscarCategory.BEST_ANIMATED_FEATURE_FILM,
                OscarCategory.BEST_DOCUMENTARY_FEATURE)),
        OscarNomination(id = 0, itemId = 722778,
            type = ItemType.MOVIE,
            title = resources?.getString(R.string.oscar_hand_god) ?: "",
            subtitle = resources?.getString(R.string.country_italy) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/i0X3iJGYcjkx7xoOAkTzo6Rg4ek.jpg",
            releaseDate = "2021-11-24",
            categories = listOf(
                OscarCategory.BEST_FOREIGN_LANGUAGE_FILM)),
        OscarNomination(id = 0, itemId = 718032,
            type = ItemType.MOVIE,
            title = resources?.getString(R.string.oscar_lunana) ?: "",
            subtitle = resources?.getString(R.string.country_bhutan) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/ec0pKo0efNJDoND784YVMzQR9oG.jpg",
            releaseDate = "2019-11-16",
            categories = listOf(
                OscarCategory.BEST_FOREIGN_LANGUAGE_FILM)),
        OscarNomination(id = 0, itemId = 660120,
            type = ItemType.MOVIE,
            title = resources?.getString(R.string.oscar_worst_person) ?: "",
            subtitle = resources?.getString(R.string.country_norway) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/1eOElXpU3y7dkH2p9jn3WPArA6V.jpg",
            releaseDate = "2021-10-13",
            categories = listOf(
                OscarCategory.BEST_FOREIGN_LANGUAGE_FILM,
                OscarCategory.BEST_ORIGINAL_SCREENPLAY)),
        OscarNomination(id = 0, itemId = 554230,
            type = ItemType.MOVIE,
            title = resources?.getString(R.string.oscar_lost_daughter) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/w5ZyodD6md3mmPWO3nSPGXklxLk.jpg",
            releaseDate = "2021-12-16",
            categories = listOf(
                OscarCategory.BEST_ADAPTED_SCREENPLAY)),
        OscarNomination(id = 0, itemId = 337404,
            type = ItemType.MOVIE,
            title = "Cruella", imageUrl = "https://image.tmdb.org/t/p/w500/rRYNGhcAZlTfIUS7iFoF4H9iDbc.jpg",
            releaseDate = "2021-05-26",
            categories = listOf(
                OscarCategory.BEST_COSTUME_DESIGN,
                OscarCategory.BEST_MAKEUP_AND_HAIRSTYLING)),
        OscarNomination(id = 0, itemId = 730047,
            type = ItemType.MOVIE,
            title = "Cyrano",
            imageUrl = "https://image.tmdb.org/t/p/w500/e4koV8iC2cCM57bqUnEnIL2a2zH.jpg",
            releaseDate = "2022-01-06",
            categories = listOf(
                OscarCategory.BEST_COSTUME_DESIGN)),
        OscarNomination(id = 0, itemId = 766798,
            type = ItemType.MOVIE,
            title = resources?.getString(R.string.oscar_parallel_mothers) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/w6RhGdQ6u38BCa0L0IOrosmFdOT.jpg",
            releaseDate = "2021-10-08",
            categories = listOf(
                OscarCategory.BEST_ORIGINAL_SCORE)),
        OscarNomination(id = 0, itemId = 568124,
            type = ItemType.MOVIE,
            title = "Encanto", imageUrl = "https://image.tmdb.org/t/p/w500/4j0PNHkMr5ax3IA8tjtxcmPU3QT.jpg",
            subtitle = "Dos Oruguitas",
            releaseDate = "2021-11-24",
            categories = listOf(
                OscarCategory.BEST_ORIGINAL_SCORE,
                OscarCategory.BEST_ANIMATED_FEATURE_FILM,
                OscarCategory.BEST_ORIGINAL_SONG)),
        OscarNomination(id = 0, itemId = 508943,
            type = ItemType.MOVIE,
            title = "Luca", imageUrl = "https://image.tmdb.org/t/p/w500/9jPa6SlUYxPFMVZlEuceiPPAA15.jpg",
            releaseDate = "2021-06-17",
            categories = listOf(
                OscarCategory.BEST_ANIMATED_FEATURE_FILM)),
        OscarNomination(id = 0, itemId = 501929,
            type = ItemType.MOVIE,
            title = resources?.getString(R.string.oscar_mitchells_machines) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/k1p10mLm1uM1jqR7RlzB0SalD00.jpg",
            releaseDate = "2021-04-22",
            categories = listOf(
                OscarCategory.BEST_ANIMATED_FEATURE_FILM)),
        OscarNomination(id = 0, itemId = 527774,
            type = ItemType.MOVIE,
            title = resources?.getString(R.string.oscar_raya_last_dragon) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/yXrb84zJidCefyhTWHwo4yCDvwz.jpg",
            releaseDate = "2021-03-03",
            categories = listOf(
                OscarCategory.BEST_ANIMATED_FEATURE_FILM)),
        OscarNomination(id = 0, itemId = 742843,
            type = ItemType.MOVIE,
            title = "Affairs of the Art", imageUrl = "https://image.tmdb.org/t/p/w500/nl60pAipFbxljZ8g04U1vGSx088.jpg",
            releaseDate = "2021-01-29",
            categories = listOf(
                OscarCategory.BEST_ANIMATED_SHORT)),
        OscarNomination(id = 0, itemId = 828751,
            type = ItemType.MOVIE,
            title = "Bestia", imageUrl = "https://image.tmdb.org/t/p/w500/kpPMOPYy8OkOQo6smPR3SRQHfS5.jpg",
            releaseDate = "2021-11-04",
            categories = listOf(
                OscarCategory.BEST_ANIMATED_SHORT)),
        OscarNomination(id = 0, itemId = 746080,
            type = ItemType.MOVIE,
            title = "Boxballet", imageUrl = "https://image.tmdb.org/t/p/w500/itMGtzYChYodWjZ1mBs5EPCDqPP.jpg",
            releaseDate = "2020-03-12",
            categories = listOf(
                OscarCategory.BEST_ANIMATED_SHORT)),
        OscarNomination(id = 0, itemId = 649928,
            type = ItemType.MOVIE,
            title = resources?.getString(R.string.oscar_robin_robin) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/5xQuLvSpUhsa4NYzkA4yyOWwLL7.jpg",
            releaseDate = "2021-10-09",
            categories = listOf(
                OscarCategory.BEST_ANIMATED_SHORT)),
        OscarNomination(id = 0, itemId = 601329,
            type = ItemType.MOVIE,
            title = "The Windshield Wiper", imageUrl = "https://image.tmdb.org/t/p/w500/lIsMfqvk4sg1ehjjYrhoBobgTnR.jpg",
            releaseDate = "2021-07-13",
            categories = listOf(
                OscarCategory.BEST_ANIMATED_SHORT)),
        OscarNomination(id = 0, itemId = 853760,
            type = ItemType.MOVIE,
            title = "Ala Kachuu", imageUrl = "https://image.tmdb.org/t/p/w500/24995XT20z34m090bQs2DwSGrIe.jpg",
            releaseDate = "2021-10-22",
            categories = listOf(
                OscarCategory.BEST_LIVE_ACTION_SHORT)),
        OscarNomination(id = 0, itemId = 681015,
            type = ItemType.MOVIE,
            title = "The Long Goodbye", imageUrl = "https://image.tmdb.org/t/p/w500/etLOhBuFUszVxxWlbyWo5WT7HoC.jpg",
            releaseDate = "2020-03-06",
            categories = listOf(
                OscarCategory.BEST_LIVE_ACTION_SHORT)),
        OscarNomination(id = 0, itemId = 918627,
            type = ItemType.MOVIE,
            title = "On My Mind", imageUrl = "https://image.tmdb.org/t/p/w500/3nDI3UCxdJrOo2jsT6gokFhfnDe.jpg",
            releaseDate = "2021-07-10",
            categories = listOf(
                OscarCategory.BEST_LIVE_ACTION_SHORT)),
        OscarNomination(id = 0, itemId = 741607,
            type = ItemType.MOVIE,
            title = "Please Hold", imageUrl = "https://image.tmdb.org/t/p/w500/zHtMFz4o55ZJbCvNwwqUz36aDpv.jpg",
            releaseDate = "2020-09-25",
            categories = listOf(
                OscarCategory.BEST_LIVE_ACTION_SHORT)),
        OscarNomination(id = 0, itemId = 822138,
            type = ItemType.MOVIE,
            title = "Ascension", imageUrl = "https://image.tmdb.org/t/p/w500/8Tng4QWveeazE2gFcrZxvbyvvl8.jpg",
            releaseDate = "2021-10-08",
            categories = listOf(
                OscarCategory.BEST_DOCUMENTARY_FEATURE)),
        OscarNomination(id = 0, itemId = 858059,
            type = ItemType.MOVIE,
            title = "Attica", imageUrl = "https://image.tmdb.org/t/p/w500/8dpc49O7G9B0y2crRbI7hHtbQzB.jpg",
            releaseDate = "2021-09-09",
            categories = listOf(
                OscarCategory.BEST_DOCUMENTARY_FEATURE)),
        OscarNomination(id = 0, itemId = 776527,
            type = ItemType.MOVIE,
            title = resources?.getString(R.string.oscar_summer_soul) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/zPMSeDxHdinpbJJdXQS8IrfD1Ux.jpg",
            releaseDate = "2021-07-02",
            categories = listOf(
                OscarCategory.BEST_DOCUMENTARY_FEATURE)),
        OscarNomination(id = 0, itemId = 776557,
            type = ItemType.MOVIE,
            title = "Writing with Fire", imageUrl = "https://image.tmdb.org/t/p/w500/O7cmXnEEAVioEQ1JdRl9iss5vQ.jpg",
            releaseDate = "2021-01-30",
            categories = listOf(
                OscarCategory.BEST_DOCUMENTARY_FEATURE)),
        OscarNomination(id = 0, itemId = 820492,
            type = ItemType.MOVIE,
            title = "Audible", imageUrl = "https://image.tmdb.org/t/p/w500/9oslTRMKWpdg96pvLRFogXh516x.jpg",
            releaseDate = "2021-04-29",
            categories = listOf(
                OscarCategory.BEST_DOCUMENTARY_SHORT)),
        OscarNomination(id = 0, itemId = 822582,
            type = ItemType.MOVIE,
            title = "The Queen of Basketball", imageUrl = "https://image.tmdb.org/t/p/w500/zgEDo7dlDoy9jHmcUUsDbSUnrzQ.jpg",
            releaseDate = "2021-06-10",
            categories = listOf(
                OscarCategory.BEST_DOCUMENTARY_SHORT)),
        OscarNomination(id = 0, itemId = 869602,
            type = ItemType.MOVIE,
            title = "Lead Me Home", imageUrl = "https://image.tmdb.org/t/p/w500/cnAF0z3HOnVAccQjAs9v5U4pTe7.jpg",
            releaseDate = "2021-09-03",
            categories = listOf(
                OscarCategory.BEST_DOCUMENTARY_SHORT)),
        OscarNomination(id = 0, itemId = 826740,
            type = ItemType.MOVIE,
            title = resources?.getString(R.string.oscar_three_songs_benazir) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/4Q1P44IPm9FyxBH4vmxM7pRgopP.jpg",
            releaseDate = "2021-06-02",
            categories = listOf(
                OscarCategory.BEST_DOCUMENTARY_SHORT)),
        OscarNomination(id = 0, itemId = 776769,
            type = ItemType.MOVIE,
            title = "When We Were Bullies", imageUrl = "https://image.tmdb.org/t/p/w500/t7NCC4dWEoWPMI3rVXkQUHWlIzE.jpg",
            releaseDate = "2021-01-28",
            categories = listOf(
                OscarCategory.BEST_DOCUMENTARY_SHORT)),
        OscarNomination(id = 0, itemId = 370172,
            type = ItemType.MOVIE,
            title = resources?.getString(R.string.oscar_007_no_time_die) ?: "",
            subtitle = "No time to die",
            imageUrl = "https://image.tmdb.org/t/p/w500/luE0KG1rWfUptbgNtlNtL5sihyd.jpg",
            releaseDate = "2021-09-29",
            categories = listOf(
                OscarCategory.BEST_SOUND_MIXING,
                OscarCategory.BEST_ORIGINAL_SONG,
                OscarCategory.BEST_VISUAL_EFFECTS)),
        OscarNomination(id = 0, itemId = 641960,
            type = ItemType.MOVIE,
            title = resources?.getString(R.string.oscar_four_good_days) ?: "",
            subtitle = "Somehow you do",
            imageUrl = "https://image.tmdb.org/t/p/w500/n3zOpdd8pWZrxgLHSGBLjLICFi1.jpg",
            releaseDate = "2021-04-30",
            categories = listOf(
                OscarCategory.BEST_ORIGINAL_SONG)),
        OscarNomination(id = 0, itemId = 484718,
            type = ItemType.MOVIE,
            title = resources?.getString(R.string.oscar_coming_2_america) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/mpXRG5zPUT18IeYvCoC4zOscked.jpg",
            releaseDate = "2021-03-04",
            categories = listOf(
                OscarCategory.BEST_MAKEUP_AND_HAIRSTYLING)),
        OscarNomination(id = 0, itemId = 601470,
            type = ItemType.MOVIE,
            title = resources?.getString(R.string.oscar_eyes_tammy) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/bxb83Bf5jLLzAv2CsXdRrAtHPNI.jpg",
            releaseDate = "2021-09-17",
            categories = listOf(
                OscarCategory.BEST_MAKEUP_AND_HAIRSTYLING)),
        OscarNomination(id = 0, itemId = 601470,
            type = ItemType.MOVIE,
            title = resources?.getString(R.string.oscar_house_gucci) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/dAPdK3L2STpHo4DFKwtL2gdo89L.jpg",
            releaseDate = "2021-09-17",
            categories = listOf(
                OscarCategory.BEST_MAKEUP_AND_HAIRSTYLING)),
        OscarNomination(id = 0, itemId = 550988,
            type = ItemType.MOVIE,
            title = "Free Guy",
            imageUrl = "https://image.tmdb.org/t/p/w500/jXlGeLOg2RKHmV9CinVaIB4ijKU.jpg",
            releaseDate = "2021-08-11",
            categories = listOf(
                OscarCategory.BEST_VISUAL_EFFECTS)),
        OscarNomination(id = 0, itemId = 566525,
            type = ItemType.MOVIE,
            title = resources?.getString(R.string.oscar_shang_chi_ten_rings) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/ArrOBeio968bUuUOtEpKa1teIh4.jpg",
            releaseDate = "2021-09-01",
            categories = listOf(
                OscarCategory.BEST_VISUAL_EFFECTS)),
        OscarNomination(id = 0, itemId = 634649,
            type = ItemType.MOVIE,
            title = resources?.getString(R.string.oscar_spider_man_no_way_home) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/fVzXp3NwovUlLe7fvoRynCmBPNc.jpg",
            releaseDate = "2021-12-15",
            categories = listOf(
                OscarCategory.BEST_VISUAL_EFFECTS)),
        OscarNomination(id = 0, itemId = 591538,
            type = ItemType.MOVIE,
            title = resources?.getString(R.string.oscar_tragedy_macbeth) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/pIKT6a3bD44a5sY4vVdP4xSPm9f.jpg",
            releaseDate = "2021-12-05",
            categories = listOf(
                OscarCategory.BEST_CINEMATOGRAPHY,
                OscarCategory.BEST_PRODUCTION_DESIGN)),
        OscarNomination(id = 0, itemId = 537116,
            type = ItemType.MOVIE,
            title = "tick, tick... BOOM!",
            imageUrl = "https://image.tmdb.org/t/p/w500/DPmfcuR8fh8ROYXgdjrAjSGA0o.jpg",
            releaseDate = "2021-11-11",
            categories = listOf(
                OscarCategory.BEST_EDITING)),

        // BEST_DIRECTING
        OscarNomination(id = 0, itemId = 11181, leastOneMovieId = 777270,
            type = ItemType.PERSON,
            title = "Kenneth Branagh",
            subtitle = "Belfast",
            imageUrl = "https://image.tmdb.org/t/p/w500/AbCqqFxNi5w3nDUFdQt0DGMFh5H.jpg",
            releaseDate = "2021-08-11",
            categories = listOf(
                OscarCategory.BEST_DIRECTING)),
        OscarNomination(id = 0, itemId = 1487492, leastOneMovieId = 758866,
            type = ItemType.PERSON,
            title = "Ryusuke Hamaguchi",
            subtitle = "Drive My Card",
            imageUrl = "https://image.tmdb.org/t/p/w500/cF1wv0I6YDz3rToo6fl3jBNMZcJ.jpg",
            releaseDate = "2021-08-18",
            categories = listOf(
                OscarCategory.BEST_DIRECTING)),
        OscarNomination(id = 0, itemId = 10757, leastOneMovieId = 600583,
            type = ItemType.PERSON,
            title = "Jane Campion",
            subtitle = resources?.getString(R.string.oscar_best_power_dog) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/vzvW3oWisk3n5UzeLTQU0tzfk6y.jpg",
            releaseDate = "2021-11-18",
            categories = listOf(
                OscarCategory.BEST_DIRECTING)),
        OscarNomination(id = 0, itemId = 488, leastOneMovieId = 511809,
            type = ItemType.PERSON,
            title = "Steven Spielberg",
            subtitle = resources?.getString(R.string.oscar_west_side_story) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/tZxcg19YQ3e8fJ0pOs7hjlnmmr6.jpg",
            releaseDate = "2021-12-02",
            categories = listOf(
                OscarCategory.BEST_DIRECTING)),
        OscarNomination(id = 0, itemId = 4762, leastOneMovieId = 718032,
            type = ItemType.PERSON,
            title = "Paul Thomas Anderson",
            subtitle = "Licorice Pizza",
            imageUrl = "https://image.tmdb.org/t/p/w500/29DHpRKddqTwo2NU162Y49JoQYa.jpg",
            releaseDate = "2021-12-08",
            categories = listOf(
                OscarCategory.BEST_DIRECTING)),

        // BEST_ACTRESS
        OscarNomination(id = 0, itemId = 83002, leastOneMovieId = 601470,
            type = ItemType.PERSON,
            title = "Jessica Chastain",
            subtitle = resources?.getString(R.string.oscar_eyes_tammy) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/lodMzLKSdrPcBry6TdoDsMN3Vge.jpg",
            releaseDate = "2021-08-11",
            categories = listOf(
                OscarCategory.BEST_ACTRESS)),
        OscarNomination(id = 0, itemId = 39187, leastOneMovieId = 554230,
            type = ItemType.PERSON,
            title = "Olivia Colman",
            subtitle = resources?.getString(R.string.oscar_lost_daughter) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/1KTXGJaqWRnsoA6qeaUa7U2zkHL.jpg",
            releaseDate = "2021-08-18",
            categories = listOf(
                OscarCategory.BEST_ACTRESS)),
        OscarNomination(id = 0, itemId = 955, leastOneMovieId = 766798,
            type = ItemType.PERSON,
            title = "Penélope Cruz",
            subtitle = resources?.getString(R.string.oscar_parallel_mothers) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/tU2ATiHHBAKn4SHqKOagYqdpHiy.jpg",
            releaseDate = "2021-11-18",
            categories = listOf(
                OscarCategory.BEST_ACTRESS)),
        OscarNomination(id = 0, itemId = 2227, leastOneMovieId = 517088,
            type = ItemType.PERSON,
            title = "Nicole Kidman",
            subtitle = resources?.getString(R.string.oscar_being_ricardos) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/dlcNtb7y0Be1pOWdsogowLEsOta.jpg",
            releaseDate = "2021-12-02",
            categories = listOf(
                OscarCategory.BEST_ACTRESS)),
        OscarNomination(id = 0, itemId = 37917, type = ItemType.PERSON,
            title = "Kristen Stewart", subtitle = "Spencer", leastOneMovieId = 716612,
            imageUrl = "https://image.tmdb.org/t/p/w500/ryhCjTGqS6G6OprbR0qUEH355lA.jpg",
            releaseDate = "2021-12-08",
            categories = listOf(
                OscarCategory.BEST_ACTRESS)),

        // BEST_ACTOR
        OscarNomination(id = 0, itemId = 3810, leastOneMovieId = 517088,
            type = ItemType.PERSON,
            title = "Javier Bardem",
            subtitle = resources?.getString(R.string.oscar_being_ricardos) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/gJTtH22fk7nAi45f7BN2P7DjvuE.jpg",
            releaseDate = "2021-08-11",
            categories = listOf(
                OscarCategory.BEST_ACTOR)),
        OscarNomination(id = 0, itemId = 71580, leastOneMovieId = 600583,
            type = ItemType.PERSON,
            title = "Benedict Cumberbatch",
            subtitle = resources?.getString(R.string.oscar_best_power_dog) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/fBEucxECxGLKVHBznO0qHtCGiMO.jpg",
            releaseDate = "2021-08-18",
            categories = listOf(
                OscarCategory.BEST_ACTOR)),
        OscarNomination(id = 0, itemId = 37625, leastOneMovieId = 537116,
            type = ItemType.PERSON,
            title = "Andrew Garfield",
            subtitle = "Tick, tick... Boom!",
            imageUrl = "https://image.tmdb.org/t/p/w500/h0A3pzHaNTVRD7xNfabuoyadJba.jpg",
            releaseDate = "2021-11-18",
            categories = listOf(
                OscarCategory.BEST_ACTOR)),
        OscarNomination(id = 0, itemId = 2888, leastOneMovieId = 614917,
            type = ItemType.PERSON,
            title = "Will Smith",
            subtitle = resources?.getString(R.string.oscar_king_richard) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/6a6cl4ZNufJzrx5HZKWPU1BjjRF.jpg",
            releaseDate = "2021-12-02",
            categories = listOf(
                OscarCategory.BEST_ACTOR)),
        OscarNomination(id = 0, itemId = 5292, leastOneMovieId = 591538,
            type = ItemType.PERSON,
            title = "Denzel Washington",
            subtitle = resources?.getString(R.string.oscar_tragedy_macbeth) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/8osNHZW1ZlBVp50xVdWH0fGu9Dx.jpg",
            releaseDate = "2021-12-08",
            categories = listOf(
                OscarCategory.BEST_ACTOR)),

        // BEST_SUPPORTING_ACTRESS
        OscarNomination(id = 0, itemId = 1498158, leastOneMovieId = 554230,
            type = ItemType.PERSON,
            title = "Jessie Buckley",
            subtitle = resources?.getString(R.string.oscar_lost_daughter) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/sreHqDq6AAujGDf7VsDzYPdxVlM.jpg",
            releaseDate = "2021-08-11",
            categories = listOf(
                OscarCategory.BEST_SUPPORTING_ACTRESS)),
        OscarNomination(id = 0, itemId = 1437491, leastOneMovieId = 511809,
            type = ItemType.PERSON,
            title = "Ariana DeBose",
            subtitle = resources?.getString(R.string.oscar_west_side_story) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/8HTSA2iVTsDN83OncAvFTcqxsAr.jpg",
            releaseDate = "2021-08-18",
            categories = listOf(
                OscarCategory.BEST_SUPPORTING_ACTRESS)),
        OscarNomination(id = 0, itemId = 5309, leastOneMovieId = 777270,
            type = ItemType.PERSON,
            title = "Judi Dench",
            subtitle = "Belfast",
            imageUrl = "https://image.tmdb.org/t/p/w500/1KqI8VYcbawaQYf0eIfajXqXTIn.jpg",
            releaseDate = "2021-11-18",
            categories = listOf(
                OscarCategory.BEST_SUPPORTING_ACTRESS)),
        OscarNomination(id = 0, itemId = 205, leastOneMovieId = 600583,
            type = ItemType.PERSON,
            title = "Kirsten Dunst",
            subtitle = resources?.getString(R.string.oscar_best_power_dog) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/sFYHUU1gWd57pttD8732tkBsXV5.jpg",
            releaseDate = "2021-12-02",
            categories = listOf(
                OscarCategory.BEST_SUPPORTING_ACTRESS)),
        OscarNomination(id = 0, itemId = 53923, leastOneMovieId = 614917,
            type = ItemType.PERSON,
            title = "Aunjanue Ellis",
            subtitle = resources?.getString(R.string.oscar_king_richard) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/rKLTo72uhxvQbkGw27nTjH1uCHA.jpg",
            releaseDate = "2021-12-08",
            categories = listOf(
                OscarCategory.BEST_SUPPORTING_ACTRESS)),

        // BEST_SUPPORTING_ACTOR
        OscarNomination(id = 0, itemId = 8785, leastOneMovieId = 777270,
            type = ItemType.PERSON,
            title = "Ciarán Hinds",
            subtitle = "Belfast",
            imageUrl = "https://image.tmdb.org/t/p/w500/fhtHm9SW5YH9AiAeN6Z3vOy2z6u.jpg",
            releaseDate = "2021-08-11",
            categories = listOf(
                OscarCategory.BEST_SUPPORTING_ACTOR)),
        OscarNomination(id = 0, itemId = 1571661, leastOneMovieId = 776503,
            type = ItemType.PERSON,
            title = "Troy Kotsur",
            subtitle = resources?.getString(R.string.oscar_best_coda) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/24K3og12jGBGL9dqH5OYFdjF9wu.jpg",
            releaseDate = "2021-08-18",
            categories = listOf(
                OscarCategory.BEST_SUPPORTING_ACTOR)),
        OscarNomination(id = 0, itemId = 88124, leastOneMovieId = 600583,
            type = ItemType.PERSON,
            title = "Jesse Plemons",
            subtitle = resources?.getString(R.string.oscar_best_power_dog) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/ckTthGclQE0y6b7gR0RpRo7LskL.jpg",
            releaseDate = "2021-11-18",
            categories = listOf(
                OscarCategory.BEST_SUPPORTING_ACTOR)),
        OscarNomination(id = 0, itemId = 18999, leastOneMovieId = 517088,
            type = ItemType.PERSON,
            title = "J.K. Simmons",
            subtitle = resources?.getString(R.string.oscar_being_ricardos) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/7kIiPojgSVNRXb5z0hiijcD5LJ6.jpg",
            releaseDate = "2021-12-02",
            categories = listOf(
                OscarCategory.BEST_SUPPORTING_ACTOR)),
        OscarNomination(id = 0, itemId = 113505, leastOneMovieId = 600583,
            type = ItemType.PERSON,
            title = "Kodi Smit-McPhee",
            subtitle = resources?.getString(R.string.oscar_best_power_dog) ?: "",
            imageUrl = "https://image.tmdb.org/t/p/w500/sesCWba9NwPDYDZzbVLs7OgLOti.jpg",
            releaseDate = "2021-12-08",
            categories = listOf(
                OscarCategory.BEST_SUPPORTING_ACTOR)),

    )
}