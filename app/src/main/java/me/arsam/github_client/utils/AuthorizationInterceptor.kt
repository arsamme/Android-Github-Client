package me.arsam.github_client.utils

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "Bearer " + "gh p_5scbTy y0K9wxmEeQBOzzfv LGSmFOpL2w 6NCA".replace(" ", "")
            )
            .build()

        return chain.proceed(request)
    }
}