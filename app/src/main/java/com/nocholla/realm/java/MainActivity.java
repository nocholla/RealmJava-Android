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
    Button add, view, update, delete;
    EditText roll_no, name;
    TextView text;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = findViewById(R.id.add);
        view = findViewById(R.id.view);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        roll_no = findViewById(R.id.roll_no);
        name = findViewById(R.id.name);
        text = findViewById(R.id.text);

        Realm.init(this);
        realm = Realm.getDefaultInstance();
    }

    public void clickAction(View view) {
        switch (view.getId()){
            case R.id.add:  addRecord();
                break;
            case R.id.view: viewRecord();
                break;
            case R.id.update:   updateRecord();
                break;
            case R.id.delete:   deleteRecord();
        }
    }

    public void addRecord(){
        realm.beginTransaction();

        Student student = realm.createObject(Student.class);
        student.setRoll_no(Integer.parseInt(roll_no.getText().toString()));
        student.setName(name.getText().toString());

        realm.commitTransaction();
    }

    public void viewRecord(){
        RealmResults<Student> results = realm.where(Student.class).findAll();

        text.setText("");

        for(Student student : results){
            text.append(student.getRoll_no() + " " + student.getName() + "\n");
        }
    }

    public void updateRecord(){
        RealmResults<Student> results = realm.where(Student.class).equalTo("roll_no", Integer.parseInt(roll_no.getText().toString())).findAll();

        realm.beginTransaction();

        for(Student student : results){
            student.setName(name.getText().toString());
        }

        realm.commitTransaction();
    }

    public void deleteRecord(){
        RealmResults<Student> results = realm.where(Student.class).equalTo("roll_no", Integer.parseInt(roll_no.getText().toString())).findAll();

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
