package com.erbek.remotepatientm;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class DoctorHomeFragment extends Fragment {

    private EditText choosepatient;
    private TextView text11;
    private Spinner spinnerpatient;
    private TextView showpatient;
    private ArrayList<CharSequence> patientlist;
    private ArrayAdapter<CharSequence> adapterPatientlist;
    private String takenuserid;
    private TextView doctorname;
    private Spinner denemespinner;
    private ArrayAdapter<CharSequence> denemeadapter;
    private EditText pulse,spo2,roomtemp,stress,bodytemp,hum;
    private Button ecg;
    private TextView patientname;
    private EditText denemedit;
    private DatabaseReference mDatabase;
    private String userid;
    private Button showgraph;
    private TextView userid1;
    private ImageView img1,img2,img3,img4,img5;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_doctor_home, container, false);


        denemedit = (EditText) v.findViewById(R.id.editText4);

        ecg = (Button) v.findViewById(R.id.button4);
        pulse = (EditText) v.findViewById(R.id.editText3);
        spo2 = (EditText) v.findViewById(R.id.editText5);
        roomtemp = (EditText) v.findViewById(R.id.editText);
        stress = (EditText) v.findViewById(R.id.editText2);
        bodytemp = (EditText) v.findViewById(R.id.editText4);
        hum = (EditText) v.findViewById(R.id.editText2);
        img1 = (ImageView) v.findViewById(R.id.imageView7);
        img2 = (ImageView) v.findViewById(R.id.imageView8);
        img3 = (ImageView) v.findViewById(R.id.imageView9);
        img4 = (ImageView) v.findViewById(R.id.imageView10);
        img5 = (ImageView) v.findViewById(R.id.imageView11);
        patientname = (TextView) v.findViewById(R.id.textView6);


        mDatabase = FirebaseDatabase.getInstance().getReference("values");


            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 0;
                    String abc[] = new String[6];


                    for (DataSnapshot snp : dataSnapshot.getChildren()) {

                        abc[i] = snp.getValue().toString();
                        roomtemp.setText(abc[5] + "°C");
                        pulse.setText(abc[2] + "bpm");
                        hum.setText('%' + abc[3]);
                        spo2.setText("% " + abc[4]);

                        if (i == 0) {
                            float x1 = Float.parseFloat(abc[0]);
                            Float d = (float) (Math.round(x1 * 10.0) / 10.0);
                            String x2 = Float.toString(d);
                            bodytemp.setText(x2 + "°C");
                        }


                        //SetEmoji
                        String pulseimg = abc[2];
                        if (!TextUtils.isEmpty(pulseimg)) {
                            int sayi = Integer.parseInt(pulseimg);
                            if (sayi > 120 || sayi < 70) {
                                img1.setImageResource(R.drawable.sad);
                            } else {
                                img1.setImageResource(R.drawable.smile);
                            }
                        }
                        String spo2img = abc[4];
                        if (!TextUtils.isEmpty(spo2img)) {
                            int sayi = Integer.parseInt(spo2img);
                            if (sayi > 100 || sayi < 88) {
                                img2.setImageResource(R.drawable.sad);
                            } else {
                                img2.setImageResource(R.drawable.smile);
                            }
                        }
                        String bodytempimg = abc[0];
                        if (!TextUtils.isEmpty(bodytempimg)) {
                            Float sayi = Float.parseFloat(bodytempimg);
                            if (sayi > 37 || sayi < 35.5) {
                                img3.setImageResource(R.drawable.sad);
                            } else {
                                img3.setImageResource(R.drawable.smile);
                            }
                        }
                        String roomtempimg = abc[5];
                        if (!TextUtils.isEmpty(roomtempimg)) {
                            Float sayi = Float.parseFloat(roomtempimg);
                            if (sayi > 35 || sayi < 10) {
                                img4.setImageResource(R.drawable.sad);
                            } else {
                                img4.setImageResource(R.drawable.smile);
                            }
                        }
                        String humimg = abc[3];
                        if (!TextUtils.isEmpty(humimg)) {
                            int sayi = Integer.parseInt(humimg);
                            if (sayi > 55 || sayi < 10) {
                                img5.setImageResource(R.drawable.sad);
                            } else {
                                img5.setImageResource(R.drawable.smile);
                            }
                        }


                        i++;
                    }
                    // String value = dataSnapshot.getValue(String.class);
                    //pulse.setText(value);
                    //System.out.println(dataSnapshot.getValue());

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Toast.makeText(getContext(), "hata!", Toast.LENGTH_SHORT).show();
                }
            });







        denemespinner=(Spinner) v.findViewById(R.id.spinner);
        denemeadapter = ArrayAdapter.createFromResource(getContext(),R.array.patientlist, android.R.layout.simple_spinner_item);
        denemeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        denemespinner.setAdapter(denemeadapter);



        denemespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                patientname.setText(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            patientname.setText(adapterView.getItemAtPosition(0).toString());
            }
        });


        showgraph = (Button) v.findViewById(R.id.button4);
        showgraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ShowGraph.class);
                startActivity(intent);
            }
        });




        return v;
    }
}