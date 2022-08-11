package com.example.chatwithme.domain.usecase.profileScreen

import android.net.Uri
import com.example.chatwithme.domain.repository.ProfileScreenRepository

class UploadPictureToFirebase(
    private val profileScreenRepository: ProfileScreenRepository
) {
    suspend operator fun invoke(url: Uri) = profileScreenRepository.uploadPictureToFirebase(url)
}