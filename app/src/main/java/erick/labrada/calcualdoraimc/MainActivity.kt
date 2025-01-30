package erick.labrada.calcualdoraimc

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        val heightVar = findViewById<EditText>(R.id.editTextNumberDecimal);
        val weightVar = findViewById<EditText>(R.id.editTextNumberDecimal2);

        val calculateButton = findViewById<Button>(R.id.button);
        val imcResult = findViewById<TextView>(R.id.textView2);
        val state = findViewById<TextView>(R.id.textView3);

        calculateButton.setOnClickListener {
            val imc = calculateIMC(heightVar, weightVar, imcResult)
            updateStatus(imc, state,imcResult);
        }





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



    }

    private fun calculateIMC(
        heightVar: EditText,
        weightVar: EditText,
        imcResult: TextView,
    ):Float {

        val heightStr = heightVar.text.toString();
        val weightStr = weightVar.text.toString();

        if (heightStr.isNotBlank() && weightStr.isNotBlank()){

            val height = heightStr.toFloat()
            val weight = weightStr.toFloat()
            val imc = weight / (height * height)

            return imc;

        }

        return 0.0f;

    }

    private fun updateStatus(
        imc: Float,
        state: TextView,
        imcResult: TextView,
    ){
        if (imc > 0.0f){

        val (category, color) = when {
            imc < 18.5 -> "Bajo peso" to R.color.yellow
            imc in 18.5..24.9 -> "Normal" to R.color.green
            imc in 25.0..29.9 -> "Sobrepeso" to R.color.greenish
            imc in 30.0..34.9 -> "Obesidad (grado 1)" to R.color.orange
            imc in 35.0..39.9 -> "Obesidad (grado 2" to R.color.red
            else -> "Obesidad (grado 3)" to R.color.brown
        }
            imcResult.text = "%.2f".format(imc)
            imcResult.setTextColor(resources.getColor(color, theme))

            state.text = category
            state.setTextColor(resources.getColor(color, theme))
        }

        else {
            imcResult.text = "Porfavor ingrese todos los datos"
            state.text = ""
        }

    }
}