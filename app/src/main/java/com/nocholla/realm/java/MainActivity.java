package com.nocholla.realm.java;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nocholla.realm.java.model.Student;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    Button addRecordBtn, viewRecordBtn, updateRecordBtn, deleteRecordBtn;
    EditText rollNoEditTxt, nameEditTxt;
    TextView studentTxt;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addRecordBtn = findViewById(R.id.add);
        viewRecordBtn = findViewById(R.id.view);
        updateRecordBtn = findViewById(R.id.update);
        deleteRecordBtn = findViewById(R.id.delete);

        rollNoEditTxt = findViewById(R.id.roll_no);
        nameEditTxt = findViewById(R.id.name);
        studentTxt = findViewById(R.id.text);

        Realm.init(this);
        realm = Realm.getDefaultInstance();
    }

    public void clickAction(View view) {
        switch (view.getId()){
            case R.id.add:
                addRecord();
                break;
            case R.id.view:
                viewRecord();
                break;
            case R.id.update:
                updateRecord();
                break;
            case R.id.delete:
                deleteRecord();
        }
    }

    public void addRecord(){
        realm.beginTransaction();

        Student student = realm.createObject(Student.class);
        student.setRollNo(Integer.parseInt(rollNoEditTxt.getText().toString()));
        student.setName(nameEditTxt.getText().toString());

        realm.commitTransaction();
    }

    public void viewRecord(){
        RealmResults<Student> results = realm.where(Student.class).findAll();

        studentTxt.setText("");

        for(Student student : results){
            studentTxt.append(student.getRollNo() + " " + student.getName() + "\n");
        }
    }

    public void updateRecord(){
        RealmResults<Student> results = realm.where(Student.class).equalTo("rollNo", Integer.parseInt(rollNoEditTxt.getText().toString())).findAll();

        realm.beginTransaction();

        for(Student student : results){
            student.setName(nameEditTxt.getText().toString());
        }

        realm.commitTransaction();
    }

    public void deleteRecord(){
        RealmResults<Student> results = realm.where(Student.class).equalTo("rollNo", Integer.parseInt(rollNoEditTxt.getText().toString())).findAll();

        realm.beginTransaction();

        results.deleteAllFromRealm();

        realm.commitTransaction();
    }

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }

}
