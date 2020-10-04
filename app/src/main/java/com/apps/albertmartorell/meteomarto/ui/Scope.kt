package com.apps.albertmartorell.meteomarto.ui

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Each entity that has a lifecycle will need the below functions.
 * So I have created this interface to avoid boilerplate code
 */
interface Scope : CoroutineScope {

    class Impl(override val uiDispatcher: CoroutineDispatcher) : Scope {

        override lateinit var job: Job

    }

    //In kotlin can have interfaces with code but no with state
    var job: Job
    val uiDispatcher: CoroutineDispatcher

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun initScope() {

        //A job created using SupervisorJob won’t fail when one of its children fails
        job = SupervisorJob()

    }

    fun destroyScope() {

        job.cancel()

    }

}
