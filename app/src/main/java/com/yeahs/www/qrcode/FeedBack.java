package com.yeahs.www.qrcode;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yeahs.www.qrcode.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
//帮助反馈类
public class FeedBack extends BaseActivity {
    LoadingDialog loadingDialog = null;
    @BindView(R.id.feed_back_text) EditText feedback_content;
    @BindView(R.id.text_title) TextView title;
    @BindView(R.id.button_backward) Button back;
    private Timer timer;
    private TimerTask task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_back);
        ButterKnife.bind(this);
        back.setVisibility(View.VISIBLE);
        title.setText("意见反馈");
        loadingDialog = new LoadingDialog(this, R.style.LoadingDialog);
    }
    @OnFocusChange(R.id.feed_back_text)
    protected void textFocus () {
        Log.i("input", "正在输入");
        feedback_content.setFocusable(true);
        feedback_content.setFocusableInTouchMode(true);
        feedback_content.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(feedback_content, 0);
    }
    @OnClick(R.id.feed_back_btn)
    protected void feedbackSubmit () {
        Log.i("click", "正在点击提交按钮");
        loadingDialog.show();
        task = new TimerTask() {
            @Override
            public void run() {
                loadingDialog.dismiss();
                FeedBack.this.runOnUiThread(new Runnable() {
                        public void run() {
                            feedback_content.setText("");
                            feedback_content.setFocusable(false);
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            if (imm != null) {
                                View v = new View(FeedBack.this);
                                ViewGroup g1 = (ViewGroup)getWindow().getDecorView();
                                ViewGroup g2 = (ViewGroup)g1.getChildAt(0);
                                g2.addView(v);
                                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                            }
                            else {
                                Log.i("imm", "imm为空");
                            }
                            Toast.makeText(FeedBack.this, "您的反馈我们已收到，请耐心等候我们的回复", Toast.LENGTH_LONG).show();
                        }
                    });
            }
        };

        timer = new Timer();
        // 参数：
        // 1000，延时1秒后执行。
        // 2000，每隔2秒执行1次task。
        timer.schedule(task, 1000);
//        FeedBack.this.runOnUiThread(new Runnable() {
//            public void run() {
//                Toast.makeText(FeedBack.this, "Hello", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
    @OnClick(R.id.button_backward)
    protected void back () {
        finish();
    }
    @Override
    protected void onStop() {
        super.onStop();

        // 暂停
        // timer.cancel();
        // task.cancel();
    }
}
