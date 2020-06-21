package br.com.petersonmashni.minhascontasemdia.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.petersonmashni.minhascontasemdia.R;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail, edtSenha;
    private Button btnEntrar, btnCadastar;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = (EditText)findViewById(R.id.login_edtEmail);
        edtSenha = (EditText)findViewById(R.id.login_edtSenha);

        btnEntrar = (Button) findViewById(R.id.login_btnEntrar);
        btnCadastar = (Button) findViewById(R.id.login_btnCadastrar);

        auth = FirebaseAuth.getInstance();
        auth.useAppLanguage();
        auth.addAuthStateListener(
            new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    user = auth.getCurrentUser();

                    if (user != null)
                    {
                        Intent intent = new Intent(LoginActivity.this, InicioActivity.class);
                        startActivity(intent);
                    }
                }
        });

        btnCadastar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entrar();
            }
        });
    }

    private void entrar(){
        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();
        findViewById(R.id.login_loading).setVisibility(View.VISIBLE);

        if(email.isEmpty() | senha.isEmpty())
            Toast.makeText(LoginActivity.this, R.string.login_msg_erro_email_senha_vazios, Toast.LENGTH_LONG).show();
        else
        {
            auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    findViewById(R.id.login_loading).setVisibility(View.GONE);
                    if(!task.isSuccessful())
                        Toast.makeText(
                                LoginActivity.this,
                                String.format(
                                        getString(R.string.login_msg_erro_login),
                                        task.getException().getMessage()),
                                Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void cadastrar(){
        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();
        findViewById(R.id.login_loading).setVisibility(View.VISIBLE);

        if(email.isEmpty() | senha.isEmpty())
            Toast.makeText(LoginActivity.this, R.string.login_msg_erro_email_senha_vazios, Toast.LENGTH_LONG).show();
        else
        {
            auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    findViewById(R.id.login_loading).setVisibility(View.GONE);
                    if(task.isSuccessful()) {
                        user = auth.getCurrentUser();
                        Toast.makeText(LoginActivity.this, R.string.login_msg_usuario_criado, Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(
                                LoginActivity.this,
                                String.format(
                                        getString(R.string.login_msg_erro_criar_usuario),
                                        task.getException().getMessage()),
                                Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
