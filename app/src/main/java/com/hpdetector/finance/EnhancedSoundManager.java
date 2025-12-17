package com.hpdetector.finance;

import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Vibrator;

public class EnhancedSoundManager {
    
    private Context context;
    private ToneGenerator toneGenerator;
    private Vibrator vibrator;
    private AudioManager audioManager;
    
    public EnhancedSoundManager(Context context) {
        this.context = context;
        this.audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        this.toneGenerator = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        this.vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }
    
    public void playFinanceDetectedAlert() {
        new Thread(() -> {
            try {
                int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
                audioManager.setStreamVolume(AudioManager.STREAM_ALARM, maxVolume, 0);
                
                for (int i = 0; i < 5; i++) {
                    toneGenerator.startTone(ToneGenerator.TONE_CDMA_EMERGENCY_RINGBACK, 150);
                    Thread.sleep(200);
                    toneGenerator.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 150);
                    Thread.sleep(200);
                }
                
                long[] dangerPattern = {0, 300, 100, 300, 100, 300, 100, 500};
                vibrator.vibrate(dangerPattern, -1);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    public void playWarningAlert() {
        new Thread(() -> {
            try {
                int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
                audioManager.setStreamVolume(AudioManager.STREAM_ALARM, maxVolume * 3/4, 0);
                
                for (int i = 0; i < 3; i++) {
                    toneGenerator.startTone(ToneGenerator.TONE_CDMA_PIP, 250);
                    Thread.sleep(350);
                }
                
                long[] warningPattern = {0, 200, 150, 200, 150, 400};
                vibrator.vibrate(warningPattern, -1);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    public void playSafeSound() {
        new Thread(() -> {
            int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
            audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, maxVolume * 2/3, 0);
            
            toneGenerator.startTone(ToneGenerator.TONE_CDMA_CONFIRM, 800);
            vibrator.vibrate(300);
        }).start();
    }
    
    public void playStartScanSound() {
        toneGenerator.startTone(ToneGenerator.TONE_DTMF_1, 200);
        vibrator.vibrate(100);
    }
    
    public void playCompleteScanSound() {
        toneGenerator.startTone(ToneGenerator.TONE_CDMA_CONFIRM, 300);
    }
    
    public void release() {
        if (toneGenerator != null) {
            toneGenerator.release();
        }
    }
}
