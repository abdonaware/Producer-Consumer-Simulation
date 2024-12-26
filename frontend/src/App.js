import React, { useState } from "react";
import SimulatorCanvas from "./components/SimulatorCanvas";
import Toolbar from "./components/Toolbar";

function App() {
  const [elements, setElements] = useState([]);
  const [connections, setConnections] = useState([]);
  const [isRunning, setIsRunning] = useState(false);
  const [productCount, setProductCount] = useState(Number(0));
  const [customers, setCustomers] = useState([]); // Customer positions

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
        customers={customers}
        setCustomers={setCustomers}
      />
      <SimulatorCanvas
        elements={elements}
        setElements={setElements}
        connections={connections}
        setConnections={setConnections}
        isRunning={isRunning}
        customers={customers}
        setCustomers={setCustomers}
      />
    </div>
  );
}

export default App;
