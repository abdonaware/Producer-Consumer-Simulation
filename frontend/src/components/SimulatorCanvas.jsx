import React, { useState, useEffect } from "react";
import { Stage, Layer, Arrow } from "react-konva";
import Machine from "./Machine";
import Queue from "./Queue";
import StageServices from "../services/StageServices";

const SimulatorCanvas = ({
  elements,
  setElements,
  connections,
  setConnections,
  isRunning,
  setIsRunning,
  error,
  setError,
}) => {
  const [tempConnection, setTempConnection] = useState(null); // Temporary connection
  const [showError, setShowError] = useState(false);

  useEffect(() => {
    if (error) {
      setShowError(true);
      const timer = setTimeout(() => {
        setShowError(false);
        setError(false); // Reset error state after hiding the modal
        setIsRunning(false);
      }, 1500);
      return () => clearTimeout(timer); // Cleanup timer on unmount
    }
  }, [error, setError, setIsRunning]);

  // Handle dragging elements
  const handleDrag = (id, newPos) => {
    setElements(
      elements.map((el) =>
        el.id === id ? { ...el, x: newPos.x, y: newPos.y } : el
      )
    );
  };

  // Add delete handler
  const handleDelete = async (id, type) => {
    if (type === "machine") {
      await StageServices.deleteMachine(id);
    } else if (type === "queue") {
      await StageServices.deleteQueue(id);
    }
    setElements(elements.filter((el) => el.id !== id));
    setConnections(
      connections.filter((conn) => conn.from !== id && conn.to !== id)
    );
  };

  // Start a connection when clicking on a source element
  const startConnection = (id, type) =>
    setTempConnection({ from: id, type: type });

  // Complete a connection when clicking on a target element
  const completeConnection = async (id, type) => {
    console.log("completeConnection", id);
    console.log("tempConnection", tempConnection);
    if (
      connections.some(
        (conn) =>
          (conn.from === tempConnection.from && conn.to === id) ||
          (conn.from === id && conn.to === tempConnection.from)
      )
    ) {
      console.log("Connection already exists");
      setTempConnection(null);
      return;
    }
    if (tempConnection && tempConnection.from !== id) {
      if (tempConnection.type === "queue" && type === "machine") {
        await StageServices.addMachineInQueue(tempConnection.from, id);
        setConnections([...connections, { from: tempConnection.from, to: id }]);
        setTempConnection(null);
      } else if (tempConnection.type === "machine" && type === "queue") {
        await StageServices.addMachineOutQueue(tempConnection.from, id);
        setConnections([...connections, { from: tempConnection.from, to: id }]);
        setTempConnection(null);
      } else {
        setTempConnection(null);
      }
    }
    console.log("Coneection", connections);
  };

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
    <div className="flex-1 bg-gradient-to-br from-gray-50 to-gray-100 relative">
      {/* Error Modal */}
      {showError && (
        <div className="absolute z-10  top-0 left-0 w-full h-full flex justify-center items-center bg-black bg-opacity-50">
          <div className="bg-white rounded-md p-6 shadow-lg text-center z-100">
            <h2 className="text-lg font-bold text-red-600">Connection Error</h2>
            <p className="text-md text-gray-600">
              Please check that your connections are valid.
            </p>
          </div>
        </div>
      )}
      <Stage
        width={window.innerWidth}
        height={window.innerHeight - 80}
        className="shadow-lg z-10"
      >
        <Layer>
          {/* Render elements first as background */}
          {elements.map((el) =>
            el.type === "machine" ? (
              <Machine
                key={el.id}
                {...el}
                onDrag={handleDrag}
                onDelete={() => handleDelete(el.id, "machine")}
                onClick={() =>
                  tempConnection
                    ? completeConnection(el.id, "machine")
                    : startConnection(el.id, "machine")
                }
                isRunning={isRunning}
              />
            ) : (
              <Queue
                key={el.id}
                {...el}
                onDrag={handleDrag}
                onDelete={() => handleDelete(el.id, "queue")}
                onClick={() =>
                  tempConnection
                    ? completeConnection(el.id, "queue")
                    : startConnection(el.id, "queue")
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
        </Layer>
      </Stage>
    </div>
  );
};

export default SimulatorCanvas;
