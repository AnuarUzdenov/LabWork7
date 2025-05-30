import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "RegistrationForm";
    private EditText etFullName, etLogin, etEmail, etPhone, etPassword, etRepeatPassword, etBirthDate;
    private Spinner spFurnitureType;
    private CheckBox cbAgree;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFullName = findViewById(R.id.etFullName);
        etLogin = findViewById(R.id.etLogin);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etRepeatPassword = findViewById(R.id.etRepeatPassword);
        etBirthDate = findViewById(R.id.etBirthDate);
        spFurnitureType = findViewById(R.id.spFurnitureType);
        cbAgree = findViewById(R.id.cbAgree);
        btnRegister = findViewById(R.id.btnRegister);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.furniture_items,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFurnitureType.setAdapter(adapter);

        btnRegister.setOnClickListener(v -> validateAndRegister());
    }

    private void validateAndRegister() {
        boolean isValid = true;

        if (!validateFullName()) isValid = false;
        if (!validateLogin()) isValid = false;
        if (!validateEmail()) isValid = false;
        if (!validatePhone()) isValid = false;
        if (!validatePasswords()) isValid = false;
        if (!validateBirthDate()) isValid = false;
        if (!cbAgree.isChecked()) {
            Toast.makeText(this, "Необходимо согласие на обработку данных", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        if (isValid) {
            Toast.makeText(this, "Регистрация успешна!", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Пользователь успешно зарегистрирован");
        }
    }

    private boolean validateFullName() {
        String fullName = etFullName.getText().toString();
        if (fullName.isEmpty()) {
            etFullName.setError("Введите ФИО");
            return false;
        }
        if (!Pattern.matches("[а-яА-ЯёЁ\\s-]+", fullName)) {
            etFullName.setError("Только кириллица, пробелы и дефис");
            return false;
        }
        return true;
    }

    private boolean validateLogin() {
        String login = etLogin.getText().toString();
        if (login.isEmpty()) {
            etLogin.setError("Введите логин");
            return false;
        }
        if (!Pattern.matches("[a-zA-Z]+", login)) {
            etLogin.setError("Только латинские буквы");
            return false;
        }
        return true;
    }

    private boolean validateEmail() {
        String email = etEmail.getText().toString();
        if (email.isEmpty()) {
            etEmail.setError("Введите email");
            return false;
        }
        if (!Pattern.matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]{2,3}", email)) {
            etEmail.setError("Некорректный email");
            return false;
        }
        return true;
    }

    private boolean validatePhone() {
        String phone = etPhone.getText().toString();
        if (phone.isEmpty()) {
            etPhone.setError("Введите телефон");
            return false;
        }
        if (!Pattern.matches("\\+[0-9]{11,15}", phone)) {
            etPhone.setError("Формат: +код страны номер");
            return false;
        }
        return true;
    }

    private boolean validatePasswords() {
        String password = etPassword.getText().toString();
        String repeatPassword = etRepeatPassword.getText().toString();

        if (password.isEmpty()) {
            etPassword.setError("Введите пароль");
            return false;
        }
        if (repeatPassword.isEmpty()) {
            etRepeatPassword.setError("Повторите пароль");
            return false;
        }
        if (!password.equals(repeatPassword)) {
            etRepeatPassword.setError("Пароли не совпадают");
            return false;
        }
        return true;
    }

    private boolean validateBirthDate() {
        String date = etBirthDate.getText().toString();
        if (date.isEmpty()) {
            etBirthDate.setError("Введите дату рождения");
            return false;
        }
        return true;
    }
}