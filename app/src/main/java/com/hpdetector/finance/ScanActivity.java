package com.hpdetector.finance;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class ScanActivity extends Activity {
    
    private TextView resultText;
    private TextView riskScoreText;
    private ListView detailsList;
    private Button shareButton;
    private Button scanAgainButton;
    private Button uninstallButton;
    private ArrayAdapter<String> adapter;
    private EnhancedSoundManager soundManager;
    private List<String> detectedPackages;
    private List<String> detectedAppNames;
    private UninstallManager uninstallManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        
        resultText = findViewById(R.id.resultText);
        riskScoreText = findViewById(R.id.riskScoreText);
        detailsList = findViewById(R.id.appsList);
        shareButton = findViewById(R.id.shareButton);
        scanAgainButton = findViewById(R.id.scanAgainButton);
        uninstallButton = findViewById(R.id.uninstallButton);
        
        shareButton.setOnClickListener(v -> shareResults());
        scanAgainButton.setOnClickListener(v -> restartScan());
        uninstallButton.setOnClickListener(v -> {
            if (uninstallManager != null && detectedPackages != null && !detectedPackages.isEmpty()) {
                uninstallManager.showUninstallOptions(detectedPackages, detectedAppNames);
            } else {
                Toast.makeText(this, "[SKULL] No targets found", Toast.LENGTH_SHORT).show();
            }
        });
        
        // Hide uninstall button initially
        uninstallButton.setVisibility(View.GONE);
        
        adapter = new ArrayAdapter<String>(this, R.layout.list_item_terminal, R.id.contentText) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView iconText = view.findViewById(R.id.iconText);
                TextView contentText = view.findViewById(R.id.contentText);
                
                String item = getItem(position);
                
                // Set terminal-style icons
                if (item.contains("[SKULL]") || item.contains("DANGER") || item.contains("TARGET")) {
                    iconText.setText("!");
                    iconText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                } else if (item.contains("[KNIFE]") || item.contains("WARNING") || item.contains("ATTACK")) {
                    iconText.setText("*");
                    iconText.setTextColor(getResources().getColor(android.R.color.holo_orange_light));
                } else if (item.contains("[OK]") || item.contains("SAFE") || item.contains("SUCCESS")) {
                    iconText.setText("+");
                    iconText.setTextColor(getResources().getColor(android.R.color.holo_green_light));
                } else if (item.contains("[SEARCH]") || item.contains("SCAN") || item.contains("INIT")) {
                    iconText.setText(">");
                    iconText.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
                } else {
                    iconText.setText("-");
                    iconText.setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
                
                return view;
            }
        };
        detailsList.setAdapter(adapter);
        
        soundManager = new EnhancedSoundManager(this);
        uninstallManager = new UninstallManager(this);
        
        new ScanTask().execute();
    }
    
    private class ScanTask extends AsyncTask<Void, String, SimpleDetector.ScanResult> {
        
        @Override
        protected void onPreExecute() {
            resultText.setText("[INITIALIZING] Preparing destruction sequence...");
            adapter.add("[SYSTEM] Finance Destroyer v2.0 - Professional Edition");
            adapter.add("[INIT] Initializing nuclear protocols...");
            adapter.add("");
            
            soundManager.playStartScanSound();
        }
        
        @Override
        protected SimpleDetector.ScanResult doInBackground(Void... params) {
            try {
                publishProgress("[SCAN] Analyzing installed packages...");
                Thread.sleep(1500);
                
                publishProgress("[ANALYZE] Processing finance applications...");
                Thread.sleep(1000);
                
                publishProgress("[CALCULATE] Computing threat assessment...");
                Thread.sleep(1000);
                
                SimpleDetector detector = new SimpleDetector(ScanActivity.this);
                return detector.performScan();
                
            } catch (Exception e) {
                SimpleDetector.ScanResult errorResult = new SimpleDetector.ScanResult();
                errorResult.status = "ERROR";
                errorResult.message = "Scan error: " + e.getMessage();
                return errorResult;
            }
        }
        
        @Override
        protected void onProgressUpdate(String... progress) {
            adapter.add(progress[0]);
        }
        
        @Override
        protected void onPostExecute(SimpleDetector.ScanResult result) {
            soundManager.playCompleteScanSound();
            
            adapter.clear();
            displayResults(result);
            
            // Store detected packages for uninstall
            detectedPackages = result.detectedPackages;
            detectedAppNames = result.detectedApps;
            
            // Show uninstall button if finance apps detected
            if (!result.detectedApps.isEmpty()) {
                uninstallButton.setVisibility(View.VISIBLE);
            }
            
            // Play alert sound
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    playAlertSound(result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
    
    private void displayResults(SimpleDetector.ScanResult result) {
        // Main result
        resultText.setText(result.message);
        resultText.setTextColor(getStatusColor(result.status));
        
        // Risk score
        riskScoreText.setText("RISK SCORE: " + result.riskScore + "/100");
        riskScoreText.setTextColor(getStatusColor(result.status));
        
        // Details
        adapter.add("[REPORT] === OPERATION RESULTS ===");
        adapter.add("");
        
        if (!result.detectedApps.isEmpty()) {
            adapter.add("[DETECTED] FINANCE APPLICATIONS FOUND:");
            for (String app : result.detectedApps) {
                adapter.add("• " + app);
            }
            adapter.add("");
        }
        
        adapter.add("[PROTOCOL] === RECOMMENDED ACTIONS ===");
        adapter.add(result.recommendation);
        
        if (result.isDangerous()) {
            adapter.add("");
            adapter.add("[ALERT] CRITICAL THREAT DETECTED:");
            adapter.add("[WARNING] Device compromised by finance apps!");
            adapter.add("[DANGER] Immediate action required!");
        } else if (result.isSafe()) {
            adapter.add("");
            adapter.add("[SECURE] DEVICE STATUS: CLEAN");
            adapter.add("[OK] No financial threats detected.");
            adapter.add("[INFO] Verify purchase documentation.");
        }
    }
    
    private int getStatusColor(String status) {
        switch (status) {
            case "DANGER": return Color.RED;
            case "WARNING": return Color.parseColor("#FF8C00");
            case "SAFE": return Color.GREEN;
            default: return Color.BLACK;
        }
    }
    
    private void playAlertSound(SimpleDetector.ScanResult result) {
        if (result.isDangerous()) {
            soundManager.playFinanceDetectedAlert();
        } else if ("WARNING".equals(result.status)) {
            soundManager.playWarningAlert();
        } else if (result.isSafe()) {
            soundManager.playSafeSound();
        }
    }
    
    private void shareResults() {
        StringBuilder results = new StringBuilder();
        results.append("[REPORT] FINANCE DESTROYER - OPERATION RESULTS\n\n");
        results.append(resultText.getText().toString()).append("\n");
        results.append(riskScoreText.getText().toString()).append("\n\n");
        
        for (int i = 0; i < adapter.getCount(); i++) {
            results.append(adapter.getItem(i)).append("\n");
        }
        
        results.append("\n[CLASSIFIED] Finance Destroyer Professional v2.0");
        
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, results.toString());
        startActivity(Intent.createChooser(shareIntent, "Share Scan Results"));
    }
    
    private void restartScan() {
        finish();
        startActivity(getIntent());
    }
    

    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (soundManager != null) {
            soundManager.release();
        }
    }
}
