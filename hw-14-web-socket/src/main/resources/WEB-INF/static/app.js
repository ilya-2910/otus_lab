let stompClient = null;
let connected = null;

const connectAndSend = (sendFunc) => {
  if (connected) {
    if (sendFunc) sendFunc();
  } else {
    socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, (frame) => {
      console.log('Connected: ' + frame);
      connected = true;
      if (sendFunc) sendFunc();
    });

    socket.onclose = function () {
      console.log('close');
      stompClient.disconnect();
      connected = false;
    };
  }

}

