package oscarf.androidcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        Button zeroButton = (Button)findViewById(R.id.button0);
        Button oneButton = (Button)findViewById(R.id.button1);
        Button twoButton = (Button)findViewById(R.id.button2);
        Button threeButton = (Button)findViewById(R.id.button3);
        Button fourButton = (Button)findViewById(R.id.button4);
        Button fiveButton = (Button)findViewById(R.id.button5);
        Button sixButton = (Button)findViewById(R.id.button6);
        Button sevenButton = (Button)findViewById(R.id.button7);
        Button eightButton = (Button)findViewById(R.id.button8);
        Button nineButton = (Button)findViewById(R.id.button9);

        Button plusButton = (Button)findViewById(R.id.buttonPlus);
        Button minusButton = (Button)findViewById(R.id.buttonMinus);
        Button multButton = (Button)findViewById(R.id.buttonMult);
        Button divButton = (Button)findViewById(R.id.buttonDiv);

        Button eqButton = (Button)findViewById(R.id.buttonEq);
        Button dotButton = (Button)findViewById(R.id.buttonDot);
        Button CButton = (Button)findViewById(R.id.buttonC);

        zeroButton.setOnClickListener(this);
        oneButton.setOnClickListener(this);
        twoButton.setOnClickListener(this);
        threeButton.setOnClickListener(this);
        fourButton.setOnClickListener(this);
        fiveButton.setOnClickListener(this);
        sixButton.setOnClickListener(this);
        sevenButton.setOnClickListener(this);
        eightButton.setOnClickListener(this);
        nineButton.setOnClickListener(this);

        plusButton.setOnClickListener(this);
        minusButton.setOnClickListener(this);
        multButton.setOnClickListener(this);
        divButton.setOnClickListener(this);

        eqButton.setOnClickListener(this);
        CButton.setOnClickListener(this);
        dotButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String number = button.getText().toString();
        String currentText = "0";
        switch(number){
            case "C":
                break;
            case "=":
                double result = eval(this.textView.getText().toString());
                currentText = Double.toString(result);
                if(currentText.equals("NaN")){
                    currentText = "0";
                }
                else if((result % 1) == 0){
                    currentText = Integer.toString((int)result);
                }
                break;
            default:
                currentText = this.textView.getText().toString() + number;
        }
        this.textView.setText(currentText);
    }

    private static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()){
                    return 0;
                }
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    try {
                        x = Double.parseDouble(str.substring(startPos, this.pos));
                    } catch (Exception e) {
                        return 0;
                    }
                } else {
                    return 0;
                }
                return x;
            }
        }.parse();
    }

}
