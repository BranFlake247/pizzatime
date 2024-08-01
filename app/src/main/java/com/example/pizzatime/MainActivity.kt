package com.example.pizzatime

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    //pizza size cost
    val medium = 9.99
    val large = 13.99
    val extraLarge = 15.99
    val partySize = 25.99

    //toppings cost
    private val tomatoes = 1.0
    private val mushrooms = 2.3
    private val olives = 1.7
    private val onions = 1.25
    private val broccoli = 1.8
    private val spinach = 2.0

    private val extraSpicy  = 1.00


    private var deliveryFee = 0.00
    private var pizzaPrice = 0.00
    private var quantity =  1
    private var subtotal = 0.00
    private var totalPrice = 0.00
    private var tax = 0.00


    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pizzaSize = listOf("Choose a pizza type", "Medium", "Large", "Extra Large", "Party Size")

        val pizzaSizeAdapter = ArrayAdapter<String>(this,   android.R.layout.simple_spinner_dropdown_item, pizzaSize)

        val pizzaSizeSpinner = findViewById<Spinner>(R.id.pizza_size_spinner)

        pizzaSizeSpinner.adapter = pizzaSizeAdapter

        pizzaSizeSpinner.onItemSelectedListener = this

        findViewById<Button>(R.id.button2)
        findViewById<Button>(R.id.button)

        val Quantity = findViewById<TextView>(R.id.quantity)
        Quantity.text = quantity.toString()

        val seekBarLabel = findViewById<TextView>(R.id.seek_bar_label)
        val seekBarSpicy = findViewById<SeekBar>(R.id.seek_bar_spicy)

        seekBarLabel.visibility = View.INVISIBLE
        seekBarSpicy.visibility = View.INVISIBLE


        findViewById<SeekBar>(R.id.seek_bar_spicy).setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                seekBarLabel.text = "Spiciness Level: $progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }

    fun radioButtonClick(view: View) {
        val imageIdOfSelectedPizzaType = when (view.id) {
            R.id.pepperoni ->  R.drawable.pepperoni
            R.id.bbqChicken -> R.drawable.bbq_chicken
            R.id.margherita -> R.drawable.margheritta
            R.id.hawaiian -> R.drawable.hawaiian
            else -> R.drawable.pizza_crust
        }

        findViewById<ImageView>(R.id.image_pizzaType).setImageResource(imageIdOfSelectedPizzaType)
    }

    fun updatePriceTextView(){

        subtotal = (pizzaPrice * quantity) + deliveryFee
        val subtotalTextView = findViewById<TextView>(R.id.subtotalTextView)
        subtotalTextView.text = String.format("%.2f", subtotal)

        tax = subtotal * 0.0635

        val taxTextView = findViewById<TextView>(R.id.taxTextView)
        taxTextView.text = String.format("%.2f", tax)

        totalPrice = subtotal + tax

        val totalPriceTextView = findViewById<TextView>(R.id.totalPriceTextView)
        totalPriceTextView.text =  String.format("%.2f", totalPrice)

    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    fun switchClick(view: View) {
        val switchExtraSpicy = findViewById<Switch>(R.id.extraspicy)
        val seekBarLabel = findViewById<TextView>(R.id.seek_bar_label)
        val seekBarSpicy = findViewById<SeekBar>(R.id.seek_bar_spicy)
        val switchText: String
        if (switchExtraSpicy.isChecked) {
            switchText = "Yes, $1.00"
            pizzaPrice += extraSpicy
            seekBarLabel.visibility = View.VISIBLE
            seekBarSpicy.visibility = View.VISIBLE

        } else {
            switchText = "No, $0.00"
            pizzaPrice -= extraSpicy
            seekBarLabel.visibility = View.INVISIBLE
            seekBarSpicy.visibility = View.INVISIBLE
        }
        switchExtraSpicy.text = switchText
        updatePriceTextView()
    }

    fun onCheckboxClicked(view: View){
        if (view is CheckBox){
            val checked: Boolean = view.isChecked

            when(view.id){
                R.id.checkbox_tomatoes ->  {
                    if (checked){
                        pizzaPrice += tomatoes
                    } else {
                        pizzaPrice -= tomatoes
                    }
                }
                R.id.checkbox_onions -> {
                    if (checked){
                        pizzaPrice += onions
                    } else {
                        pizzaPrice -= onions
                    }
                }
                R.id.checkbox_mushrooms -> {
                    if (checked){
                        pizzaPrice +=  mushrooms
                    } else {
                        pizzaPrice -= mushrooms
                    }
                }
                R.id.checkbox_broccoli ->{
                    if (checked){
                        pizzaPrice += broccoli
                    } else {
                        pizzaPrice -= broccoli
                    }
                }
                R.id.checkbox_olives -> {
                    if (checked){
                        pizzaPrice += olives
                    } else {
                        pizzaPrice -= olives
                    }
                }
                R.id.checkbox_spinach -> {
                    if (checked){
                        pizzaPrice += spinach
                    } else {
                        pizzaPrice -= spinach
                    }
                }
            }
        }
        updatePriceTextView()
    }

    fun increaseQuantityButton(view: View) {
        quantity++
        subtotal = pizzaPrice * quantity

        val Quantity = findViewById<TextView>(R.id.quantity)
        Quantity.text = quantity.toString()

        updatePriceTextView()
    }

    fun decreaseQuantityButton(view: View){
        if(quantity > 1){
            quantity--
            subtotal = pizzaPrice * quantity
        }

        val Quantity = findViewById<TextView>(R.id.quantity)
        Quantity.text = quantity.toString()

        updatePriceTextView()
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    fun switchClick2(view: View) {
        val switchDeliveryFee = findViewById<Switch>(R.id.deliveryfee)
        val switchText2: String
        if (switchDeliveryFee.isChecked) {
            switchText2 = "Yes, $2.00"
            deliveryFee = 2.00
        } else {
            switchText2 = "No, $0.00"
            deliveryFee = 0.00
        }
        switchDeliveryFee.text = switchText2
        updatePriceTextView()
    }

    var lastSelectedPizzaCost = 0.0

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedItem = parent?.getItemAtPosition(position).toString()

        pizzaPrice -= lastSelectedPizzaCost

        val price: Double = when (selectedItem) {
            "Medium" -> 9.99
            "Large" -> 13.99
            "Extra Large" -> 15.99
            "Party Size" -> 25.99
            else -> 0.0 // Default or handle other cases
        }

        lastSelectedPizzaCost = price

        pizzaPrice += price

        updatePriceTextView()

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    fun resetButton(view: View) {
        // Reset quantity
        quantity = 1
        val Quantity = findViewById<TextView>(R.id.quantity)
        Quantity.text = quantity.toString()
        // Uncheck all checkboxes
        val checkboxes = arrayOf(
            R.id.checkbox_tomatoes,
            R.id.checkbox_onions,
            R.id.checkbox_mushrooms,
            R.id.checkbox_broccoli,
            R.id.checkbox_olives,
            R.id.checkbox_spinach
        )

        for (checkboxId in checkboxes) {
            val checkbox = findViewById<CheckBox>(checkboxId)
            checkbox.isChecked = false
        }

        // Uncheck switches and reset values
        val switchExtraSpicy = findViewById<Switch>(R.id.extraspicy)
        switchExtraSpicy.isChecked = false
        pizzaPrice = 0.00

        val seekBarLabel = findViewById<TextView>(R.id.seek_bar_label)
        val seekBarSpicy = findViewById<SeekBar>(R.id.seek_bar_spicy)

        seekBarLabel.visibility = View.INVISIBLE
        seekBarSpicy.visibility = View.INVISIBLE

        val switchDeliveryFee = findViewById<Switch>(R.id.deliveryfee)
        switchDeliveryFee.isChecked = false
        deliveryFee = 0.00

        // Reset lastSelectedPizzaCost
        lastSelectedPizzaCost = 0.00

        // Update UI with default values
        updatePriceTextView()

        // Reset the spinner to the default selection
        val pizzaSizeSpinner = findViewById<Spinner>(R.id.pizza_size_spinner)
        pizzaSizeSpinner.setSelection(0)

        // Unselect all radio buttons
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        radioGroup.clearCheck()

        // Reset the pizza type image
        findViewById<ImageView>(R.id.image_pizzaType).setImageResource(R.drawable.pizza_crust)
    }

}