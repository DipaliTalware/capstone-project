import React, { useState, useEffect } from "react";
import axios from "axios";
import { Question } from "../interface/questionTypes.ts";
import QuestionComponent from "../components/QuestionComponent.tsx";
import Pagination from "../components/Pagination.tsx";

const QUESTIONS_PER_PAGE = 5;

const QuestionAnswerPage: React.FC = () => {
  const [questions, setQuestions] = useState<Question[]>([]);
  const [currentPage, setCurrentPage] = useState<number>(1);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  const [selectedAnswers, setSelectedAnswers] = useState<{
    [key: number]: string[];
  }>({});
  const [answered, setAnswered] = useState<{ [key: number]: boolean }>({});
  const [isCorrect, setIsCorrect] = useState<{ [key: number]: boolean }>({});

  const fetchQuestions = async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await axios.get(
        "http://localhost:8080/questions-answers",
      );
      if (response.status === 200) {
        setQuestions(response.data);
      } else {
        setError(`Error: Unexpected status code ${response.status}`);
      }
    } catch (err: any) {
      setError(`Error fetching data: ${err.message}`);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchQuestions();
  }, []);

  const startIndex = (currentPage - 1) * QUESTIONS_PER_PAGE;
  const endIndex = startIndex + QUESTIONS_PER_PAGE;
  const paginatedQuestions = questions.slice(startIndex, endIndex);

  const nextPage = () => {
    if (endIndex < questions.length) {
      setCurrentPage(currentPage + 1);
    }
  };

  const prevPage = () => {
    if (startIndex > 0) {
      setCurrentPage(currentPage - 1);
    }
  };

  return (
    <div className="flex justify-center py-4">
      {" "}
      <div className="max-w-4xl w-full px-4">
        {" "}
        <h1 className="text-center text-2xl font-bold mb-6">
          AWS Cloud Practitioner Questions
        </h1>
        {loading && <p>Loading...</p>}
        {error && <p className="text-red-500">{error}</p>}
        {paginatedQuestions.map((question, idx) => {
          const questionId = startIndex + idx;
          return (
            <QuestionComponent
              key={questionId}
              question={question}
              questionId={questionId}
              selectedAnswers={selectedAnswers}
              setSelectedAnswers={setSelectedAnswers}
              answered={answered}
              setAnswered={setAnswered}
              isCorrect={isCorrect}
              setIsCorrect={setIsCorrect}
            />
          );
        })}
        <Pagination
          currentPage={currentPage}
          nextPage={nextPage}
          prevPage={prevPage}
          questions={questions}
        />
      </div>
    </div>
  );
};

export default QuestionAnswerPage;
