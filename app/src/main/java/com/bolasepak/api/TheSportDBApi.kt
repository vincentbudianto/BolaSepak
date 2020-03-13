package com.bolasepak.api

import com.bolasepak.BuildConfig

object TheSportDBApi {
    fun getEvent(league: String?, event: String?): String{
        return BuildConfig.TSDB_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/"+event+".php?id=" + league
    }

    fun getAllTeam(): String{
        return BuildConfig.TSDB_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/search_all_teams.php?l=English%20Premier%20League"
    }

    fun getEventDetail(eventId: String?): String{
        return BuildConfig.TSDB_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupevent.php?id=" + eventId
    }

    fun getEventbyName(event: String?): String{
        return BuildConfig.TSDB_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/searchevents.php?e=" + event
    }

    fun getTeambyID(teamId: String?): String{
        return BuildConfig.TSDB_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupteam.php?id=" + teamId
    }

    fun getNextEventbyID(teamId: String?): String{
        return "https://www.thesportsdb.com/api/v1/json/1/eventsnext.php?id=$teamId"
    }

    fun getLastEventbyID(teamId: String?): String{
        return "https://www.thesportsdb.com/api/v1/json/1/eventslast.php?id=$teamId"
    }
}