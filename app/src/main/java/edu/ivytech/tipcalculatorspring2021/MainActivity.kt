package edu.ivytech.tipcalculatorspring2021




import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.material.snackbar.Snackbar


import edu.ivytech.tipcalculatorspring2021.databinding.ActivityMainBinding
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import kotlin.math.round

class MainActivity : AppCompatActivity() {
    private lateinit var billAmountET:EditText
    private lateinit var tipPercentET:EditText
    var tipPercent = .15
    private lateinit var calculateBtn:Button
    private lateinit var binding:ActivityMainBinding
    private val ROUND_NONE = 0
    private val ROUND_TIP = 1
    private val ROUND_TOTAL = 2
    private var rounding = ROUND_NONE
    private var split = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        billAmountET = binding.billAmountEditText
        tipPercentET = binding.tipPercentEditText

        calculateBtn = binding.calculateBtn
        calculateBtn.setOnClickListener { calculateAndDisplay() }
        binding.seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tipPercent = progress/100.0
                tipPercentET.setText(progress.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        binding.radioGroup.check(R.id.noRoundRadio)
        binding.radioGroup.setOnCheckedChangeListener(object: RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when(checkedId) {
                    R.id.noRoundRadio -> rounding = ROUND_NONE
                    R.id.roundTipRadio -> rounding = ROUND_TIP
                    R.id.roundTotalRadio -> rounding = ROUND_TOTAL
                }
                calculateAndDisplay()
            }
        })

        val items = resources.getStringArray(R.array.split_array)
        val adapter = ArrayAdapter(this,R.layout.list_item,items)
        (binding.splitBillET as? AutoCompleteTextView)?.setAdapter(adapter)
        binding.splitBillET.setText(items[0], false)
        binding.splitBillET.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                for(i in items.indices) {
                    if(items[i] == s.toString()) {
                        split = i + 1
                        break
                    }
                }
                if(split > 1) {
                    binding.eachPayLayout.visibility = View.VISIBLE
                } else {
                    binding.eachPayLayout.visibility = View.GONE
                }
                calculateAndDisplay()


            }

            override fun afterTextChanged(s: Editable?) {

            }
        })


    }
    fun calculateAndDisplay() {
        var billAmountStr = billAmountET.text.toString()
        var billAmount = 0.0
        if(billAmountStr.length > 0) {
            billAmount = billAmountStr!!.toDouble()
        }
        var tipAmount = billAmount * tipPercent
        if(rounding == ROUND_TIP){
            tipAmount = round(tipAmount)
        }

        var totalAmount = billAmount + tipAmount
        if(rounding == ROUND_TOTAL) {
            totalAmount = round(totalAmount)
            tipAmount = totalAmount - billAmount
        }
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

        if(split > 1){
            var splitAmount = totalAmount/split
            binding.eachPayText.setText(currencyFormat.format(splitAmount))
        }


    }
}