package com.example.mperk.sayac;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    private int count;
    private Button btn;
    private SharedPreferences preferences,ayarlar;
    private RelativeLayout arkaplan;
    private Boolean ses_durumu,titresim_durumu;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btn = (Button) findViewById(R.id.button);
        arkaplan = (RelativeLayout) findViewById(R.id.rl);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        final MediaPlayer ses = MediaPlayer.create(getApplicationContext(),R.raw.buttonsesi);
        final Vibrator titresim = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        ayarlariYukle();
        reklamiYukle();
        count = preferences.getInt("count_anahtari", 0);//buradaki sıfı değeri hiç olmadığı zaman yani kaydetme işlemi yapılmadığı zaman sıfır değeri atanır.
        btn.setText(""+count);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ses_durumu){
                    ses.start();
                }
                if(titresim_durumu){
                    titresim.vibrate(250);
                }
                count++;
                btn.setText(""+count);
            }
        });
    }

    private void reklamiYukle() {
        adView=new AdView(this);
        adView.setAdSize(AdSize.BANNER); //burada değişik reklam boyutlarını araştırarak kullanabirsin.
        adView.setAdUnitId(getString(R.string.reklam_kimligi));

        LinearLayout layout = (LinearLayout) findViewById(R.id.reklam);
        layout.addView(adView);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);

    }

    private void ayarlariYukle() {
        String pozisyon= ayarlar.getString("arkaplan","3"); //XML dosyasında verdiğimiz key değeri ile ulaşıyoruz.
        //Buradaki üç değeri ise default değerinin hangi pozisyon olacağını seçer. Bu da renk_pozisyon dizisindeki indisler ile sağlanır.
        switch (Integer.valueOf(pozisyon)){
            case 0:
                arkaplan.setBackgroundColor(Color.parseColor("#D500F9"));
                break;
            case 1:
                arkaplan.setBackgroundColor(Color.parseColor("#2196F3"));
                break;
            case 2:
                arkaplan.setBackgroundColor(Color.parseColor("#1DE9B6"));
                break;
            case 3:
                arkaplan.setBackgroundColor(Color.parseColor("#D32F2F"));
                break;
            case 4:
                arkaplan.setBackgroundColor(Color.parseColor("#FFFF00"));
                break;
            case 5:
                arkaplan.setBackgroundColor(Color.parseColor("#9E9E9E"));
                break;
            case 6:
                arkaplan.setBackgroundColor(Color.parseColor("#EC407A"));
                break;
            case 7:
                arkaplan.setBackgroundColor(Color.parseColor("#1A237E"));
                break;
            case 8:
                arkaplan.setBackgroundResource(R.drawable.background);
                break;
        }

        //ayarlar xml'indeki key değerleri ile getiriyoruz
        ses_durumu=ayarlar.getBoolean("ses",false);
        titresim_durumu=ayarlar.getBoolean("titresim",false);
        ayarlar.registerOnSharedPreferenceChangeListener(MainActivity.this);//Buraya getApplicationContext() yazınca hata verdi sebebini bilmiyoruz.


    }

    @Override
    protected void onPause() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("count_anahtari",count);
        editor.commit();
        super.onPause();
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
            Intent intent = new Intent(getApplicationContext(),Ayarlar.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.sifirla) {
            count =  0;
            btn.setText(""+count);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        ayarlariYukle();
    }
}
