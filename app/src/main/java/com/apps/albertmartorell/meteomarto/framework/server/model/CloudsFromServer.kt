package com.apps.albertmartorell.meteomarto.framework.server.model

import com.google.gson.annotations.SerializedName

data class CloudsFromServer(@SerializedName("all") var coverage: Float? = 0F)
