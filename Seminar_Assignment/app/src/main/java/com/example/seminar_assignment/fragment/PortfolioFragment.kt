package com.example.seminar_assignment.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seminar_assignment.etc.ItemMoveCallback
import com.example.seminar_assignment.etc.MySharedPreferences
import com.example.seminar_assignment.R
import com.example.seminar_assignment.activity.MainActivity
import com.example.seminar_assignment.portfolio.PortfolioAdapter
import com.example.seminar_assignment.portfolio.PortfolioData
import com.example.seminar_assignment.portfolio.PortfolioGridAdapter
import kotlinx.android.synthetic.main.fragment_portfolio.*
import kotlinx.android.synthetic.main.fragment_portfolio.fragment_portfolio_rv_grid_profile

class PortfolioFragment : Fragment() {
    private lateinit var portfolioAdapter: PortfolioAdapter
    private lateinit var portfolioGridAdapter: PortfolioGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_portfolio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 레이아웃 변경
        fragment_portfolio_btn_change_layout.setOnClickListener {
            if(fragment_portfolio_btn_change_layout.text == "Grid Version") {
                fragment_portfolio_btn_change_layout.text = "Linear Version"
                fragment_portfolio_rv_profile.visibility = GONE
                fragment_portfolio_rv_grid_profile.visibility = VISIBLE
            } else {
                fragment_portfolio_btn_change_layout.text = "Grid Version"
                fragment_portfolio_rv_profile.visibility = VISIBLE
                fragment_portfolio_rv_grid_profile.visibility = GONE
            }

        }

        // linear recyclerview
        portfolioAdapter = PortfolioAdapter(view.context)

        fragment_portfolio_rv_profile.adapter = portfolioAdapter
        fragment_portfolio_rv_profile.layoutManager = LinearLayoutManager(view.context)

        portfolioAdapter.data = loadData()

        val callback = ItemMoveCallback(portfolioAdapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(fragment_portfolio_rv_profile)

        portfolioAdapter.notifyDataSetChanged()


        // grid recyclerview
        portfolioGridAdapter = PortfolioGridAdapter(view.context)

        fragment_portfolio_rv_grid_profile.adapter = portfolioGridAdapter
        fragment_portfolio_rv_grid_profile.layoutManager = GridLayoutManager(view.context, 3, RecyclerView.VERTICAL, false)

        portfolioGridAdapter.data = loadData()

        portfolioGridAdapter.notifyDataSetChanged()

    }

    fun loadData(): MutableList<PortfolioData> {
        return mutableListOf(
            PortfolioData("SWU",
                "Information Security",
                "2016. 03 ~ 2021. 02\nSWU\nDept. of Information Security",
                "2020/10/20"),
            PortfolioData("SOPT", "26th YB", "2020. 03 ~ 2020. 07\n26기 안드로이드파트 YB", "2020/10/20"),
            PortfolioData("MONGLE",
                "Android Developer",
                "2020. 07\nAndroid Developer\n몽글 ♡",
                "2020/10/20"),
            PortfolioData("SOPT", "27th OB", "2020. 09 ~ 2021. 01\n27기 안드로이드파트 OB", "2020/10/21"),
            PortfolioData("STUDY",
                "27th Study",
                "1. 안드로이드 심화스터디\n2. 기상 스터디\n3. 모각공 스터디\n4. 몽그리즘",
                "2020/10/21"),
            PortfolioData("S.W.A.N",
                "Orchestra",
                "2016. 09 ~ 2018. 08\n악기 하고 싶다 ~~~~",
                "2020/10/21")
        )
    }

}