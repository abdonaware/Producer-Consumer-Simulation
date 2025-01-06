import React from "react";
import { Group, Rect, Text, Circle } from "react-konva";

const Queue = ({
  id,
  x,
  y,
  onDrag,
  onClick,
  onDelete,
  queueNumber,
  productCount = 0,
  isRunning,
}) => {
  return (
    <Group
      x={x}
      y={y}
      draggable={!isRunning && id !== 0 && id !== 1000}
      onDragEnd={(e) =>
        !isRunning && onDrag(id, { x: e.target.x(), y: e.target.y() })
      }
    >
      <Rect
        width={100}
        height={60}
        fill="#10b981"
        cornerRadius={8}
        strokeWidth={2}
        stroke="#059669" // Darker green border
        shadowColor="rgba(0,0,0,0.2)"
        shadowBlur={10}
        shadowOffsetX={3}
        shadowOffsetY={3}
      />
      <Text
        text={
          queueNumber === 1000
            ? "End"
            : queueNumber === 0
            ? "Start"
            : `Queue ${queueNumber}`
        }
        fill="white"
        fontSize={16}
        fontFamily="'Inter', sans-serif"
        width={100}
        align="center"
        y={12}
      />
      <Text
        text={`Products: ${productCount}`}
        fill="white"
        fontSize={12}
        fontFamily="'Inter', sans-serif"
        width={100}
        align="center"
        y={32}
      />

      {/* Delete button matching Machine's style */}
      {!isRunning && id !== 0 && id !== 1000 && (
        <Group
          x={80}
          y={0}
          onClick={(e) => {
            e.cancelBubble = true;
            onDelete();
          }}
        >
          <Circle
            radius={10}
            fill="#ef4444"
            shadowColor="rgba(0,0,0,0.2)"
            shadowBlur={2}
          />
          <Text
            text="×"
            fill="white"
            fontSize={16}
            x={-5}
            y={-8}
            fontFamily="Arial"
          />
        </Group>
      )}

      {/* Clickable area for connections */}
      <Rect
        width={100}
        height={60}
        opacity={0}
        onClick={!isRunning ? onClick : undefined}
      />
    </Group>
  );
};

export default Queue;
