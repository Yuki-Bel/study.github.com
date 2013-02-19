package org.example.sudoku;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

//ゲームのアーキテクト部分
public class Game extends Activity {

	private static final String TAG = "Sudoku";

	public static final String KEY_DIFFICULTY = "difficulty";
	public static final int DIFFICULTY_EASY = 0;
	public static final int DIFFICULTY_MEDIUM = 1;
	public static final int DIFFICULTY_ = 2;

	private int puzzle[] = new int[9 * 9];

	// private PuzzleView puzzleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");

		int diff = getIntent().getIntExtra(KEY_DIFFICULTY, DIFFICULTY_EASY);
		// puzzle = getPuzzle(diff);
		// calculateUsedTiles();

		// puzzleView = newpuzzleView;
		// setContentView(puzzleView);
		// puzzleView.requestFocus}();
	}
	// ...
}
