package com.julius745.connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.julius745.connect.data.BackendService;
import com.julius745.connect.fragment.AddpostFragment;
import com.julius745.connect.fragment.ContentFragment;
import com.julius745.connect.fragment.SettingsFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (BackendService.service == null) {
            startActivity(new Intent(this, SplashScreenActivity.class));
            finish();
            return;
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        // WAJIB!!!!!
        // if (Build.VERSION.SDK_INT > 9) {
        // StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        // StrictMode.setThreadPolicy(policy);
        //}
    }

    ContentFragment contentFragment = new ContentFragment();
    AddpostFragment addpostFragment = new AddpostFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dashboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, contentFragment).commit();
                return true;
            case R.id.create:
                tryAskCameraPermissions();
                return true;
            case R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, settingsFragment).commit();
                return true;
        }
        return false;
    }

    void tryAskCameraPermissions() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    43);
        } else {
            takeImage();
        }
    }
                 Uri outputFileUri;
    void takeImage() {
        // android.os.FileUriExposedException workaround
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo;
        try {
            // place where to store camera taken picture
            photo = createTemporaryFile("capture", ".jpg");
            photo.delete();
        } catch (Exception e) {
            Log.v("Djsce Image capture", "Can't create file to take picture!");
            Toast.makeText(getApplicationContext(), "Please check SD card! Image shot is impossible!", Toast.LENGTH_LONG).show();
            return;
        }
        mImageUri = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        startActivityForResult(intent, 42);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 43) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if(Arrays.stream(grantResults).allMatch(x -> x == PackageManager.PERMISSION_GRANTED)) {
                    takeImage();
                } else {
                    Toast.makeText(this, "Gagal mengakses Kamera", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 42 && resultCode == RESULT_OK) {
            File f = grabImageFile();
            if(f!=null){
                Toast.makeText(getApplicationContext(),"File to upload is " + f.getAbsolutePath(),Toast.LENGTH_LONG).show();
                addpostFragment.setImage(Uri.fromFile(f));
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, addpostFragment).commit();
            }
            else{
                Toast.makeText(getApplicationContext(),"Image Capture Error",Toast.LENGTH_LONG).show();
            }
        }
    }
    private Uri mImageUri;
    private File createTemporaryFile(String part, String ext) throws Exception {
        File tempDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        return File.createTempFile(part, ext, tempDir);
    }

    public File grabImageFile() {
        File returnFile = null;
        try {
            //InputStream is = getContentResolver().openInputStream(mImageUri);
            returnFile = new File(mImageUri.getPath());//
        } catch (Exception e){
            Log.e("Image Capture Error",e.getMessage());
        }
        return returnFile;
    }
}