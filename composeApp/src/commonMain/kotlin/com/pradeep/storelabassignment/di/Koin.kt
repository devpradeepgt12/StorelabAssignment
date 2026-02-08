package com.pradeep.storelabassignment.di

import com.pradeep.storelabassignment.data.remote.PicsumApi
import com.pradeep.storelabassignment.data.remote.PicsumApiImpl
import com.pradeep.storelabassignment.data.repository.GalleryRepositoryImpl
import com.pradeep.storelabassignment.domain.model.PicsumImage
import com.pradeep.storelabassignment.domain.repository.GalleryRepository
import com.pradeep.storelabassignment.presentation.detail.DetailViewModel
import com.pradeep.storelabassignment.presentation.gallery.GalleryViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

/**
 * Koin DI module for app
 */
val appModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }
    // Bind implementation to interface
    single<PicsumApi> { PicsumApiImpl(get()) }
    single<GalleryRepository> { GalleryRepositoryImpl(get()) }

    // ViewModels
    factory { GalleryViewModel(get()) }
    factory { (image: PicsumImage) -> DetailViewModel(image) }
}

// Helper function to start Koin
fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration() // Allows Android to pass its specific context
    modules(appModule)
}