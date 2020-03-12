package com.bolasepak.model

import com.google.gson.annotations.SerializedName

data class Event(
        var stepCounter: String? = null,
        var stepComment: String? = null,

        @SerializedName("idEvent")
        var eventId: String? = null,

        @SerializedName("dateEvent")
        var eventDate: String? = null,

        //home

        @SerializedName("idHomeTeam")
        var idHome: String? = null,

        @SerializedName("strHomeTeam")
        var teamHome: String? = null,

        @SerializedName("intHomeScore")
        var scoreHome: String? = null,

        //away

        @SerializedName("idAwayTeam")
        var idAway: String? = null,

        @SerializedName("strAwayTeam")
        var teamAway: String? = null,

        @SerializedName("intAwayScore")
        var scoreAway: String? = null

)