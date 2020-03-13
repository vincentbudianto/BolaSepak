package com.bolasepak.detail.event

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bolasepak.BuildConfig
import com.google.gson.Gson
import com.bolasepak.R
import com.bolasepak.api.ApiRepository
import com.bolasepak.detail.team.TeamDetailActivity
import com.bolasepak.model.AllTeam
import com.bolasepak.model.EventDetail
import com.bolasepak.util.invisible
import com.bolasepak.util.visible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_event_detail.*
import okhttp3.*
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onRefresh
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class EventDetailActivity : AppCompatActivity(), EventDetailView {
    private lateinit var presenter: EventDetailPresenter
    private lateinit var idEvent: String
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private var idHome: String = ""
    private var idAway: String = ""
    private var location: String = ""
    val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        supportActionBar?.title = "Match Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        progressBar = find(R.id.progress_bar_detail)
        swipeRefresh = find(R.id.swipe_refresh_detail)

        val intent = intent

        idEvent = intent.getStringExtra("id")
        idHome = intent.getStringExtra("idhome")
        idAway = intent.getStringExtra("idaway")
        location = intent.getStringExtra("location")

        var city = location.split(",")?.last().trim()

        val request = ApiRepository()
        val gson = Gson()

        presenter = EventDetailPresenter(this, request, gson)
        presenter.getEventDetail(idEvent)

        swipeRefresh.onRefresh {
            presenter.getEventDetail(idEvent)
        }

        val logo = arrayOf(idHome, idAway)

        getBadge(logo)
        getWeather(city)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showEventDetail(data1: List<EventDetail>, data2: List<AllTeam>) {
        swipeRefresh.isRefreshing = false

        val homeId = data1[0].idHome
        val home = data2.indexOfFirst { it.teamId == homeId }

        val tanggal = SimpleDateFormat("EEE, d MMM yyyy")
            .format(convertToGMT(data1[0].eventDate, data1[0].eventTime))
        val waktu = SimpleDateFormat("HH:mm")
            .format(convertToGMT(data1[0].eventDate, data1[0].eventTime))

        date_detail.text = tanggal
        time_detail.text = waktu
        home_detail.text = data1[0].teamHome
        away_detail.text = data1[0].teamAway

        if (data1[0].scoreHome.isNullOrEmpty() && data1[0].scoreAway.isNullOrEmpty()) {
            skor_detail.text = "0 vs 0"
        } else {
            skor_detail.text = data1[0].scoreHome + "  vs  " + data1[0].scoreAway
        }

        home_formation.text = data1[0].formationHome
        away_formation.text = data1[0].formationAway

        stadium_name.text = data2[home].teamStadium + " Stadium - " + data2[home].teamStadiumLoc
        var stadium_img = data2[home].teamStadiumImg
        Picasso.get().load(stadium_img).into(img_stadium)

        home_goals.text = data1[0].goalHome?.replace(";", "\n")
        away_goals.text = data1[0].goalAway?.replace(";", "\n")

        home_shots.text = data1[0].shotHome
        away_shots.text = data1[0].shotAway

        home_ycards.text = data1[0].ycardsHome?.replace(";", "\n")
        away_ycards.text = data1[0].ycardsAway?.replace(";", "\n")

        home_rcards.text = data1[0].rcardsHome?.replace(";", "\n")
        away_rcards.text = data1[0].rcardsAway?.replace(";", "\n")

        home_gk.text = cleanString(data1[0].gkHome)
        away_gk.text = cleanString(data1[0].gkAway)
        home_def.text = cleanString(data1[0].defHome)
        away_def.text = cleanString(data1[0].defAway)
        home_mid.text = cleanString(data1[0].midHome)
        away_mid.text = cleanString(data1[0].midAway)
        home_forward.text = cleanString(data1[0].forwHome)
        away_forward.text = cleanString(data1[0].forwAway)
        home_substitute.text = cleanString(data1[0].subsHome)
        away_substitute.text = cleanString(data1[0].subsAway)
    }

    private fun getBadge(logo: Array<String>) {
        for (i in 0..1) {
            val request = Request.Builder()
                    .url("https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id="+logo[i])
                    .build()

            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {}

                override fun onResponse(call: Call, response: Response) {
                    var res = response.body()?.string()

                    runOnUiThread {
                        run() {
                            var json = JSONObject(res)
                            var badge = json.getJSONArray("teams").getJSONObject(0).getString("strTeamBadge")

                            if (i == 0) {
                                Picasso.get().load(badge).into(img_home)
                            } else {
                                Picasso.get().load(badge).into(img_away)
                            }
                        }
                    }
                }
            })
        }
    }

    private fun getWeather(city: String) {
        val request = Request.Builder()
            .url(BuildConfig.OW_URL + "weather?q=" + city + "&appid=" + BuildConfig.OW_API_KEY)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}

            override fun onResponse(call: Call, response: Response) {
                var res = response.body()?.string()

                runOnUiThread {
                    run() {
                        var data = JSONObject(res)

                        weather.text = data.getJSONArray("weather").getJSONObject(0).getString("main")
                        var iconId = data.getJSONArray("weather").getJSONObject(0).getString("icon")
                        var icon = "http://openweathermap.org/img/w/" + iconId + ".png"
                        Picasso.get().load(icon).into(img_weather)
                    }
                }
            }
        })
    }

    private fun convertToGMT(date: String?, time: String?): Date? {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val dateTime = "$date $time"

        return formatter.parse(dateTime)
    }

    private fun cleanString(pemain: String?): String? {
        return pemain?.replace("; ", "\n")
    }

    fun goToHomeTeamDetail(view: View){
        val intent = Intent(this, TeamDetailActivity::class.java).apply {
            putExtra("team_id", idHome)
        }
        startActivity(intent)
    }

    fun goToAwayTeamDetail(view: View){
        val intent = Intent(this, TeamDetailActivity::class.java).apply {
            putExtra("team_id", idAway)
        }
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}


