package com.example.miband;

import android.app.Service;
import android.bluetooth.BluetoothGattCallback;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class DeviceService extends Service {
    private boolean mStarted;
    public MiBandSupport mMiBandSupport;
    public MiBandDevice mDevice;

    final String PREFIX = "com.example.miband";

    final String ACTION_START = PREFIX + ".action.start";
    final String ACTION_CONNECT = PREFIX + ".action.connect";
    String ACTION_NOTIFICATION = PREFIX + ".action.notification";
    String ACTION_DELETE_NOTIFICATION = PREFIX + ".action.delete_notification";
    String ACTION_CALLSTATE = PREFIX + ".action.callstate";
    String ACTION_SETCANNEDMESSAGES = PREFIX + ".action.setcannedmessages";
    String ACTION_SETTIME = PREFIX + ".action.settime";
    String ACTION_SETMUSICINFO = PREFIX + ".action.setmusicinfo";
    String ACTION_SETMUSICSTATE = PREFIX + ".action.setmusicstate";
    String ACTION_REQUEST_DEVICEINFO = PREFIX + ".action.request_deviceinfo";
    String ACTION_REQUEST_APPINFO = PREFIX + ".action.request_appinfo";
    String ACTION_REQUEST_SCREENSHOT = PREFIX + ".action.request_screenshot";
    String ACTION_STARTAPP = PREFIX + ".action.startapp";
    String ACTION_DELETEAPP = PREFIX + ".action.deleteapp";
    String ACTION_APP_CONFIGURE = PREFIX + ".action.app_configure";
    String ACTION_APP_REORDER = PREFIX + ".action.app_reorder";
    String ACTION_INSTALL = PREFIX + ".action.install";
    String ACTION_RESET = PREFIX + ".action.reset";
    String ACTION_HEARTRATE_TEST = PREFIX + ".action.heartrate_test";
    String ACTION_FETCH_RECORDED_DATA = PREFIX + ".action.fetch_activity_data";
    String ACTION_DISCONNECT = PREFIX + ".action.disconnect";
    String ACTION_FIND_DEVICE = PREFIX + ".action.find_device";
    String ACTION_SET_CONSTANT_VIBRATION = PREFIX + ".action.set_constant_vibration";
    String ACTION_SET_ALARMS = PREFIX + ".action.set_alarms";
    String ACTION_ENABLE_REALTIME_STEPS = PREFIX + ".action.enable_realtime_steps";
    String ACTION_REALTIME_SAMPLES = PREFIX + ".action.realtime_samples";
    String ACTION_ENABLE_REALTIME_HEARTRATE_MEASUREMENT = PREFIX + ".action.realtime_hr_measurement";
    String ACTION_ENABLE_HEARTRATE_SLEEP_SUPPORT = PREFIX + ".action.enable_heartrate_sleep_support";
    String ACTION_SET_HEARTRATE_MEASUREMENT_INTERVAL = PREFIX + ".action.set_heartrate_measurement_intervarl";
    String ACTION_ADD_CALENDAREVENT = PREFIX + ".action.add_calendarevent";
    String ACTION_DELETE_CALENDAREVENT = PREFIX + ".action.delete_calendarevent";
    String ACTION_SEND_CONFIGURATION = PREFIX + ".action.send_configuration";
    String ACTION_READ_CONFIGURATION = PREFIX + ".action.read_configuration";
    String ACTION_SEND_WEATHER = PREFIX + ".action.send_weather";
    String ACTION_TEST_NEW_FUNCTION = PREFIX + ".action.test_new_function";
    String ACTION_SET_FM_FREQUENCY = PREFIX + ".action.set_fm_frequency";
    String ACTION_SET_LED_COLOR = PREFIX + ".action.set_led_color";
    String EXTRA_NOTIFICATION_BODY = "notification_body";
    String EXTRA_NOTIFICATION_FLAGS = "notification_flags";
    String EXTRA_NOTIFICATION_ID = "notification_id";
    String EXTRA_NOTIFICATION_PHONENUMBER = "notification_phonenumber";
    String EXTRA_NOTIFICATION_SENDER = "notification_sender";
    String EXTRA_NOTIFICATION_SOURCENAME = "notification_sourcename";
    String EXTRA_NOTIFICATION_SOURCEAPPID = "notification_sourceappid";
    String EXTRA_NOTIFICATION_SUBJECT = "notification_subject";
    String EXTRA_NOTIFICATION_TITLE = "notification_title";
    String EXTRA_NOTIFICATION_TYPE = "notification_type";
    String EXTRA_NOTIFICATION_ACTIONS = "notification_actions";
    String EXTRA_NOTIFICATION_PEBBLE_COLOR = "notification_pebble_color";
    String EXTRA_FIND_START = "find_start";
    String EXTRA_VIBRATION_INTENSITY = "vibration_intensity";
    String EXTRA_CALL_COMMAND = "call_command";
    String EXTRA_CALL_PHONENUMBER = "call_phonenumber";
    String EXTRA_CALL_DISPLAYNAME = "call_displayname";
    String EXTRA_CANNEDMESSAGES = "cannedmessages";
    String EXTRA_CANNEDMESSAGES_TYPE = "cannedmessages_type";
    String EXTRA_MUSIC_ARTIST = "music_artist";
    String EXTRA_MUSIC_ALBUM = "music_album";
    String EXTRA_MUSIC_TRACK = "music_track";
    String EXTRA_MUSIC_DURATION = "music_duration";
    String EXTRA_MUSIC_TRACKNR = "music_tracknr";
    String EXTRA_MUSIC_TRACKCOUNT = "music_trackcount";
    String EXTRA_MUSIC_STATE = "music_state";
    String EXTRA_MUSIC_SHUFFLE = "music_shuffle";
    String EXTRA_MUSIC_REPEAT = "music_repeat";
    String EXTRA_MUSIC_POSITION = "music_position";
    String EXTRA_MUSIC_RATE = "music_rate";
    String EXTRA_APP_UUID = "app_uuid";
    String EXTRA_APP_START = "app_start";
    String EXTRA_APP_CONFIG = "app_config";
    String EXTRA_APP_CONFIG_ID = "app_config_id";
    String EXTRA_URI = "uri";
    String EXTRA_CONFIG = "config";
    String EXTRA_ALARMS = "alarms";
    String EXTRA_CONNECT_FIRST_TIME = "connect_first_time";
    String EXTRA_BOOLEAN_ENABLE = "enable_realtime_steps";
    String EXTRA_INTERVAL_SECONDS = "interval_seconds";
    String EXTRA_WEATHER = "weather";
    String EXTRA_RECORDED_DATA_TYPES = "data_types";
    String EXTRA_FM_FREQUENCY = "fm_frequency";
    String EXTRA_LED_COLOR = "led_color";
    String EXTRA_RESET_FLAGS = "reset_flags";

    /**
     * Use EXTRA_REALTIME_SAMPLE instead
     */
    @Deprecated
    String EXTRA_REALTIME_STEPS = "realtime_steps";
    String EXTRA_REALTIME_SAMPLE = "realtime_sample";
    String EXTRA_TIMESTAMP = "timestamp";
    /**
     * Use EXTRA_REALTIME_SAMPLE instead
     */
    @Deprecated
    String EXTRA_HEART_RATE_VALUE = "hr_value";
    String EXTRA_CALENDAREVENT_ID = "calendarevent_id";
    String EXTRA_CALENDAREVENT_TYPE = "calendarevent_type";
    String EXTRA_CALENDAREVENT_TIMESTAMP = "calendarevent_timestamp";
    String EXTRA_CALENDAREVENT_DURATION = "calendarevent_duration";
    String EXTRA_CALENDAREVENT_TITLE = "calendarevent_title";
    String EXTRA_CALENDAREVENT_DESCRIPTION = "calendarevent_description";
    String EXTRA_CALENDAREVENT_LOCATION = "calendarevent_location";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public synchronized int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            Log.d(MainActivity.TAG, "no intent");
            return START_NOT_STICKY;
        }

        String action = intent.getAction();
        boolean firstTime = intent.getBooleanExtra(EXTRA_CONNECT_FIRST_TIME, false);

        if (action == null) {
            Log.d(MainActivity.TAG, "no action");
            return START_NOT_STICKY;
        }

        Log.d(MainActivity.TAG, "Service startcommand: " + action);

        if (!action.equals(ACTION_START) && !action.equals(ACTION_CONNECT)) {
            if (!mStarted) {
                // using the service before issuing ACTION_START
                Log.d(MainActivity.TAG, "Must start service with " + ACTION_START + " or " + ACTION_CONNECT + " before using it: " + action);
                return START_NOT_STICKY;
            }
        }

        switch (action) {
            case ACTION_START:
                start();
                break;
            case ACTION_CONNECT:
                start(); // ensure started
                MiBandDevice device = intent.getParcelableExtra(MiBandDevice.EXTRA_DEVICE);
                String btDeviceAddress = device.getAddress();

                boolean autoReconnect = true; //TODO Should we use it?

                if (device != null && !device.isConnecting() && !device.isConnected()) {
                    setDeviceSupport(null);
                    try {
                        MiBandSupport miBandSupport = new MiBandSupport(device, this, new GattCallback());
                        if (miBandSupport != null) {
                            setDeviceSupport(miBandSupport);
                            if (firstTime) {
                                miBandSupport.connectFirstTime();
                            } else {
                                miBandSupport.setAutoReconnect(autoReconnect);
                                miBandSupport.connect();
                            }
                        } else {
                            AndroidUtils.toast(this, "Cannot connect: Can't create device support", Toast.LENGTH_SHORT);
                        }
                    } catch (Exception e) {
                        AndroidUtils.toast(this, "Cannot connect:" + e.getMessage(), Toast.LENGTH_SHORT);
                        setDeviceSupport(null);
                    }
                } else if (device != null) {
                    // send an update at least
                    device.sendDeviceUpdateIntent(this);
                }
                break;
            default:
                Log.d(MainActivity.TAG, "Unable to recognize action: " + action);
/*
                if (mDeviceSupport == null || mGBDevice == null) {
                    Log.d(MainActivity.TAG, "device support:" + mDeviceSupport + ", device: " + mGBDevice + ", aborting");
                } else {
                    handleAction(intent, action, prefs);
                }*/
                break;
        }
        return START_STICKY;
    }

    private void start() {
        if (!mStarted) {
            // TODO startForeground...
            //
            // startForeground(1, GB.createNotification(getString(R.string.gadgetbridge_running), this));
            mStarted = true;
        }
    }

    private void setDeviceSupport(@Nullable MiBandSupport deviceSupport) {
        if (deviceSupport != mMiBandSupport && mMiBandSupport != null) {
            mMiBandSupport.dispose();
            mMiBandSupport = null;
            mDevice = null;
        }
        mMiBandSupport = deviceSupport;
        mDevice = mMiBandSupport != null ? mMiBandSupport.getDevice() : null;
    }

    public boolean isStarted() {
        return mStarted;
    }
}
