package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private var currentInput = ""
    private var operator: String? = null
    private var firstNumber: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Kết nối các view với id
        tvResult = findViewById(R.id.tvResult)

        // Gán sự kiện cho các nút
        setupNumberButtons()
        setupOperatorButtons()

        // Nút C: reset lại màn hình
        findViewById<Button>(R.id.btnC)?.setOnClickListener {
            resetCalculator()
        }
    }

    // Cài đặt sự kiện cho các nút số
    private fun setupNumberButtons() {
        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        for (id in numberButtons) {
            findViewById<Button>(id)?.setOnClickListener { button ->
                val value = (button as Button).text.toString()
                currentInput += value
                tvResult.text = currentInput
            }
        }
    }

    // Cài đặt sự kiện cho các nút toán tử
    private fun setupOperatorButtons() {
        val operatorButtons = mapOf(
            R.id.btnPlus to "+",
            R.id.btnMinus to "-",
            R.id.btnMultiply to "*",
            R.id.btnDivide to "/"
        )

        for ((id, op) in operatorButtons) {
            findViewById<Button>(id)?.setOnClickListener {
                if (currentInput.isNotEmpty()) {
                    val secondNumber = currentInput.toDouble()
                    // Nếu chưa có firstNumber thì gán ngay
                    if (firstNumber == null) {
                        firstNumber = secondNumber
                    } else {
                        // Tính toán và gán kết quả cho firstNumber
                        firstNumber = calculateResult(secondNumber)
                    }
                    operator = op // Gán toán tử
                    currentInput = "" // Reset currentInput để nhập số tiếp theo
                    tvResult.text = firstNumber.toString() // Cập nhật kết quả
                }
            }
        }

        // Nút =: Thực hiện phép tính
        findViewById<Button>(R.id.btnEquals)?.setOnClickListener {
            if (operator != null && currentInput.isNotEmpty()) {
                val secondNumber = currentInput.toDouble()
                val result = calculateResult(secondNumber)
                tvResult.text = result.toString()
                // Gán kết quả cho firstNumber để tiếp tục tính toán
                firstNumber = result
                operator = null // Reset operator để tránh tính toán tự động
                currentInput = "" // Reset currentInput sau khi tính toán
            }
        }
    }

    // Hàm thực hiện tính toán dựa trên toán tử
    private fun calculateResult(secondNumber: Double): Double {
        return when (operator) {
            "+" -> firstNumber!!.plus(secondNumber)
            "-" -> firstNumber!!.minus(secondNumber)
            "*" -> firstNumber!!.times(secondNumber)
            "/" -> if (secondNumber != 0.0) firstNumber!!.div(secondNumber) else Double.NaN
            else -> firstNumber ?: 0.0 // Nếu không có operator, trả về firstNumber
        }
    }

    // Reset lại toàn bộ dữ liệu
    private fun resetCalculator() {
        currentInput = ""
        operator = null
        firstNumber = null
        tvResult.text = "0"
    }
}
