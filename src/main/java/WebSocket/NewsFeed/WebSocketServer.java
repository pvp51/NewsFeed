package WebSocket.NewsFeed;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/news")
public class WebSocketServer {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	@OnOpen
	public void onOpen(Session session) {
		logger.info("Connected" + session.getId());
		publishToClient(session);
	}

	@OnMessage
	public String onMessage(String message, Session session) {
		switch (message) {
		case "exit":
			try {
				session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE, "Live News ended"));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			break;
		}
		return message;
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info(String.format("Session %s closed with reason %s", session.getId(), closeReason));
	}

	/**
	 * This method could be use to publish message to client
	 * @param session
	 */
	private void publishToClient(Session session) {
		try {
			session.getBasicRemote().sendText("Local time: " + LocalTime.now().format(timeFormatter));
			logger.info("Published..." + session.getId());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
