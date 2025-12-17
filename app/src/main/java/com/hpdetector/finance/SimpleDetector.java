package com.hpdetector.finance;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import java.util.ArrayList;
import java.util.List;

public class SimpleDetector {
    
    private Context context;
    private PackageManager pm;
    
    // Finance apps database
    private String[][] financeApps = {
        {"com.akulaku.user", "Akulaku"},
        {"com.kredivo.user", "Kredivo"},
        {"com.homecredit.mobile", "Home Credit"},
        {"com.indodana.app", "Indodana"},
        {"com.tunaiku.mobile", "Tunaiku"},
        {"com.kredit.pintar", "Kredit Pintar"},
        {"com.julo.app", "Julo"},
        {"com.adakami.app", "AdaKami"},
        {"com.baf.mobile", "BAF Finance"},
        {"com.fif.mobile", "FIF Group"},
        {"com.adira.finance", "Adira Finance"},
        {"com.oto.finance", "OTO Finance"}
    };
    
    public SimpleDetector(Context context) {
        this.context = context;
        this.pm = context.getPackageManager();
    }
    
    public ScanResult performScan() {
        ScanResult result = new ScanResult();
        
        try {
            List<ApplicationInfo> installedApps = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            
            for (ApplicationInfo app : installedApps) {
                for (String[] financeApp : financeApps) {
                    if (app.packageName.equals(financeApp[0])) {
                        result.detectedApps.add(financeApp[1]);
                        result.detectedPackages.add(financeApp[0]); // Store package name
                        result.riskScore += 25;
                    }
                }
            }
            
            // Calculate status
            if (result.riskScore >= 75) {
                result.status = "DANGER";
                result.message = "🚨 BAHAYA! Ditemukan " + result.detectedApps.size() + " aplikasi finance";
                result.recommendation = "JANGAN BELI HP INI!";
            } else if (result.riskScore >= 25) {
                result.status = "WARNING";
                result.message = "⚠️ PERINGATAN! Ada " + result.detectedApps.size() + " aplikasi finance";
                result.recommendation = "PERTIMBANGKAN ULANG!";
            } else {
                result.status = "SAFE";
                result.message = "✅ AMAN! Tidak ada aplikasi finance terdeteksi";
                result.recommendation = "RELATIF AMAN UNTUK DIBELI";
            }
            
        } catch (Exception e) {
            result.status = "ERROR";
            result.message = "Error: " + e.getMessage();
        }
        
        return result;
    }
    
    public static class ScanResult {
        public List<String> detectedApps = new ArrayList<>();
        public List<String> detectedPackages = new ArrayList<>(); // Package names for uninstall
        public int riskScore = 0;
        public String status = "";
        public String message = "";
        public String recommendation = "";
        
        public boolean isDangerous() {
            return "DANGER".equals(status);
        }
        
        public boolean isSafe() {
            return "SAFE".equals(status);
        }
    }
}
