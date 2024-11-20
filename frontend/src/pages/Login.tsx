import React, { useState, FormEvent } from "react";
import axios from "axios";

// @ts-ignore
import login from "../assets/login.png";

const Login: React.FC = () => {
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [error, setError] = useState<string>("");

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();

    const loginData = {
      email: username, // Assuming you are using email as the username
      password: password,
    };

    try {
      const response = await axios.post(
        "http://localhost:8080/auth/login",
        loginData,
        {
          headers: {
            "Content-Type": "application/json",
          },
        },
      );

      const token = response.data.accessToken; // Assuming the backend returns token in the `accessToken` field

      console.log("Login successful, token saved:", token);

      // Store the token in localStorage for future requests
      localStorage.setItem("jwtToken", token);

      // Optional: Redirect to a protected route/dashboard after successful login
      window.location.href = "/"; // Example of redirecting to the dashboard
    } catch (error) {
      if (axios.isAxiosError(error) && error.response) {
        // Handle API-specific error (e.g., invalid credentials)
        setError(error.response.data.message || "Invalid credentials");
      } else {
        // Handle other errors (network error, etc.)
        setError("An error occurred. Please try again.");
      }
      console.log("Login failed:", error);
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      <div className="flex w-full max-w-7xl bg-white shadow-md">
        <div className="w-full md:w-1/2 p-8">
          <div className="max-w-sm mx-auto">
            <h2 className="text-2xl font-bold text-center mb-8 mt-8">Login</h2>
            {error && (
              <div className="text-red-500 text-center mb-4">{error}</div>
            )}
            <form onSubmit={handleSubmit}>
              <div className="mb-8">
                <label
                  htmlFor="username"
                  className="block text-sm font-medium text-gray-700"
                >
                  Username (Email)
                </label>
                <input
                  type="text"
                  id="username"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                  className="mt-1 block w-full px-4 py-2 text-sm text-gray-900 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500"
                  placeholder="Enter your username"
                />
              </div>

              <div className="mb-6">
                <label
                  htmlFor="password"
                  className="block text-sm font-medium text-gray-700"
                >
                  Password
                </label>
                <input
                  type="password"
                  id="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  className="mt-1 block w-full px-4 py-2 text-sm text-gray-900 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500"
                  placeholder="Enter your password"
                />
              </div>

              <div className="mt-4">
                <button
                  type="submit"
                  className="w-full py-2 px-4 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                  Log In
                </button>
              </div>
            </form>
          </div>
        </div>

        {/* Right side: Login image */}
        <div className="hidden md:block w-1/2">
          <img alt="Login" src={login} className="w-full h-full object-cover" />
        </div>
      </div>
    </div>
  );
};

export default Login;
