package app.sqliteapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper mydb;
    EditText editText_marks,editText_name,editText_surname,editText_id;
    Button btnAddData,btnviewall,btnupdate, btndelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb=new DatabaseHelper(this);
        editText_name=(EditText)findViewById(R.id.editText_name);
        editText_surname=(EditText)findViewById(R.id.editText_surname);
        editText_marks=(EditText)findViewById(R.id.editText_marks);
        editText_id=(EditText)findViewById(R.id.editText_id);

        btnAddData=(Button)findViewById(R.id.btn_adddata);
        btnviewall=(Button)findViewById(R.id.btn_viewall);
        btnupdate=(Button)findViewById(R.id.btn_update);
        btndelete=(Button)findViewById(R.id.btn_delete);
        addData();
        viewAll();
        updateData();
        deleteData();

    }
    public void addData()
    {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isinserted=mydb.insertData(editText_name.getText().toString(),
                                editText_surname.getText().toString(),editText_marks.getText().toString());
                        if(isinserted)
                        {
                            Toast.makeText(MainActivity.this,"data inserted",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"data not inserted",Toast.LENGTH_LONG).show();
                        }

                    }
                }
        );
    }
    public void viewAll()
    {
        btnviewall.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res=mydb.getAllData();
                        if(res.getCount()==0)
                        {
                            showMessage("Error","no data found");
                            return;
                        }
                        StringBuffer stringBuffer=new StringBuffer();
                        while(res.moveToNext())
                        {
                            stringBuffer.append("id: "+res.getString(0)+"\n");
                            stringBuffer.append("name: "+res.getString(1)+"\n");
                            stringBuffer.append("surname: "+res.getString(2)+"\n");
                            stringBuffer.append("marks: "+res.getString(3)+"\n\n");

                        }
                        showMessage("Data",stringBuffer.toString());
                    }
                }
        );
    }
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }
    public void updateData()
    {
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate=mydb.updateData(editText_id.getText().toString(),
                        editText_name.getText().toString(),editText_surname.getText().toString(),
                        editText_marks.getText().toString());
                if(isUpdate)
                {
                    Toast.makeText(MainActivity.this,"data updated",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"data not updated",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    public void deleteData()
    {
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deleteRows = mydb.deleteData(editText_id.getText().toString());
                if (deleteRows > 0) {
                    Toast.makeText(MainActivity.this, "data deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "data not updated", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
