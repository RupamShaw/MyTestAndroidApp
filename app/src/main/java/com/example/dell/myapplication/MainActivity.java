package com.example.dell.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    public static final String TAG = "rupamslog";
    Button addbutton;
    EditText identEditText, nameEditText, addressEditText, emailEditText, phoneEditText;
    TabHost tabhost;
    private TabHost.TabSpec tabspecs;
    List<Student> listStudent = new ArrayList<Student>();
    ListView studetListView;
    ImageView studentImgView;
    Uri imageUri=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewbyId();
        textChanged();
        tabCreator();

        studetListView = (ListView) findViewById(R.id.listView);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent(identEditText.getText().toString(), nameEditText.getText().toString(), addressEditText.getText().toString(), phoneEditText.getText().toString(), emailEditText.getText().toString(),imageUri);
                ArrayAdapter<Student> adapter = new StudentListAdapter();
                studetListView.setAdapter(adapter);
                Toast.makeText(getApplicationContext(), nameEditText.getText().toString() + " added to list", Toast.LENGTH_SHORT).show();
            }
        });
        studentImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select contact image"),1);

            }
        });
        Log.i(TAG, "onCreate");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if (resultCode==RESULT_OK)
        if(requestCode==1) {
            imageUri=data.getData();
            studentImgView.setImageURI(data.getData());
        }
    }

    private void addStudent(String id, String name, String address, String phone, String email,Uri imageUri) {
        listStudent.add(new Student(id, name, address, phone, email,imageUri));
    }

    private void tabCreator() {
        tabhost.setup();
        tabspecs = tabhost.newTabSpec("creator");
        tabspecs.setContent(R.id.creatorTab);
        tabspecs.setIndicator("Creator");
        tabhost.addTab(tabspecs);

        tabspecs = tabhost.newTabSpec("listStudent");
        tabspecs.setContent(R.id.listTab);
        tabspecs.setIndicator("ListStudent");
        tabhost.addTab(tabspecs);
    }

    public void viewbyId() {
        addbutton = (Button) findViewById(R.id.addButton);
        identEditText = (EditText) findViewById(R.id.ident);
        nameEditText = (EditText) findViewById(R.id.name);
        addressEditText = (EditText) findViewById(R.id.address);
        emailEditText = (EditText) findViewById(R.id.email);
        phoneEditText = (EditText) findViewById(R.id.phone);
        tabhost = (TabHost) findViewById(R.id.tabHost);
        studentImgView=(ImageView)findViewById(R.id.studentImageView);


    }

    public void textChanged() {
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addbutton.setEnabled(!nameEditText.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public class StudentListAdapter extends ArrayAdapter<Student> {
        public StudentListAdapter() {
            super(MainActivity.this, R.layout.listview_item, listStudent);
            //   Log.i(TAG, "in constructor of adapter");

        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);
            Log.i(TAG, "in getview");
            Student currentstudent = listStudent.get(position);
            TextView id = (TextView) view.findViewById(R.id.liststudentID);
            id.setText(currentstudent.get_id());
            TextView name = (TextView) view.findViewById(R.id.listname);
            name.setText(currentstudent.get_name());
            TextView address = (TextView) view.findViewById(R.id.listaddress);
            address.setText(currentstudent.get_address());
            TextView phone = (TextView) view.findViewById(R.id.listphone);
            phone.setText(currentstudent.get_phone());
            TextView email = (TextView) view.findViewById(R.id.listemail);
            email.setText(currentstudent.get_email());
            ImageView image=(ImageView) view.findViewById(R.id.listimageView);
            image.setImageURI(currentstudent.get_imageUri());
            return view;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
