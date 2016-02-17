package com.example.omiplekevin.fileencryptiondecryption;

import android.app.ProgressDialog;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    public static final String filename = "sample-pdf1.pdf";

    public static final String enc_type = "AES/CFB8/NoPadding";

    public static final String MAIN_FOLDER = Environment.getExternalStorageDirectory() + "/DDC";

    public static final String SECRET_KEY = "!QAZ@WSX#EDC$RFV";

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File f = new File(MAIN_FOLDER);
        f.mkdir();

        f = new File(MAIN_FOLDER + "/temp");
        f.mkdir();
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

    public void downloadAndEncrypt(View v){
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    Log.e("MAIN", "downloading... " + filename);
                    URL sourceURL = new URL("http://192.168.8.10/ddcapi/" + filename);
                    URLConnection urlConnection = sourceURL.openConnection();
                    urlConnection.connect();

                    InputStream inputStream = new BufferedInputStream(sourceURL.openStream(), 4096);
                    FileOutputStream outputStream = new FileOutputStream(MAIN_FOLDER + "/" + filename);

                    //cipher
                    SecretKeySpec sks = new SecretKeySpec(SECRET_KEY.getBytes(), enc_type);
                    Cipher cipher = Cipher.getInstance(enc_type);
                    cipher.init(Cipher.ENCRYPT_MODE, sks, new IvParameterSpec(new byte[cipher.getBlockSize()]));

                    CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);


                    byte data[] = new byte[4096];
                    int count;
                    while ((count = inputStream.read(data)) != -1) {
                        Log.e("MAIN", count + "");
                        cipherOutputStream.write(data, 0, count);
                    }

                    outputStream.flush();
                    outputStream.close();
                    inputStream.close();
                    cipherOutputStream.flush();
                    cipherOutputStream.close();

                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(runnable);
        t.start();
    }

    public void decryptContent(View v) {

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.show();
        progressDialog.setMessage("Decrypting...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    FileInputStream fileInputStream = new FileInputStream(MAIN_FOLDER + "/" + filename);
                    FileOutputStream fileOutputStream = new FileOutputStream(MAIN_FOLDER + "/temp/" + filename);
                    SecretKeySpec sks = new SecretKeySpec(SECRET_KEY.getBytes(), enc_type);
                    Cipher cipher = Cipher.getInstance(enc_type);
                    cipher.init(Cipher.DECRYPT_MODE, sks, new IvParameterSpec(new byte[cipher.getBlockSize()]));

                    CipherInputStream cipherInputStream = new CipherInputStream(fileInputStream, cipher);

                    byte data[] = new byte[4096];
                    int count;
                    while ((count = cipherInputStream.read(data)) != -1) {
                        fileOutputStream.write(data, 0, count);
                    }

                    fileOutputStream.flush();
                    fileOutputStream.close();
                    cipherInputStream.close();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

    }
}
