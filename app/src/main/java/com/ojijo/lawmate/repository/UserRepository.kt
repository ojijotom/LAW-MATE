package com.ojijo.lawmate.repository

import com.ojijo.lawmate.data.UserDao
import com.ojijo.lawmate.model.User

class UserRepository(private val userDao: UserDao) {
    suspend fun registerUser(user: User) {
        userDao.registerUser(user)
    }

    suspend fun loginUser(email: String, password: String): User? {
        return userDao.loginUser(email, password)
    }
}