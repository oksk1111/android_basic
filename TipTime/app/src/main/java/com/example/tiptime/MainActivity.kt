package com.example.tiptime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding   // lateinint: 코드가 변수를 사용하기 전에 초기화 해야 함

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        // activity_mail.xml 레이아웃에서 Views에 액세스 하는데 사용할 binding 객체 초기화
        binding = ActivityMainBinding.inflate(layoutInflater)
        // 최상이 root 와 바인딩하여 자원을 사용한다는 것; id 가 있는 모든 View에 접근 가능
        setContentView(binding.root)

        /*
         * # Old way with findViewById()
         * val myButton: Button = findViewById(R.id.my_bytton)
         * myBytton.text = "A button"
         *
         * # Better way with view binding (주의사항: id의 언더바 형식이 카멜케이스로 접근가능하도록 바뀐다!)
         * val myButton: Button = binding.myButton
         * myButton.text = "A button"
         *
         * # Best way with view binding and no extra variable
         * binding.myButton.text = "A button"
         */

        binding.calculateButton.setOnClickListener { calculateTip() }
    }

    private fun calculateTip() {
        val stringInTextField = binding.costOfService.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        if (cost == null) {
            binding.tipResult.text = ""
            return
        }
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            //binding.optionTwentyPercent -> 0.20   //? 이건 왜 안될까?
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }
        var tip = tipPercentage * cost      // var 는 변수, val 은 상수
        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}