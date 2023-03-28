package com.example.mvvmrepositorylab.local

import android.content.Context
import androidx.room.*
import com.example.mvvmrepositorylab.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User): Long // returns row Id on SUCCESS

    @Query("SELECT * FROM user_table")
    suspend fun getAllUsers(): List<User>

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE from user_table")
    suspend fun deleteAllUsers()
}

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null
        fun getInstance(context: Context): UserDatabase {

            return INSTANCE
                ?: synchronized(this) {
                    INSTANCE ?: Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java, "user_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                        .also { INSTANCE = it }
                }
        }
    }

}
