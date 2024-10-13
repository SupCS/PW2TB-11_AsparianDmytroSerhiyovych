package ua.asparian.practice2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonCalculator1: Button = findViewById(R.id.button_calculator1)
        val buttonCalculator2: Button = findViewById(R.id.button_calculator2)
        val buttonCombinedCalculator: Button = findViewById(R.id.button_combined_calculator)

        // Переходимо на перший калькулятор
        buttonCalculator1.setOnClickListener {
            val intent = Intent(this, Formula1Activity::class.java)
            startActivity(intent)
        }

        // Переходимо на другий калькулятор
        buttonCalculator2.setOnClickListener {
            val intent = Intent(this, Formula2Activity::class.java)
            startActivity(intent)
        }

        // Переходимо на комбінований калькулятор
        buttonCombinedCalculator.setOnClickListener {
            val intent = Intent(this, CombinedCalculatorActivity::class.java)
            startActivity(intent)
        }
    }
}
