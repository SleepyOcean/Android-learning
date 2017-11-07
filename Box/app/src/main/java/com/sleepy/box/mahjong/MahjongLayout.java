package com.sleepy.box.mahjong;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sleepy.box.R;
import com.sleepy.box.util.SleepyUtil;

/**
 * Created by gehou on 2017/10/17.
 */

public class MahjongLayout extends RelativeLayout implements View.OnClickListener {
    int width;
    int itemWidth;
    int column = 6;
    int margin = 2;
    float distance;
    TextView levelTv;
    TextView scoreTv;
    TextView timerTv;

    public void setCounterPad(TextView level, TextView score, TextView timer) {
        levelTv = level;
        scoreTv = score;
        timerTv = timer;
    }

    int score;

    boolean isSecondLoad = false;

    boolean isFirst;
    boolean isSecond;
    int firstChoose;
    int secondChoose;
    Point firstPoint;
    Point secondPoint;
    ImageView firstView;
    ImageView secondView;
    int[][] matrix;
    Canvas canvas;
    Paint paint;

    public MahjongLayout(Context context) {
        super(context);
    }

    public MahjongLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = Math.min(getMeasuredHeight(), getMeasuredWidth());
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        init();
        super.onLayout(changed, l, t, r, b);
    }

    private void arrangeItemPosition() {
        int[] count = new int[4];
        int[] mark = new int[2];
        for (int i = 0; i < column * column; i++) {
            matrix[i / column][i % column] = SleepyUtil.generateRandomNum(1, 5);
        }
        for(int i = 0;i<column * column;i++){
            if(matrix[i/column][i%column]==1){
                count[0]++;
            }else if(matrix[i/column][i%column]==2){
                count[1]++;
            }else if(matrix[i/column][i%column]==3){
                count[2]++;
            }else if(matrix[i/column][i%column]==4){
                count[3]++;
            }
        }
        int j=0;
        for(int i=0;i<count.length;i++){
            if(count[i]%2==0){
                continue;
            }
            mark[j++] = i+1;
        }
        for(int i = 0;i<column * column;i++){
            if(matrix[i/column][i%column]==mark[1]){
                matrix[i/column][i%column] = mark[0];
                break;
            }
        }
        isSecondLoad = true;
    }

    private void init() {
        if (!isSecondLoad) {
            isFirst = true;
            matrix = new int[column][column];
            firstPoint = new Point();
            secondPoint = new Point();
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
            paint.setStrokeCap(Paint.Cap.ROUND);
            arrangeItemPosition();
            initItem();
            loadingAnim();
        }
    }

    private void initItem() {
        itemWidth = (width - margin * (column - 1)) / column;

        ImageView[] items = new ImageView[column * column];

        for (int i = 0; i < items.length; i++) {
            ImageView itemView = new ImageView(getContext());
            itemView.setImageDrawable(getResources().getDrawable(getDrawableId(matrix[i / column][i % column]), null));
            itemView.setOnClickListener(this);
            itemView.setFocusable(true);
            items[i] = itemView;
            itemView.setId(i + 1);

//            TextView tvItem = new TextView(getContext());
//            tvItem.setText(String.valueOf(matrix[i / column][i % column]));

            LayoutParams lp = new LayoutParams(itemWidth, itemWidth);

            if ((i + 1) % column != 0) {
                lp.rightMargin = margin;
            }

            if (i % column != 0) {
                lp.addRule(RelativeLayout.RIGHT_OF,
                        items[i - 1].getId());
            }

            if ((i + 1) > column) {
                lp.topMargin = margin;
                lp.addRule(RelativeLayout.BELOW,
                        items[i - column].getId());
            }
            addView(itemView, lp);
//            addView(tvItem, lp);
        }
    }

    private int getDrawableId(int i) {
        switch (i) {
            case 1:
                return R.drawable.maitem1;
            case 2:
                return R.drawable.maitem2;
            case 3:
                return R.drawable.maitem3;
            case 4:
                return R.drawable.maitem4;
            default:
                return R.drawable.maitem0;
        }
    }


    @Override
    public void onClick(View view) {
//        ((ImageView)view).setImageResource(R.drawable.border);


        int pos = view.getId() - 1;
        int raw = pos / column;
        int col = pos % column;
//        Log.d("TAG", "onClick: >>>>>" + matrix[raw][col]);
        if (matrix[raw][col] != 0) {
            if (isFirst) {
                firstChoose = pos;
                firstPoint.set(view.getLeft(), view.getTop());
                firstView = (ImageView) view;
                isFirst = false;
                isSecond = true;
//                Log.d("TAG", "onClick: >>>>>>>>>>>>>>>first: " + pos);
            } else if (isSecond) {
                secondChoose = pos;
                secondPoint.set(view.getX(), view.getY());
                secondView = (ImageView) view;
//                Log.d("TAG", "onClick: >>>>>>>>>>>>>>>first: " + firstChoose + " second: " + pos);
                int x1 = firstChoose / column;
                int y1 = firstChoose % column;
                int x2 = secondChoose / column;
                int y2 = secondChoose % column;
//                Log.d("TAG", "eliminateEstimate: " + "    x1=" + x1 + "    y1=" + y1 + "    x2=" + x2 + "    y2=" + y2 + "   " + matrix[x1][y1] + "     " + matrix[x2][y2]);
                if (eliminateEstimate(x1, y1, x2, y2)) {
                    vanishAnim(firstView, secondView);
                    score++;
                    scoreTv.setText(String.valueOf(score));
                }
                isSecond = false;
                isFirst = true;
            }
        }
//        Toast.makeText(getContext(), "" + (view.getId() - 1) / column + "     " + (view.getId() - 1) % column + "     value:" + matrix[(view.getId() - 1) / column][(view.getId() - 1) % column], Toast.LENGTH_SHORT).show();

    }

    private void vanishAnim(ImageView firstView, ImageView secondView) {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(firstView, ALPHA, 1, 0);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(secondView, ALPHA, 1, 0);
        anim1.setDuration(1000);
        anim2.setDuration(1000);
        AnimatorSet set = new AnimatorSet();
        set.play(anim1).with(anim2);
        set.start();
        matrix[(firstView.getId() - 1) / column][(firstView.getId() - 1) % column] = 0;
        matrix[(secondView.getId() - 1) / column][(secondView.getId() - 1) % column] = 0;
//        firstView.setImageDrawable(getResources().getDrawable(R.drawable.maitem0,null));
//        secondView.setImageDrawable(getResources().getDrawable(R.drawable.maitem0,null));
    }

    private void lineAnim(Path path) {
        PathMeasure pathMeasure = new PathMeasure();
        pathMeasure.setPath(path, false);
        float length = pathMeasure.getLength();
        float stopD = distance * length;
        float startD = 0;

        pathMeasure.getSegment(startD, stopD, path, true);
        canvas.drawPath(path, paint);
    }

    private Path obtainPath(Point p1, Point p2) {
        Path path = new Path();
        path.moveTo(p1.x, p1.y);
        path.lineTo(p1.x, p1.y - 20);
        path.lineTo(p2.x, p1.y - 20);
        path.lineTo(p2.x, p2.y);
        return path;
    }

    private boolean eliminateEstimate(int x1, int y1, int x2, int y2) {
        Log.d("TAG", "eliminateEstimate: " + "    x1=" + x1 + "    y1=" + y1 + "    x2=" + x2 + "    y2=" + y2 + "   " + matrix[x1][y1] + "     " + matrix[x2][y2]);
        if (x1 == x2 && y1 == y2) {
            return false;
        }
        if (matrix[x1][y1] == matrix[x2][y2]) {
            if (zeroTurn(x1, y1, x2, y2)) {
                return true;
            }
            if (oneTurn(x1, y1, x2, y2)) {
                return true;
            }
            if (twoTurn(x1, y1, x2, y2)) {
                return true;
            }
        }
        return false;
    }

    private boolean zeroTurn(int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            if (y1 < y2) {
                for (int i = y1 + 1; i < y2; i++) {
                    if (matrix[x1][i] != 0) {
                        return false;
                    }
                }
                return true;
            } else {
                for (int i = y2 + 1; i < y1; i++) {
                    if (matrix[x1][i] != 0) {
                        return false;
                    }
                }
                return true;
            }
        }
        if (y1 == y2) {
            if (x1 < x2) {
                for (int i = x1 + 1; i < x2; i++) {
                    if (matrix[i][y1] != 0) {
                        return false;
                    }
                }
                return true;
            } else {
                for (int i = x2 + 1; i < x1; i++) {
                    if (matrix[i][y1] != 0) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private boolean oneTurn(int x1, int y1, int x2, int y2) {
        if (x1 == x2 || y1 == y2) {
            return false;
        }
        int relativePosition = ((x1 < x2) ? 0 : 2) + ((y1 < y2) ? 4 : 8);
        switch (relativePosition) {
            case (0 + 4):
                return oneTurnFeasibility(x1, y1, x2, y2, 1);
            case (0 + 8):
                return oneTurnFeasibility(x1, y1, x2, y2, 3);
            case (2 + 4):
                return oneTurnFeasibility(x1, y1, x2, y2, 2);
            case (2 + 8):
                return oneTurnFeasibility(x1, y1, x2, y2, 4);
        }
        return false;
    }

    private boolean oneTurnFeasibility(int x1, int y1, int x2, int y2, int relativePos) {
        /**
         * relativePos : 1.右下, 2.右上, 3.左下, 4.左上
         * 判断一折点是否可行
         */
        boolean firstFeasibility = true;
        int a, b, c, d, e, f, m, n, o, p, q, r;
        a = b = c = d = e = f = m = n = o = p = q = r = 0;
        if (relativePos > 2) {
            int tmp;
            tmp = x1;
            x1 = x2;
            x2 = tmp;
            tmp = y1;
            y1 = y2;
            y2 = tmp;
        }
        switch (relativePos) {
            case 1:
            case 4:
                a = x1 + 1;
                b = x2 + 1;
                c = y1;
                d = y1 + 1;
                e = y2;
                f = x2;
                m = x1;
                n = x2;
                o = y2;
                p = y1 + 1;
                q = y2;
                r = x1;
                break;
            case 2:
            case 3:
                a = x2;
                b = x1;
                c = y1;
                d = y1 + 1;
                e = y2;
                f = x2;
                m = x2+1;
                n = x1+1;
                o = y2;
                p = y1 + 1;
                q = y2;
                r = x1;
                break;
            default:
        }
        for (int i = a; i < b; i++) {
            if (matrix[i][c] != 0) {
                firstFeasibility = false;
                break;
            }
        }
        for (int j = d; j < e; j++) {
            if (matrix[f][j] != 0) {
                firstFeasibility = false;
                break;
            }
        }
        if (!firstFeasibility) {
            for (int i = m; i < n; i++) {
                if (matrix[i][o] != 0) {
                    return false;
                }
            }
            for (int j = p; j < q; j++) {
                if (matrix[r][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }


    private boolean twoTurn(int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            if (y1 < y2) {
                for (int i = y1; i < y2 + 1; i++) {
                }
            }
            if (x1 == 0 || x1 == column - 1 || y1 == 0 || y1 == column - 1) {
                return true;
            }
        } else if (y1 == y2) {

        }

        int relativePosition = ((x1 < x2) ? 0 : 2) + ((y1 < y2) ? 4 : 8);
        switch (relativePosition) {
            case (0 + 4):
                return twoTurnFeasibility(x1, y1, x2, y2, 1);
            case (0 + 8):
                return twoTurnFeasibility(x1, y1, x2, y2, 2);
            case (2 + 4):
                return twoTurnFeasibility(x1, y1, x2, y2, 3);
            case (2 + 8):
                return twoTurnFeasibility(x1, y1, x2, y2, 4);
        }
        return false;
    }

    private boolean twoTurnFeasibility(int x1, int y1, int x2, int y2, int relativePos) {
        // TODO: 2017/10/20 判断两折点是否可行
        return false;
    }


    private void loadingAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(59, 0);
        valueAnimator.setDuration(59000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                distance = (int) valueAnimator.getAnimatedValue();
                timerTv.setText(String.valueOf(distance) + " s");
                timerTv.invalidate();
                if (distance == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("游戏结束");
                    TextView result = new TextView(getContext());
                    result.setText("你的得分是：" + score);
                    result.setTextSize(20);
                    result.setGravity(Gravity.CENTER);
                    result.setPadding(80, 80, 80, 80);
                    builder.setView(result);
                    builder.show();
                }
            }
        };
        valueAnimator.addUpdateListener(updateListener);
        valueAnimator.start();
    }

    class Point {
        private float x;
        private float y;

        public void set(float x, float y) {
            this.x = x;
            this.y = y;
        }

    }
}

