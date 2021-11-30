package me.arsam.github_client.utils

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "Bearer " + "ghp _5scbTyy0K9wxmEeQBOzzfvLGSmFOpL2w6NCA".replace(" ", "")
            )
            .build()

        return chain.proceed(request)
    }
}