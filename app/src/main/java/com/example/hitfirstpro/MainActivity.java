package com.example.hitfirstpro;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast; // הוספת Toast כדי להציג הודעה למשתמש

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private boolean isOperatorPressed = false;
    private TextView result;
    private TextView eq;
    double firstNumber;
    double secondNumber;
    char ch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        result = findViewById(R.id.textViewResult);
        eq = findViewById(R.id.textVieweq);
        eq.setText(" ");
        result.setText(" ");
    }

    // פונקציה שמבצע כל כפתור מספר
    public void numfunction(View view) {
        // אם התוצאה היא Infinity, מאפס את המחשבון לפני שמבצע את הפעולה
        if (result.getText().toString().equals("Infinity") || result.getText().toString().equals("-Infinity")) {
            resetfunc(view); // מאפס את התצוגות
        }
        Button button = (Button) view;
        eq.append(button.getText().toString());
    }

    // פונקציה שמבצע כפתור C למחיקת תו אחרון
    public void Cfunction(View view) {
        String currentText = eq.getText().toString();  // result הוא ה-TextView או EditText שבו מוצג החישוב

        if (!currentText.isEmpty()) {
            // מחיקת התו האחרון שהוזן
            eq.setText(currentText.substring(0, currentText.length() - 1));
        }
    }

    // פונקציה להוספת נקודה
    public void pointfunction(View view) {
        Button button = (Button) view;
        String currentText = eq.getText().toString();

        // בדיקה אם המספר הנוכחי כבר מכיל נקודה
        if (!currentText.isEmpty()) {
            // בדוק אם הנקודה כבר קיימת במספר הנוכחי
            String[] parts = currentText.split("[+\\-*/]");
            String currentNumber = parts[parts.length - 1];

            if (!currentNumber.contains(".")) {
                eq.append(button.getText().toString());
            }
        } else {
            // אם אין טקסט, אפשר להתחיל עם 0.
            eq.append("0.");
        }
    }

    // פונקציה לטיפול במבצע חשבוני (פעולה כמו + - * /)
    public void funcaction(View view) {
        // אם התוצאה היא Infinity, מאפס את המחשבון ומציג הודעה
        if (result.getText().toString().equals("Infinity") || result.getText().toString().equals("-Infinity")) {
            resetfunc(view); // מאפס את התצוגות
            // הצגת הודעה למשתמש
            Toast.makeText(this, "נא להכניס מספר", Toast.LENGTH_SHORT).show();
            return; // חוזרים מבלי לבצע את הפעולה
        }

        // אם אין מספר להזין לפני הפעולה, הצג הודעה למשתמש
        if (eq.getText().toString().isEmpty()) {
            Toast.makeText(this, "נא להכניס מספר לפני פעולה", Toast.LENGTH_SHORT).show();
            return; // לא מבצעים פעולה אם המסך ריק
        }

        if (isOperatorPressed) {
            Toast.makeText(view.getContext(), "כבר בחרת פעולה", Toast.LENGTH_SHORT).show();
            return;
        }

        ch = ((Button) view).getText().toString().charAt(0);
        firstNumber = Double.parseDouble((eq.getText().toString()));
        eq.setText("");
        isOperatorPressed = true;
    }

    // פונקציה לביצוע החישוב הסופי
    public void sumfunction(View view) {
        // אם התוצאה היא Infinity, מאפס את המחשבון ומציג הודעה
        if (result.getText().toString().equals("Infinity") || result.getText().toString().equals("-Infinity")) {
            resetfunc(view); // מאפס את התצוגות
            // הצגת הודעה למשתמש
            Toast.makeText(this, "נא להכניס מספר", Toast.LENGTH_SHORT).show();
            return; // חוזרים מבלי לבצע את הפעולה
        }

        // אם אין מספר להזין לפני הפעולה, הצג הודעה למשתמש
        if (eq.getText().toString().isEmpty()) {
            Toast.makeText(this, "נא להכניס מספר לפני פעולה", Toast.LENGTH_SHORT).show();
            return; // לא מבצעים פעולה אם המסך ריק
        }

        secondNumber = Double.parseDouble((eq.getText()).toString());
        double reso = 0;

        switch (ch) {
            case '+':
                reso = firstNumber + secondNumber;
                break;
            case '-':
                reso = firstNumber - secondNumber;
                break;
            case 'x':
                reso = firstNumber * secondNumber;
                break;
            case '/':
                // אם מחלקים באפס, מכניסים את התוצאה כ-INFINITY
                if (secondNumber == 0) {
                    reso = Double.POSITIVE_INFINITY;
                } else {
                    reso = firstNumber / secondNumber;
                }
                break;
            default:
            firstNumber = Double.parseDouble((eq.getText()).toString());
            reso = firstNumber;
            break;
        }

        // הצגת התוצאה
        if (Double.isInfinite(reso)) {
            result.setText("Infinity");
            eq.setText("Infinity");
        } else {
            result.setText(reso + "");
            eq.setText(reso + "");
        }
        isOperatorPressed = false;
        firstNumber = 0;
        secondNumber = 0;
        ch = '\0';
    }

    // פונקציה לאיפוס המחשבון
    public void resetfunc(View view) {
        result.setText("");
        eq.setText("");
    }
}
