package com.bolasepak.detail.event

import com.google.gson.Gson
import com.bolasepak.api.ApiRepository
import com.bolasepak.api.TheSportDBApi
import com.bolasepak.model.AllTeamResponse
import com.bolasepak.model.EventDetailResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EventDetailPresenter(private val view: EventDetailView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson){

    fun getEventDetail(eventId: String?){
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .request(TheSportDBApi.getEventDetail(eventId)),
                    EventDetailResponse::class.java)

            val team = gson.fromJson(apiRepository
                .request(TheSportDBApi.getAllTeam()),
                AllTeamResponse::class.java)


            uiThread {
                view.hideLoading()
                view.showEventDetail(data.events)
            }
        }
    }
}