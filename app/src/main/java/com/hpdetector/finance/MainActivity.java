package com.hpdetector.finance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    
    private Button scanButton;
    private Button uninstallButton;
    private TextView statusText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        scanButton = findViewById(R.id.scanButton);
        uninstallButton = findViewById(R.id.uninstallButton);
        statusText = findViewById(R.id.statusText);
        
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScan();
            }
        });
        
        uninstallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUninstallActivity();
            }
        });
        
        // Update status for self-scan mode
        statusText.setText("[NUCLEAR] MODE: FINANCE DESTROYER\n\n" +
                          "[TARGET] DETECTION CAPABILITIES:\n" +
                          "├─ Finance Apps Scanner\n" +
                          "├─ 12+ Indonesian Credit Apps\n" +
                          "├─ Real-time Risk Analysis\n" +
                          "├─ Root-level Destruction\n\n" +
                          "[MISSION] OBJECTIVES:\n" +
                          "├─ Eliminate Financial Parasites\n" +
                          "├─ Verify Device Security\n" +
                          "├─ Protect User Privacy\n" +
                          "└─ Complete Data Annihilation");
    }
    
    private void startScan() {
        scanButton.setText("[INITIALIZING] DESTRUCTION SEQUENCE...");
        scanButton.setEnabled(false);
        
        scanButton.postDelayed(() -> {
            Intent intent = new Intent(this, ScanActivity.class);
            startActivity(intent);
            
            scanButton.setText("[INITIATE] SCAN & DESTROY SEQUENCE");
            scanButton.setEnabled(true);
        }, 1000);
    }
    
    public void recheckConnection(View view) {
        // Simple refresh for self-scan mode
        statusText.setText("[REFRESH] SYSTEM DIAGNOSTICS...\n\nNuclear mode ready for deployment!");
        
        view.postDelayed(() -> {
            statusText.setText("[BLOOD] MODE: PEMBUNUH FINANCE [BLOOD]\n\n" +
                              "[SKULL] AKAN MENDETEKSI DAN MEMBUNUH:\n" +
                              "[KNIFE] Aplikasi finance tersembunyi\n" +
                              "[COFFIN] 12+ aplikasi kredit berbahaya\n" +
                              "[BLOOD] Analisis risiko mematikan\n\n" +
                              "[BOMB] COCOK UNTUK:\n" +
                              "[FIRE] Membersihkan HP dari parasit\n" +
                              "[BOLT] Verifikasi HP bebas hutang\n" +
                              "[SHIELD] Perlindungan dari penipuan");
        }, 1000);
    }
    
    private void startUninstallActivity() {
        uninstallButton.setText("[LOADING] NUCLEAR PROTOCOLS...");
        uninstallButton.setEnabled(false);
        
        uninstallButton.postDelayed(() -> {
            Intent intent = new Intent(this, UninstallActivity.class);
            startActivity(intent);
            
            uninstallButton.setText("[NUCLEAR] DIRECT ANNIHILATION MODE");
            uninstallButton.setEnabled(true);
        }, 500);
    }
}
