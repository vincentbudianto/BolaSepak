package com.bolasepak.detail.event

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.bolasepak.R
import com.bolasepak.api.ApiRepository
import com.bolasepak.detail.team.TeamDetailActivity
import com.bolasepak.model.EventDetail
import com.bolasepak.util.invisible
import com.bolasepak.util.visible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_event_detail.*
import okhttp3.*
import org.jetbrains.anko.ctx
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

        val request = ApiRepository()
        val gson = Gson()

        presenter = EventDetailPresenter(this, request, gson)
        presenter.getEventDetail(idEvent)

        swipeRefresh.onRefresh {
            presenter.getEventDetail(idEvent)
        }

        val logo = arrayOf(idHome, idAway)

        getBadge(logo)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showEventDetail(data: List<EventDetail>) {
        swipeRefresh.isRefreshing = false

        val tanggal = SimpleDateFormat("EEE, d MMM yyyy")
            .format(convertToGMT(data[0].eventDate, data[0].eventTime))
        val waktu = SimpleDateFormat("HH:mm")
            .format(convertToGMT(data[0].eventDate, data[0].eventTime))

        date_detail.text = tanggal
        time_detail.text = waktu
        home_detail.text = data[0].teamHome
        away_detail.text = data[0].teamAway

        if (data[0].scoreHome.isNullOrEmpty() && data[0].scoreAway.isNullOrEmpty()) {
            skor_detail.text = "0 vs 0"
        } else {
            skor_detail.text = data[0].scoreHome + "  vs  " + data[0].scoreAway
        }

        home_goals.text = data[0].goalHome?.replace(";", "\n")
        away_goals.text = data[0].goalAway?.replace(";", "\n")
        home_shots.text = data[0].shotHome
        away_shots.text = data[0].shotAway
        home_gk.text = cleanString(data[0].gkHome)
        away_gk.text = cleanString(data[0].gkAway)
        home_def.text = cleanString(data[0].defHome)
        away_def.text = cleanString(data[0].defAway)
        home_mid.text = cleanString(data[0].midHome)
        away_mid.text = cleanString(data[0].midAway)
        home_forward.text = cleanString(data[0].forwHome)
        away_forward.text = cleanString(data[0].forwAway)
        home_substitute.text = cleanString(data[0].subsHome)
        away_substitute.text = cleanString(data[0].subsAway)
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
                                Picasso.with(ctx).load(badge).into(img_home)
                            } else {
                                Picasso.with(ctx).load(badge).into(img_away)
                            }
                        }
                    }
                }
            })
        }
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


