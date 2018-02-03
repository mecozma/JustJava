/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //This method
        EditText editText = findViewById(R.id.customers_name);
        String customerName = editText.getText().toString();
        Log.v("MainActivity", "name: " + customerName);

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        Log.v("MainActivity", "Has chocolate: " + Boolean.toString(hasChocolate));


        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(customerName, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + customerName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Calculates the price of the order.
     *
     *@param addWhippedCream
     * @param addChocolate
     *
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        // sets the base price of the coffee
        int basePrice = 5;

        //checks if the whipped cream has been added to the coffe order
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }
        //checks if chocolate has bee nadded to the coffee order
        if (addChocolate) {
            basePrice = basePrice + 2;
        }

        return quantity * basePrice;
    }

    /**
     * This method creates the order summary
     *
     * @param price           of the order
     * @param addWhippedCream to the order
     * @param addChocolate
     * @param customerName
     * @return text summary
     */
    private String createOrderSummary(String customerName, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = getString(R.string.order_summary_name, customerName) +  "\n" +
                getString(R.string.whippedCreamTopping) + addWhippedCream + "\n" +
                getString(R.string.chocolateTopping) + addChocolate + "\n" +
                getString(R.string.quantity) + quantity + "\n" +
                getString(R.string.total) + price + "\n" +
                getString(R.string.thankYou);
        return priceMessage;

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

//    /**
//     * This method displays the given price on the screen.
//     */
//    private void displayPrice(int number) {
//        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
//    }

    /**
     * This method is called when the + button is clicked
     */
    public void increment(View view) {
        //The maximum number of coffees to be ordered is set to 100
        if (quantity == 100) {
            quantity = 100;
            //The return statement stops the following code to be executed;
            Toast.makeText(this, "You cannot order more than 100 cups of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        //If the codnition above it is not met, the quantity is increased by 1;
        quantity = quantity + 1;
        //The quantity of coffees is displayed
        displayQuantity(quantity);
    }

    /**
     * This method is called when the - button is clicked
     */
    public void decrement(View view) {
        //The minim order of coffees to be orderes is set to 1;
        if (quantity < 2) {
            quantity = 1;
            Toast.makeText(this, "You cannot order less than one cup of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;

        displayQuantity(quantity);
    }



}

