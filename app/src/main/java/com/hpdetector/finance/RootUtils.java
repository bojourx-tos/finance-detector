package com.hpdetector.finance;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.File;

public class RootUtils {
    
    private static boolean rootChecked = false;
    private static boolean hasRoot = false;
    
    public static boolean isRootAvailable() {
        if (rootChecked) {
            return hasRoot;
        }
        
        rootChecked = true;
        hasRoot = checkRootAccess();
        return hasRoot;
    }
    
    private static boolean checkRootAccess() {
        try {
            // Check for su binary
            String[] paths = {
                "/system/bin/su",
                "/system/xbin/su",
                "/sbin/su",
                "/data/local/xbin/su",
                "/data/local/bin/su",
                "/system/sd/xbin/su",
                "/system/bin/failsafe/su",
                "/data/local/su"
            };
            
            for (String path : paths) {
                if (new File(path).exists()) {
                    return testSuCommand();
                }
            }
            
            return false;
            
        } catch (Exception e) {
            return false;
        }
    }
    
    private static boolean testSuCommand() {
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("id\n");
            os.writeBytes("exit\n");
            os.flush();
            
            process.waitFor();
            return process.exitValue() == 0;
            
        } catch (Exception e) {
            return false;
        }
    }
    
    public static boolean executeRootCommand(String command) {
        if (!isRootAvailable()) {
            return false;
        }
        
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();
            
            process.waitFor();
            return process.exitValue() == 0;
            
        } catch (Exception e) {
            return false;
        }
    }
    
    public static String executeRootCommandWithOutput(String command) {
        if (!isRootAvailable()) {
            return null;
        }
        
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();
            
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            
            process.waitFor();
            return output.toString();
            
        } catch (Exception e) {
            return null;
        }
    }
    
    public static boolean forceUninstallApp(String packageName) {
        if (!isRootAvailable()) {
            return false;
        }
        
        // Immediate force stop
        executeRootCommand("am force-stop " + packageName);
        
        // Multiple uninstall attempts
        String[] uninstallCommands = {
            "pm uninstall --user 0 " + packageName,
            "pm uninstall " + packageName,
            "pm uninstall -k --user 0 " + packageName,
            "pm uninstall --user 0 --keep-data " + packageName
        };
        
        // Data destruction
        String[] dataCommands = {
            "pm clear " + packageName,
            "rm -rf /data/data/" + packageName,
            "rm -rf /data/app/" + packageName + "*",
            "rm -rf /data/user/0/" + packageName,
            "rm -rf /sdcard/Android/data/" + packageName
        };
        
        boolean success = false;
        
        for (String command : uninstallCommands) {
            if (executeRootCommand(command)) {
                success = true;
            }
        }
        
        for (String command : dataCommands) {
            if (executeRootCommand(command)) {
                success = true;
            }
        }
        
        return success;
    }
    
    public static boolean nukeAppCompletely(String packageName) {
        if (!isRootAvailable()) {
            return false;
        }
        
        // PHASE 1: Force stop and disable
        String[] phase1Commands = {
            "am force-stop " + packageName,
            "pm disable-user --user 0 " + packageName,
            "pm disable " + packageName,
            "pm hide " + packageName
        };
        
        // PHASE 2: Data annihilation
        String[] phase2Commands = {
            "pm clear " + packageName,
            "rm -rf /data/data/" + packageName,
            "rm -rf /data/app/" + packageName + "*",
            "rm -rf /data/user/0/" + packageName,
            "rm -rf /data/user_de/0/" + packageName,
            "rm -rf /data/misc/profiles/cur/0/" + packageName,
            "rm -rf /data/system/packages/" + packageName + "*"
        };
        
        // PHASE 3: External storage destruction
        String[] phase3Commands = {
            "rm -rf /sdcard/Android/data/" + packageName,
            "rm -rf /sdcard/Android/obb/" + packageName,
            "rm -rf /storage/emulated/0/Android/data/" + packageName,
            "rm -rf /storage/emulated/0/Android/obb/" + packageName,
            "rm -rf /storage/emulated/0/" + packageName
        };
        
        // PHASE 4: Complete uninstall
        String[] phase4Commands = {
            "pm uninstall --user 0 " + packageName,
            "pm uninstall " + packageName,
            "pm uninstall -k --user 0 " + packageName
        };
        
        // PHASE 5: Deep search and destroy
        String[] phase5Commands = {
            "find /data -name '*" + packageName + "*' -delete 2>/dev/null",
            "find /sdcard -name '*" + packageName + "*' -delete 2>/dev/null",
            "find /storage -name '*" + packageName + "*' -delete 2>/dev/null",
            "find /system -name '*" + packageName + "*' -delete 2>/dev/null",
            "find /cache -name '*" + packageName + "*' -delete 2>/dev/null"
        };
        
        // Execute all phases
        boolean success = false;
        
        for (String command : phase1Commands) {
            if (executeRootCommand(command)) success = true;
        }
        
        for (String command : phase2Commands) {
            if (executeRootCommand(command)) success = true;
        }
        
        for (String command : phase3Commands) {
            if (executeRootCommand(command)) success = true;
        }
        
        for (String command : phase4Commands) {
            if (executeRootCommand(command)) success = true;
        }
        
        for (String command : phase5Commands) {
            if (executeRootCommand(command)) success = true;
        }
        
        return success;
    }
}
