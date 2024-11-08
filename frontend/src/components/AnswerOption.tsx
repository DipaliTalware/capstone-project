import React from "react";

interface AnswerOptionProps {
  option: string;
  onSelect: () => void;
  isSelected: boolean;
  isCorrect: boolean;
  hasSelected: boolean;
  isIncorrect: boolean;
}

const AnswerOption: React.FC<AnswerOptionProps> = ({
  option,
  onSelect,
  isSelected,
  isCorrect,
  hasSelected,
  isIncorrect,
}) => {
  return (
    <div
      className={`flex items-center space-x-2 cursor-pointer p-2 rounded-md ${
        isSelected
          ? "bg-blue-200"
          : hasSelected
            ? isCorrect
              ? "bg-green-500 text-white"
              : isIncorrect
                ? "bg-red-500 text-white"
                : ""
            : ""
      }`}
      onClick={onSelect}
    >
      <span className={`text-xl ${isSelected ? "text-blue-600" : ""}`}>
        {hasSelected && isCorrect
          ? "✔"
          : hasSelected && isIncorrect
            ? "✘"
            : ""}
      </span>
      <span>{option}</span>
    </div>
  );
};

export default AnswerOption;
