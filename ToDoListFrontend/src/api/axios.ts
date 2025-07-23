import axios from "axios";

const axiosInstance = axios.create({
  baseURL: `${import.meta.env.VITE_SERVER_URL}:${import.meta.env.VITE_SERVER_PORT}`,
});

export default axiosInstance;
