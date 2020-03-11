package com.bolasepak.event

import com.google.gson.Gson
import com.bolasepak.api.ApiRepository
import com.bolasepak.api.TheSportDBApi
import com.bolasepak.model.EventResponse
import com.bolasepak.model.EventSearchResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EventPresenter(private val view: EventView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson){
    fun getEventList(league: String?, event: String?){
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .request(TheSportDBApi.getEvent(league, event)),
                    EventResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showEventList(data.events)
            }
        }
    }

    fun getEventSearch(name: String?){
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .request(TheSportDBApi.getEventbyName(name)),
                    EventSearchResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showEventList(data.event)
            }
        }
    }
}
