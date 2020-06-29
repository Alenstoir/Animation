package com.alenstoir.layer_animation;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;

class Circ {
    private float x, y, r;

    public Circ(float x,float y){
        this.x = x;
        this.y = y;
        r = 0;
    }

    public float getR() {
        return r;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void incR() {
        this.r+=5;
    }
}

class Graph extends View{
    protected Paint p, b;
    protected int x,y,z = 0,colorTable[]={0xffe87e04,0xff1f3a93,0xff9a12b3,0xffdb0a5b,0xffcf000f},
    arr[][][], size = 50, way = 0, bitmaps[][], state = 0;

    protected boolean waiter = false;

    protected ArrayList<Circ> circles = new ArrayList<Circ>();

    Bitmap bitmapSrc, bitmap;

    public Graph(Context context) {
        super(context);
        p=new Paint();
        p.setColor(0x0000FFFF);
        b = new Paint();
        b.setStyle(Paint.Style.STROKE);
        b.setStrokeWidth(1);
        b.setColor(0xFFFFFFFF);
    }

    void setPosition(int x,int y,int way){
        this.x=x;
        this.y=y;
        this.way = way;
    }

    public void setBitmaps(int [][] arr){
        this.bitmaps = arr;
    }

    public void incstate(){
        if (state < bitmaps.length - 1){
            state++;
            z = 0;
        }
        else{
            state = 0;
            z = 0;
        }
    }

    public void setRes(int[][][] arr){
        this.arr = arr;
    };

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public void onDraw(Canvas c) {
        bitmapSrc = BitmapFactory.decodeResource(getResources(), bitmaps[state][z]);
        float scale = 0;
        Matrix matrix = new Matrix();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            scale = (float) (getResources().getDisplayMetrics().widthPixels) / (float) (bitmapSrc.getWidth());
            matrix.postScale(scale, scale);
        }
        else {
            scale = (float) (getResources().getDisplayMetrics().heightPixels) / (float) (bitmapSrc.getHeight());
            matrix.postScale(scale, scale);
            //matrix.postRotate(90);
        }

        if (new Random().nextInt(100) >= 20){
            if (new Random().nextBoolean()) {
                circles.add(new Circ(new Random().nextInt(getResources().getDisplayMetrics().widthPixels), new Random().nextInt(getResources().getDisplayMetrics().heightPixels)));
            }
            else {
                circles.add(new Circ(new Random().nextInt(getResources().getDisplayMetrics().widthPixels), new Random().nextInt(getResources().getDisplayMetrics().heightPixels)));
            }
        }

        int j = circles.size();
        //Log.d("Circles amount ", ""+j);
        for (int i = 0; i < j; i++){
            c.drawCircle(circles.get(i).getX(), circles.get(i).getY(), circles.get(i).getR(), b);
            //Log.d("Drawing (x)(y)(r):"," "+circles.get(i).getX()+" "+circles.get(i).getY()+" "+circles.get(i).getR());
            circles.get(i).incR();
            if (circles.get(i).getR() >= 100) {
                circles.remove(i);
                i = i-1;
                j = j-1;
            }
        }

        bitmap = Bitmap.createBitmap(bitmapSrc, 0, 0, bitmapSrc.getWidth(), bitmapSrc.getHeight(), matrix, true);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                c.drawBitmap(bitmap, x, ((getResources().getDisplayMetrics().heightPixels) / 2) - (bitmapSrc.getHeight() / 2 * scale), paint);
            }
            else {
                c.drawBitmap(bitmap, ((getResources().getDisplayMetrics().widthPixels) / 2) - (bitmapSrc.getWidth() / 2 * scale), y, paint);
            }

     /*   for (int i = 0; i < arr[0].length; i++) {
            for (int j = 0; j < arr[0][0].length; j++) {
                if (arr[z][i][j] == 0)
                    c.drawRect(new Rect(x + j * size, y + i * size, x + j * size + size, x + i * size + size), p);
                else
                    c.drawRect(new Rect(x + j * size, y + i * size, x + j * size + size, x + i * size + size), b);
            }
        }



        p.setColor(0xffff0000);
        c.drawCircle(x + (J * (r * 2)), y + (I * (r * 2)), r, p);
        switch (new Random().nextInt(8)){
            case 0:if(I>0)I--;break;
            case 1:if(I<17)I++;break;
            case 2:if(J>0)J--;break;
            case 3:if(J<11)J++;break;

            case 4:
                if(I>0 && J>0){
                    I--;
                    J--;
                }
                break;

            case 5:
                if(I>0 && J<11){
                    I--;
                    J++;
                }
                break;

            case 6:
                if(I<17 && J>0){
                    I++;
                    J--;
                }
                break;

            case 7:
                if(I<17 && J<11){
                    I++;
                    J++;
                }
                break;
        }
        c.drawCircle(x + (L * (r * 2)), y + (K * (r * 2)), r, p);
        switch (new Random().nextInt(8)){
            case 0:if(K>0)K--;break;
            case 1:if(K<17)K++;break;
            case 2:if(L>0)L--;break;
            case 3:if(L<11)L++;break;

            case 4:
                if(K>0 && L>0){
                    K--;
                    L--;
                }
                break;

            case 5:
                if(K>0 && L<11){
                    K--;
                    L++;
                }
                break;

            case 6:
                if(K<17 && L>0){
                    K++;
                    L--;
                }
                break;

            case 7:
                if(K<17 && L<11){
                    K++;
                    L++;
                }
                break;
        }
        /*
        if(J<12)J++;else {
            J=0;
            if(I<18)I++;else I=0;
        }
        */
    if (waiter) {
        if (way == 1){
            waiter =! waiter;
            if (z < bitmaps[state].length - 1){
                z++;
            }
            else{
                z = 0;
            }}
        else {
            waiter =! waiter;
            if (z > 0){
                z--;
            }
            else{
                z = bitmaps[state].length -1;
            }
        }} else {
        waiter =! waiter;
    }



        invalidate();

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class MainActivity extends AppCompatActivity {


    int animation[][][] = {
            {
                    {
                            0,0,0,1
                    },
                    {
                            0,0,1,0
                    },
                    {
                            0,0,0,0
                    },
                    {
                            0,1,0,0
                    },
                    {
                            1,0,0,0
                    },
            },
            {
                    {
                            0,0,0,0
                    },
                    {
                            0,0,0,0
                    },
                    {
                            1,1,1,1
                    },
                    {
                            0,0,0,0
                    },
                    {
                            0,0,0,0
                    },
            },
            {
                    {
                            1,0,0,0
                    },
                    {
                            0,1,0,0
                    },
                    {
                            0,0,0,0
                    },
                    {
                            0,0,1,0
                    },
                    {
                            0,0,0,1
                    },
            },
    };

    int bitmaps[][] = {{R.drawable.bottle_00, R.drawable.bottle_01,R.drawable.bottle_02,R.drawable.bottle_03,R.drawable.bottle_04,R.drawable.bottle_05,R.drawable.bottle_06,R.drawable.bottle_07,R.drawable.bottle_08,R.drawable.bottle_09,R.drawable.bottle_10,R.drawable.bottle_11},
            {R.drawable.cat_00, R.drawable.cat_01},
            {R.drawable.rocket_00, R.drawable.rocket_01, R.drawable.rocket_02, R.drawable.rocket_03},
};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        final Graph g=new Graph(this);
        g.setPosition(0,0, 1);
       // g.setRes(animation);
        g.setBitmaps(bitmaps);
        g.setBackgroundColor(0xff000000);



        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("@@@@@@@@@@@@@@@","__________");
                g.incstate();
            }
        });

        setContentView(g);

    }
}