package v1.cn.unionc_pad.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import io.rong.callkit.RongCallCustomerHandlerListener;
import io.rong.callkit.RongCallKit;
import io.rong.calllib.RongCallCommon;
import io.rong.calllib.RongCallSession;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import v1.cn.unionc_pad.PadTest;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.Rong.Test;
import v1.cn.unionc_pad.UnioncApp;
import v1.cn.unionc_pad.listener.MyConnectionStatusListener;
import v1.cn.unionc_pad.listener.MyReceiveMessageListener;
import v1.cn.unionc_pad.listener.MySendMessageListener;
import v1.cn.unionc_pad.model.GetRongTokenData;
import v1.cn.unionc_pad.network_frame.ConnectHttp;
import v1.cn.unionc_pad.network_frame.UnionAPIPackage;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {
Button button;
Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
//        RongIM.connect(Test.Token1, SealAppContext.getConnectCallback(this));
        connect(Test.Token1);
//        RongIM.connect(Test.Token2,new RongIMClient.ConnectCallback() {
//            @Override
//            public void onTokenIncorrect() {
//                Log.e("linshi", "reToken still Incorrect");
//            }
//
//            @Override
//            public void onSuccess(String s) {
//                Log.e("linshi", "onSuccess");
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode e) {
//                Log.e("linshi", "reToken occur error ErrorCode =" + e);
//            }
//        });
        button=findViewById(R.id.button);
        button2=findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent =new Intent(MainActivity.this,ConversationListActivity.class);
//                startActivity(intent);
                RongCallKit.startSingleCall(MainActivity.this,"15510253516", RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
                RongCallKit.setCustomerHandlerListener(new RongCallCustomerHandlerListener() {
                    @Override
                    public List<String> handleActivityResult(int requestCode, int resultCode, Intent data) {
                        Log.d("linshi","handleActivityResult:"+requestCode+","+resultCode+","+data.toString());
                        return null;
                    }

                    @Override
                    public void addMember(Context context, ArrayList<String> currentMemberIds) {
                        Log.d("linshi","addMember:"+context.toString()+","+currentMemberIds.toString());
                    }

                    @Override
                    public void onRemoteUserInvited(String userId, RongCallCommon.CallMediaType mediaType) {
                        Log.d("linshi","onRemoteUserInvited:"+userId+mediaType.toString());
                    }

                    @Override
                    public void onCallConnected(RongCallSession callSession, SurfaceView localVideo) {
                        Log.d("linshi","onCallConnected:"+callSession.toString()+","+localVideo.toString());
                    }

                    @Override
                    public void onCallDisconnected(RongCallSession callSession, RongCallCommon.CallDisconnectedReason reason) {
                        Log.d("linshi","onCallConnected:"+callSession.toString()+","+reason.toString());
                    }
                });
            }
        });
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 构造 TextMessage 实例
//                TextMessage myTextMessage = TextMessage.obtain("我是消息内容");
//
//                /* 生成 Message 对象。
//                 * "7127" 为目标 Id。根据不同的 conversationType，可能是用户 Id、群组 Id 或聊天室 Id。
//                 * Conversation.ConversationType.PRIVATE 为私聊会话类型，根据需要，也可以传入其它会话类型，如群组。
//                 */
//                Message myMessage = Message.obtain("7127", Conversation.ConversationType.PRIVATE, myTextMessage);
//
///**
// * <p>发送消息。
// * 通过 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}
// * 中的方法回调发送的消息状态及消息体。</p>
// *
// * @param message     将要发送的消息体。
// * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。
// *                    如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
// *                    如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
// * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessage#getPushData()} 方法获取。
// * @param callback    发送消息的回调，参考 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}。
// */
//                RongIM.getInstance().sendMessage(myMessage, null, null, new IRongCallback.ISendMessageCallback() {
//                    @Override
//                    public void onAttached(Message message) {
//                        //消息本地数据库存储成功的回调
//                    }
//
//                    @Override
//                    public void onSuccess(Message message) {
//                        //消息通过网络发送成功的回调
//                    }
//
//                    @Override
//                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
//                        //消息发送失败的回调
//                    }
//                });
//            }
//        });
    }

//    /**
//     * <p>连接服务器，在整个应用程序全局，只需要调用一次，需在 {@link #(Context)} 之后调用。</p>
//     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
//     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
//     *
//     * @param token    从服务端获取的用户身份令牌（Token）。
//     * @return RongIM  客户端核心类的实例。
//     */
//    private void connect(String token) {
//
//        if (getApplicationInfo().packageName.equals(UnioncApp.getCurProcessName(getApplicationContext()))) {
//
//            RongIM.connect(token, new RongIMClient.ConnectCallback() {
//
//                /**
//                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
//                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
//                 */
//                @Override
//                public void onTokenIncorrect() {
//                    Log.e("linshi", "onTokenIncorrect");
//                }
//
//                /**
//                 * 连接融云成功
//                 * @param userid 当前 token 对应的用户 id
//                 */
//                @Override
//                public void onSuccess(String userid) {
//                    Log.d("linshi", "--onSuccess" + userid);
//                }
//
//                /**
//                 * 连接融云失败
//                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
//                 */
//                @Override
//                public void onError(RongIMClient.ErrorCode errorCode) {
//                    Log.e("linshi", "onError"+errorCode);
//
//                }
//            });
//            RongIM.getInstance().setSendMessageListener(new MySendMessageListener());
//            RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());
//            RongIM.setConnectionStatusListener(new MyConnectionStatusListener());
//        }
//    }





}
