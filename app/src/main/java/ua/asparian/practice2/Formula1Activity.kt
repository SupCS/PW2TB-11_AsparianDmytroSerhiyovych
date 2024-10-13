package ua.asparian.practice2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Formula1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formula1)

        val backButton: Button = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val inputHeatValue: EditText = findViewById(R.id.input_heat_value)
        val inputFuelMass: EditText = findViewById(R.id.input_fuel_mass)
        val inputEmissionFactor: EditText = findViewById(R.id.input_emission_factor)
        val calculateButton: Button = findViewById(R.id.calculate_button)
        val resultText: TextView = findViewById(R.id.result_text)

        calculateButton.setOnClickListener {
            hideKeyboard()

            val Q_i = inputHeatValue.text.toString().replace(",", ".").toDoubleOrNull() ?: 0.0
            val B_i = inputFuelMass.text.toString().replace(",", ".").toDoubleOrNull() ?: 0.0
            val k_j = inputEmissionFactor.text.toString().replace(",", ".").toDoubleOrNull() ?: 0.0

            if (Q_i > 0 && B_i > 0 && k_j > 0) {
                val E_j = 1e-6 * k_j * Q_i * B_i
                resultText.text = "Валовий викид: ${"%.2f".format(E_j)} т"
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
