import React, { useEffect } from "react";
import { FaPlay, FaPause } from "react-icons/fa";
import { PiQueueBold } from "react-icons/pi";
import { IoSettings } from "react-icons/io5";
import { AiOutlineClear } from "react-icons/ai";
import { BiCartAdd } from "react-icons/bi";
import axios from "axios";

const Toolbar = ({
  elements,
  setElements,
  setConnections,
  isRunning,
  setIsRunning,
  productCount,
  setProductCount,
  sendMessage,
}) => {
  const buttonClass =
    "px-6 py-3 rounded-lg font-medium transition-all duration-200 shadow-md hover:shadow-lg flex items-center gap-2";

  const addMachine =async () => {
    try {
      const response =  await axios.post("http://localhost:8080/addMachine", {
      });
      console.log(response.data);
      setElements([
        ...elements,
        { id: response.data, type: "machine", x: 100, y: 100 },
      ]);
      console.log(elements);
      
    } catch (error) {
      
    }
    
  };

  const addQueue = async() => {
    

    try {
      const response = await axios.post("http://localhost:8080/addQueue", {
      });
      console.log(response.data);
      setElements([
        ...elements,
        {
          id:response.data,
          type: "queue",
          x: 300,
          y: 100,
          queueNumber:  response.data,
          productCount: 0,
        },
      ]);
    } catch (error) {
      
    }
  };

  const clearStage = () => {
    setElements([]);
    setConnections([]);
    setIsRunning(false);
    setElements([
      {
        id: 0,
        type: "queue",
        x: 150, // Left side
        y: window.innerHeight / 2 - 80, // Middle vertically
        queueNumber: 0,
        productCount: productCount,
      },
      {
        id: 1000,
        type: "queue",
        x: 1085, // Left side
        y: window.innerHeight / 2 - 80, // Middle vertically
        queueNumber: 1000,
        productCount: 0,
      },
    ]);
  };

  const handleProductCountChange = (value) => {
    setProductCount(value);
    setElements(
      elements.map((el) =>
        el.type === "queue" && el.queueNumber === 0
          ? { ...el, productCount: value }
          : el
      )
    );
  };
  const handleRunnigChange = () => {
    if (isRunning) {
      sendMessage("Stop Simulation");
    } else {
    setIsRunning(!isRunning);
    sendMessage("Start Simulation");
  }};

  useEffect(() => {
    console.log("xx");
    if (elements.length === 0) {
      setElements([
        {
          id: 0,
          type: "queue",
          x: 150,
          y: window.innerHeight / 2 - 80,
          queueNumber: 0,
          productCount: productCount,
        },
        {
          id: 1000,
          type: "queue",
          x: 1085,
          y: window.innerHeight / 2 - 80,
          queueNumber: 1000,
          productCount: 0,
        },
      ]);
    }
    // eslint-disable-next-line
  }, []);

  return (
    <div className="p-6 bg-white border-b border-gray-200 flex justify-center gap-4 items-center">
      <button
        onClick={addMachine}
        disabled={isRunning}
        className={`${buttonClass} ${
          isRunning
            ? "bg-gray-100 cursor-not-allowed text-gray-400"
            : "bg-blue-500 hover:bg-blue-600 text-white"
        }`}
      >
        <IoSettings size={18} />
        Add Machine
      </button>

      <button
        onClick={addQueue}
        disabled={isRunning}
        className={`${buttonClass} ${
          isRunning
            ? "bg-gray-100 cursor-not-allowed text-gray-400"
            : "bg-emerald-500 hover:bg-emerald-600 text-white"
        }`}
      >
        <PiQueueBold size={20} />
        Add Queue
      </button>

      <div className="h-8 w-px bg-gray-200 mx-2" />

      {/* Combined Run/Pause Toggle Button */}
      <button
        onClick={() =>{handleRunnigChange()}}
        className={`${buttonClass} ${
          isRunning
            ? "bg-amber-500 hover:bg-amber-600 text-white"
            : "bg-indigo-500 hover:bg-indigo-600 text-white"
        }`}
      >
        {isRunning ? <FaPause size={16} /> : <FaPlay size={16} />}
        {isRunning ? "Pause" : "Run"}
      </button>

      <div className="h-8 w-px bg-gray-200 mx-2" />

      <button
        onClick={clearStage}
        disabled={isRunning}
        className={`${buttonClass} ${
          isRunning
            ? "bg-gray-100 cursor-not-allowed text-gray-400"
            : "bg-red-500 hover:bg-red-600 text-white"
        }`}
      >
        <AiOutlineClear size={24} />
        Clear Stage
      </button>
      <div className="flex items-center gap-3 px-4 py-2  bg-white/30 backdrop-blur-sm rounded-xl border border-gray-200 shadow-sm hover:shadow-md transition-all duration-200">
        <label className="text-gray-700 flex items-center gap-2 font-medium">
          <BiCartAdd
            size={24}
            className="text-blue-500 hover:text-blue-600 transition-colors"
          />
          <span className="text-sm uppercase tracking-wide">Products:</span>
        </label>

        <div className="relative">
          <input
            type="number"
            min="0"
            value={productCount}
            onChange={(e) =>
              handleProductCountChange(parseInt(e.target.value) || 0)
            }
            disabled={isRunning}
            className={`
              w-20 px-2
              border-2 rounded-lg 
              text-center text-lg font-semibold
              focus:outline-none focus:border-blue-500
              transition-all duration-200
              ${
                isRunning
                  ? "bg-gray-100 border-gray-200 text-gray-400 cursor-not-allowed"
                  : "bg-white border-gray-200 hover:border-gray-300"
              }
            `}
          />
          {!isRunning && (
            <div className="absolute right-2 inset-y-0 flex items-center pointer-events-none">
              <span className="text-gray-400 text-sm"></span>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Toolbar;
