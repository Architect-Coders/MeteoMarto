package com.apps.albertmartorell.meteomarto.ui

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Each entity that has a lifecycle will need the below functions.
 * So I have created this interface to avoid boilerplate code
 */
abstract class ScopedViewModel(uiDispatcher: CoroutineDispatcher) : ViewModel(),
    Scope by Scope.Impl(uiDispatcher) {

    init {

        initScope()

    }

    @CallSuper
    override fun onCleared() {

        destroyScope()
        super.onCleared()

    }

}
