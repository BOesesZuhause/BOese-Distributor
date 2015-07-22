import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/events/")
public class BoeseSocket {
	
	@Inject
	SocketHandler handler; //TODO ist immer null, wird nicht injected

	@OnOpen
	public void open(Session session) {
		handler.addSession(session);
	}

	@OnClose
	public void close(Session session) {
		handler.removeSession(session);
	}

	@OnError
	public void onError(Throwable error) {
	}

	@OnMessage
	public void handleMessage(String message, Session session) {
		
	}
}
