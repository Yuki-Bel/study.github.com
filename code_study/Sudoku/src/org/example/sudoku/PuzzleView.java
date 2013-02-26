package org.example.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

public class PuzzleView extends View {
	private static final String TAG = "Sudoku";
	// ゲームクラスを静的参照の元とする
	private final Game game;
	// 盤マスの変数
	private float width;// マスの横の長さ
	private float height;// マスの縦の長さ
	private int selX;// 選択されたマスのX軸のインデックス
	private int selY;// 選択されたマスのY軸のインデックス
	private final Rect selRect = new Rect();

	// コンストラクタよりゲームクラスの参照を保存(context)し、フォーカス(入力場)を設定
	public PuzzleView(Context context) {
		super(context);
		this.game = (Game) context;
		setFocusable(true);
		setFocusableInTouchMode(true);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		width = w / 9f;
		height = h / 9f;
		//getRect(selX,selY,selRect);
		Log.d(TAG, "onSizechanged:width" + width + ",heght" + height);
		// super = 継承もとクラス(メソッド)の宣言処理
		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	//選択キーを移動させる（フォーカス）
	//onKeyDownオーバライドよりキー入力のイベント動作処理
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent event){
		switch(keyCode){		
		case KeyEvent.KEYCODE_DPAD_UP:
			select(selX,selY-1);
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			select(selX,selY+1);
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			select(selX-1,selY);
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			select(selX+1,selY);
			break;
			default:
				return super.onKeyDown(keyCode, event);
		}
		return true;
	}

	//セルのフォーカス領域計算
	private void select(int x,int y){
		invalidate(selRect);
		selX = Math.min(Math.max(x, 0), 8);
		selY = Math.min(Math.max(x, 0), 8);	
		getRect(selX,selY,selRect);		
	}
	private void getRect(int x, int y ,Rect rect){
		rect.set((int)(x*width), (int)(y*height), (int)(x*width+width), (int)(y*height+height));		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// 背景を描画する
		Paint background = new Paint();
		background.setColor(getResources().getColor(R.color.puzzle_background));
		canvas.drawRect(0, 0, getWidth(), getHeight(), background);
		// 盤面を描画する
		// 枠線の色を定義する
		Paint dark = new Paint();
		dark.setColor(getResources().getColor(R.color.puzzle_dark));
		Paint hilite = new Paint();
		hilite.setColor(getResources().getColor(R.color.puzzle_hilite));
		Paint light = new Paint();
		light.setColor(getResources().getColor(R.color.puzzle_light));
		for (int i = 0; i < 9; i++) {
			canvas.drawLine(0, i * height, getWidth(), i * height, light);
			canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1,
					hilite);
			canvas.drawLine(i * width, 0, i * width, getHeight(), light);
			canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(),
					hilite);
		}
		// 3*3ブロックを区切る線
		for (int i = 0; i < 9; i++) {
			if (i % 3 != 0)
				continue;
			canvas.drawLine(0, i * height, getWidth(), i * height, dark);
			canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1,
					hilite);
			canvas.drawLine(i*width, 0, i*width, getHeight(), dark);
			canvas.drawLine(i*width+1, 0, i*width+1, getHeight(), hilite);			
		}
		// 数値を描画する
		//数値の色とスタイルを定義する
		Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
		foreground.setColor(getResources().getColor(R.color.puzzle_foreground));
		foreground.setStyle(Style.FILL);
		foreground.setTextSize(height*0.75f);
		foreground.setTextScaleX(width/height);
		foreground.setTextAlign(Paint.Align.CENTER);
		
		//マス目の中央に数字を描く
		FontMetrics fm = foreground.getFontMetrics();
		//X軸方向でセンタリングする。アライメントを使う。
		float x = width / 2;
		//Y軸方向でセンタリングする。
		//まずアセント/ディセント（上半分と下半分）を調べる
		float y = height / 2 -(fm.ascent + fm.descent) / 2;
		for(int i = 0; i<9 ; i++){
			for(int j = 0; j<9 ; j++){
				//数値を盤に格納
		//	canvas.drawText(this.game.getTileString(i,j), i*width+x
		//			,j * height + y, foreground);
		//			canvas.drawText("N", i*width+x
		//					,j * height + y, foreground);
		}
		
		// ヒントを描画する
		// 選択されたマスを描画する
			Log.d(TAG,"selRect=" + selRect);
			Paint selected = new Paint();
			selected.setColor(getResources().getColor(R.color.puzzle_selected));
			canvas.drawRect(selRect, selected);
	}
	}

}
