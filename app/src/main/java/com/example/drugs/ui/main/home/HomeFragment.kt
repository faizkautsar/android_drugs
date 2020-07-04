package com.example.drugs.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.core.cartesian.series.Column
import com.anychart.enums.Anchor
import com.anychart.enums.HoverMode
import com.anychart.enums.Position
import com.anychart.enums.TooltipPositionMode
import com.example.drugs.R
import com.example.drugs.extensions.gone
import com.example.drugs.extensions.toast
import com.example.drugs.extensions.visible
import com.example.drugs.models.Rehabilitation
import com.example.drugs.ui.drugs.DrugActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        card_narkotika.setOnClickListener { startActivity(Intent(requireActivity(), DrugActivity::class.java).apply {
            putExtra("type", 3)
        }) }
        card_psicotropica.setOnClickListener { startActivity(Intent(requireActivity(), DrugActivity::class.java).apply {
            putExtra("type", 2)
        }) }
        card_zat_adiktif.setOnClickListener { startActivity(Intent(requireActivity(), DrugActivity::class.java).apply {
            putExtra("type", 1)
        }) }
        observe()
        fetchRehab()
    }

    private fun fetchRehab() = homeViewModel.fetchRehabilitations()

    private fun observe(){
        observeState()
        observeRehabStats()
    }

    private fun observeState() = homeViewModel.getState().observer(viewLifecycleOwner, Observer { handleState(it) })
    private fun observeRehabStats() = homeViewModel.getRehabStats().observe(viewLifecycleOwner, Observer { handleRehabStat(it) })

    private fun handleState(homeState: HomeState){
        when(homeState){
            is HomeState.ShowToast -> requireActivity().toast(homeState.message)
            is HomeState.Loading -> isLoading(homeState.state)
        }
    }

    private fun isLoading(b: Boolean) = if(b) requireView().referral_progressBar.visible() else requireView().referral_progressBar.gone()

    private fun handleRehabStat(rehabs: HashMap<String, List<Rehabilitation>>){
        if(homeViewModel.getRehabStats().value != null){
            requireView().referral_chart.setProgressBar(requireView().referral_progressBar)
            APIlib.getInstance().setActiveAnyChartView(requireView().referral_chart)
            val cartesian: Cartesian = AnyChart.column()
            val data: MutableList<DataEntry> = ArrayList()
            val temp = hashMapOf<String, Int>()
            rehabs.forEach { (t, u) -> temp.put(t, u.count())  }

            val temp2 = temp.toList().sortedByDescending { (_, value) -> value}.toMap()
            temp2.forEach { (k, v) ->
                data.add(ValueDataEntry(k, v))
            }
            val column: Column = cartesian.column(data)

            column.tooltip().titleFormat("{%X}").position(Position.CENTER_BOTTOM).anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0.0)
                .offsetY(5.0)
                .format("Jumlah rujukan.{%Value}{groupsSeparator: }")

            cartesian.animation(false)
            cartesian.yScale().minimum(0.0)
            cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }")
            cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
            cartesian.interactivity().hoverMode(HoverMode.BY_X)

            cartesian.xAxis(0).title("Nama rumah klinik/rumah sakit")
            cartesian.yAxis(0).title("Jumlah rujukan")
            requireView().referral_chart.setChart(cartesian)
        }
    }
}