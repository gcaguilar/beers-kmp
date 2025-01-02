package org.gcaguilar.kmmbeers.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.component.KoinComponent

class FireStoreService(private val firebase: Firebase) : KoinComponent {
    suspend fun getClientId(): String {
        return withContext(Dispatchers.IO) {
            firebase.firestore.collection("dread").get().documents.first().get("id")
        }
    }

    suspend fun getClientSecret(): String {
        return withContext(Dispatchers.IO) {
            firebase.firestore.collection("dread").get().documents.first().get("not_secret")
        }
    }
}
