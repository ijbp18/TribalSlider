package com.home.tribalslider.presentation.sliderAmount.viewone

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.home.tribalslider.R
import com.home.tribalslider.presentation.sliderAmount.viewone.viewmodel.SliderViewModel
import com.home.tribalslider.presentation.utils.extension.Constants.AMOUNT_MAX
import com.home.tribalslider.presentation.utils.extension.Constants.AMOUNT_MIN
import com.home.tribalslider.presentation.utils.extension.Constants.AMOUNT_STEP
import com.home.tribalslider.presentation.utils.extension.toPrice
import com.home.tribalslider.presentation.utils.extension.toPricePlus
import kotlinx.android.synthetic.main.activity_main.*
import me.tankery.lib.circularseekbar.CircularSeekBar
import me.tankery.lib.circularseekbar.CircularSeekBar.OnCircularSeekBarChangeListener

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SliderViewModel
    var availableAmount = 800000f
    var selectedAmount = 0f
    var isInvalidEF = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seek_bar?.max = (AMOUNT_MAX - AMOUNT_MIN) / AMOUNT_STEP

        setupViewModel()
        setupListeners()
    }

    private fun setupListeners() {

        seek_bar.setOnSeekBarChangeListener(object : OnCircularSeekBarChangeListener {
            override fun onProgressChanged(circularSeekBar: CircularSeekBar, customAmount: Float, fromUser: Boolean) {

                selectedAmount = customAmount
                viewModel.formatAmounts(customAmount, availableAmount)
                viewModel.showDialogMaxAmount(selectedAmount)

            }

            override fun onStopTrackingTouch(seekBar: CircularSeekBar) {

            }

            override fun onStartTrackingTouch(seekBar: CircularSeekBar) {
            }
        })


        btn_custom_amount.setOnClickListener {
            if(edt_text_custom_amount.text.isEmpty())
                Toast.makeText(this, "Ingrese monto", Toast.LENGTH_SHORT).show()
            else{
                val customAmountFromEdt = edt_text_custom_amount.text.toString().toFloat()
                val sliderAmount = customAmountFromEdt / AMOUNT_STEP
                viewModel.formatAmounts(sliderAmount, availableAmount)

                seek_bar.progress = sliderAmount
            }

        }

    }



    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(SliderViewModel::class.java)
        viewModel.formatAmount.observe(this, renderAmount)
        viewModel.formatAvailableAmount.observe(this, renderAmountAvailable)

        viewModel.showDialogMaximumAmount.observe(this, renderViewDialog)
    }

    private val renderAmount = Observer<String> { selectedAmount ->
        text_progress.text = selectedAmount
    }

    private val renderAmountAvailable = Observer<String> { availableCustom ->
        seekArcProgressAvailable.text = "De $availableCustom disponibles"
    }

    private val renderViewDialog = Observer<Boolean> { isAmountFull ->
        isInvalidEF = isAmountFull
        if (isAmountFull) {

            var amountMax = availableAmount.toPricePlus()
            text_progress.text = amountMax
            seekArcProgressAvailable.text = "De ${availableAmount.toPrice()} disponibles"

        }
    }

}

