import React, { useState, useEffect, useRef } from "react";
import axios from "axios";

interface Option {
  id: number;
  text: string;
}

interface Question {
  id: number;
  question: string;
  options: Option[];
  correct: number; // The ID of the correct option
}

const Exams: React.FC = () => {
  const [questions, setQuestions] = useState<Question[]>([]);
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [selectedOptions, setSelectedOptions] = useState<{
    [key: number]: number | null;
  }>({});
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string>("");
  const [isPaused, setIsPaused] = useState<boolean>(false);
  const [timeLeft, setTimeLeft] = useState<number>(90 * 60); // 90 minutes in seconds
  const [isSubmitted, setIsSubmitted] = useState<boolean>(false);
  const [score, setScore] = useState<number>(0);

  // @ts-ignore
  const timerRef = useRef<NodeJS.Timeout | null>(null);

  // Fetch questions on component mount
  useEffect(() => {
    const fetchQuestions = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8080/exam/questions",
        );
        setQuestions(response.data);
        setLoading(false);
      } catch (err: any) {
        setError(err.message || "Error fetching questions.");
        setLoading(false);
      }
    };

    fetchQuestions();
  }, []);

  // Timer effect
  useEffect(() => {
    if (!isPaused && timeLeft > 0) {
      timerRef.current = setInterval(() => {
        setTimeLeft((prev) => prev - 1);
      }, 1000);
    } else if (isPaused || timeLeft <= 0) {
      clearInterval(timerRef.current!);
    }

    return () => clearInterval(timerRef.current!);
  }, [isPaused, timeLeft]);

  // Handle option selection
  const handleOptionChange = (optionId: number) => {
    setSelectedOptions((prev) => ({
      ...prev,
      [questions[currentQuestionIndex].id]: optionId, // Update only the current question's selected option
    }));
  };

  // Navigation between questions
  const goToNextQuestion = () => {
    setCurrentQuestionIndex((prev) => prev + 1);
  };

  const goToPreviousQuestion = () => {
    setCurrentQuestionIndex((prev) => prev - 1);
  };

  // Pause/Resume timer
  const togglePause = () => {
    setIsPaused((prev) => !prev);
  };

  // Submit the exam
  const submitExam = () => {
    // Calculate score
    let calculatedScore = 0;
    questions.forEach((question) => {
      if (selectedOptions[question.id] === question.correct) {
        calculatedScore += 1;
      }
    });

    setScore(calculatedScore);
    setIsSubmitted(true);
    clearInterval(timerRef.current!); // Stop the timer
  };

  // Convert timeLeft to MM:SS format
  const formatTime = (seconds: number) => {
    const mins = Math.floor(seconds / 60);
    const secs = seconds % 60;
    return `${mins.toString().padStart(2, "0")}:${secs
      .toString()
      .padStart(2, "0")}`;
  };

  if (loading) {
    return (
      <div className="text-center p-4">
        <p>Loading questions...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="text-center p-4 text-red-500">
        <p>{error}</p>
      </div>
    );
  }

  if (isSubmitted) {
    return (
      <div className="text-center p-4">
        <h1 className="text-xl font-bold text-green-600">Exam Submitted!</h1>
        <p className="mt-2">
          You scored {score} out of {questions.length}.
        </p>
        <p>Thank you for taking the exam.</p>
      </div>
    );
  }

  const currentQuestion = questions[currentQuestionIndex];
  const progress = ((currentQuestionIndex + 1) / questions.length) * 100;

  return (
    <div className="max-w-4xl mx-auto p-4 h-screen">
      {/* Progress Bar */}
      <div className="w-full bg-gray-200 h-2 rounded">
        <div
          className="bg-blue-600 h-2 rounded"
          style={{ width: `${progress}%` }}
        ></div>
      </div>

      {/* Timer and Pause/Resume Button */}
      <div className="flex justify-between items-center mt-4">
        <div className="text-gray-800 font-bold">
          Time Left: {formatTime(timeLeft)}
        </div>
        <button
          className={`px-4 py-2 rounded ${isPaused ? "bg-green-500 text-white" : "bg-red-500 text-white"}`}
          onClick={togglePause}
        >
          {isPaused ? "Resume" : "Pause"}
        </button>
      </div>

      {/* Question Section */}
      <div className="mt-4">
        <h2 className="text-lg font-bold text-gray-800">
          {currentQuestion.question}
        </h2>

        {/* Options */}
        <div className="mt-4">
          {currentQuestion.options.map((option: Option) => (
            <label
              key={option.id}
              className={`block border rounded-md p-2 mb-2 cursor-pointer ${
                selectedOptions[currentQuestion.id] === option.id
                  ? "bg-blue-100 border-blue-600"
                  : "bg-white border-gray-300"
              }`}
            >
              <input
                type="radio"
                name={`question-${currentQuestion.id}`}
                value={option.id}
                checked={selectedOptions[currentQuestion.id] === option.id}
                onChange={() => handleOptionChange(option.id)}
                className="mr-2"
              />
              {option.text}
            </label>
          ))}
        </div>
      </div>

      {/* Navigation Buttons */}
      <div className="flex justify-between mt-4">
        <button
          className="bg-gray-300 px-4 py-2 rounded disabled:opacity-50"
          onClick={goToPreviousQuestion}
          disabled={currentQuestionIndex === 0}
        >
          Previous
        </button>
        <button
          className="bg-blue-600 text-white px-4 py-2 rounded disabled:opacity-50"
          onClick={goToNextQuestion}
          disabled={currentQuestionIndex === questions.length - 1}
        >
          Next
        </button>
        <button
          className="bg-green-600 text-white px-4 py-2 rounded"
          onClick={submitExam}
        >
          Submit
        </button>
      </div>
    </div>
  );
};

export default Exams;
