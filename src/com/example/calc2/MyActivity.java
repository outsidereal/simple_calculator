package com.example.calc2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.util.ReversePolishNotation;
import com.example.util.exception.ExpressionParseException;

public class MyActivity extends Activity {
    private TextView monitor;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        componentsSetUp();
    }

    private void componentsSetUp() {
        monitor = (TextView) findViewById(R.id.monitor);
    }

    public void doClickDigit(final View view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (monitor.getText().length() > 0) {
                    int length = monitor.getText().length();
                    Character prevChar = monitor.getText().charAt(length - 1);
                    if (prevChar == '+' || prevChar == '-' || prevChar == '*' || prevChar == '/' || prevChar == '(' || prevChar == ')')
                        monitor.append(" ");
                }
                monitor.append(((Button) view).getText());
            }
        });
    }

    public void doClickOperation(final View view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (monitor.getText().length() > 0)
                    monitor.append(" ");
                monitor.append(((Button) view).getText());
            }
        });
    }

    public void doCalculate(final View view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ReversePolishNotation notation = new ReversePolishNotation();
                try {
                    Double result = notation.polishCalculate(monitor.getText().toString());
                    monitor.setText(result.toString());
                } catch (ExpressionParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void doClear(final View view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
               monitor.setText("");
            }
        });
    }
}
