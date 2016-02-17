package com.example.omiplekevin.f45timerrevamp;

import android.os.Environment;
import android.os.SystemClock;

import java.util.concurrent.atomic.AtomicBoolean;

public class Constants {

    public static final int _GENERIC_DELAY_TIME = 5000;

    public static final int _NANOHTTPD_PORT = 12345;

    public static final String _DIR_MAIN = Environment.getExternalStorageDirectory() + "/F45";

    public static final String _DIR_VIDEO = _DIR_MAIN + "/videos";

    public static final String _DIR_IMAGE = _DIR_MAIN + "/images";

    public static final String _DIR_MUSIC = _DIR_MAIN + "/music";

    public static final String _DIR_EXTRAS = _DIR_MAIN + "/extras";

    public static final String _DIR_TEMP = _DIR_MAIN + "/.temp";

    public static final String _CACHE_WORKOUT_CONFIG = "workoutCacheConfig.txt";

    public static final int _TIMER_NO_ACT = -1;

    public static final int _TIMER_PAUSE = 0;

    public static final int _TIMER_RUN = 1;

    public static int _TIMER_STATE = 0;

    public static int _IS_PAUSED = 0;

    public static int _IS_STARTED = 0;

    public static int _IS_MAIN_TIMER = 1;

    // TODO: 10/12/2015 Please remove this. We will use _WORKOU_CONFIG instead.
    public static int _MOVE_STATION_EVERY = 1;

    public static int _SCROLL_POSITION = 0;

    public static int _STATION_COUNT = 1;

    // TODO: 10/12/2015 Please remove this. We will use _WORKOU_CONFIG instead.
    public static long _WORK_PER_SET = 1L;

    public static long _TOTAL_RUNTIME_SEQUENCE = 0L;

    // TODO: 10/12/2015 Please remove this. We will use _WORKOU_CONFIG instead.
    public static long _TIMER_COUNT = 0L;

    // TODO: 10/12/2015 Please remove this. We will use _WORKOU_CONFIG instead.
    public static long _REST_COUNT = 0L;

    // TODO: 10/12/2015 Please remove this. We will use _WORKOU_CONFIG instead.
    public static long _SETS = 0L;

    public static long _DEVICE_TIME = 0L;

    public static long _SERVER_TIME = 0L;

    public static long _PAUSE_TIME = 0L;

    public static long _RESUME_TIME = 0L;

    public static long _BASE_TIME_REFERENCE = System.currentTimeMillis() + - SystemClock.elapsedRealtime();

    public static int _TOTAL_STATIONS;

    public static AtomicBoolean _ON_LINE = new AtomicBoolean(Boolean.FALSE);

    public static AtomicBoolean _IS_PERFORMING_USB_TASK = new AtomicBoolean(Boolean.FALSE);

    public static AtomicBoolean _IS_WARMUP_PLAYING = new AtomicBoolean(Boolean.FALSE);

    public static AtomicBoolean _NO_CONNECTION = new AtomicBoolean(Boolean.FALSE);

    public static String _IS_SCREEN = "";

    public static String _GYM_ID = "";

    public static String _SCREEN_ID = "";

    public static final int _HORIZONTAL_PROGRESS_BAR = 1;

    public static final int _CIRCULAR_PROGRESS_BAR = 0;

//    public static String _HOST = BuildConfig.host;

    public static final long _DISCONNECT_TIMEOUT = 120000;

    public static int _TICK = -1;

    public static final String _GRAB_A_DRINK = "GrabADrink";

    public static final String _ANYONE_NEW = "AnyoneNew";

    public static final String _ANY_INJURY = "AnyInjury";

    public static final String _GREAT_JOB = "GreatJob";

    public static final String _WARMUP = "Warmup";

    public static final String _COOLDOWN = "Cooldown";

    public static final String _IMAGE_COUNTDOWN = "ImageCountdown";

    public static final String _FULLSCREEN_COUNTDOWN = "FullscreenCountdown";

    public static final String _TEXT_ON_SCREEN = "TextOnScreen";

    public static final String _CACHE_FALSE_PING = "falsePingCache.txt";

}
