package com.bolasepak.event

import android.content.Context
import android.database.Cursor
import com.google.gson.Gson
import com.bolasepak.api.ApiRepository
import com.bolasepak.api.TheSportDBApi
import com.bolasepak.model.AllTeamResponse
import com.bolasepak.model.Event
import com.bolasepak.model.EventResponse
import com.bolasepak.model.EventSearchResponse
import com.bolasepak.sqlite.EventDB
import com.bolasepak.sqlite.OpenHelper
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EventPresenter(private val view: EventView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson){
    fun getEventList(league: String?, event: String?, context: Context){
        view.showLoading()

        doAsync {

            val res1 = apiRepository.request(TheSportDBApi.getEvent(league, event))
            val res2 = apiRepository.request(TheSportDBApi.getAllTeam())
            addToDB(context, res1, res2)
            val data1 = gson.fromJson(res1, EventResponse::class.java)
            val data2 = gson.fromJson(res2, AllTeamResponse::class.java)

            uiThread {
                view.hideLoading()
                view.showEventList(data1.events.filter { it.leagueId == "4328" }, data2.teams)
            }
        }
    }

    fun getEventListDB(context: Context){
        view.showLoading()

        doAsync {
            val cursor = getAllFromDB(context)
            cursor.moveToFirst()
            val res1 = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_NAME_1))
            val res2 = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_NAME_2))
            val data1 = gson.fromJson(res1, EventResponse::class.java)
            val data2 = gson.fromJson(res2, AllTeamResponse::class.java)
            uiThread {
                view.hideLoading()
                view.showEventList(data1.events.filter { it.leagueId=="4328" }, data2.teams)
            }
        }
    }

    fun getEventSearch(name: String?){
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
                    view.showEventList(data1.event.filter { it.leagueId == "4328" }, data2.teams)
                }
            }
        }
    }

    fun addToDB(context: Context, responseEvent: String, responseTeam: String){
        val dbHelper = OpenHelper(context, null)
        val eventDB = EventDB(responseEvent, responseTeam)
        dbHelper.addEvent(eventDB)
    }

    fun getAllFromDB(context: Context) : Cursor {
        val dbHelper = OpenHelper(context, null)
        return dbHelper.getAllResponse()
    }
}
