package com.example.camino_android;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity
{
    private AndroidClient androidClient = null;
    private ExecutorService executorService = null;

    private TextView connectionText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        androidClient = new AndroidClient();
        executorService = Executors.newSingleThreadExecutor();

        connectionText = (TextView) findViewById(R.id.connectionText);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void connectToServer(View v)
    {
        executorService.execute(() -> {
            boolean connected = androidClient.connectToServer(this, "password");
            runOnUiThread(() -> {
                if(connected)
                    connectionText.setText("Connected to server.");
                else
                    connectionText.setText("Failed to connect to server.");
            });
        });
    }
}