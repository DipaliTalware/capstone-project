import React, { useState, useEffect } from "react";

interface AnswerOptionProps {
  option: string;
  onSelect: (isSelected: boolean) => void;
  isCorrect: boolean;
  maxSelections: number;
  isSelected: boolean;
}

const AnswerOption: React.FC<AnswerOptionProps> = ({
  option,
  onSelect,
  isCorrect,
  isSelected: initialIsSelected,
}) => {
  const [isSelected, setIsSelected] = useState(initialIsSelected);

  useEffect(() => {
    setIsSelected(initialIsSelected);
  }, [initialIsSelected]);

  const handleClick = () => {
    const newIsSelected = !isSelected;
    setIsSelected(newIsSelected);
    onSelect(newIsSelected);
  };

  return (
    <div
      className={`flex items-center space-x-2 cursor-pointer p-2 rounded-md ${
        isSelected
          ? isCorrect
            ? "bg-green-500 text-white"
            : "bg-blue-200"
          : ""
      }`}
      onClick={handleClick}
      onKeyDown={(e) => {
        if (e.key === "Enter" || e.key === " ") {
          handleClick();
        }
      }}
      tabIndex={0}
      role="button"
    >
      <span className={`text-xl ${isSelected ? "text-blue-600" : ""}`}>
        {isCorrect && isSelected ? "✔" : ""}
      </span>
      <span>{option}</span>
    </div>
  );
};

export default AnswerOption;
