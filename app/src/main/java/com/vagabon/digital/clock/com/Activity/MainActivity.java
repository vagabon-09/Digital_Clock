package com.vagabon.digital.clock.com.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.vagabon.digital.clock.com.R;
import com.vagabon.digital.clock.com.ReusableCode.Settings;
import com.vagabon.digital.clock.com.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Settings st;
    private boolean checkNight, checkNight2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Settings class is calling
        st = new Settings();
        setCustomTheme();
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        /*This function is only for text some required method*/
//        disableNotification();


        //Checking is night mode enable or not
        checkNight = isNight();
        checkNight2 = st.settings_check(getApplicationContext(), "setting", "night_mode");
        //Applying settings
        setSettings();
        //Blocking status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Display keeping on until app closed
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //when click on any button
        getConnect();

    }

    private void setCustomTheme() {
        Log.d("checkTheme", "setCustomTheme: " + checkTheme() + "");
        if (checkTheme() == 0) {
            setTheme(R.style.customMode);
        } else if (checkTheme() == 1) {
            setTheme(R.style.NightBat);
        } else if (checkTheme() == 2) {
            setTheme(R.style.orangeMode);
        }
    }

    private int checkTheme() {
        return st.get_theme(getApplicationContext(), "theme", "theme");
    }


    private boolean isNight() {
        int nightModeFlags = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setSettings() {
        /*
         * This condition is the part of the setting
         * we are checking, is the button clicked using SharedReference
         * if clicked then we are setting the the background of the view
         * this condition is for Focus mode
         * */
        if (st.settings_check(getApplicationContext(), "setting", "focus")) {
            if (checkNight || checkNight2) {
                makeUsersFocused();
                binding.sleepModeId.setBackground(getDrawable(R.drawable.select_option_settings_night));
            } else {
                binding.sleepModeId.setBackground(getDrawable(R.drawable.select_option_settings_day));
            }
        } else {
            makeUserNormal();
        }

        /*
         * This condition is the part of the setting
         * we are checking, is the button clicked using SharedReference
         * if clicked then we are setting the the background of the view
         * this condition is for Block calls
         */

        if (st.settings_check(getApplicationContext(), "setting", "calls")) {
            if (checkNight || checkNight2) {
                binding.blockCallViewBtnId.setBackground(getDrawable(R.drawable.select_option_settings_night));
            } else {
                binding.blockCallViewBtnId.setBackground(getDrawable(R.drawable.select_option_settings_day));
            }
        }


        /*
         * This condition is the part of the setting
         * we are checking, is the button clicked using SharedReference
         * if clicked then we are setting the the background of the view
         * this condition is for Block notification
         */
        if (st.settings_check(getApplicationContext(), "setting", "notification")) {
            if (checkNight || checkNight2) {
                Log.d("IsNightMode", "setSettings: Yes Night Mode");
//                binding.notificationView.setBackground(getDrawable(R.drawable.select_option_settings_night));
            } else {
//                binding.notificationView.setBackground(getDrawable(R.drawable.select_option_settings_day));
                Log.d("IsNightMode", "setSettings: Night mode not active");

            }
        }


        /*
         * This condition is the part of the setting
         * we are checking, is the button clicked using SharedReference
         * if clicked then we are setting the the background of the view
         * this condition is for Customise
         */

//        if (st.settings_check(getApplicationContext(), "setting", "customise")) {
//            if (checkNight || checkNight2) {
//                binding.customiseViewId.setBackground(getDrawable(R.drawable.select_option_settings_night));
//            } else {
//                binding.customiseViewId.setBackground(getDrawable(R.drawable.select_option_settings_day));
//            }
//        }


        /*
         * This condition is the part of the setting
         * we are checking, is the button clicked using SharedReference
         * if clicked then we are setting the the background of the view
         * this condition is for Customise
         */

        if (st.settings_check(getApplicationContext(), "setting", "night_mode")) {
            if (checkNight || checkNight2) {
                binding.nightViewId.setBackground(getDrawable(R.drawable.select_option_settings_night));
            } else {
                binding.nightViewId.setBackground(getDrawable(R.drawable.select_option_settings_day));
            }
            nightMode();
        } else {
            nightModeDisable();
        }
    }

    private void makeUserNormal() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void getConnect() {
        /*
        When clicked on setting button get visible the setting view
         and set alpha in timer view
         */
        binding.settingsBtnId.setOnClickListener(v -> {
            binding.settingViewId.setVisibility(View.VISIBLE);
            binding.timerViewId.setAlpha(0.2f);
        });

        /*
        When click on any setting option or over setting view then set visibility gone
        and also set alpha in timer view
       */
        binding.settingViewId.setOnClickListener(v -> {
            binding.settingViewId.setVisibility(View.GONE);
            binding.timerViewId.setAlpha(1f);
        });

        /*
         * Focus mode is a mode when uer active this mode , no call , no message can disturb the user
         * here makeUserNormal() method make the system normal
         * and makeUserFocused method make users focused on there work
         */

        binding.sleepModeId.setOnClickListener(v -> {
            if (st.settings_check(getApplicationContext(), "setting", "focus")) {
                makeUserNormal();
                st.settings_option(getApplicationContext(), "setting", "focus", false);
                binding.sleepModeId.setBackground(getDrawable(R.drawable.settings_options_shape_normal));
            } else {
                st.settings_option(getApplicationContext(), "setting", "focus", true);
//                makeUsersFocused();
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.sleep_mode_warning_dialog);
                dialog.show();
                if (checkNight || checkNight2) {
                    binding.sleepModeId.setBackground(getDrawable(R.drawable.select_option_settings_night));
                } else {
                    binding.sleepModeId.setBackground(getDrawable(R.drawable.select_option_settings_day));
                }
            }
        });

        /*
         * Block calls basically block incoming calls to make more focus on work ,
         * first we will check is this feature is already activated or not
         * and according to the behaviour we will manage the button,
         * we will add the feature according the behaviour if button clicked and
         * already the feature is enable then we will deactivate the feature
         * if not activated then we will activate the feature
         */

        binding.blockCallViewBtnId.setOnClickListener(v -> {
//            blockCalls();
            if (st.settings_check(getApplicationContext(), "setting", "calls")) {
                st.settings_option(getApplicationContext(), "setting", "calls", false);
                binding.blockCallViewBtnId.setBackground(getDrawable(R.drawable.settings_options_shape_normal));
            } else {
                st.settings_option(getApplicationContext(), "setting", "calls", true);
                if (checkNight || checkNight2) {
                    binding.blockCallViewBtnId.setBackground(getDrawable(R.drawable.select_option_settings_night));
                } else {
                    binding.blockCallViewBtnId.setBackground(getDrawable(R.drawable.select_option_settings_day));
                }
            }

        });


        /*
         * block notification feature , block notification for the users ,
         * first we will check is this feature is already activated or not
         * and according to the behaviour we will manage the button,
         * we will add the feature according the behaviour, if button clicked and
         * already the feature is enable then we will deactivate the feature
         * if not activated then we will activate the feature
         * disableNotification method will redirect to notification block page in settings
         * where users can block there required app notification
         */

        binding.notificationView.setOnClickListener(v -> {

            if (checkNight || checkNight2) {
                binding.notificationView.setBackground(getDrawable(R.drawable.select_option_settings_night));
            } else {
                binding.notificationView.setBackground(getDrawable(R.drawable.select_option_settings_day));
            }

            disableNotification();
//            if (st.settings_check(getApplicationContext(), "setting", "notification")) {
//                st.settings_option(getApplicationContext(), "setting", "notification", false);
//                disableNotification();
//                binding.notificationView.setBackground(getDrawable(R.drawable.settings_options_shape_normal));
//            } else {
//                st.settings_option(getApplicationContext(), "setting", "notification", true);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////                    Log.d("DisableNotification", "getConnect: It is inside the first condition");
//                    disableNotification();
//                }
//                if (checkNight || checkNight2) {
//                    binding.notificationView.setBackground(getDrawable(R.drawable.select_option_settings_night));
//                } else {
//                    binding.notificationView.setBackground(getDrawable(R.drawable.select_option_settings_day));
//                }
//            }

            new Handler().postDelayed(() -> binding.notificationView.setBackground(getDrawable(R.drawable.settings_options_shape_normal)), 1000);

        });



        /*
         * customise feature , users can customise theme color ,
         * first we will check is this feature is already activated or not
         * and according to the behaviour we will manage the button,
         * we will add the feature according the behaviour, if button clicked and
         * already the feature is enable then we will deactivate the feature
         * if not activated then we will activate the feature
         */

        binding.customiseViewId.setOnClickListener(v -> {
//            if (st.settings_check(getApplicationContext(), "setting", "customise")) {
//                st.settings_option(getApplicationContext(), "setting", "customise", false);
//                binding.customiseViewId.setBackground(getDrawable(R.drawable.settings_options_shape_normal));
//            } else {
//                st.settings_option(getApplicationContext(), "setting", "customise", true);
//                if (checkNight || checkNight2) {
//                    binding.customiseViewId.setBackground(getDrawable(R.drawable.select_option_settings_night));
//                } else {
//                    binding.customiseViewId.setBackground(getDrawable(R.drawable.select_option_settings_day));
//                }
//            }
            if (checkNight || checkNight2) {
                binding.customiseViewId.setBackground(getDrawable(R.drawable.select_option_settings_night));
            } else {
                binding.customiseViewId.setBackground(getDrawable(R.drawable.select_option_settings_day));
            }
            new Handler().postDelayed(() -> binding.customiseViewId.setBackground(getDrawable(R.drawable.settings_options_shape_normal)), 1000);
            Intent intent = new Intent(MainActivity.this, CustomiseActivity.class);
            startActivity(intent);

        });




        /*
         * night mode feature , users can enable night mode ,
         * first we will check is this feature is already activated or not
         * and according to the behaviour we will manage the button,
         * we will add the feature according the behaviour, if button clicked and
         * already the feature is enable then we will deactivate the feature
         * if not activated then we will activate the feature
         */

        binding.nightViewId.setOnClickListener(v -> {
            if (st.settings_check(getApplicationContext(), "setting", "night_mode")) {
                st.settings_option(getApplicationContext(), "setting", "night_mode", false);
                binding.nightViewId.setBackground(getDrawable(R.drawable.settings_options_shape_normal));
                nightModeDisable();
            } else {
                st.settings_option(getApplicationContext(), "setting", "night_mode", true);
                if (checkNight || checkNight2) {
                    binding.nightViewId.setBackground(getDrawable(R.drawable.select_option_settings_night));
                } else {
                    binding.nightViewId.setBackground(getDrawable(R.drawable.select_option_settings_day));
                }
                nightMode();
            }

        });


    }

    private void makeUsersFocused() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (!notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivity(intent);
        } else {
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
        // Now below code is to hide call notification from the users
        hideNotification();
    }

    private void hideNotification() {


    }


    private void disableNotification() {
        Intent intent = new Intent();
        intent.setAction(android.provider.Settings.ACTION_ALL_APPS_NOTIFICATION_SETTINGS);
        startActivity(intent);

    }

    public void nightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    public void nightModeDisable() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    protected void onResume() {
        setCustomTheme();
        super.onResume();
    }
}


