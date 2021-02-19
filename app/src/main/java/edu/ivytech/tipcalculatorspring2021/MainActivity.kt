package edu.ivytech.tipcalculatorspring2021




import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var billAmountET:EditText
    private lateinit var tipPercentET:EditText
    var tipPercent = .15
    private lateinit var upBtn:ImageButton
    private lateinit var downBtn:ImageButton
    private lateinit var calculateBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        billAmountET = findViewById(R.id.billAmountEditText)
        tipPercentET = findViewById(R.id.tipPercentEditText)
       // upBtn = findViewById(R.id.upBtn)
        //upBtn.setOnClickListener {
          //  tipPercent = tipPercent + .01
            //tipPercent * 100
            //tipPercentET.setText((tipPercent * 100).toString())
            //calculateAndDisplay()
        //}
        //downBtn = findViewById(R.id.downBtn)
        //downBtn.setOnClickListener {
          //  tipPercent -= 0.01
            //calculateAndDisplay()
        //}
        calculateBtn = findViewById(R.id.calculateBtn)
        calculateBtn.setOnClickListener { calculateAndDisplay() }


    }
    fun calculateAndDisplay() {
        var billAmountStr = billAmountET.text.toString()
        var billAmount = 0.0
        if(billAmountStr.length > 0) {
            billAmount = billAmountStr!!.toDouble()
        }
        var tipAmount = billAmount * tipPercent
        var totalAmount = billAmount + tipAmount
        var currencyFormat = NumberFormat.getCurrencyInstance()
        var percentFormat = NumberFormat.getPercentInstance()
        var symbol = DecimalFormatSymbols()
        symbol.currencySymbol = ""
        symbol.percent = ' '
        (currencyFormat as DecimalFormat).decimalFormatSymbols = symbol
        (percentFormat as DecimalFormat).decimalFormatSymbols = symbol
        var tipAmountEditText:EditText = findViewById(R.id.tipAmountEditText)
        tipAmountEditText.setText(currencyFormat.format(tipAmount))
        var totalAmountEditText:EditText = findViewById(R.id.totalEditText)
        totalAmountEditText.setText(currencyFormat.format(totalAmount))

        tipPercentET.setText(percentFormat.format(tipPercent))


    }
}