/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jackpal.androidterm.util;

import jackpal.androidterm.R;
import jackpal.androidterm.compat.AndroidCompat;
import jackpal.androidterm.compat.UIModeCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;

/**
 * Terminal emulator settings
 */
public class TermSettings {
    private SharedPreferences mPrefs;

    private int mStatusBar;
    private int mActionBarMode;
    private int mOrientation;
    private boolean mSafeMargins;
    private int mCursorStyle;
    private int mCursorBlink;
    private int mFontSize;
    private int mColorId;
    private boolean mUTF8ByDefault;
    private int mBackKeyAction;
    private int mControlKeyId;
    private int mFnKeyId;
    private int mUseCookedIME;
    private String mShell;
    private String mFailsafeShell;
    private String mInitialCommand;
    private String mTermType;
    private boolean mCloseOnExit;
    private boolean mVerifyPath;
    private boolean mDoPathExtensions;
    private boolean mAllowPathPrepend;
    private String mHomePath;
    private String mFontPath;
    private boolean mUseSystemWallpaper;
    private int mWallpaperAlpha;

    private String mPrependPath = null;
    private String mAppendPath = null;

    private boolean mAltSendsEsc;

    private boolean mMouseTracking;

    private boolean mUseKeyboardShortcuts;

    private int mToastPosition = 4;

    private static final int TOAST_POSITION_MAX=8;

    private static final String STATUSBAR_KEY = "statusbar";
    private static final String ACTIONBAR_KEY = "actionbar";
    private static final String ORIENTATION_KEY = "orientation";
    private static final String SAFE_MARGINS_KEY = "safe_margins";
    private static final String CURSORSTYLE_KEY = "cursorstyle";
    private static final String FONTSIZE_KEY = "fontsize";
    private static final String COLOR_KEY = "color";
    private static final String UTF8_KEY = "utf8_by_default";
    private static final String BACKACTION_KEY = "backaction";
    private static final String CONTROLKEY_KEY = "controlkey";
    private static final String FNKEY_KEY = "fnkey";
    private static final String IME_KEY = "ime";
    private static final String SHELL_KEY = "shell";
    private static final String INITIALCOMMAND_KEY = "initialcommand";
    private static final String TERMTYPE_KEY = "termtype";
    private static final String CLOSEONEXIT_KEY = "close_window_on_process_exit";
    private static final String VERIFYPATH_KEY = "verify_path";
    private static final String PATHEXTENSIONS_KEY = "do_path_extensions";
    private static final String PATHPREPEND_KEY = "allow_prepend_path";
    private static final String HOMEPATH_KEY = "home_path";
    private static final String ALT_SENDS_ESC = "alt_sends_esc";
    private static final String MOUSE_TRACKING = "mouse_tracking";
    private static final String USE_KEYBOARD_SHORTCUTS = "use_keyboard_shortcuts";
    private static final String TOAST_POSITION = "toast_position";
    private static final String CUSTOM_FONT_PATH = "custom_font_filepath";
    private static final String USE_WALLPAPER = "use_system_wallpaper";
    private static final String WALLPAPER_ALPHA = "system_wallpaper_alpha";

    public static final int WHITE               = 0xffffffff;
    public static final int BLACK               = 0xff000000;
    public static final int BLUE                = 0xff344ebd;
    public static final int GREEN               = 0xff00ff00;
    public static final int AMBER               = 0xffffb651;
    public static final int RED                 = 0xffff0113;
    public static final int HOLO_BLUE           = 0xff33b5e5;
    public static final int SOLARIZED_FG        = 0xff657b83;
    public static final int SOLARIZED_BG        = 0xfffdf6e3;
    public static final int SOLARIZED_DARK_FG   = 0xff839496;
    public static final int SOLARIZED_DARK_BG   = 0xff002b36;
    public static final int LINUX_CONSOLE_WHITE = 0xffaaaaaa;

    // foreground color, background color
    public static final int[][] COLOR_SCHEMES = {
        {BLACK,             WHITE},
        {WHITE,             BLACK},
        {WHITE,             BLUE},
        {GREEN,             BLACK},
        {AMBER,             BLACK},
        {RED,               BLACK},
        {HOLO_BLUE,         BLACK},
        {SOLARIZED_FG,      SOLARIZED_BG},
        {SOLARIZED_DARK_FG, SOLARIZED_DARK_BG},
        {LINUX_CONSOLE_WHITE, BLACK}
    };

