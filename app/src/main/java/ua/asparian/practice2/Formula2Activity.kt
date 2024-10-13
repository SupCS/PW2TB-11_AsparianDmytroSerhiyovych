package ua.asparian.practice2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Formula2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formula2)

        val backButton: Button = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Поля введення для формули 2.2
        val inputHeatValue: EditText = findViewById(R.id.input_heat_value) // Q_i
        val inputAshContent: EditText = findViewById(R.id.input_ash_content) // A_r
        val inputGammaContent: EditText = findViewById(R.id.input_gamma_content) // Г_вив
        val inputEfficiency: EditText = findViewById(R.id.input_efficiency) // η_ЗУ
        val inputLightAsh: EditText = findViewById(R.id.input_light_ash) // a_вив
        val inputSulfurInteraction: EditText = findViewById(R.id.input_sulfur_interaction) // k_твS (опціональний параметр)

        val calculateButton: Button = findViewById(R.id.calculate_button)
        val resultText: TextView = findViewById(R.id.result_text)

        calculateButton.setOnClickListener {
            hideKeyboard()

            // Зчитуємо значення
            val Q_i = inputHeatValue.text.toString().replace(",", ".").toDoubleOrNull() ?: -1.0
            val A_r = inputAshContent.text.toString().replace(",", ".").toDoubleOrNull() ?: -1.0
            val G_viv = inputGammaContent.text.toString().replace(",", ".").toDoubleOrNull() ?: -1.0
            val efficiency = inputEfficiency.text.toString().replace(",", ".").toDoubleOrNull() ?: -1.0
            val a_viv = inputLightAsh.text.toString().replace(",", ".").toDoubleOrNull() ?: -1.0

            // Перевіряємо опціональний параметр
            val k_tvs = inputSulfurInteraction.text.toString().replace(",", ".").toDoubleOrNull() ?: 0.0

            // Дозволяємо введення 0 для деяких параметрів (наприклад, A_r, G_viv), але відкидаємо некоректні (від'ємні або Q_i не може бути 0)
            if (Q_i > 0 && A_r >= 0 && G_viv >= 0 && efficiency >= 0 && a_viv >= 0) {
                // Розрахунок показника емісії твердих частинок з урахуванням k_твS
                val k_tv = (1e6 / Q_i) * a_viv * (A_r / (100 - G_viv)) * (1 - efficiency) + k_tvs

                resultText.text = "Показник емісії твердих частинок: ${"%.2f".format(k_tv)} г/ГДж"
            } else {
                resultText.text = "Будь ласка, введіть коректні значення"
            }
        }
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
