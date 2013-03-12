package org.example.sudoku;

import android.os.Bundle;
import android.content.Context;
import android.preference.PreferenceManager;
import android.preference.PreferenceActivity;

public class Settings extends PreferenceActivity {
	// オプション名とデフォルト値
	private static final String OPT_MUSIC = "music";
	private static final boolean OPT_MUSIC_DEF = true;
	private static final String OPT_HINTS = "hints";
	private static final boolean OPT_HINTS_DEF = true;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}

	/** musicオプションの現在値取得 */
	public static boolean getMusic(Context context) {
		//チェックボックスプリファレンスからmusicキーよりチェックしてあるかを調べ、正負を出す。
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(OPT_MUSIC, OPT_MUSIC_DEF);
	}
	
	/** hintオプションの現在値取得 */
	public static boolean getHints(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(OPT_HINTS, OPT_HINTS_DEF);
	}
}
