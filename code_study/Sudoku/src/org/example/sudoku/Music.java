package org.example.sudoku;

import android.content.Context;
import android.media.MediaPlayer;

public class Music {
	private static MediaPlayer mp = null;
	
	/**元の曲を止めて新しい曲を開始する */
	//リソースIDはint型でも使える
	public static void play(Context context,int resource){
		stop(context);
		//プレファレンスで無効にされてない限り、曲が開始される。=フラグ化
		if(Settings.getMusic(context)){
		mp = MediaPlayer.create(context, resource);
		mp.setLooping(true);
		mp.start();
		}
	}
	
	/**曲を停止する */
	public static void stop(Context context){
		if(mp != null){
			mp.stop();
			mp.release();
			mp=null;		
		}
	}
	
}
