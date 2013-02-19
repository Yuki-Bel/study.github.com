package org.example.sudoku;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

public class PuzzleView extends View{
	private static final String TAG = "Sudoku";
	//ゲームクラスを静的参照の元とする
	private final Game game;
	//盤マスの変数
	private float width;//マスの横の長さ
	private float height;//マスの縦の長さ
	private int selX;//選択されたマスのX軸のインデックス
	private int selY;//選択されたマスのY軸のインデックス
	private final Rect selRect = new Rect();
	
	//コンストラクタよりゲームクラスの参照を保存(context)し、フォーカス(入力場)を設定
	public PuzzleView(Context context){
		super(context);
		this.game = (Game) context;
		setFocusable(true);
		setFocusableInTouchMode(true);		
	}
	@Override
	protected void onSizeChanged(int w,int h,int oldw,int oldh){
		width =w / 9f;
		height = h / 9f;
		//getRect(selX,selY,selRect);
		Log.d(TAG, "onSizechanged:width"+width+",heght"+height);
		//super = 継承もとクラス(メソッド)の宣言処理
		super.onSizeChanged(w, h, oldw, oldh);
	}
	

}
