package com.joshgm3z.room

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserRepository(
    context: Context,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO),
    private val mainScope: CoroutineScope = CoroutineScope(Dispatchers.Main),
) {
    fun updateAge(user: User) {
        scope.launch {
            db.userDao().updateAge(user.uid, user.age + 1)
        }
    }

    private val db = Room.databaseBuilder(
        context,
        AndroidDemoRoomDb::class.java, "android-demo-room-db"
    ).build()


    private val _uiState = MutableStateFlow(emptyList<User>())
    val uiState = _uiState.asStateFlow()

    init {
        scope.launch {
            val users = mutableListOf<User>()
            repeat(5) {
                users.add(User(uid = it, firstName = "guy $it", lastName = "guy sson $it", age = 3))
            }
            db.userDao().insertAll(users)
        }
        scope.launch {
            db.userDao().getAll().collectLatest {
                _uiState.value = it
            }
        }
    }
}