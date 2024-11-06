package com.joshgm3z.room

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SomeView(userRepository: UserRepository) {
    val onClick: (User) -> Unit = {
        userRepository.updateAge(it)
    }
    val uiState = userRepository.uiState.collectAsState()
    LazyColumn(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        items(uiState.value) {
            UserView(it) {
                onClick(it)
            }
        }
    }
}

@Composable
fun UserView(
    user: User,
    onClick: () -> Unit
) {
    Text(user.toString(), modifier = Modifier.clickable { onClick() })
}
