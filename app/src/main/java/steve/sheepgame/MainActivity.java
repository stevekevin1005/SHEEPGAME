package steve.sheepgame;

import android.app.Activity;
import android.app.Service;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Vibrator;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity {
    Button start,next1,next2,end;
    TextView score,result,painter,lastTime;
    ImageView image;
    Timer timer,timer2;
    TimerTask task1,task2,task3,task4;
    Handler handler; //UI thread
    Handler Boss =null,Boss2 = null; //子thread
    HandlerThread Staff = null,Staff2 = null;
    LayoutParams para;//控制圖片用
    DisplayMetrics metrics;//取得螢幕尺寸
    String temp,temp2;//顯示的字元
    Typeface font;//字形元件
    Vibrator myVibrator;//震動元件
    MediaPlayer m;//撥音樂用
    int control = 1,getScore=0,endTime = 15;
    static int width;
    static int heigth;
    int a;
    int b;
    boolean touch = false;
    final int normal = 1000,difficult = 750,lucifa = 500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*連結畫面上元件*/
        start = (Button) findViewById(R.id.start);
        next1 = (Button) findViewById(R.id.next1);
        next2 = (Button) findViewById(R.id.next2);
        end  = (Button)findViewById(R.id.end);
        score = (TextView) findViewById(R.id.score);
        result = (TextView) findViewById(R.id.text);
        painter = (TextView) findViewById(R.id.painter);
        lastTime = (TextView)findViewById(R.id.time);
        image = (ImageView) findViewById(R.id.imageView);
        font = Typeface.createFromAsset(getAssets(), "fonts/W3.ttf");
        final String x = (String) score.getText();
        final String y = (String)lastTime.getText();
        /*設定字型*/
        start.setTypeface(font);
        next1.setTypeface(font);
        next2.setTypeface(font);
        painter.setTypeface(font);
        score.setTypeface(font);
        result.setTypeface(font);
        lastTime.setTypeface(font);
        /*實作物件*/

        painter.setMovementMethod(LinkMovementMethod.getInstance());
        painter.setText(Html.fromHtml("<a href=\"https://www.facebook.com/cat1101/\">繪師:貓ㄦ<a/>"));
        para = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,(LayoutParams.WRAP_CONTENT));//實作物件
        metrics = new DisplayMetrics();//實作物件
        myVibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);
        m = new MediaPlayer();
        /*設定子thread*/
        Staff = new HandlerThread("main");
        Staff.start();
        Boss = new Handler(Staff.getLooper());
        Staff2 = new HandlerThread("time");
        Staff2.start();
        Boss2 = new Handler(Staff2.getLooper());
        /*撥放音樂*/
        m = MediaPlayer.create(getApplicationContext(), R.raw.bokuzyo);
        m.setLooping(true);
        m.start();
        /*取得螢幕大小*/
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        Log.d(String.valueOf(MainActivity.width), "寬長");
        heigth = metrics.heightPixels;
        Log.d(String.valueOf(MainActivity.heigth), "高長");
        /*設定timer task   每次呼叫timer都要新做一個*/
        task1 = new TimerTask() {
            @Override
            public void run() {
                // TODO 自動產生的方法 Stub
                if(control == 0 ){
                    control =1;
                }
                else{
                    control = 0;
                }
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);//丟給UI thread
            }
        };
        task2 = new TimerTask() {
            @Override
            public void run() {
                // TODO 自動產生的方法 Stub
                if(control == 0 ){
                    control =1;
                }
                else{
                    control = 0;
                }
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);//丟給UI thread
            }
        };
        task3 = new TimerTask() {
            @Override
            public void run() {
                // TODO 自動產生的方法 Stub
                if(control == 0 ){
                    control =1;
                }
                else{
                    control = 0;
                }
                Message message = new Message();
                message.what = 3;
                handler.sendMessage(message);//丟給UI thread
            }
        };
        task4 = new TimerTask() {
            @Override
            public void run() {
                // TODO 自動產生的方法 Stub
                Message message = new Message();
                message.what = 4;
                handler.sendMessage(message);//丟給UI thread
            }
        };
         /*按鈕功能實作*/
        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO 自動產生的方法 Stub
                painter.setText("繪師:貓ㄦ");
                lastTime.setVisibility(View.VISIBLE);
                start.setVisibility(View.INVISIBLE);//按鈕隱藏
                result.setVisibility(View.INVISIBLE);
                timer = new Timer();
                timer2 = new Timer();
                Boss2.post(r4);
                Boss.post(r1);//啟動timer
            }
        });
        next1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO 自動產生的方法 Stub
                endTime = 15;
                temp2 = y +  Integer.toString(endTime);
                lastTime.setText(temp2);
                lastTime.setVisibility(View.VISIBLE);

                next1.setVisibility(View.INVISIBLE);//按鈕隱藏
                control = 1;

                timer = new Timer();
                Boss.post(r2);//啟動timer
            }
        });
        next2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO 自動產生的方法 Stub
                endTime =15;
                temp2 = y +  Integer.toString(endTime);
                lastTime.setText(temp2);
                lastTime.setVisibility(View.VISIBLE);

                next2.setVisibility(View.INVISIBLE);//按鈕隱藏
                control = 1;

                timer = new Timer();
                Boss.post(r3);//啟動timer
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 自動產生的方法 Stub
                onDestroy();
            }
        });
        /*觸摸圖片加分*/
        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO 自動產生的方法 Stub
               if(touch==false) {
                   myVibrator.vibrate(100);//震動 抖抖抖
                   getScore = getScore + 1;
                   temp = x + Integer.toString(getScore);
                   score.setText(temp);
                   image.setImageDrawable(getResources().getDrawable(R.drawable.sheep2));
                   touch= true;
               }
                else{
                   image.setVisibility(View.INVISIBLE);
               }
                return false;
            }
        });
        /*UI thread實作*/
        handler = new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch(msg.what){
                    case 4:
                        if (endTime == 0){
                            lastTime.setVisibility(View.INVISIBLE);
                        }
                        endTime = endTime -1;
                        temp2 = y +  Integer.toString(endTime);
                        lastTime.setText(temp2);
                        break;
                    case 1:
                        if(control==0){
                            touch = false;
                            int xx = 150+(int)(Math.random()*200);
                            para.height = xx;
                            Log.d(String.valueOf(xx), "高長");
                            image.setImageDrawable(getResources().getDrawable(R.drawable.sheep1));
                            a = (int) (Math.random()*(width -(xx*1.5)));
                            b = (int) (Math.random()*(heigth-(xx*1.5)));
                            Log.d(String.valueOf(a), "寬位置");
                            Log.d(String.valueOf(b), "高位置");
                            para.setMargins(a,b,0,0);

                            image.setLayoutParams(para);
                            image.setVisibility(View.VISIBLE);
                            if(endTime<=0){

                                image.setVisibility(View.INVISIBLE);
                                timer.cancel();
                                next1.setVisibility(View.VISIBLE);
                            }
                            break;
                        }
                        else{
                            image.setVisibility(View.INVISIBLE);
                            break;
                        }
                    case 2:
                        if(control==0){
                            touch = false;
                            int xx = 150+(int)(Math.random()*200);
                            para.height = xx;
                            Log.d(String.valueOf(xx), "高長");
                            image.setImageDrawable(getResources().getDrawable(R.drawable.sheep1));
                            a = (int) (Math.random()*(width -(xx*1.5)));
                            b = (int) (Math.random()*(heigth-(xx*1.5)));
                            Log.d(String.valueOf(a), "寬位置");
                            Log.d(String.valueOf(b), "高位置");
                            para.setMargins(a,b,0,0);
                            image.setLayoutParams(para);
                            image.setVisibility(View.VISIBLE);
                            if(endTime<=0){

                                image.setVisibility(View.INVISIBLE);
                                timer.cancel();
                                next2.setVisibility(View.VISIBLE);
                            }
                            break;
                        }
                        else{
                            image.setVisibility(View.INVISIBLE);
                            break;
                        }
                    case 3:
                        if(control==0){
                            touch = false;
                            int xx = 150+(int)(Math.random()*200);
                            para.height = xx;
                            Log.d(String.valueOf(xx), "高長");
                            image.setImageDrawable(getResources().getDrawable(R.drawable.sheep1));
                            a = (int) (Math.random()*(width -(xx*1.5)));
                            b = (int) (Math.random()*(heigth-(xx*1.5)));
                            Log.d(String.valueOf(a), "寬位置");
                            Log.d(String.valueOf(b), "高位置");
                            para.setMargins(a,b,0,0);
                            image.setLayoutParams(para);
                            image.setVisibility(View.VISIBLE);
                            if(endTime<=0){

                                String evulation;
                                if(getScore>30){
                                    evulation = " 您太厲害了";
                                }
                                else if(getScore>10){
                                    evulation = " 您技巧還可以";
                                }
                                else {
                                    evulation = " 您要加油唷";
                                }
                                image.setVisibility(View.INVISIBLE);
                                timer.cancel();
                                temp = "遊戲結束 \n您得分為:"+Integer.toString(getScore) + evulation;
                                result.setText(temp);
                                result.setVisibility(View.VISIBLE);
                                end.setVisibility(View.VISIBLE);
                            }
                            break;
                        }
                        else{
                            image.setVisibility(View.INVISIBLE);
                            break;
                        }
                }
            }
        };
    }
    Runnable r1 = new Runnable(){
        @Override
        public void run() {
            // TODO 自動產生的方法 Stub
            //設定Timer(task為執行內容，多少時間後才開始執行TimerTask,間格1秒執行一次)
            timer.schedule(task1,0,normal);
        }
    };
    Runnable r2 = new Runnable(){
        @Override
        public void run() {
            // TODO 自動產生的方法 Stub
            //設定Timer(task為執行內容，多少時間後才開始執行TimerTask,間格1秒執行一次)
            timer.schedule(task2,0,difficult);
        }
    };
    Runnable r3 = new Runnable(){
        @Override
        public void run() {
            // TODO 自動產生的方法 Stub
            //設定Timer(task為執行內容，多少時間後才開始執行TimerTask,間格1秒執行一次)
            timer.schedule(task3,0,lucifa);
        }
    };
    Runnable r4 = new Runnable(){
        @Override
        public void run() {
            // TODO 自動產生的方法 Stub
            //設定Timer(task為執行內容，多少時間後才開始執行TimerTask,間格1秒執行一次)
            timer2.schedule(task4,0,1000);
        }
    };
    protected void onPause(){
        onDestroy();
    }
    protected void onStop(){
        onDestroy();
    }
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        timer.cancel();
        timer2.cancel();
        m.release();//釋放
        finish();
    }
}
