package com.android.pdfreader;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final int PAGE_INDEX = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.pdfPageView);

        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/Download/markdown-cheatsheet-online.pdf");
        PdfRenderer pdfRenderer = null;
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
             parcelFileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY);
             pdfRenderer = new PdfRenderer(parcelFileDescriptor);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            PdfRenderer.Page currentPage = pdfRenderer.openPage(PAGE_INDEX);
            Bitmap currentPageBitmap = Bitmap.createBitmap(currentPage.getWidth(), currentPage.getHeight(), Bitmap.Config.ARGB_8888);

            currentPage.render(currentPageBitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

            imageView.setImageBitmap(currentPageBitmap);

            currentPage.close();
            pdfRenderer.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Page " + (PAGE_INDEX+1) + " is not present!", Toast.LENGTH_SHORT).show();
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
