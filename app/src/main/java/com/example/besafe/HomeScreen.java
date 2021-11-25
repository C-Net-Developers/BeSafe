package com.example.besafe;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class HomeScreen extends AppCompatActivity {

    Button menuButton,sos;
    FusedLocationProviderClient fusedLocationProviderClient;
    double latitude,longitude;
    DBHelper DB;
    String contact,message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        sos=findViewById(R.id.sos);
        DB=new DBHelper(this);
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(HomeScreen.this);
        if(ActivityCompat.checkSelfPermission(HomeScreen.this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED&&
                ActivityCompat.checkSelfPermission(HomeScreen.this,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED)
        {
            getCurrentLocation();
        }
        //connecting db
        SQLiteDatabase myDatabase=this.openOrCreateDatabase("sosMsg",MODE_PRIVATE,null);

        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //retreiving sos message
                Cursor c=myDatabase.rawQuery("SELECT * FROM sosMsg",null);
                //storing starting index
                int messageIndex=c.getColumnIndex("message");

                 c.moveToLast();
                //Toast.makeText(EditSOSMessage.this, c.getString(messageIndex), Toast.LENGTH_SHORT).show();
                message=c.getString(messageIndex);
                c.close();
                if(ActivityCompat.checkSelfPermission(HomeScreen.this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED&&
                        ActivityCompat.checkSelfPermission(HomeScreen.this,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                {
                    getCurrentLocation();
                }
                else{
                    ActivityCompat.requestPermissions(HomeScreen.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);
                }
                //retrieving mobile numbers
                Cursor res=DB.viewData();
                if(res.getCount()==0)
                {
                    //no data selected
                    Toast.makeText(HomeScreen.this, "No entry exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                int ContactIndex=res.getColumnIndex("Contact");
                res.moveToFirst();
                while(!res.isAfterLast())
                {
                    contact=res.getString(ContactIndex);
                    res.moveToNext();
                    //checking permission for sending message
                    if(ContextCompat.checkSelfPermission(HomeScreen.this,Manifest.permission.SEND_SMS)
                            ==PackageManager.PERMISSION_GRANTED)
                    {
                        sendMessage(contact,message,latitude,longitude);
                    }
                    else
                    {
                        //when permission to send message is not granted
                        ActivityCompat.requestPermissions(HomeScreen.this,new String[]{Manifest.permission.SEND_SMS},200);
                    }
                    //Toast.makeText(HomeScreen.this, contact+Double.toString(latitude)+message, Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(HomeScreen.this, "Message sent successfully", Toast.LENGTH_SHORT).show();
               /* if(longitude==0&&latitude==0)

                {
                    getCurrentLocation();
                    Toast.makeText(HomeScreen.this, Double.toString(longitude)+" "+Double.toString(latitude), Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(HomeScreen.this, Double.toString(longitude)+" "+Double.toString(latitude), Toast.LENGTH_SHORT).show();

                }*/

            }
        });
        menuButton=(Button) findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu=new Intent(HomeScreen.this,Menu.class);
                startActivity(menu);
            }
        });

    }

    private void sendMessage(String contact, String message, double latitude, double longitude) {
        SmsManager smsManager=SmsManager.getDefault();
        //sending message
        String msg=message+" https://maps.google.com/?q="+Double.toString(latitude)+","+Double.toString(longitude);
        smsManager.sendTextMessage(contact,null,msg,null,null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100&&grantResults.length>0&&(grantResults[0]+grantResults[1]
                ==PackageManager.PERMISSION_GRANTED))
        {
            getCurrentLocation();
        }
        else{
            Toast.makeText(HomeScreen.this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        //intialise
        LocationManager locationManager = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE
        );
        //check
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            //when location service is enabled
            //get location
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    //initialise location
                    Location location = task.getResult();
                    if (location != null) {
                        //when location result is not null
                        //set latitude
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();

                    } else {
                        //when location result is null
                        //initialise location
                        //latitude = 0;
                        //longitude = 0;
                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                Location location1 = locationResult.getLastLocation();
                                latitude = location1.getLatitude();
                                longitude = location1.getLongitude();
                            }
                        };

                    }
                }
            });
        }
    }
}