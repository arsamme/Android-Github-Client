package me.arsam.github_client

import android.content.Context
import com.apollographql.apollo.ApolloClient
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

class Apollo(context: Context) {
    val apolloClient = ApolloClient.builder()
        .serverUrl("https://api.github.com/graphql")
        .okHttpClient(
            OkHttpClient.Builder()
                .addInterceptor(AuthorizationInterceptor(context))
                .build()
        )
        .build()
}


private class AuthorizationInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer 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")
            .build()

        return chain.proceed(request)
    }
}