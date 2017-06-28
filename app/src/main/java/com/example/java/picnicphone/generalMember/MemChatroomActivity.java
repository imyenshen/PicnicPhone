package com.example.java.picnicphone.generalMember;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java.picnicphone.R;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

// 這是對話視窗
public class MemChatroomActivity extends AppCompatActivity {

    // web socket 測試{


    private static final String TAG = "MemChatroomActivity";
    private static final String SERVER_URI =
            "ws://10.0.2.2:8081/WebSocketChat_Web/MyWebSocketServer/android/100";

    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_MESSAGE = "message";

    private MyWebSocketClient myWebSocketClient;
    private TextView tvMemChatroomMes;
    private EditText etMemChatroomMes;
    private ScrollView svMemChatroomMes;
    public String USER_NAME = null;
    public static Context mContext;

    private LinearLayout llMemChatroomMes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mem_chatroom);
        findViews();
        mContext = getApplicationContext();

        // 改變對話視窗上面的名稱 {
        TextView etChatroomName = (TextView) findViewById(R.id.etChatroomName);
        Bundle bundle = getIntent().getExtras();
        String MEM_NAME = bundle.getString("MEM_NAME");
        etChatroomName.setText(MEM_NAME);
        USER_NAME = MEM_NAME;
        // }

        // web socket 測試 {
        URI uri = null;
        try {
            uri = new URI(SERVER_URI);
        } catch (URISyntaxException e) {
            Log.e(TAG, e.toString());
        }
        myWebSocketClient = new MyWebSocketClient(uri);
        myWebSocketClient.connect();
        // }
    }

    class MyWebSocketClient extends WebSocketClient {

        public MyWebSocketClient(URI serverUri) {
            // Draft_17是連接協議，就是標準的RFC 6455（JSR256）
            super(serverUri, new Draft_17());
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            Log.d(TAG, "onOpen: handshakedata.toString() = " + handshakedata.toString());
        }

        @Override
        public void onMessage(final String message) {
            Log.d(TAG, "onMessage: " + message);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject jsonObject = new JSONObject(message);
                        String userName = jsonObject.get(KEY_USER_NAME).toString();
                        String message = jsonObject.get(KEY_MESSAGE).toString();
                        String text = userName + ": " + message + "\n";

                        // 用這邊判斷誰收到誰送出
//                        if (USER_NAME.equals(userName)){
//                            tvMemChatroomMes.setGravity(Gravity.RIGHT);
//                            tvMemChatroomMes.append(text);
//                        }else {
//                            tvMemChatroomMes.setGravity(Gravity.LEFT);
//                            tvMemChatroomMes.append(text);
//                        }


                        // 測試方法2 只能顯示出一行
//                        if(USER_NAME.equals(userName)){
//                            LinearLayout ll_append = new LinearLayout(mContext);
//                            LinearLayout.LayoutParams  ll_params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//                            ll_append.setLayoutParams(ll_params);
//                            ll_append.setGravity(Gravity.RIGHT);
//                            TextView tvMemChatroomMes = new TextView(mContext);
//                            tvMemChatroomMes.setText(text);
//                            (ll_append).addView(tvMemChatroomMes);
//                            llMemChatroomMes.addView(ll_append);
//                        } else{
//                            LinearLayout ll_append = new LinearLayout(mContext);
//                            LinearLayout.LayoutParams  ll_params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//                            ll_append.setLayoutParams(ll_params);
//                            ll_append.setGravity(Gravity.LEFT);
//                            TextView tvMemChatroomMes = new TextView(mContext);
//                            tvMemChatroomMes.setText(text);
//                            (ll_append).addView(tvMemChatroomMes);
//                            llMemChatroomMes.addView(ll_append);
//                        }

                        // 測試方法3
                        if (USER_NAME.equals(userName)) {
                            TextView tvMes = new TextView(mContext);
                            tvMes.setGravity(Gravity.RIGHT);
                            tvMes.setText(text);
                            llMemChatroomMes.addView(tvMes);
                        }else{
                            TextView tvMes = new TextView(mContext);
                            tvMes.setGravity(Gravity.LEFT);
                            tvMes.setText(text);
                            llMemChatroomMes.addView(tvMes);
                        }


                    svMemChatroomMes.fullScroll(View.FOCUS_DOWN);
                    } catch (JSONException e) {
                        Log.e(TAG, e.toString());
                    }
                }
            });
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            String text = String.format(Locale.getDefault(),
                    "code = %d, reason = %s, remote = %b",
                    code, reason, remote);
            Log.d(TAG, "onClose: " + text);
        }

        @Override
        public void onError(Exception ex) {
            Log.d(TAG, "onError: exception = " + ex.toString());
        }

    }




    private void findViews() {
//        tvMemChatroomMes = (TextView) findViewById(R.id.tvMemChatroomMes);
        etMemChatroomMes = (EditText) findViewById(R.id.etMemChatroomMes);
        svMemChatroomMes = (ScrollView) findViewById(R.id.svMemChatroomMes);
        llMemChatroomMes = (LinearLayout) findViewById(R.id.llMemChatroomMes);
    }

    public void onSendMessage(View view) {
        String message = etMemChatroomMes.getText().toString();
        if (message.trim().isEmpty()) {
            showToast(R.string.text_MessageEmpty);
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put(KEY_USER_NAME, USER_NAME);
        map.put(KEY_MESSAGE, message);
        if (myWebSocketClient != null) {
            myWebSocketClient.send(new JSONObject(map).toString());

            // 清除輸入對話的字
            etMemChatroomMes.setText(null);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (myWebSocketClient != null) {
                myWebSocketClient.close();
                showToast(R.string.text_LeftChatRoom);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showToast(int messageId) {
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
    }

    //  返回通訊錄
    public void backMemList(View view) {
        Intent intent = new Intent(this, MemListActivity.class);
        startActivity(intent);
    }
}
