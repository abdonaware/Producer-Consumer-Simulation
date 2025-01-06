import React from "react";
import { Group, Rect, Text, Circle } from "react-konva";

const Machine = ({ id, x, y, onDrag, onClick, onDelete, isRunning, color }) => {
  return (
    <Group
      x={x}
      y={y}
      draggable={!isRunning}
      onDragEnd={(e) => onDrag(id, { x: e.target.x(), y: e.target.y() })}
    >
      <Rect
        width={120}
        height={60}
        fill={color ? color : "#3b82f6"}
        cornerRadius={8}
        strokeWidth={2}
        stroke="#2563eb" // Darker blue border
        shadowColor="rgba(0,0,0,0.2)"
        shadowBlur={10}
        shadowOffsetX={3}
        shadowOffsetY={3}
      />
      <Text
        text="Machine"
        fill="white"
        fontSize={16}
        fontFamily="'Inter', sans-serif"
        width={120}
        align="center"
        y={20}
      />

      {/* Delete button - hide when running */}
      {!isRunning && (
        <Group
          x={100}
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
        width={120}
        height={60}
        opacity={0}
        onClick={!isRunning ? onClick : undefined}
      />
    </Group>
  );
};

export default Machine;
