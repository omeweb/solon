package org.noear.solon.boot.websocket;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@SuppressWarnings("unchecked")
public class WsServer extends WebSocketServer {
    private WsContextHandler _contextHandler;
    private Charset def_charset = StandardCharsets.UTF_8;

    public WsServer(int port, WsContextHandler contextHandler) throws UnknownHostException {
        super(new InetSocketAddress(port));
        _contextHandler = contextHandler;
    }

    @Override
    public void onStart() {
        System.out.println("Solon.Server:Websocket onStart...");
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake shake) {
        System.out.println("Solon.Server:Websocket onOpen=" + shake.getResourceDescriptor());
    }

    @Override
    public void onClose(WebSocket conn, int i, String s, boolean b) {
        System.out.println("Solon.Server:Websocket onClose...");
    }

    @Override
    public void onMessage(WebSocket conn, String data) {
        try {
            _contextHandler.handle(conn, data.getBytes(def_charset), true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer data) {
        try {
            _contextHandler.handle(conn, data.array(), false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println("Solon.Server:Websocket onError:");
        ex.printStackTrace();
    }

}
