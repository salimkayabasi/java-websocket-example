package com.ikimuhendis.server;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.logging.Logger;

@ServerEndpoint(value = "/server")
public class WSServer {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @OnOpen
    public void onOpen(Session session) {
        logger.info("Connected ... " + session.getId());
    }

    @OnMessage
    public void onMessage(Session session, String msg) {



        try {
            for (Session sess : session.getOpenSessions()) {
                if (sess.isOpen())
                    sess.getBasicRemote().sendText("Message from server" + msg + " " + sess.getId());
            }
        } catch (IOException e) {
            //throw new RuntimeException(e);
            logger.info(e.getMessage());
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
    }

    @OnError
    public void error(Session session, Throwable error) {
        logger.info("ERROR");
        logger.info("SessionId " + session.getId());
        logger.info("ErrorMsg " + error.getMessage());
    }
}