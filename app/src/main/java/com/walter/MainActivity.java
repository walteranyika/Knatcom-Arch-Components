package com.walter;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.walter.repository.UsersRepository;

public class MainActivity extends AppCompatActivity {
    EditText inputNames, inputAge;
    TextView txtRecords;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputNames = findViewById(R.id.inputNames);
        inputAge = findViewById(R.id.inputAge);
        txtRecords = findViewById(R.id.txtRecords);
        btnSave = findViewById(R.id.btnSave);

        inputNames.requestFocus();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save info
                save();
            }
        });
        MyDatabase.getInstatnce(this).getUserDao().getCount()
                .observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                txtRecords.setText(integer+"");
            }
        });

        UsersRepository usersRepository=new UsersRepository();
        usersRepository.makeFrequentCalls();
    }

    private void save() {
       String names= inputNames.getText().toString().trim();
       String age_string = inputAge.getText().toString().trim();
       if (names.isEmpty() || age_string.isEmpty()){
           Toast.makeText(this, "Fill All Values", Toast.LENGTH_SHORT).show();
           return;
       }
       int age = Integer.parseInt(age_string);
       User x = new User();
       x.setName(names);
       x.setAge(age);
       MyDatabase.getInstatnce(this).getUserDao().insertUser(x);
       inputNames.setText("");
       inputAge.setText("");
       inputNames.requestFocus();
    }
}
