package com.bolasepak.api

import com.bolasepak.BuildConfig

object TheSportDBApi {
    fun getEvent(league: String?, event: String?): String{
        return BuildConfig.TSDB_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/"+ event +".php?id=" + league
    }

    fun getAllLeague(): String{
        return BuildConfig.TSDB_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/all_leagues.php"
    }

    fun getAllTeam(teamId: String?): String{
        return BuildConfig.TSDB_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookup_all_teams.php?id=" + teamId
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
        return BuildConfig.TSDB_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventsnext.php?id=" + teamId
    }

    fun getLastEventbyID(teamId: String?): String{
        return BuildConfig.TSDB_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventslast.php?id=" + teamId
    }
}