package com.example.instagramclone.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.instagramclone.presentation.BottomNavigationItem
import com.example.instagramclone.presentation.BottomNavigationMenu
import com.example.instagramclone.presentation.Toast
import com.example.instagramclone.utils.Response

@Composable
fun ProfileScreen(
    navController: NavController,
    userProfileViewModel: UserProfileViewModel = hiltViewModel()
) {
    when(val response = userProfileViewModel.getUserData.value){
        is Response.Loading -> {

        }
        is Response.Success -> {
            Toast(message = "User info :${response.data.toString()}")
        }
        else ->{

        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Profile Screen")
        }
        BottomNavigationMenu(selectedItem = BottomNavigationItem.PROFILE, navController = navController)
    }

}