package org.example.sudoku;

import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.app.AlertDialog;

//インターフェースの継承はimplements ただし継承した場合はoverrideか再定義が必要
public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = "Sudoku";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// すべてのボタンについてクリックリスナーをセットアップする
		// this = 自身のクラス（インスタンス）そのもの
		View continueButton = this.findViewById(R.id.continue_button);
		continueButton.setOnClickListener((OnClickListener) this);
		View newButton = this.findViewById(R.id.new_button);
		newButton.setOnClickListener((OnClickListener) this);
		View aboutButton = this.findViewById(R.id.about_button);
		aboutButton.setOnClickListener((OnClickListener) this);
		View exitButton = this.findViewById(R.id.exit_button);
		exitButton.setOnClickListener((OnClickListener) this);
	}

	// onClick定義　タップ時の反応＝intent（Activity）呼び出し
	// @Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.new_button:
			openNewGameDialog();
			break;
		case R.id.about_button:
			Intent i = new Intent(this, About.class);
			startActivity(i);
			break;
		case R.id.exit_button:
			finish();
			break;
		}
	}

	// onCreateOptionsMenu定義　メニューボタンのxml有効化
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	// メニュー項目選択後のintent呼び出し
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.settings:
			startActivity(new Intent(this, Settings.class));
			return true;
		}
		return false;

	}

	private void openNewGameDialog() {
		new AlertDialog.Builder(this)
				.setTitle(R.string.new_game_title)
				.setItems(R.array.difficulty,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int i) {
								// TODO Auto-generated method stub
								startGame(i);

							}
						}).show();
	}

	/* 指定された難易度レベルで新しいゲームを開始する　 */
	private void startGame(int Level) {
		Log.d(TAG, "clicke on " + Level);		
		Intent intent = new Intent(MainActivity.this, Game.class);
		intent.putExtra(Game.KEY_DIFFICULTY, Level);
		startActivity(intent);
		// start geme here
	}
}
