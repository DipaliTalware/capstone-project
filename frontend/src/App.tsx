import NavBar from "./components/NavBar.tsx";
import Login from "./pages/Login.tsx";
import QuestionAnswerPage from "./pages/QuestionAnswerPage.tsx";
import HomePage from "./pages/HomePage.tsx";
// @ts-ignore
import "./styles/index.css";
import { Routes, Route } from "react-router-dom";
import Footer from "./components/Footer.tsx";

const App = () => {
  return (
    <>
      <NavBar />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/QuestionAnswerPage" element={<QuestionAnswerPage />} />
        <Route path="/Login" element={<Login />} />
      </Routes>
      <Footer />
    </>
  );
};

export default App;
