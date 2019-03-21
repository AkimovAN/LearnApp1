package kaczmarek.learnapp1.ui.login.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;

import kaczmarek.learnapp1.R;
import kaczmarek.learnapp1.ui.posts.activities.NavigationViewActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final TextInputLayout passwordTextInput = findViewById(R.id.password_text_input);
        final TextInputEditText passwordEditText = findViewById(R.id.password_edit_text);
        final TextInputEditText nameEditText = findViewById(R.id.name_edit_text);
        final TextInputLayout nameTextInput = findViewById(R.id.name_text_input);

        Button nextButton = findViewById(R.id.next_button);

        nextButton.setOnClickListener(view -> {
            if (!isPasswordValid(passwordEditText.getText()) && !isNameValid(nameEditText.getText())) {
                passwordTextInput.setError(getString(R.string.error_password));
                nameTextInput.setError(getString(R.string.error_name));
            } else if (!isPasswordValid(passwordEditText.getText())) {
                passwordTextInput.setError(getString(R.string.error_password));

            } else if (!isNameValid(nameEditText.getText())){
                nameTextInput.setError(getString(R.string.error_name));
            } else {
                passwordTextInput.setError(null);
                nameTextInput.setError(null);
                Intent intent = new Intent(LoginActivity.this, NavigationViewActivity.class);
                startActivity(intent);
                finish();
            }
        });

        passwordEditText.setOnKeyListener((view, i, keyEvent) -> {
            if (isPasswordValid(passwordEditText.getText())) {
                passwordTextInput.setError(null);
            }
            if (isNameValid(nameEditText.getText())) {
                nameTextInput.setError(null);
            }
            return false;
        });

    }

    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 6;
    }

    private boolean isNameValid(@Nullable Editable text) {
        return text != null && text.toString().equals(getString(R.string.login_user1));
    }
}


