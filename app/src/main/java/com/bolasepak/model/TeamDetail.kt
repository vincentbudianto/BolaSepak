package com.bolasepak.model

import com.google.gson.annotations.SerializedName

data class TeamDetail(
        // Team Information
        @SerializedName("idTeam")
        var teamId: String? = null,

        @SerializedName("strTeam")
        var teamName: String? = null,

        @SerializedName("intFormedYear")
        var teamFormed: String? = null,

        @SerializedName("strLeague")
        var teamLeague: String? = null,

        @SerializedName("strWebsite")
        var teamWebsite: String? = null,

        @SerializedName("strFacebook")
        var teamFacebook: String? = null,

        @SerializedName("strTwitter")
        var teamTwitter: String? = null,

        @SerializedName("strInstagram")
        var teamInstagram: String? = null,

        @SerializedName("strDescriptionEN")
        var teamDesc: String? = null,

        @SerializedName("strCountry")
        var teamCountry: String? = null,

        @SerializedName("strTeamBadge")
        var teamBadge: String? = null,

        @SerializedName("strTeamJersey")
        var teamJersey: String? = null,

        @SerializedName("strTeamLogo")
        var teamLogo: String? = null,

        @SerializedName("strTeamBanner")
        var teamBanner: String? = null,

        @SerializedName("strYoutube")
        var teamYoutube: String? = null,

        // Stadium Information
        @SerializedName("strStadium")
        var teamStadium: String? = null,

        @SerializedName("strStadiumThumb")
        var stadiumPic: String? = null,

        @SerializedName("strStadiumLocation")
        var stadiumLoc: String? = null,

        @SerializedName("intStadiumCapacity")
        var stadiumCap: String? = null
)