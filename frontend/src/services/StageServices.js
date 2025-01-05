import api from "./api";
class StageServices {
  static addMachine = async () => {
    try {
      const response = await api.post("http://localhost:8080/addMachine", {});
      console.log(response.data);
      return response.data;
    } catch (error) {
      console.error(error);
    }
  };

  static addQueue = async () => {
    try {
      const response = await api.post("http://localhost:8080/addQueue", {});
      console.log(response.data);
      return response.data;
    } catch (error) {
      console.error(error);
    }
  };

  static addMachineInQueue = async (queueId, machineId) => {
    try {
      const response = await api.put(
        "http://localhost:8080/editMachineInQueue",
        {
          queueId: queueId,
          machineId: machineId,
        }
      );
      console.log(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  static addMachineOutQueue = async (machineId, queueId) => {
    try {
      const response = await api.put(
        "http://localhost:8080/editMachineOutQueue",
        {
          machineId: machineId,
          queueId: queueId,
        }
      );
      console.log(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  static deleteMachine = async (id) => {
    try {
      const response = await api.delete(
        `http://localhost:8080/deleteMachine/${id}`
      );
      console.log(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  static deleteQueue = async (id) => {
    try {
      const response = await api.delete(
        `http://localhost:8080/deleteQueue/${id}`
      );
      console.log(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  static clearStage = async () => {
    try {
      const response = await api.delete("http://localhost:8080/clearStage");
      console.log(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  static restart = async () => {
    try {
      const response = await api.put("http://localhost:8080/restart", {});
      console.log(response.data);
    } catch (error) {
      console.error(error);
    }
  };
}
export default StageServices;
