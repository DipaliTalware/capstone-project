import React, { useState, FormEvent } from "react";
// @ts-ignore
import login from "../assets/signup.jpg"; // Your login image path

const Login: React.FC = () => {
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    console.log("Username:", username);
    console.log("Password:", password);
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      <div className="flex w-full max-w-7xl bg-white shadow-md">
        <div className="w-full md:w-1/2 p-8">
          <div className="max-w-sm mx-auto">
            <h2 className="text-2xl font-bold text-center mb-8 mt-8">Login</h2>
            <form onSubmit={handleSubmit}>
              <div className="mb-8">
                <label
                  htmlFor="username"
                  className="block text-sm font-medium text-gray-700"
                >
                  Username
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
