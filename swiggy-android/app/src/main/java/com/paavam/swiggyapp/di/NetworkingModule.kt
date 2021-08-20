package com.paavam.swiggyapp.di

//@Module
//@InstallIn(SingletonComponent::class)
//object NetworkingModule {
//
//    @Provides
//    @Singleton
//    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
//        return OkHttpClient.Builder()
//            .addInterceptor(RequestInterceptor)
//            .cache(CoilUtils.createDefaultCache(context))
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
//        return Retrofit.Builder()
//            .client(okHttpClient)
//            .baseUrl(BuildConfig.SERVER_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//}