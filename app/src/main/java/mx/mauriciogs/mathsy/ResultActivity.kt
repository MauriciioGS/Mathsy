package mx.mauriciogs.mathsy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import mx.mauriciogs.mathsy.MatsyConstants.BUNDLE.FORMULA
import mx.mauriciogs.mathsy.MatsyConstants.BUNDLE.VAR1
import mx.mauriciogs.mathsy.MatsyConstants.BUNDLE.VAR2
import mx.mauriciogs.mathsy.MatsyConstants.BUNDLE.VAR3
import mx.mauriciogs.mathsy.MatsyConstants.BUNDLE.VAR4
import mx.mauriciogs.mathsy.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private var option: Int? = 0
    private var var1: Double? = 0.0
    private var var2: Double? = 0.0
    private var var3: Double? = 0.0
    private var var4: Double? = 0.0
    private var result: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getBundleData()
        resolve()
    }

    private fun getBundleData() {
        val bundle = intent.extras
        option = bundle?.getInt(FORMULA)
        var1 = bundle?.getDouble(VAR1)
        var2 = bundle?.getDouble(VAR2)
        var3 = bundle?.getDouble(VAR3)
        var4 = bundle?.getDouble(VAR4)
        Toast.makeText(this, "$var1, $var2, $var3, $var4", Toast.LENGTH_LONG).show()
    }

    private fun resolve() {
        when(option){
            1 -> result = leyOhm()
            2 -> result = velocidad()
            3 -> result = pendiente()
            else -> {}
        }
        setResult()
    }

    private fun setResult() {
        with(binding){
            val formulas = resources.getStringArray(R.array.formulas)
            when(option){
                1 -> { // voltaje
                    tvFormula.text = formulas[0]
                    ivFormula.setImageResource(R.drawable.leyohm)
                    ivFormula.scaleType = ImageView.ScaleType.CENTER_INSIDE
                    tvVariables.text = getString(
                        R.string.tv_vars,
                        String.format(getString(R.string.txt_I), var1),
                        String.format(getString(R.string.txt_R), var2),
                        "", ""
                    )
                    tvResult.text = getString(
                        R.string.tv_result,
                        getString(R.string.txt_tension),
                        String.format(getString(R.string.txt_tension_res), result)
                    )
                }
                2 -> { // velocidad
                    tvFormula.text = formulas[1]
                    ivFormula.setImageResource(R.drawable.velocidad)
                    ivFormula.scaleType = ImageView.ScaleType.CENTER_CROP
                    tvVariables.text = getString(
                        R.string.tv_vars,
                        String.format(getString(R.string.txt_d), var1),
                        String.format(getString(R.string.txt_t), var2),
                        "", ""
                    )
                    tvResult.text = getString(
                        R.string.tv_result,
                        getString(R.string.txt_velocidad),
                        String.format(getString(R.string.txt_velocidad_res), result)
                    )
                }
                3 -> { // pendiente de una recta
                    tvFormula.text = formulas[2]
                    ivFormula.setImageResource(R.drawable.pendiente)
                    ivFormula.scaleType = ImageView.ScaleType.CENTER_CROP
                    tvVariables.text = getString(
                        R.string.tv_vars,
                        String.format(getString(R.string.txt_y2), var1),
                        String.format(getString(R.string.txt_y1), var2),
                        String.format(getString(R.string.txt_x2), var3),
                        String.format(getString(R.string.txt_x1), var4),
                    )
                    tvResult.text = getString(
                        R.string.tv_result,
                        getString(R.string.txt_pendiente),
                        String.format(getString(R.string.txt_pendiente_res), result)
                    )
                }
                else -> {}
            }
        }
    }

    private fun pendiente() : Double{
        return if(var1 != 0.0 && var2 != 0.0 && var3 != 0.0 && var4 != 0.0) {
            val dY = (var1!! - var2!!)
            val dX = (var3!! - var4!!)
            val m = dY / dX
            m
        } else
            0.0
    }

    private fun velocidad() : Double {
        return if (var1 != 0.0 && var2 != 0.0)
            var1!! / var2!!
        else
            0.0
    }

    private fun leyOhm() = (var1!! * var2!!)

}