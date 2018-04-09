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

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {



        CheckBox toppingWhippedCream = (CheckBox) findViewById(R.id.topping_whippedCream);
        boolean hasWhippedCream = toppingWhippedCream.isChecked();

        CheckBox toppingChocolate = (CheckBox) findViewById(R.id.topping_chocolate);
        boolean hasChocolate = toppingChocolate.isChecked();

        EditText customerNameField = (EditText) findViewById(R.id.customer_name);
        String customerName = customerNameField.getText().toString();




        int price = calculatePrice(quantity, 5, hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, quantity, hasWhippedCream, hasChocolate, customerName);

        Log.v("MainActivity","Price Message is: \n" + priceMessage);



        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this

        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order For: " + customerName);
        intent.putExtra(Intent.EXTRA_TEXT, "Just Java Order For: " + customerName);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        //displayMessage(priceMessage);
    }

    public void incrementQ(View view) {
        if(quantity > 100) {
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrementQ(View view) {
        if(quantity == 0) {
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int num) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(num));
    }

    /**
     * This method displays the given text on the screen.
     * MEthod is no longer used, since the order summary is being sent via intent as part of email
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */

    private int calculatePrice(int quantity, int pricePerCup, boolean whippedCream, boolean chocolate){
        if(whippedCream) {
            pricePerCup = pricePerCup + 1;
            Log.v("MainActivity","Topping Whipped cream price : " + pricePerCup);
        } else if(chocolate){
            pricePerCup = pricePerCup + 2;
            Log.v("MainActivity","Topping Chocolate price : " + pricePerCup);
        }
        int price = quantity * pricePerCup;
        return price;
    }

    private String createOrderSummary(int price, int quantity, boolean whippedCreamStatus, boolean chocolateStatus, String name){
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        String orderSummary = getString(R.string.order_summary_name, name) + "\nQuantity: " + quantity;
        orderSummary += "\n" + getString(R.string.add_whipped_cream)  + whippedCreamStatus;
        orderSummary += "\n" + getString(R.string.add_chocolate) + chocolateStatus;
        orderSummary += "\n" + getString(R.string.total)+ price;
        orderSummary += "\n" + getString(R.string.thank_you);
        //priceTextView.setText(orderSummary);
        return orderSummary;
    }

}

