import { useState } from "react";

const questions = [
  {
    id: 1,
    question:
      "Which AWS service assists in investigating and swiftly identifying the root cause of security issues?",
    options: [
      { id: 1, text: "AWS Inspector" },
      { id: 2, text: "Amazon Macie" },
      { id: 3, text: "Amazon GuardDuty" },
      { id: 4, text: "Amazon Detective" },
    ],
    correct: 3,
  },
  // Add more questions here
];

const Exams = () => {
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [selectedOption, setSelectedOption] = useState<number | null>(null);

  const handleOptionChange = (optionId: number) => {
    setSelectedOption(optionId);
  };

  const goToNextQuestion = () => {
    setSelectedOption(null); // Reset selection
    setCurrentQuestionIndex((prev) => prev + 1);
  };

  const goToPreviousQuestion = () => {
    setSelectedOption(null); // Reset selection
    setCurrentQuestionIndex((prev) => prev - 1);
  };

  const currentQuestion = questions[currentQuestionIndex];
  const progress = ((currentQuestionIndex + 1) / questions.length) * 100;

  return (
    <div className="max-w-4xl mx-auto p-4">
      {/* Progress Bar */}
      <div className="w-full bg-gray-200 h-2 rounded">
        <div
          className="bg-blue-600 h-2 rounded"
          style={{ width: `${progress}%` }}
        ></div>
      </div>

      {/* Timer */}
      <div className="text-gray-600 text-sm mt-2">
        <span className="font-bold">Question {currentQuestionIndex + 1}</span> /{" "}
        {questions.length}
      </div>

      {/* Question Section */}
      <div className="mt-4">
        <h2 className="text-lg font-bold text-gray-800">
          {currentQuestion.question}
        </h2>

        {/* Options */}
        <div className="mt-4">
          {currentQuestion.options.map((option) => (
            <label
              key={option.id}
              className={`block border rounded-md p-2 mb-2 cursor-pointer ${
                selectedOption === option.id
                  ? "bg-blue-100 border-blue-600"
                  : "bg-white border-gray-300"
              }`}
            >
              <input
                type="radio"
                name={`question-${currentQuestion.id}`}
                value={option.id}
                checked={selectedOption === option.id}
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
      </div>
    </div>
  );
};

export default Exams;
