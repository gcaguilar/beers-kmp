package org.gcaguilar.kmmbeers.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

class FireStoreService {
    suspend fun clientId(): String {
        return withContext(Dispatchers.IO) {
            Firebase.firestore.collection("dread").get().documents.first().get("id")
        }
    }

    suspend fun clientSecret(): String {
        return withContext(Dispatchers.IO) {
            Firebase.firestore.collection("dread").get().documents.first().get("not_secret")
        }
    }
}
