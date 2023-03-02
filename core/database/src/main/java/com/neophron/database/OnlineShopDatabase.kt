package com.neophron.database
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//
//@Database(
//    version = 1,
//    entities = [],
//    exportSchema = true
//)
//abstract class OnlineShopDatabase : RoomDatabase() {
//
//    companion object {
//        @Volatile
//        private var database: OnlineShopDatabase? = null
//
//        fun getDatabase(appContext: Context): OnlineShopDatabase {
//            val temp1 = database
//            if (temp1 != null) return temp1
//
//            synchronized(this) {
//                val temp2 = database
//                if (temp2 != null) return temp2
//                val temp3 = Room.databaseBuilder(
//                    appContext,
//                    OnlineShopDatabase::class.java,
//                    "online_shop_db"
//                ).build()
//                database = temp3
//                return temp3
//            }
//        }
//    }
//
//}