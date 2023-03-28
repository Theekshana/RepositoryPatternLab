package com.example.mvvmrepositorylab.repository

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.mvvmrepositorylab.local.UserDao
import com.example.mvvmrepositorylab.local.UserDatabase
import com.example.mvvmrepositorylab.model.User
import com.example.mvvmrepositorylab.remote.ApiInterface
import com.example.mvvmrepositorylab.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface UserRepository {

    suspend fun getUsers() : List<User>
}

class UserRepositoryImpl(private val userDao: UserDao , private val userService: ApiInterface) : UserRepository {


    //private lateinit var userService: ApiInterface
    //private lateinit var userDao: UserDao


    /*init {
        initRetrofit()
        //initDb()
    }*/

    private suspend fun getUsersFromRemoteService() : List<User>{
        var users: List<User> = listOf()
        val response = userService.getUsers()
        if (response.isSuccessful) {
            val userResponse = response.body()
            users = userResponse?.data!!
            Log.d("LNBTI", "Remote user data fetching SUCCESSFUL users size: ${users.size}")
        }
        return users
    }

    /*private fun initRetrofit(){
        var retrofit = RetrofitClient.getInstance()
        userService = retrofit.create(ApiInterface::class.java)
    }
*/


    override suspend fun getUsers(): List<User> {
            var users: List<User>
            return withContext(Dispatchers.IO){
                users = getUsersFromRemoteService()
                if (users.isNotEmpty())
                    addUsersToLocalDb(users)

                return@withContext getUsersFromLocalDb()
            }
        }

    private suspend fun addUsersToLocalDb(users: List<User>) {
        var rowIds = mutableListOf<Long>()
        for (user in users) {
            // do not send user.id to DB -> to avoid UNIQUE constraint failure
            val userWithoutId = User(firstName = user.firstName,
                lastName = user.lastName,
                email = user.email,
                avatar = user.avatar)
            rowIds.add(userDao.insertUser(userWithoutId))
        }
        Log.d("LNBTI", "Local user data saving SUCCESSFUL users row Ids: $rowIds")
    }




    suspend fun getUsersFromLocalDb():List<User>{
        var users = userDao.getAllUsers()
        Log.d("LNBTI", "Local user data fetching SUCCESSFUL users size: ${users.size}")
        return users


    }

    /*private fun initDb(){
        userDao = UserDatabase.getInstance().userDao() // TODO: context is required
    }*/

}

