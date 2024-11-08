import React from "react";
import { Question } from "../interface/questionTypes.ts";
import AnswerOption from "./AnswerOption";

interface QuestionComponentProps {
  question: Question;
  questionId: number;
  selectedAnswers: { [key: number]: string[] };
  setSelectedAnswers: React.Dispatch<
    React.SetStateAction<{ [key: number]: string[] }>
  >;
  answered: { [key: number]: boolean };
  setAnswered: React.Dispatch<React.SetStateAction<{ [key: number]: boolean }>>;
  isCorrect: { [key: number]: boolean };
  setIsCorrect: React.Dispatch<
    React.SetStateAction<{ [key: number]: boolean }>
  >;
}

const QuestionComponent: React.FC<QuestionComponentProps> = ({
  question,
  questionId,
  selectedAnswers,
  setSelectedAnswers,
  answered,
  setAnswered,
  isCorrect,
  setIsCorrect,
}) => {
  const handleSelectAnswer = (option: string) => {
    setSelectedAnswers((prev) => {
      const newSelection = prev[questionId] || [];
      if (newSelection.includes(option)) {
        return {
          ...prev,
          [questionId]: newSelection.filter((item) => item !== option),
        };
      } else {
        return { ...prev, [questionId]: [...newSelection, option] };
      }
    });
  };

  const handleSubmit = () => {
    const correctAnswers = question.correctAnswer.split(",");
    const selected = selectedAnswers[questionId] || [];

    // Check if the selected answers match the correct answers
    const isCorrectSelection =
      selected.length === correctAnswers.length &&
      selected.every((answer) => correctAnswers.includes(answer));

    setAnswered((prev) => ({ ...prev, [questionId]: true }));
    setIsCorrect((prev) => ({ ...prev, [questionId]: isCorrectSelection }));
  };

  return (
    <div className="p-4 bg-white rounded-lg shadow-md">
      <h3 className="text-xl font-semibold mb-4">{question.text}</h3>
      <div className="space-y-3">
        {question.options.map((option, index) => {
          const isSelected = selectedAnswers[questionId]?.includes(option);
          const isCorrectAnswer = question.correctAnswer
            .split(",")
            .includes(option);
          const hasSelected = answered[questionId] && isSelected;
          const isAnswerIncorrect =
            answered[questionId] && !isSelected && !isCorrectAnswer;

          return (
            <AnswerOption
              key={index}
              option={option}
              onSelect={() => handleSelectAnswer(option)}
              isSelected={isSelected}
              isCorrect={isCorrectAnswer}
              hasSelected={hasSelected}
              isIncorrect={isAnswerIncorrect}
            />
          );
        })}
      </div>
      {!answered[questionId] && (
        <button
          onClick={handleSubmit}
          className="mt-4 px-4 py-2 bg-blue-500 text-white rounded-md"
          disabled={selectedAnswers[questionId]?.length === 0}
        >
          Submit
        </button>
      )}
      {answered[questionId] && (
        <div
          className={`mt-4 text-lg font-semibold ${isCorrect[questionId] ? "text-green-600" : "text-red-600"}`}
        >
          {isCorrect[questionId]
            ? "Correct Answer!"
            : `The correct answer 
            : ${question.correctAnswer}`}
        </div>
      )}
    </div>
  );
};

export default QuestionComponent;