    public static final int ACTION_BAR_MODE_NONE = 0;
    public static final int ACTION_BAR_MODE_ALWAYS_VISIBLE = 1;
    public static final int ACTION_BAR_MODE_HIDES = 2;
    private static final int ACTION_BAR_MODE_MAX = 2;

    public static final int ORIENTATION_UNSPECIFIED = 0;
    public static final int ORIENTATION_LANDSCAPE = 1;
    public static final int ORIENTATION_PORTRAIT = 2;

    /** An integer not in the range of real key codes. */
    public static final int KEYCODE_NONE = -1;

    public static final int CONTROL_KEY_ID_NONE = 7;
    public static final int[] CONTROL_KEY_SCHEMES = {
        KeyEvent.KEYCODE_DPAD_CENTER,
        KeyEvent.KEYCODE_AT,
        KeyEvent.KEYCODE_ALT_LEFT,
        KeyEvent.KEYCODE_ALT_RIGHT,
        KeyEvent.KEYCODE_VOLUME_UP,
        KeyEvent.KEYCODE_VOLUME_DOWN,
        KeyEvent.KEYCODE_CAMERA,
        KEYCODE_NONE
    };

    public static final int FN_KEY_ID_NONE = 7;
    public static final int[] FN_KEY_SCHEMES = {
        KeyEvent.KEYCODE_DPAD_CENTER,
        KeyEvent.KEYCODE_AT,
        KeyEvent.KEYCODE_ALT_LEFT,
        KeyEvent.KEYCODE_ALT_RIGHT,
        KeyEvent.KEYCODE_VOLUME_UP,
        KeyEvent.KEYCODE_VOLUME_DOWN,
        KeyEvent.KEYCODE_CAMERA,
        KEYCODE_NONE
    };

    public static final int BACK_KEY_STOPS_SERVICE = 0;
    public static final int BACK_KEY_CLOSES_WINDOW = 1;
    public static final int BACK_KEY_CLOSES_ACTIVITY = 2;
    public static final int BACK_KEY_SENDS_ESC = 3;
    public static final int BACK_KEY_SENDS_TAB = 4;
    private static final int BACK_KEY_MAX = 4;

    public TermSettings(Resources res, SharedPreferences prefs) {
        readDefaultPrefs(res);
        readPrefs(prefs);
    }

    public TermSettings(Context context, SharedPreferences prefs) {
        readDefaultPrefs(context);
        readPrefs(prefs);
    }
    private void readDefaultPrefs(Context context) {
        Resources res=context.getResources();
        readDefaultPrefs(res);
        mSafeMargins = UIModeCompat.isUIModeTV(context);
    }
    private void readDefaultPrefs(Resources res) {
        mStatusBar = Integer.parseInt(res.getString(R.string.pref_statusbar_default));
        mActionBarMode = res.getInteger(R.integer.pref_actionbar_default);
        mOrientation = res.getInteger(R.integer.pref_orientation_default);
        mCursorStyle = Integer.parseInt(res.getString(R.string.pref_cursorstyle_default));
        mCursorBlink = Integer.parseInt(res.getString(R.string.pref_cursorblink_default));
        mFontSize = Integer.parseInt(res.getString(R.string.pref_fontsize_default));
        mColorId = Integer.parseInt(res.getString(R.string.pref_color_default));
        mUTF8ByDefault = res.getBoolean(R.bool.pref_utf8_by_default_default);
        mBackKeyAction = Integer.parseInt(res.getString(R.string.pref_backaction_default));
        mControlKeyId = Integer.parseInt(res.getString(R.string.pref_controlkey_default));
        mFnKeyId = Integer.parseInt(res.getString(R.string.pref_fnkey_default));
        mUseCookedIME = Integer.parseInt(res.getString(R.string.pref_ime_default));
        mFailsafeShell = res.getString(R.string.pref_shell_default);
        mShell = mFailsafeShell;
        mInitialCommand = res.getString(R.string.pref_initialcommand_default);
        mTermType = res.getString(R.string.pref_termtype_default);
        mCloseOnExit = res.getBoolean(R.bool.pref_close_window_on_process_exit_default);
        mVerifyPath = res.getBoolean(R.bool.pref_verify_path_default);
        mDoPathExtensions = res.getBoolean(R.bool.pref_do_path_extensions_default);
        mAllowPathPrepend = res.getBoolean(R.bool.pref_allow_prepend_path_default);
        // the mHomePath default is set dynamically in readPrefs()
        mAltSendsEsc = res.getBoolean(R.bool.pref_alt_sends_esc_default);
        mMouseTracking = res.getBoolean(R.bool.pref_mouse_tracking_default);
        mUseKeyboardShortcuts = res.getBoolean(R.bool.pref_use_keyboard_shortcuts_default);
        mFontPath = res.getString(R.string.pref_customfontfilepath_default);
        mToastPosition = res.getInteger(R.integer.pref_toast_position_default);
        mSafeMargins = res.getBoolean(R.bool.pref_safe_margins_default);
        mUseSystemWallpaper = res.getBoolean(R.bool.pref_use_system_wallpaper_default);
        mWallpaperAlpha = res.getInteger(R.integer.pref_transparency_default);
    }

