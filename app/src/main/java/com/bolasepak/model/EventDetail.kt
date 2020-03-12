package com.bolasepak.model

import com.google.gson.annotations.SerializedName

data class EventDetail(
        @SerializedName("idEvent")
        var eventId: String? = null,

        @SerializedName("dateEvent")
        var eventDate: String? = null,

        @SerializedName("strTime")
        var eventTime: String? = null,

//        @SerializedName("?") //ubah ? sma val buat get weather
//        var weather: String? = null,

        //Home Team
        @SerializedName("idHomeTeam")
        var idHome: String? = null,

        @SerializedName("strHomeTeam")
        var teamHome: String? = null,

        @SerializedName("intHomeScore")
        var scoreHome: String? = null,

        @SerializedName("strHomeGoalDetails")
        var goalHome: String? = null,

        @SerializedName("intHomeShots")
        var shotHome: String? = null,

        @SerializedName("strHomeLineupGoalkeeper")
        var gkHome: String? = null,

        @SerializedName("strHomeLineupDefense")
        var defHome: String? = null,

        @SerializedName("strHomeLineupMidfield")
        var midHome: String? = null,

        @SerializedName("strHomeLineupForward")
        var forwHome: String? = null,

        @SerializedName("strHomeLineupSubstitutes")
        var subsHome: String? = null,

        //Away Team
        @SerializedName("idAwayTeam")
        var idAway: String? = null,

        @SerializedName("strAwayTeam")
        var teamAway: String? = null,

        @SerializedName("intAwayScore")
        var scoreAway: String? = null,

        @SerializedName("strAwayGoalDetails")
        var goalAway: String? = null,

        @SerializedName("intAwayShots")
        var shotAway: String? = null,

        @SerializedName("strAwayLineupGoalkeeper")
        var gkAway: String? = null,

        @SerializedName("strAwayLineupDefense")
        var defAway: String? = null,

        @SerializedName("strAwayLineupMidfield")
        var midAway: String? = null,

        @SerializedName("strAwayLineupForward")
        var forwAway: String? = null,

        @SerializedName("strAwayLineupSubstitutes")
        var subsAway: String? = null
)