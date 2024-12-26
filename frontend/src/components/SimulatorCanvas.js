import React, { useState } from "react";
import { Stage, Layer, Arrow } from "react-konva";
import Machine from "./Machine";
import Queue from "./Queue";

const SimulatorCanvas = ({
  elements,
  setElements,
  connections,
  setConnections,
  isRunning,
}) => {
  const [tempConnection, setTempConnection] = useState(null); // Temporary connection

  // Handle dragging elements
  const handleDrag = (id, newPos) => {
    setElements(
      elements.map((el) =>
        el.id === id ? { ...el, x: newPos.x, y: newPos.y } : el
      )
    );
  };

  // Add delete handler
  const handleDelete = (id) => {
    setElements(elements.filter((el) => el.id !== id));
    setConnections(
      connections.filter((conn) => conn.from !== id && conn.to !== id)
    );
  };

  // Start a connection when clicking on a source element
  const startConnection = (id) => setTempConnection({ from: id });

  // Complete a connection when clicking on a target element
  const completeConnection = (id) => {
    if (tempConnection && tempConnection.from !== id) {
      setConnections([...connections, { from: tempConnection.from, to: id }]);
      setTempConnection(null);
    }
  };

  // Simulate customer movement along connections
  //   useEffect(() => {
  //     if (isRunning) {
  //       const interval = setInterval(() => {
  //         setCustomers((prev) =>
  //           prev.map((cust) => {
  //             const connection = connections.find((c) => c.from === cust.current);
  //             if (connection) {
  //               const toElement = elements.find((el) => el.id === connection.to);
  //               const dx = toElement.x + 40 - cust.x; // Adjust for element position
  //               const dy = toElement.y + 25 - cust.y;
  //               const dist = Math.sqrt(dx * dx + dy * dy);

  //               // Move customer closer to the target
  //               if (dist < 5) {
  //                 return { ...cust, current: connection.to }; // Move to the next element
  //               }

  //               return {
  //                 ...cust,
  //                 x: cust.x + (dx / dist) * 2,
  //                 y: cust.y + (dy / dist) * 2,
  //               };
  //             }
  //             return cust; // Stay at the current position
  //           })
  //         );
  //       }, 50);
  //       return () => clearInterval(interval);
  //     }
  //   }, [isRunning,setCustomers, connections, elements]);

  //   // Add a customer to the first machine for the simulation
  //   useEffect(() => {
  //     if (isRunning && customers.length === 0 && elements.length > 0) {
  //       setCustomers([
  //         {
  //           x: elements[0].x + 40,
  //           y: elements[0].y + 25,
  //           current: elements[0].id,
  //         },
  //       ]);
  //     }
  //   }, [isRunning,setCustomers, customers, elements]);

  // Add helper function for arrow points
  const calculateArrowPoints = (from, to) => {
    const fromCenterX = from.x + 60;
    const fromCenterY = from.y + 30;
    const toCenterX = to.x + 60;
    const toCenterY = to.y + 30;

    // Calculate angle between centers
    const dx = toCenterX - fromCenterX;
    const dy = toCenterY - fromCenterY;
    const angle = Math.atan2(dy, dx);

    // Calculate start point (from border)
    const fromX = fromCenterX + Math.cos(angle) * 60;
    const fromY = fromCenterY + Math.sin(angle) * 30;

    // Calculate end point (to border)
    const toX = toCenterX - Math.cos(angle) * 60;
    const toY = toCenterY - Math.sin(angle) * 30;

    return [fromX, fromY, toX, toY];
  };

  return (
    <div className="flex-1 bg-gradient-to-br from-gray-50 to-gray-100">
      <Stage
        width={window.innerWidth}
        height={window.innerHeight - 80}
        className="shadow-lg"
      >
        <Layer>
          {/* Render elements first as background */}
          {elements.map((el) =>
            el.type === "machine" ? (
              <Machine
                key={el.id}
                {...el}
                onDrag={handleDrag}
                onDelete={() => handleDelete(el.id)}
                onClick={() =>
                  tempConnection
                    ? completeConnection(el.id)
                    : startConnection(el.id)
                }
                isRunning={isRunning}
              />
            ) : (
              <Queue
                key={el.id}
                {...el}
                onDrag={handleDrag}
                onDelete={() => handleDelete(el.id)}
                onClick={() =>
                  tempConnection
                    ? completeConnection(el.id)
                    : startConnection(el.id)
                }
                queueNumber={el.queueNumber}
                productCount={el.productCount}
                isRunning={isRunning}
              />
            )
          )}

          {/* Render arrows on top */}
          {connections.map((conn, index) => {
            const from = elements.find((el) => el.id === conn.from);
            const to = elements.find((el) => el.id === conn.to);
            if (!from || !to) return null;

            const points = calculateArrowPoints(from, to);

            return (
              <Arrow
                key={index}
                points={points}
                stroke="#000000"
                strokeWidth={2}
                fill="#000000"
                pointerWidth={10}
                pointerLength={10}
                tension={0.3}
                shadowColor="rgba(0,0,0,0.1)"
                shadowBlur={4}
                shadowOffsetX={1}
                shadowOffsetY={1}
              />
            );
          })}

          {/* Customers last */}
          {/* {customers.map((cust, index) => (
            <Circle
              key={index}
              x={cust.x}
              y={cust.y}
              radius={8}
              fill="#ef4444"
              shadowColor="rgba(0,0,0,0.2)"
              shadowBlur={4}
              shadowOffsetX={2}
              shadowOffsetY={2}
            />
          ))} */}
        </Layer>
      </Stage>
    </div>
  );
};

export default SimulatorCanvas;
