package me.arsam.github_client

import com.apollographql.apollo.ApolloClient
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

class Apollo {
    val apolloClient: ApolloClient = ApolloClient.builder()
        .serverUrl("https://api.github.com/graphql")
        .okHttpClient(
            OkHttpClient.Builder()
                .addInterceptor(AuthorizationInterceptor())
                .build()
        )
        .build()
}


private class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer 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")
            .build()

        return chain.proceed(request)
    }
}