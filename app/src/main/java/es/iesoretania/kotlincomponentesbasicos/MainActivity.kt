package es.iesoretania.kotlincomponentesbasicos

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import es.iesoretania.kotlincomponentesbasicos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // Objeto que da acceso a todas las vistas del layout (sustituye a findViewById)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Dibujar la app de borde a borde (bajo las barras del sistema)
        enableEdgeToEdge()

        // Inflar el layout con ViewBinding y establecerlo como contenido
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ---------- INSETS: que el contenido no se solape con la cámara ni las barras ----------
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { vista, insets ->
            // Espacio ocupado por las barras del sistema Y por la cámara (displayCutout)
            val barras = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )
            // Convertimos 16dp a píxeles para sumarlos como margen extra
            val extra = (16 * resources.displayMetrics.density).toInt()

            vista.setPadding(
                barras.left + extra,
                barras.top + extra,
                barras.right + extra,
                barras.bottom + extra
            )
            insets
        }

        // ---------- SPINNER: rellenarlo con una lista de opciones ----------
        val cursos = listOf("1º ASIR", "2º ASIR", "1º DAM", "2º DAM")

        // El adapter conecta los datos con el Spinner
        // simple_spinner_item = aspecto del elemento seleccionado
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, cursos)
        // simple_spinner_dropdown_item = aspecto de la lista desplegada
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCurso.adapter = adapter

        // ---------- BOTÓN: leer el estado de los tres componentes ----------
        binding.btnMostrar.setOnClickListener {

            // RADIOGROUP: nos da el id del RadioButton marcado
            val so = when (binding.radioGroupSO.checkedRadioButtonId) {
                binding.radioAndroid.id -> "Android"
                binding.radioIos.id -> "iOS"
                binding.radioLinux.id -> "Linux"
                else -> "ninguno"
            }

            // CHECKBOX: isChecked es true/false. Cada uno se consulta por separado
            val lenguajes = mutableListOf<String>()
            if (binding.checkKotlin.isChecked) lenguajes.add("Kotlin")
            if (binding.checkJava.isChecked) lenguajes.add("Java")
            val textoLenguajes = if (lenguajes.isEmpty()) "ninguno" else lenguajes.joinToString(", ")

            // SPINNER: selectedItem devuelve el elemento elegido (como Any, lo pasamos a texto)
            val curso = binding.spinnerCurso.selectedItem.toString()

            // Mostrar el resumen en el TextView
            binding.tvResultado.text =
                "Sistema operativo: $so\nLenguajes: $textoLenguajes\nCurso: $curso"
        }
    }
}