package com.nicechat.websocket;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicechat.pojo.Message;
import com.nicechat.service.ChatSysService;
import com.nicechat.utils.IDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/websocket/{loginUserId}")
@Component
public class MyWebSocket {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
//    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    //用来记录username和该session进行绑定
    private static final Map<String, Session> map = new HashMap<String, Session>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private static ChatSysService chatSysService;

    private String userId;

    @Autowired
    public void setChatService(ChatSysService chatSysService) {
        MyWebSocket.chatSysService = chatSysService;
    }

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session, @PathParam("loginUserId") String loginUserId) {
        this.session = session;
        this.userId = loginUserId;
        map.put(loginUserId, session);
        webSocketSet.add(this);     //加入set中
//        addOnlineCount();           //在线数加1
        /*try {
            System.out.println("连接成功，当前时间：" + new java.sql.Timestamp(System.currentTimeMillis()));
        } catch (Exception e) {
            System.out.println("异常");
        }*/
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message) {
//        System.out.println("收到消息：" + message);
        ObjectMapper objectMapper = new ObjectMapper();
        Message chatMsg;
        try {
            chatMsg = objectMapper.readValue(message, Message.class);
            chatMsg.setMessageId(IDUtil.genId());
            chatMsg.setSendTime(DateUtil.now());
            chatMsg.setReadFlag("0");
            Session fromSession = map.get(chatMsg.getSenderId());
            Session toSession = map.get(chatMsg.getReceiverId());
            chatSysService.chatWith(chatMsg.getReceiverId(), chatMsg.getSenderId());
            chatSysService.saveMessage(chatMsg);
            //发送给发送者.
            fromSession.getAsyncRemote().sendText(JSONObject.toJSONString(chatMsg));
            if(toSession != null) {
                //发送给接收者.
                toSession.getAsyncRemote().sendText(JSONObject.toJSONString(chatMsg));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        map.remove(userId);
//        subOnlineCount();           //在线数减1
//        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }


    /**
     * 发生错误时调用
     * */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        webSocketSet.remove(this);
        map.remove(userId);
        error.printStackTrace();
    }


    /**
     * 自定义的群发消息，我们可以在其他的类中调用该方法，然后向前台推送消息
     * */
    /*public static void sendInfo(@PathParam("sname") String sname,String message) throws IOException {
        for (MyWebSocket item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sname的，为null则全部推送
                if(sname==null) {
                    item.sendMessage(message);
                    System.out.println("推送消息给:"+item.name+",消息是===》"+message);
                }else if(item.name.equals(sname)){
                    item.sendMessage(message);
                    System.out.println("推送消息给:"+item.name+",消息是===》"+message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }*/

    /*public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        MyWebSocket.onlineCount--;
    }*/
}
