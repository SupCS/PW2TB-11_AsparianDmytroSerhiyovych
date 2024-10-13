package ua.asparian.practice2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CombinedCalculatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_combined_calculator)

        // Поля введення
        val inputHeatValue: EditText = findViewById(R.id.input_heat_value)
        val inputAshContent: EditText = findViewById(R.id.input_ash_content)
        val inputGammaContent: EditText = findViewById(R.id.input_gamma_content)
        val inputEfficiency: EditText = findViewById(R.id.input_efficiency)
        val inputLightAsh: EditText = findViewById(R.id.input_light_ash)
        val inputFuelMass: EditText = findViewById(R.id.input_fuel_mass)
        val inputSulfurInteraction: EditText = findViewById(R.id.input_sulfur_interaction)

        val calculateButton: Button = findViewById(R.id.calculate_button)
        val resultText: TextView = findViewById(R.id.result_text)
        val backButton: Button = findViewById(R.id.back_button)

        // Обробка натискання кнопки "Розрахувати"
        calculateButton.setOnClickListener {
            hideKeyboard()

            // Зчитуємо дані
            val Q_i = inputHeatValue.text.toString().replace(",", ".").toDoubleOrNull() ?: -1.0
            val A_r = inputAshContent.text.toString().replace(",", ".").toDoubleOrNull() ?: -1.0
            val G_viv = inputGammaContent.text.toString().replace(",", ".").toDoubleOrNull() ?: -1.0
            val efficiency = inputEfficiency.text.toString().replace(",", ".").toDoubleOrNull() ?: -1.0
            val a_viv = inputLightAsh.text.toString().replace(",", ".").toDoubleOrNull() ?: -1.0
            val B_i = inputFuelMass.text.toString().replace(",", ".").toDoubleOrNull() ?: -1.0
            val k_tvs = inputSulfurInteraction.text.toString().replace(",", ".").toDoubleOrNull() ?: 0.0

            if (Q_i > 0 && A_r >= 0 && G_viv >= 0 && efficiency >= 0 && a_viv >= 0 && B_i > 0) {
                // Розраховуємо показник емісії
                val k_tv = (1e6 / Q_i) * a_viv * (A_r / (100 - G_viv)) * (1 - efficiency) + k_tvs

                // Розраховуємо валовий викид
                val E_j = 1e-6 * k_tv * Q_i * B_i

                // Виводимо результати
                resultText.text = "Показник емісії: ${"%.2f".format(k_tv)} г/ГДж\n" +
                        "Валовий викид: ${"%.2f".format(E_j)} т"
            } else {
                resultText.text = "Будь ласка, введіть коректні значення"
            }
        }

        // Кнопка для повернення назад
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    // Функція для приховування клавіатури
    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
