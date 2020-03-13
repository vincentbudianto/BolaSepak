package com.bolasepak.detail.team

import android.util.Log
import com.google.gson.Gson
import com.bolasepak.api.ApiRepository
import com.bolasepak.api.TheSportDBApi
import com.bolasepak.event.EventView
import com.bolasepak.model.EventResponse
import com.bolasepak.model.TeamDetailResponse
import com.bolasepak.model.TeamMatchEventResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.IllegalStateException

class TeamDetailPresenter(private val view: TeamDetailView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson){

    fun getTeamDetail(teamId: String?){
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .request(TheSportDBApi.getTeambyID(teamId)),
                    TeamDetailResponse::class.java
            )

            uiThread {
                view.hideLoading()
                if(data.teams != null){
                    Log.d("GAK NULL", "yey")
                }
                else{
                    Log.d("NULL BRO", "yah")
                }
                view.showTeamDetail(data.teams)
            }
        }
    }
}

class TeamMatchPresenter(private val view: TeamMatchEventView,
                         private val apiRepository: ApiRepository,
                         private val gson: Gson,
                         private val matchTime: String){

    fun getTeamMatches(teamId: String?){
        view.showLoading()

        if(matchTime == "sebelum") {
            doAsync {

                val data = gson.fromJson(
                    apiRepository
                        .request(TheSportDBApi.getLastEventbyID(teamId)),
                    TeamMatchEventResponse::class.java
                )

                uiThread {
                    view.hideLoading()
                    if (data.results != null) {
                        Log.d("TEAM MATCH", "GAK NULL")
                    } else {
                        Log.d("TEAM MATCH", "NULL BRO")
                    }
                    view.showEventList(data.results)
                }
            }
        }
        else{
            doAsync {

                val data = gson.fromJson(
                    apiRepository
                        .request(TheSportDBApi.getNextEventbyID(teamId)),
                    TeamMatchEventResponse::class.java
                )

                uiThread {
                    view.hideLoading()
                    if (data.events != null) {
                        Log.d("TEAM MATCH", "GAK NULL")
                    } else {
                        Log.d("TEAM MATCH", "NULL BRO")
                    }
                    view.showEventList(data.events)
                }
            }
        }
    }
}