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
  const [messages, setMessages] = useState([]);
  const [inputMessage, setInputMessage] = useState("");
  const stompClientRef = useRef(null);

  useEffect(() => {
    const handleRefresh = async () => {
      await StageServices.clearStage();
    };
    handleRefresh();
    // Create a new Stomp client
    const client = new Client({
      webSocketFactory: () => new SockJS("http://localhost:8080/ws"), // Replace with your WebSocket endpoint
      debug: (str) => {
        console.log(str);
      },
      onConnect: () => {
        console.log("Connected to WebSocket");

        // Subscribe to a topic
        client.subscribe("/topic/messages", (messageOutput) => {
          console.log("Received message: ", messageOutput.body);
          const message = JSON.parse(messageOutput.body);
          console.log("Received: ", message);
        });
      },
      onStompError: (frame) => {
        console.error("Broker error: " + frame.headers["message"]);
        console.error("Details: " + frame.body);
      },
    });

    // Assign client to the ref
    stompClientRef.current = client;

    // Activate the connection
    client.activate();

    // Cleanup on component unmount
    return () => {
      if (stompClientRef.current) {
        stompClientRef.current.deactivate();
      }
    };
  }, []);

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
        messages={messages}
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
