package com.example.loctest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "LocTest";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("loc");
    TextView textViewNewRoadBlock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewNewRoadBlock = findViewById(R.id.textViewNewRoadblock);

        //myRef.setValue("Hello World!");


        /*final Query lastQuery = database.getReference().child("loc").orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String message = dataSnapshot.child("message").getValue().toString();
                Log.e(TAG, "onDataChange: -> " + dataSnapshot.getValue());
                //Log.e(TAG, "onDataChange: -> " + dataSnapshot.getValue(RoadBlock.class).getTimeStamp() );



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
                Log.e(TAG, "onCancelled: -> " + databaseError.toString() );
            }
        });*/

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                RoadBlock roadBlock;
                int i = 1;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    roadBlock = child.getValue(RoadBlock.class);
                    roadBlock.setKey(child.getKey());
                    Log.e(TAG, "onDataChange: -> " + i + " : " + roadBlock.toString() );
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: -> " + databaseError.toString() );
            }
        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                RoadBlock roadBlock = dataSnapshot.getValue(RoadBlock.class);
                textViewNewRoadBlock.setText(roadBlock.toString());

                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 500 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    v.vibrate(500);
                }

                Toast.makeText(MainActivity.this, roadBlock.toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addNewRoadblock(View view) {
        double lat = Math.random();
        double lon = Math.random();
        String id = myRef.push().getKey();

        RoadBlock roadBlock = new RoadBlock(lat, lon);
        Log.e(TAG, "addNewRoadblock: -> " + id );
        myRef.child(id).setValue(roadBlock);


    }
}
