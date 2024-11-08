import { useState, useEffect } from "react";
import { Route, Routes, Navigate } from "react-router-dom";
import QuestionAnswerPage from "./pages/QuestionAnswerPage";
import NavBar from "./components/NavBar";
import Signup from "./pages/Signup";
// @ts-ignore
import "./styles/index.css";

function App() {
  const [user, setUser] = useState<any>(null);

  useEffect(() => {
    const loggedUser = localStorage.getItem("user");
    if (loggedUser) {
      setUser(JSON.parse(loggedUser));
    }
  }, []);

  return (
    <>
      <NavBar />
      <Routes>
        <Route
          path="/questions"
          element={user ? <QuestionAnswerPage /> : <Navigate to="/signup" />}
        />
        <Route
          path="/signup"
          element={!user ? <Signup /> : <Navigate to="/questions" />}
        />
        <Route
          path="/"
          element={
            user ? <Navigate to="/questions" /> : <Navigate to="/signup" />
          }
        />
      </Routes>
    </>
  );
}

export default App;
