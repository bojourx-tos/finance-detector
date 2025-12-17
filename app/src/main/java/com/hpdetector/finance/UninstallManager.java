package com.hpdetector.finance;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

public class UninstallManager {
    
    private Context context;
    private PackageManager pm;
    private Handler mainHandler;
    
    public UninstallManager(Context context) {
        this.context = context;
        this.pm = context.getPackageManager();
        this.mainHandler = new Handler(Looper.getMainLooper());
    }
    
    public void showUninstallOptions(List<String> detectedPackages, List<String> appNames) {
        if (detectedPackages == null || detectedPackages.isEmpty()) {
            Toast.makeText(context, "Tidak ada aplikasi finance yang terdeteksi", Toast.LENGTH_SHORT).show();
            return;
        }
        
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("[SKULL] DESTROY FINANCE APPS [SKULL]");
        builder.setMessage("[NUCLEAR] REAL DESTRUCTION WARNING!\n\n" +
                          "This will PERMANENTLY DESTROY finance apps using:\n" +
                          "• Root-level file deletion\n" +
                          "• Data directory annihilation\n" +
                          "• Cache and database destruction\n" +
                          "• System registry cleanup\n\n" +
                          "Ditemukan " + detectedPackages.size() + " aplikasi finance:\n" +
                          String.join(", ", appNames) + "\n\n" +
                          "Pilih metode pembunuhan:");
        
        builder.setPositiveButton("[SKULL] DESTROY ALL [SKULL]", (dialog, which) -> {
            confirmMassUninstall(detectedPackages, appNames);
        });
        
        builder.setNeutralButton("[KNIFE] SELECT TARGETS [KNIFE]", (dialog, which) -> {
            showSelectiveUninstall(detectedPackages, appNames);
        });
        
        builder.setNegativeButton("BATAL", null);
        
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    
    private void confirmMassUninstall(List<String> packages, List<String> names) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("[POISON] CONFIRM MASS DESTRUCTION [POISON]");
        builder.setMessage("[CRITICAL] MASS DESTRUCTION PROTOCOL!\n\n" +
                          "Akan MEMBUNUH " + packages.size() + " aplikasi finance:\n\n" +
                          String.join("\n• ", names) + "\n\n" +
                          "[SKULL] Proses EKSEKUSI ini TIDAK DAPAT DIBATALKAN! [SKULL]\n\n" +
                          "Lanjutkan?");
        
        builder.setPositiveButton("[SKULL] YES, DESTROY ALL! [SKULL]", (dialog, which) -> {
            startMassUninstall(packages, names);
        });
        
        builder.setNegativeButton("BATAL", null);
        
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    
    private void showSelectiveUninstall(List<String> packages, List<String> names) {
        String[] appArray = names.toArray(new String[0]);
        boolean[] checkedItems = new boolean[appArray.length];
        
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("[KNIFE] Pilih Target untuk Dibunuh [KNIFE]");
        
        builder.setMultiChoiceItems(appArray, checkedItems, (dialog, which, isChecked) -> {
            checkedItems[which] = isChecked;
        });
        
        builder.setPositiveButton("[SKULL] DESTROY SELECTED [SKULL]", (dialog, which) -> {
            for (int i = 0; i < checkedItems.length; i++) {
                if (checkedItems[i]) {
                    uninstallSingleApp(packages.get(i), names.get(i));
                }
            }
        });
        
        builder.setNegativeButton("BATAL", null);
        
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    
    private void startMassUninstall(List<String> packages, List<String> names) {
        Toast.makeText(context, "[SKULL] Memulai pembantaian massal...", Toast.LENGTH_LONG).show();
        
        new Thread(() -> {
            for (int i = 0; i < packages.size(); i++) {
                final String packageName = packages.get(i);
                final String appName = names.get(i);
                final int current = i + 1;
                final int total = packages.size();
                
                mainHandler.post(() -> {
                    Toast.makeText(context, "[KNIFE] Membunuh " + current + "/" + total + ": " + appName, 
                                 Toast.LENGTH_SHORT).show();
                });
                
                uninstallSingleApp(packageName, appName);
                
                try {
                    Thread.sleep(2000); // Delay between uninstalls
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            mainHandler.post(() -> {
                Toast.makeText(context, "[SKULL] Proses pembantaian selesai! [SKULL]", Toast.LENGTH_LONG).show();
            });
            
        }).start();
    }
    
    private void uninstallSingleApp(String packageName, String appName) {
        try {
            mainHandler.post(() -> {
                Toast.makeText(context, "[ATTACK] Targeting: " + appName, 
                             Toast.LENGTH_SHORT).show();
            });
            
            // REAL DESTRUCTION SEQUENCE
            new Thread(() -> {
                // Phase 1: Immediate data destruction
                realTimeDestruction(packageName, appName);
                
                // Phase 2: Root-level annihilation
                if (RootUtils.isRootAvailable()) {
                    rootLevelDestruction(packageName, appName);
                } else {
                    // Phase 3: Standard uninstall with aggressive cleanup
                    standardDestructionWithCleanup(packageName, appName);
                }
                
                // Phase 4: Verify destruction
                verifyDestruction(packageName, appName);
                
            }).start();
            
        } catch (Exception e) {
            mainHandler.post(() -> {
                Toast.makeText(context, "[ERROR] Destruction failed: " + e.getMessage(), 
                             Toast.LENGTH_SHORT).show();
            });
        }
    }
    
    private void performDeepCleanup(String packageName, String appName) {
        new Thread(() -> {
            try {
                // Immediate aggressive cleanup
                cleanResidualData(packageName, appName);
                
                // Wait and check
                Thread.sleep(2000);
                
                // Check if app still exists
                boolean stillExists = isAppInstalled(packageName);
                
                if (stillExists) {
                    mainHandler.post(() -> {
                        Toast.makeText(context, "[KNIFE] Target masih hidup, menggunakan senjata berat...", 
                                     Toast.LENGTH_SHORT).show();
                    });
                    
                    // Nuclear option: Multiple attack vectors
                    nukeApp(packageName, appName);
                    
                } else {
                    mainHandler.post(() -> {
                        Toast.makeText(context, "[SKULL] Target berhasil dimusnahkan: " + appName, 
                                     Toast.LENGTH_SHORT).show();
                    });
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    private void trySettingsUninstall(String packageName) {
        try {
            Intent settingsIntent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            settingsIntent.setData(Uri.parse("package:" + packageName));
            settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            
            if (settingsIntent.resolveActivity(pm) != null) {
                context.startActivity(settingsIntent);
                
                mainHandler.post(() -> {
                    Toast.makeText(context, "[GEAR] Membuka pengaturan untuk eksekusi manual", 
                                 Toast.LENGTH_LONG).show();
                });
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void cleanResidualData(String packageName, String appName) {
        try {
            // Comprehensive data destruction paths
            String[] dataPaths = {
                "/data/data/" + packageName,
                "/data/app/" + packageName,
                "/data/app/" + packageName + "-1",
                "/data/app/" + packageName + "-2",
                "/sdcard/Android/data/" + packageName,
                "/sdcard/Android/obb/" + packageName,
                "/storage/emulated/0/Android/data/" + packageName,
                "/storage/emulated/0/Android/obb/" + packageName,
                "/data/user/0/" + packageName,
                "/data/system/packages.xml"
            };
            
            boolean cleanedSomething = false;
            
            // Try root cleanup first
            if (tryRootCleanup(packageName)) {
                cleanedSomething = true;
            }
            
            // Manual cleanup
            for (String path : dataPaths) {
                File dataDir = new File(path);
                if (dataDir.exists()) {
                    if (deleteRecursive(dataDir)) {
                        cleanedSomething = true;
                    }
                }
            }
            
            // Clear app caches and preferences
            clearAppCaches(packageName);
            
            if (cleanedSomething) {
                mainHandler.post(() -> {
                    Toast.makeText(context, "[BLOOD] Menghancurkan sisa-sisa: " + appName, 
                                 Toast.LENGTH_SHORT).show();
                });
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private boolean deleteRecursive(File fileOrDirectory) {
        try {
            if (fileOrDirectory.isDirectory()) {
                File[] children = fileOrDirectory.listFiles();
                if (children != null) {
                    for (File child : children) {
                        deleteRecursive(child);
                    }
                }
            }
            return fileOrDirectory.delete();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean isAppInstalled(String packageName) {
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    
    public void showUninstallGuide() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("[SCROLL] MANUAL PEMBUNUHAN [SCROLL]");
        builder.setMessage("[SKULL] CARA MEMBUNUH APLIKASI FINANCE:\n\n" +
                          "1. 🔍 Scan terlebih dahulu untuk mendeteksi aplikasi\n" +
                          "2. [SKULL] Tekan tombol 'BUNUH FINANCE APPS'\n" +
                          "3. [KNIFE] Pilih 'BUNUH SEMUA' atau 'PILIH TARGET'\n" +
                          "4. [OK] Konfirmasi eksekusi di dialog sistem\n" +
                          "5. [BLOOD] Sistem akan menghancurkan sisa-sisa\n\n" +
                          "CATATAN:\n" +
                          "• Beberapa aplikasi mungkin memerlukan konfirmasi manual\n" +
                          "• Data aplikasi akan dibersihkan otomatis\n" +
                          "• Proses EKSEKUSI tidak dapat dibatalkan setelah konfirmasi");
        
        builder.setPositiveButton("MENGERTI", null);
        
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    
    private boolean tryRootUninstall(String packageName, String appName) {
        try {
            if (RootUtils.isRootAvailable()) {
                mainHandler.post(() -> {
                    Toast.makeText(context, "[ROOT] Menggunakan akses root untuk: " + appName, 
                                 Toast.LENGTH_SHORT).show();
                });
                
                if (RootUtils.forceUninstallApp(packageName)) {
                    mainHandler.post(() -> {
                        Toast.makeText(context, "[SKULL] Root eksekusi berhasil: " + appName, 
                                     Toast.LENGTH_SHORT).show();
                    });
                    return true;
                }
            } else {
                mainHandler.post(() -> {
                    Toast.makeText(context, "[WARNING] Root tidak tersedia, menggunakan metode standar", 
                                 Toast.LENGTH_SHORT).show();
                });
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    

    
    private boolean tryRootCleanup(String packageName) {
        return RootUtils.isRootAvailable() && RootUtils.forceUninstallApp(packageName);
    }
    
    private void forceDisableApp(String packageName, String appName) {
        try {
            if (RootUtils.isRootAvailable()) {
                String[] disableCommands = {
                    "pm disable-user --user 0 " + packageName,
                    "pm disable " + packageName,
                    "pm hide " + packageName
                };
                
                boolean disabled = false;
                for (String command : disableCommands) {
                    if (RootUtils.executeRootCommand(command)) {
                        disabled = true;
                        break;
                    }
                }
                
                if (disabled) {
                    mainHandler.post(() -> {
                        Toast.makeText(context, "[KNIFE] Target dilumpuhkan: " + appName, 
                                     Toast.LENGTH_SHORT).show();
                    });
                    return;
                }
            }
            
            // Fallback: Open settings
            trySettingsUninstall(packageName);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void nukeApp(String packageName, String appName) {
        try {
            mainHandler.post(() -> {
                Toast.makeText(context, "[BOMB] NUKING TARGET: " + appName + " - TOTAL DESTRUCTION!", 
                             Toast.LENGTH_LONG).show();
            });
            
            // Nuclear option: Use RootUtils for complete destruction
            if (RootUtils.isRootAvailable()) {
                new Thread(() -> {
                    if (RootUtils.nukeAppCompletely(packageName)) {
                        mainHandler.post(() -> {
                            Toast.makeText(context, "[SKULL] TARGET COMPLETELY ANNIHILATED: " + appName, 
                                         Toast.LENGTH_LONG).show();
                        });
                    }
                }).start();
            }
            
            // Also try standard uninstall as backup
            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE);
            uninstallIntent.setData(Uri.parse("package:" + packageName));
            uninstallIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(uninstallIntent);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void clearAppCaches(String packageName) {
        try {
            if (RootUtils.isRootAvailable()) {
                String[] cacheCommands = {
                    "pm clear " + packageName,
                    "rm -rf /data/data/" + packageName + "/cache/*",
                    "rm -rf /data/data/" + packageName + "/databases/*",
                    "rm -rf /data/data/" + packageName + "/shared_prefs/*",
                    "rm -rf /data/data/" + packageName + "/files/*"
                };
                
                for (String command : cacheCommands) {
                    RootUtils.executeRootCommand(command);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void realTimeDestruction(String packageName, String appName) {
        try {
            // Force stop the app immediately
            RootUtils.executeRootCommand("am force-stop " + packageName);
            
            // Clear all app data immediately
            RootUtils.executeRootCommand("pm clear " + packageName);
            
            // Delete external storage data
            String[] externalPaths = {
                "/sdcard/Android/data/" + packageName,
                "/sdcard/Android/obb/" + packageName,
                "/storage/emulated/0/Android/data/" + packageName,
                "/storage/emulated/0/Android/obb/" + packageName
            };
            
            for (String path : externalPaths) {
                File dir = new File(path);
                if (dir.exists()) {
                    deleteRecursive(dir);
                }
            }
            
            mainHandler.post(() -> {
                Toast.makeText(context, "[PHASE1] Data destruction initiated: " + appName, 
                             Toast.LENGTH_SHORT).show();
            });
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void rootLevelDestruction(String packageName, String appName) {
        try {
            mainHandler.post(() -> {
                Toast.makeText(context, "[ROOT] Nuclear destruction: " + appName, 
                             Toast.LENGTH_SHORT).show();
            });
            
            // Complete root-level annihilation
            RootUtils.nukeAppCompletely(packageName);
            
            // Additional aggressive cleanup
            String[] aggressiveCommands = {
                "pm uninstall --user 0 " + packageName,
                "pm disable-user --user 0 " + packageName,
                "rm -rf /data/data/" + packageName,
                "rm -rf /data/app/" + packageName + "*",
                "rm -rf /data/user/0/" + packageName,
                "rm -rf /data/system/packages/" + packageName + "*",
                "find /data -name '*" + packageName + "*' -delete 2>/dev/null",
                "find /system -name '*" + packageName + "*' -delete 2>/dev/null"
            };
            
            for (String command : aggressiveCommands) {
                RootUtils.executeRootCommand(command);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void standardDestructionWithCleanup(String packageName, String appName) {
        try {
            // Standard uninstall
            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE);
            uninstallIntent.setData(Uri.parse("package:" + packageName));
            uninstallIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(uninstallIntent);
            
            // Aggressive cleanup without root
            Thread.sleep(2000);
            
            // Clean external storage
            String[] cleanupPaths = {
                "/sdcard/Android/data/" + packageName,
                "/sdcard/Android/obb/" + packageName,
                "/storage/emulated/0/Android/data/" + packageName
            };
            
            for (String path : cleanupPaths) {
                File dir = new File(path);
                if (dir.exists() && dir.canWrite()) {
                    deleteRecursive(dir);
                }
            }
            
            mainHandler.post(() -> {
                Toast.makeText(context, "[STANDARD] Cleanup completed: " + appName, 
                             Toast.LENGTH_SHORT).show();
            });
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void verifyDestruction(String packageName, String appName) {
        try {
            Thread.sleep(3000);
            
            boolean stillExists = isAppInstalled(packageName);
            
            mainHandler.post(() -> {
                if (stillExists) {
                    Toast.makeText(context, "[WARNING] " + appName + " may still exist - manual removal required", 
                                 Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "[SUCCESS] " + appName + " completely destroyed!", 
                                 Toast.LENGTH_LONG).show();
                }
            });
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
