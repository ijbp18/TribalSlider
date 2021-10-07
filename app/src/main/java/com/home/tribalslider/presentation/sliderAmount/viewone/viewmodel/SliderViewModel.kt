package com.home.tribalslider.presentation.sliderAmount.viewone.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.home.tribalslider.presentation.utils.extension.Constants.AMOUNT_MAX
import com.home.tribalslider.presentation.utils.extension.toPrice
import com.home.tribalslider.presentation.utils.extension.validateMultipleV2

/**
 * Created by Joao Betancourth on 12,octubre,2020
 */
class SliderViewModel: ViewModel() {

    private val _formatAmount =MutableLiveData<String>()
    val formatAmount: LiveData<String> = _formatAmount

    private val _formatAvailableAmount =MutableLiveData<String>()
    val formatAvailableAmount: LiveData<String> = _formatAvailableAmount

    private val _showDialogMaximumAmount=MutableLiveData<Boolean>()
    val showDialogMaximumAmount:LiveData<Boolean> = _showDialogMaximumAmount

    fun formatAmounts(amount: Float, amountAvailable: Float){

        println("Monto proveniente desde el slider sin formatear: $amount")
        val progressAmount = amount.validateMultipleV2()
        println("Monto proveniente desde el slider formateado: $progressAmount")

        //evento que pinta el monto seleccionado por el user
        _formatAmount.value = progressAmount.toPrice()

        //Logica para validar el monto disponible
        val available = amountAvailable - progressAmount
        _formatAvailableAmount.value = (available).toPrice()
    }

    fun showDialogMaxAmount(amount: Float){
        _showDialogMaximumAmount.value = (amount.validateMultipleV2()) == AMOUNT_MAX
    }

}