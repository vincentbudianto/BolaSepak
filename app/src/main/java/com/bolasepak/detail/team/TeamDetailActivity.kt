package com.bolasepak.detail.team

import android.os.Bundle
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.bolasepak.R
import com.bolasepak.api.ApiRepository
import com.bolasepak.event.*
import com.bolasepak.model.TeamDetail
import com.bolasepak.util.invisible
import com.bolasepak.util.visible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_team_detail.*
import okhttp3.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onRefresh
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class TeamDetailActivity : AppCompatActivity(), TeamDetailView {
    private lateinit var presenter: TeamDetailPresenter
    private lateinit var idTeam : String
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        supportActionBar?.title = "Match Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        progressBar = find(R.id.progress_bar_detail)
        swipeRefresh = find(R.id.swipe_refresh_detail)

        val intent = intent
        idTeam = intent.getStringExtra("team_id")
        val request = ApiRepository()
        val gson = Gson()

        presenter = TeamDetailPresenter(this, request, gson)
        presenter.getTeamDetail(idTeam)

        swipeRefresh.onRefresh {
            presenter.getTeamDetail(idTeam)
        }

        getBadge(idTeam)

//        val fragment = TeamMatchFragmentBefore.newInstance(idTeam)
//        addFragment(fragment)

        team_matches.adapter = ViewPagerAdapter(supportFragmentManager,
            mapOf(
                    getString(R.string.mendatang) to TeamMatchFragmentAfter.newInstance(idTeam),
                    getString(R.string.sebelum) to TeamMatchFragmentBefore.newInstance(idTeam)
            )
        )
        team_matches_tab.setupWithViewPager(team_matches)
        
    }

//    private fun addFragment(fragment: TeamMatchFragment) {
//        supportFragmentManager
//            .beginTransaction()
//            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
//            .replace(R.id.team_matches, fragment, fragment.javaClass.simpleName)
//            .commit()
//    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showNotFound() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showTeamDetail(data: List<TeamDetail>) {
        swipeRefresh.isRefreshing = false

        team_name.text = data[0].teamName
    }

    private fun getBadge(teamId: String) {
        val request = Request.Builder()
                .url("https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id="+teamId)
                .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {}

            override fun onResponse(call: Call, response: Response) {
                var res = response.body()?.string()

                runOnUiThread {
                    run() {
                        var json = JSONObject(res)
                        var badge = json.getJSONArray("teams").getJSONObject(0).getString("strTeamBadge")

                        Picasso.with(ctx).load(badge).into(team_logo)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

//    private fun addTeamMatchFragment(fragment: TeamMatchFragment) {
//        supportFragmentManager
//            .beginTransaction()
//            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
//            .replace(R.id.content, fragment, fragment.javaClass.simpleName)
//            .commit()
//    }
}