    public void readPrefs(SharedPreferences prefs) {
        mPrefs = prefs;
        mStatusBar = readIntPref(STATUSBAR_KEY, mStatusBar, 1);
        mActionBarMode = readIntPref(ACTIONBAR_KEY, mActionBarMode, ACTION_BAR_MODE_MAX);
        mOrientation = readIntPref(ORIENTATION_KEY, mOrientation, 2);
        mSafeMargins = readBooleanPref(SAFE_MARGINS_KEY, mSafeMargins);
        mCursorStyle = readIntPref(CURSORSTYLE_KEY, mCursorStyle, 2);
        // mCursorBlink = readIntPref(CURSORBLINK_KEY, mCursorBlink, 1);
        mFontSize = readIntPref(FONTSIZE_KEY, mFontSize, 288);
        mColorId = readIntPref(COLOR_KEY, mColorId, COLOR_SCHEMES.length - 1);
        mUTF8ByDefault = readBooleanPref(UTF8_KEY, mUTF8ByDefault);
        mBackKeyAction = readIntPref(BACKACTION_KEY, mBackKeyAction, BACK_KEY_MAX);
        mControlKeyId = readIntPref(CONTROLKEY_KEY, mControlKeyId,
                CONTROL_KEY_SCHEMES.length - 1);
        mFnKeyId = readIntPref(FNKEY_KEY, mFnKeyId,
                FN_KEY_SCHEMES.length - 1);
        mUseCookedIME = readIntPref(IME_KEY, mUseCookedIME, 1);
        mShell = readStringPref(SHELL_KEY, mShell);
        mInitialCommand = readStringPref(INITIALCOMMAND_KEY, mInitialCommand);
        mTermType = readStringPref(TERMTYPE_KEY, mTermType);
        mCloseOnExit = readBooleanPref(CLOSEONEXIT_KEY, mCloseOnExit);
        mVerifyPath = readBooleanPref(VERIFYPATH_KEY, mVerifyPath);
        mDoPathExtensions = readBooleanPref(PATHEXTENSIONS_KEY, mDoPathExtensions);
        mAllowPathPrepend = readBooleanPref(PATHPREPEND_KEY, mAllowPathPrepend);
        mHomePath = readStringPref(HOMEPATH_KEY, mHomePath);
        mAltSendsEsc = readBooleanPref(ALT_SENDS_ESC, mAltSendsEsc);
        mMouseTracking = readBooleanPref(MOUSE_TRACKING, mMouseTracking);
        mUseKeyboardShortcuts = readBooleanPref(USE_KEYBOARD_SHORTCUTS,
                mUseKeyboardShortcuts);
        mFontPath = readStringPref(CUSTOM_FONT_PATH, "");
        mToastPosition = readIntPref(TOAST_POSITION, mToastPosition, TOAST_POSITION_MAX);

        mUseSystemWallpaper = readBooleanPref(USE_WALLPAPER, mUseSystemWallpaper);
        mWallpaperAlpha = readIntPref(WALLPAPER_ALPHA, mWallpaperAlpha, 255);

        mPrefs = null;  // we leak a Context if we hold on to this

    }

    private int readIntPref(String key, int defaultValue, int maxValue) {
        int val;
        try {
            String read=mPrefs.getString(key, Integer.toString(defaultValue));
            val = Integer.parseInt(read);
            //val = mPrefs.getInt(key,defaultValue);
        } catch (NumberFormatException e) {
            val = defaultValue;
        }
        val = Math.max(0, Math.min(val, maxValue));
        return val;
    }

