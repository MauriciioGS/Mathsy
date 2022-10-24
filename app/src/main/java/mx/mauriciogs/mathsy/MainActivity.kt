package mx.mauriciogs.mathsy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import mx.mauriciogs.mathsy.MatsyConstants.BUNDLE.FORMULA
import mx.mauriciogs.mathsy.MatsyConstants.BUNDLE.VAR1
import mx.mauriciogs.mathsy.MatsyConstants.BUNDLE.VAR2
import mx.mauriciogs.mathsy.MatsyConstants.BUNDLE.VAR3
import mx.mauriciogs.mathsy.MatsyConstants.BUNDLE.VAR4
import mx.mauriciogs.mathsy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var formulas: Array<String>
    private var option: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponents()
        initBtnListener()
    }

    private fun initBtnListener() {
        binding.btnDone.setOnClickListener {
            verifyData()
        }
    }

    private fun verifyData() {
        with(binding){
            val var1 = tietData1.text.toString()
            val var2 = tietData2.text.toString()
            val var3 = tietData3.text.toString()
            val var4 = tietData4.text.toString()
            when(option){
                0 -> manageError(R.string.txt_empty_fields)
                1, 2 ->{
                    when{
                        var1.isEmpty() -> manageError(R.string.txt_empty_fields)
                        var2.isEmpty() -> manageError(R.string.txt_empty_fields)
                        else -> {
                            if (option == 2 && var2.toDouble() == 0.0){
                                manageError(R.string.txt_division_by_zero, getString(R.string.msg_var_tiempo))
                                return
                            }
                            checkInput(var1, var2)
                        }
                    }
                }
                3 ->{
                    when{
                        var1.isEmpty() -> manageError(R.string.txt_empty_fields)
                        var2.isEmpty() -> manageError(R.string.txt_empty_fields)
                        var3.isEmpty() -> manageError(R.string.txt_empty_fields)
                        var4.isEmpty() -> manageError(R.string.txt_empty_fields)
                        else -> {
                            if(var4.toDouble() == var3.toDouble()){
                                manageError(R.string.txt_x2_equals_x1)
                                return
                            }
                            checkInput(var1, var2, var3, var4)
                        }
                    }
                }
                else -> {}
            }
        }
    }

    private fun checkInput(var1: String, var2: String, var3: String? = null, var4: String? = null){
        val bundle = Bundle().apply {
            putInt(FORMULA, option)
            putDouble(VAR1, var1.toDouble())
            putDouble(VAR2, var2.toDouble())
            if (var3 != null && var4 != null){
                putDouble(VAR3, var3.toDouble())
                putDouble(VAR4, var4.toDouble())
            }
        }
        navigateToResult(bundle)
    }

    private fun navigateToResult(bundle: Bundle) {
        val intent = Intent(this, ResultActivity::class.java).putExtras(bundle)
        startActivity(intent)
    }

    private fun manageError(msgId: Int, msgAdd: String? = null){
        if (msgAdd == null)
            Toast.makeText(this, getString(msgId), Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, getString(msgId, msgAdd), Toast.LENGTH_SHORT).show()
    }

    private fun initComponents() {
        formulas = resources.getStringArray(R.array.formulas)
        val adapter = ArrayAdapter(this, R.layout.items_formulas, formulas)
        (binding.tiMenu.editText as AutoCompleteTextView).apply {
            setAdapter(adapter)
            onItemClickListener = this@MainActivity
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val item = parent?.getItemAtPosition(position).toString()
        option = position+1
        setFormulaUI(item)
    }

    private fun setFormulaUI(itemSelected: String) {
        with(binding){
            when(itemSelected){
                formulas[0] -> { // Voltaje
                    ivFormula.setImageResource(R.drawable.leyohm)
                    ivFormula.scaleType = ImageView.ScaleType.CENTER_INSIDE
                    tilData1.visibility = View.VISIBLE
                    tilData2.visibility = View.VISIBLE
                    tilData3.visibility = View.GONE
                    tilData4.visibility = View.GONE

                    tilData1.hint = getString(R.string.txt_intens_value)
                    tilData2.hint = getString(R.string.txt_res_value)

                    clearInputs()
                }
                formulas[1] -> { // Velocidad
                    ivFormula.setImageResource(R.drawable.velocidad)
                    ivFormula.scaleType = ImageView.ScaleType.CENTER_CROP
                    tilData1.visibility = View.VISIBLE
                    tilData2.visibility = View.VISIBLE
                    tilData3.visibility = View.GONE
                    tilData4.visibility = View.GONE

                    tilData1.hint = getString(R.string.txt_dist_value)
                    tilData2.hint = getString(R.string.txt_time_value)

                    clearInputs()
                }
                formulas[2] -> { // Pendiente de una recta
                    ivFormula.setImageResource(R.drawable.pendiente)
                    ivFormula.scaleType = ImageView.ScaleType.CENTER_CROP
                    tilData1.visibility = View.VISIBLE
                    tilData2.visibility = View.VISIBLE
                    tilData3.visibility = View.VISIBLE
                    tilData4.visibility = View.VISIBLE

                    tilData1.hint = getString(R.string.txt_y2_value)
                    tilData2.hint = getString(R.string.txt_y1_value)
                    tilData3.hint = getString(R.string.txt_x2_value)
                    tilData4.hint = getString(R.string.txt_x1_value)

                    clearInputs()
                }
                else -> {}
            }
        }
    }

    private fun clearInputs() {
        with(binding){
            tilData1.editText?.text?.clear()
            tilData2.editText?.text?.clear()
            tilData3.editText?.text?.clear()
            tilData4.editText?.text?.clear()
        }
    }

}