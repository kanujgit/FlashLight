package sathya.com.flashlight;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

   ImageButton onBtn,share,info;
    boolean flag=true;
    String cameraID;
    CameraManager cameraManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.hide();
        }
        onBtn=(ImageButton)findViewById(R.id.button);
        share=(ImageButton)findViewById(R.id.share);
        info=(ImageButton)findViewById(R.id.info);
         cameraManager =(CameraManager)getSystemService(Context.CAMERA_SERVICE);
        try{
            cameraID = cameraManager.getCameraIdList()[0];
        }catch(Exception e){
            e.printStackTrace();
        }
        onBtn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                try {
                    if (flag)
                    {
                        onBtn.setImageResource(R.drawable.lightbulb);
                        cameraManager.setTorchMode(cameraID, true);
                        flag = false;
                    } else {
                        onBtn.setImageResource(R.drawable.light_bulb);
                        cameraManager.setTorchMode(cameraID, false);
                        flag=true;
                    }
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
            });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder ad=new AlertDialog.Builder(MainActivity.this);
                ad.setTitle("Information");
                ad.setMessage("This Application developed by \nMr. Anuj Kumar \nIf you have any query mail on me\nanujkumar30dec@gmail.com ");
                ad.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
                ad.setCancelable(false);
                ad.create().show();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try{
                    Intent intent=new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT,"Flash Light");
                    String sAux = "\nLet me recommend you this application\n\n";
                    sAux = sAux + "wait for link \n\n";
                    intent.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(intent, "choose one"));
                } catch(Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Something gone wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        }
    }