    private String readStringPref(String key, String defaultValue) {
        return mPrefs.getString(key, defaultValue);
    }

    private boolean readBooleanPref(String key, boolean defaultValue) {
        return mPrefs.getBoolean(key, defaultValue);
    }

    public boolean showStatusBar() {
        return (mStatusBar != 0);
    }

    public int actionBarMode() {
        return mActionBarMode;
    }

    public int getScreenOrientation() {
        return mOrientation;
    }

    public boolean getSafeMargins() {return mSafeMargins; }

    public int getCursorStyle() {
        return mCursorStyle;
    }

    public int getCursorBlink() {
        return mCursorBlink;
    }

    public int getFontSize() {
        return mFontSize;
    }

    public int[] getColorScheme() {
        int[] result=COLOR_SCHEMES[mColorId];
        if(mUseSystemWallpaper) {
            int alpha = ( mWallpaperAlpha << 24 ) ;
            int old= result[1];
            result[1] = old & 0x00ffffff | alpha;
        }
        return result;
    }

    public boolean defaultToUTF8Mode() {
        return mUTF8ByDefault;
    }

    public int getBackKeyAction() {
        return mBackKeyAction;
    }

    public boolean backKeySendsCharacter() {
        return mBackKeyAction >= BACK_KEY_SENDS_ESC;
    }

    public boolean getAltSendsEscFlag() {
        return mAltSendsEsc;
    }

    public boolean getMouseTrackingFlag() {
        return mMouseTracking;
    }

    public boolean getUseKeyboardShortcutsFlag() {
        return mUseKeyboardShortcuts;
    }

    public int getBackKeyCharacter() {
        switch (mBackKeyAction) {
            case BACK_KEY_SENDS_ESC: return 27;
            case BACK_KEY_SENDS_TAB: return 9;
            default: return 0;
        }
    }

    public int getControlKeyId() {
        return mControlKeyId;
    }

    public int getFnKeyId() {
        return mFnKeyId;
    }

    public int getControlKeyCode() {
        return CONTROL_KEY_SCHEMES[mControlKeyId];
    }

    public int getFnKeyCode() {
        return FN_KEY_SCHEMES[mFnKeyId];
    }

    public boolean useCookedIME() {
        return (mUseCookedIME != 0);
    }

    public String getShell() {
        return mShell;
    }

    public String getFailsafeShell() {
        return mFailsafeShell;
    }

    public String getInitialCommand() {
        return mInitialCommand;
    }

    public String getTermType() {
        return mTermType;
    }

    public boolean closeWindowOnProcessExit() {
        return mCloseOnExit;
    }

    public boolean verifyPath() {
        return mVerifyPath;
    }

    public boolean doPathExtensions() {
        return mDoPathExtensions;
    }

    public boolean allowPathPrepend() {
        return mAllowPathPrepend;
    }

    public void setPrependPath(String prependPath) {
        mPrependPath = prependPath;
    }

    public String getPrependPath() {
        return mPrependPath;
    }

    public void setAppendPath(String appendPath) {
        mAppendPath = appendPath;
    }

    public String getAppendPath() {
        return mAppendPath;
    }

    public void setHomePath(String homePath) {
        mHomePath = homePath;
    }

    public String getHomePath() {
        return mHomePath;
    }

    public int getToastPosition() { return mToastPosition; }

    public int getToastGravity() {
        int result= Gravity.CENTER;
        switch (getToastPosition()) {
            case 0: result = Gravity.TOP | Gravity.LEFT; break;
            case 1: result = Gravity.TOP ; break;
            case 2: result = Gravity.TOP | Gravity.RIGHT; break;

            case 3: result = Gravity.LEFT; break;
            case 4: result = Gravity.CENTER; break;
            case 5: result = Gravity.RIGHT; break;

            case 6: result = Gravity.BOTTOM | Gravity.LEFT; break;
            case 7: result = Gravity.BOTTOM ; break;
            case 8: result = Gravity.BOTTOM | Gravity.RIGHT; break;
        }
        return result;
    }

    public String getFontPath() { return mFontPath;}

    public boolean getUseWallpaper() {return mUseSystemWallpaper;}
    public int getmWallpaperAlpha() {return mWallpaperAlpha;}
}
