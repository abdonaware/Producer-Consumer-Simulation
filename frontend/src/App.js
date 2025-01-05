import React, { useState, useEffect, useRef } from "react";
import SimulatorCanvas from "./components/SimulatorCanvas";
import Toolbar from "./components/Toolbar";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import "./App.css";
import StageServices from "./services/StageServices";

function App() {
  const [elements, setElements] = useState([]);
  const [connections, setConnections] = useState([]);
  const [isRunning, setIsRunning] = useState(false);
  const [productCount, setProductCount] = useState(Number(0));
  const [message, setMessage] = useState(null);
  const messageQueueRef = useRef([]);
  const [inputMessage, setInputMessage] = useState("");
  const stompClientRef = useRef(null);

  useEffect(() => {
    const handleRefresh = async () => {
      await StageServices.clearStage();
    };
    handleRefresh();

    const client = new Client({
      webSocketFactory: () => new SockJS("http://localhost:8080/ws"),
      debug: (str) => {
        console.log(str);
      },
      onConnect: () => {
        console.log("Connected to WebSocket");

        client.subscribe("/topic/messages", (messageOutput) => {
          console.log("Received message: ", messageOutput.body);
          const parsedMessage = JSON.parse(messageOutput.body);

          // Add the message to the queue
          messageQueueRef.current.push(parsedMessage);

          // Trigger state update to process the queue
          setMessage(parsedMessage); // This triggers the useEffect
        });
      },
      onStompError: (frame) => {
        console.error("Broker error: " + frame.headers["message"]);
        console.error("Details: " + frame.body);
      },
    });

    stompClientRef.current = client;
    client.activate();

    return () => {
      if (stompClientRef.current) {
        stompClientRef.current.deactivate();
      }
    };
  }, []);

  useEffect(() => {
    const processMessages = () => {
      while (messageQueueRef.current.length > 0) {
        const currentMessage = messageQueueRef.current.shift(); // Get the next message in the queue
        console.log("Processing message: ", currentMessage);
        console.log(elements);

        if (currentMessage && currentMessage.type === "queue") {
          setElements((prevElements) =>
            prevElements.map((el) =>
              el.id == currentMessage.id
                ? { ...el, productCount: currentMessage.pendingProduct }
                : el
            )
          );
        }
      }
    };

    processMessages();
  }, [message]);

  const sendMessage = (message) => {
    console.log("sendMessage", message);
    const client = stompClientRef.current;
    if (client && client.connected && message.message.trim() !== "") {
      console.log("Sending message: " + message);
      client.publish({
        destination: "/app/message", // Match this with your backend MessageMapping
        body: JSON.stringify(message), // Convert the message object to JSON message,
      });
      setInputMessage("");
    } else {
      console.error(
        "Unable to send message: STOMP client not connected or message is empty"
      );
    }
  };
  return (
    <div className="flex flex-col h-screen">
      <Toolbar
        elements={elements}
        setElements={setElements}
        connections={connections}
        setConnections={setConnections}
        isRunning={isRunning}
        setIsRunning={setIsRunning}
        productCount={productCount}
        setProductCount={setProductCount}
        sendMessage={sendMessage}
      />
      <SimulatorCanvas
        elements={elements}
        setElements={setElements}
        connections={connections}
        setConnections={setConnections}
        isRunning={isRunning}
      />
    </div>
  );
}

export default App;
