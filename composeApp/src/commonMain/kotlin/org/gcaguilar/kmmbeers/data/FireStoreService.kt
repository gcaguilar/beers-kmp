package org.gcaguilar.kmmbeers.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

class FireStoreService {
    private var user: FirebaseUser? = null

    private suspend fun login() {
        user = Firebase.auth.signInAnonymously().user
    }

    suspend fun clientId(): String {
        return withContext(Dispatchers.IO) {
            if (user == null) login()
            Firebase.firestore.collection("dread").get().documents.first().get("id")
        }
    }

    suspend fun clientSecret(): String {
        return withContext(Dispatchers.IO) {
            if (user == null) login()
            Firebase.firestore.collection("dread").get().documents.first().get("not_secret")
        }
    }
}
