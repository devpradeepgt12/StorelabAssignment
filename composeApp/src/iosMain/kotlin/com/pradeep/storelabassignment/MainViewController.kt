package com.pradeep.storelabassignment

import androidx.compose.ui.window.ComposeUIViewController
import com.pradeep.storelabassignment.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }