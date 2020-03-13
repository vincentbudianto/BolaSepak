package com.bolasepak.event

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bolasepak.R
import com.bolasepak.model.Event
import com.bolasepak.model.TeamMatchEvent
import kotlinx.android.synthetic.main.item_event.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.text.SimpleDateFormat

class TeamMatchAdapter(private val context: Context, private val events: List<TeamMatchEvent>, private val listener: (TeamMatchEvent) -> Unit)
    : RecyclerView.Adapter<TeamMatchViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamMatchViewHolder {
        return TeamMatchViewHolder(LayoutInflater.from(context).inflate(R.layout.item_event, parent, false))
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: TeamMatchViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }

}

class TeamMatchViewHolder(view: View): RecyclerView.ViewHolder(view) {
    fun bindItem(events: TeamMatchEvent, listener: (TeamMatchEvent) -> Unit){
        if (events.scoreHome.isNullOrEmpty() && events.scoreAway.isNullOrEmpty()) {
            itemView.skor.text = "VS"
        } else {
            itemView.skor.text = events.scoreHome+" VS "+events.scoreAway
        }

        var  date = SimpleDateFormat("EEE, d MMM yyyy")
                .format(SimpleDateFormat("yyyy-MM-dd")
                        .parse(events.eventDate))

        itemView.date.text = date
        itemView.home.text = events.teamHome
        itemView.away.text = events.teamAway
        itemView.onClick { listener(events) }
    }
}

class ViewPagerAdapter(supportFragmentManager: FragmentManager, private val map: Map<String, Fragment>) : FragmentStatePagerAdapter(supportFragmentManager) {

    private val titles = map.keys.toList()
    private val fragments = map.values.toList()

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = map.size

    override fun getPageTitle(position: Int): CharSequence? = titles[position]
}

//class TeamMatchPagerAdapter(fm: FragmentManager, teamID: String): FragmentPagerAdapter(fm){
//
//    // sebuah list yang menampung objek Fragment
//    private val pages = listOf(
//        TeamMatchFragmentBefore(teamID),
//        TeamMatchFragmentAfter(teamID)
//    )
//
//    // menentukan fragment yang akan dibuka pada posisi tertentu
//    override fun getItem(position: Int): Fragment {
//        return pages[position]
//    }
//
//    override fun getCount(): Int {
//        return pages.size
//    }
//
//    // judul untuk tabs
//    override fun getPageTitle(position: Int): CharSequence? {
//        return when(position){
//            0 -> "Before Tab"
//            1 -> "After Tab"
//        }
//    }
//}
