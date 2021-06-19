package com.example.tooltbhk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.AlertDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnAdd,btnsubmitdiem;
    LinearLayout layoutList;
    List<String> tcList = new ArrayList<>();


    private double tongDiemSauNhan = 0;
    private int tongsotc = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getApplication().setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // disable darkmode change
        layoutList = findViewById(R.id.layoutlist);
        btnAdd = findViewById(R.id.btnAdd);
        btnsubmitdiem = findViewById(R.id.btnsubmitdiem);

        tcList.add("1");
        tcList.add("2");
        tcList.add("3");
        tcList.add("4");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addView();
            }
        });

        btnsubmitdiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkifvalidate()){
                    double ketquadiem = tongDiemSauNhan / tongsotc;
//                    ketquaTv.setText("Kết quả điểm TBHK của bạn là: "+ketquadiem);

                    AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
                    dialog.setTitle("Kết quả tính");
                    dialog.setMessage("Kết quả điểm TBHK của bạn là: "+(double) Math.round(ketquadiem * 100) / 100);
//                    dialog.getWindow().getAttributes().windowAnimations = type;
                    dialog.show();
                };

            }
        });
    }

    private boolean checkifvalidate() {
        boolean rs = true;
        tongDiemSauNhan =0;
        tongsotc = 0;
        double diemHe4 = 0;
        for(int i = 0; i<layoutList.getChildCount(); i++) {
            View inputView = layoutList.getChildAt(i);
            EditText editText = inputView.findViewById(R.id.editText1);
            AppCompatSpinner spinner = inputView.findViewById(R.id.spinner);

            if(!editText.getText().toString().equals("")) {
                double diemHe10 = Double.parseDouble(editText.getText().toString());
                diemHe4 = diemHe4(diemHe10);

                int sotc =Integer.parseInt(tcList.get(spinner.getSelectedItemPosition()));
                tongsotc += sotc;
                tongDiemSauNhan += (sotc*diemHe4);
            } else rs = false;
        }

        if(tongsotc == 0 || tongDiemSauNhan == 0) {
            rs = false;
            Toast.makeText(getApplicationContext(),"Vui lòng thêm điểm",Toast.LENGTH_SHORT).show();
        }
        return rs;
    }

    private void addView() {
        final View inputDiem = getLayoutInflater().inflate(R.layout.row_add_diem, null, false);
        EditText editText = inputDiem.findViewById(R.id.editText1);
        AppCompatSpinner spinner = inputDiem.findViewById(R.id.spinner);
        ImageView closeBtn = inputDiem.findViewById(R.id.closeBtn);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, tcList);
        spinner.setAdapter(arrayAdapter);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(inputDiem);
            }
        });
        layoutList.addView(inputDiem);
    }
    private void removeView(View V) {
        layoutList.removeView(V);
    }

    private double diemHe4(double diemHe10) {
        // TODO Auto-generated method stub
        if(diemHe10 >= 9.0 && diemHe10 <= 10.0) {
            return 4;
        } else if(diemHe10 >= 8.0 && diemHe10 <= 8.9) {
            return 3.5;
        } else if(diemHe10 >= 7.0 && diemHe10 <= 7.9) {
            return 3;
        } else if(diemHe10 >= 6.5 && diemHe10 <= 6.9) {
            return 2.5;
        } else if(diemHe10 >= 5.5 && diemHe10 <= 6.4) {
            return 2;
        } else if(diemHe10 >= 5.0 && diemHe10 <= 5.4) {
            return 1.5;
        } else if(diemHe10 >= 4.0 && diemHe10 <= 4.9) {
            return 1;
        } else {
            return 0;
        }
    }

}