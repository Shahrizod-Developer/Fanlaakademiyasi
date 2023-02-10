package uz.smartmuslim.fanlarakademiyasi.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.screen.fragment.main.page.AnsweredPage
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.screen.fragment.main.page.ReadPage
import uz.smartmuslim.fanlarakademiyasi.presentation.ui.screen.fragment.main.page.UnreadPage

class ViewPagerAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UnreadPage()
            1 -> ReadPage()
            2 -> AnsweredPage()
            else -> UnreadPage()
        }
    }
}