package com.bolasepak.model

import com.google.gson.annotations.SerializedName

data class AllTeam(
    // Team Information
    @SerializedName("idTeam")
    var teamId: String? = null,

    @SerializedName("strTeam")
    var teamName: String? = null,

    @SerializedName("strTeamBadge")
    var teamBadge: String? = null
)