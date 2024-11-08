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
  const handleKeyDown = (e: React.KeyboardEvent) => {
    // Trigger the onSelect function on Enter or Space key press
    if (e.key === "Enter" || e.key === " ") {
      onSelect();
    }
  };

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
      onKeyDown={handleKeyDown} // Handle keyboard interactions
      tabIndex={0} // Make the div focusable
      role="button" // Explicitly state this element is a button
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
