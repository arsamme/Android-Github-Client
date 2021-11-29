package me.arsam.github_client.ui.shared.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.arsam.github_client.data.repositories.impl.TokenRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor(private val tokenRepository: TokenRepositoryImpl) :
    ViewModel() {

}