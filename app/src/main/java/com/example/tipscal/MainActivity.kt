package com.example.tipscal

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat

private const val INITIAL_TIP_PERCENT=15
class MainActivity : AppCompatActivity() {
    private lateinit var etBaseAmt: EditText
    private lateinit var seekBar: SeekBar
    private lateinit var tvTipPercent: TextView
    private lateinit var tvTipAmt: TextView
    private lateinit var tvTotal: TextView
    private lateinit var tvTipDes: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etBaseAmt=findViewById(R.id.etBaseAmt)
        seekBar=findViewById(R.id.seekbarTip)
        tvTipPercent=findViewById(R.id.tvTipPercent)
        tvTipAmt=findViewById(R.id.evTipAmt)
        tvTotal=findViewById(R.id.etTotalamt)
        tvTipDes=findViewById(R.id.tvTipDes)

        seekBar.progress= INITIAL_TIP_PERCENT
        tvTipPercent.text="$INITIAL_TIP_PERCENT%"
        updateTipDescription(INITIAL_TIP_PERCENT)

// Methos to handle seekbar
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                tvTipPercent.text="$p1%"
                computeTipTotal()
                updateTipDescription(p1)


            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        etBaseAmt.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                computeTipTotal()
            }

        })

    }

    private fun updateTipDescription(tipPresent: Int) {
        var tipDes = when(tipPresent){
            in 0..9 -> "Poor"
            in 10..14 -> "Acceptable"
            in 15..19 -> "Good"
            in 20..24 -> "Great"
            else -> "Amazing"
        }
        tvTipDes.text=tipDes
        //Update color base on tipPercent
        val color = ArgbEvaluator().evaluate(
            tipPresent.toFloat()/seekBar.max,
            ContextCompat.getColor(this, R.color.color_worst_tip),
            ContextCompat.getColor(this, R.color.color_best_tip)
        ) as Int
        tvTipDes.setTextColor(color)

    }

    private fun computeTipTotal() {
        //get the value of base textview
        if(etBaseAmt.text.isEmpty()){
            tvTipAmt.text=""
            tvTotal.text=""
            return
        }
        val baseamt = etBaseAmt.text.toString().toDouble()
        //get the value of the seekBar
        val tipPercent = seekBar.progress
      //  val finalTipPercent = tipPercent/100
        //Compute Tip and Total
        val tipAmt = baseamt * tipPercent/100

        val totalAmt = baseamt+tipAmt
        tvTipAmt.text="%.2f".format(tipAmt)
        tvTotal.text="%.2f".format(totalAmt)


    }
}