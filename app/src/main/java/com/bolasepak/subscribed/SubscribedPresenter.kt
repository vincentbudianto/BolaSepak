package com.bolasepak.subscribed

import com.google.gson.Gson
import com.bolasepak.api.ApiRepository
import com.bolasepak.api.TheSportDBApi
import com.bolasepak.model.AllTeamResponse
import com.bolasepak.model.EventResponse
import com.bolasepak.model.EventSearchResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SubscribedPresenter(private val view: SubscribedView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson){
    fun getSubscribedList(league: String?, event: String?){
        view.showLoading()

        doAsync {
            val data1 = gson.fromJson(apiRepository
                    .request(TheSportDBApi.getEvent(league, event)),
                    EventResponse::class.java)
            val data2 = gson.fromJson(apiRepository
                .request(TheSportDBApi.getAllTeam()),
                AllTeamResponse::class.java)

            uiThread {
                view.hideLoading()
                view.showSubscribedList(data1.events, data2.teams)
            }
        }
    }

    fun getSubscribedSearch(name: String?){
        view.showLoading()

        doAsync {
            val data1 = gson.fromJson(apiRepository
                    .request(TheSportDBApi.getEventbyName(name)),
                    EventSearchResponse::class.java)
            val data2 = gson.fromJson(apiRepository
                .request(TheSportDBApi.getAllTeam()),
                AllTeamResponse::class.java)

            uiThread {
                view.hideLoading()

                if (data1.event.isNullOrEmpty()){
                    view.showNotFound()
                } else {
                    view.showSubscribedList(data1.event, data2.teams)
                }
            }
        }
    }
}
