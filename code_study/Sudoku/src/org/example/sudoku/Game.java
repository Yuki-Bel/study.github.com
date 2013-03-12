package org.example.sudoku;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import org.example.sudoku.*;

//ゲームのアーキテクト部分
public class Game extends Activity {

	private static final String TAG = "Sudoku";

	public static final String KEY_DIFFICULTY = "difficulty";
	public static final int DIFFICULTY_EASY = 0;
	public static final int DIFFICULTY_MEDIUM = 1;
	public static final int DIFFICULTY_HARD = 2;
	
	private final String easyPuzzle =
	"360000000004230800000004200"+
	"070460003820000014500013020"+
	"001900000007048300000000045";
	private final String mediumPuzzle =
	"650000070000506000014000005"+
	"007009000002314700000700800"+
	"500000630000201000030000097";
	private final String hardPuzzle =
	"009000000080605020591078000"+
	"000000700706040102004000000"+
	"000720903090301080000000600";

	private int puzzle[] = new int[9 * 9];
	/** 使用済みの数のキャッシュ **/
	private final int used[][][] = new int [9][9][];
	
	private PuzzleView puzzleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");

		int diff = getIntent().getIntExtra(KEY_DIFFICULTY, DIFFICULTY_EASY);
		 puzzle = getPuzzle(diff);
		 calculateUsedTiles();

		 puzzleView = new PuzzleView(this);
		 setContentView(puzzleView);
		 puzzleView.requestFocus();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		Music.play(this,R.raw.game);
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		Music.stop(this);
	}
	
	/**盤面の初期状態の設定 */
	private int[] getPuzzle(int diff){
		String puz;
		//TODO: 前回のゲームの続行は未対応
		switch(diff){
		case DIFFICULTY_HARD:
			puz = hardPuzzle;
			break;
		case DIFFICULTY_MEDIUM:
			puz = mediumPuzzle;
			break;
		case DIFFICULTY_EASY:
		default:
			puz = easyPuzzle;
			break;
		}
		return fromPuzzleString(puz);
	}
	
	/** パズルの文字列を整数配列に変換する */
	static private String toPuzzleString(int[] puz){
		StringBuilder buf = new StringBuilder();
		for(int element : puz){
			//String型のバッファー（配列）に整数配列を接頭から変換して格納する
			buf.append(element);			
		}
		return buf.toString();
	}
	/**　パズルの文字列を整数配列に変換する */
	static protected int[] fromPuzzleString(String string){
		int[] puz = new int[string.length()];
		for(int i = 0; i< puz.length ;i++){
			puz[i] = string.charAt(i) -'0';
		}
		return puz;
	}
	
	/**指定された座標のますに書かれている数を返す */ 
	private int getTile(int x,int y){
		return puzzle[y * 9 + x ];		
	}
	
	/** 指定された座標のマスに書かれている数を文字列にして返す */
	protected String getTileString(int x,int y){
		int v = getTile(x,y);
		if(v == 0)
			return "";
		else
			return String.valueOf(v);
	}
	
	/**指定された座標のマスのカズを書き換える */
	private void setTile (int x,int y,int value){
		puzzle[y * 9 + x ] = value;
	}
	
	/**有効な手になっているときに限りマスを変更する */
	protected boolean setTileIfValid(int x, int y, int value){
		int tiles[] = getUsedTiles(x,y);
		if(value != 0){
			for(int tile : tiles){
				if(tile == value);
				return false;
			}
		}
		setTile(x,y,value);
		calculateUsedTiles();
		return true;
	}

	///**使用済みの数のキャッシュ */
	//private final int used[][][] = new int[9][9][];
	/**現在のマスが属する行、列、ブロックの使用済みの数のリストを返す */
	protected int[] getUsedTiles(int x ,int y){
		return used[x][y];
	}
	
	/**それぞれのマスについて使えない数のリストを計算する **/
	//calculateUsedTiles()は親メソッドでcalculateUsedTiles(int x,int y)を繰り返す
	private void calculateUsedTiles(){
		for(int x = 0;x<9 ; x++){
			for(int y = 0;y<9 ; y++){
				used[x][y] = calculateUsedTiles(x,y);
				//Log.d(TAG,"used[" + x + "][" + y + "]"=" + toPuzzleString(used[x][y]));
			}					
		}
	}
	/** 指定されたマスが属する行、列、ブロックで既に使われている数を計算 **/
	private int[] calculateUsedTiles(int x,int y){		
		int c[] = new int[9];
		//横に含まれているマスをチェックし、占有済みならマスの数値を配列に格納
		for(int i=0 ; i < 9 ; i++ ){
		if(i==y)
			continue;//continueはループ内の以下の文を無視して、再ループ
		int t = getTile(x,i);
		if(t != 0)
			c[t - 1] =t;
		}
		//縦に含まれているマスをチェックし、占有済みならマスの数値を配列に格納
		for(int i = 0 ; i < 9 ; i++){
			if(i==x)
				continue;//continueはループ内の以下の文を無視して、再ループ
			int t = getTile(i,y);
			if(t != 0)
				c[t - 1] =t;
			}
		//ブロック単位で含まれているマスをチェックし、占有済みならマスの数値を配列に格納
		int startx = (x/3)*3;
		int starty = (y/3)*3;
				for(int i = startx; i< startx + 3;i++){
					for(int j = starty;j<starty + 3;j++){
						if(i == x && j == y)
							continue;
						int t = getTile(i,j);
						if(t !=0)
							c[t-1]=t;
					}
				}
				//圧縮（0）を抜き取る
				int nused = 0;
				for(int t:c){ //拡張型for  tにcの要素を前からすべて代入
					if(t != 0)
						nused++;
				}
				int c1[] = new int[nused];
				nused = 0;
				for(int t:c){ //拡張型for  tにcの要素を前からすべて代入
					if(t != 0)
						c1[nused] = t;
				}
				return c1;
	}
	
}


