package net.soulwolf.katanalistview.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onListViewClick(View v){
        startActivity(new Intent(this,ListActivity.class));
    }

    public void onGridViewClick(View v){
        startActivity(new Intent(this,GridActivity.class));
    }
}
