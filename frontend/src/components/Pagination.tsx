import React from "react";

interface PaginationProps {
  currentPage: number;
  nextPage: () => void;
  prevPage: () => void;
  questions: any[];
}

const Pagination: React.FC<PaginationProps> = ({
  currentPage,
  nextPage,
  prevPage,
  questions,
}) => {
  return (
    <div className="flex justify-between mt-6">
      <button
        onClick={prevPage}
        className="px-4 py-2 bg-gray-500 text-white rounded-md"
        disabled={currentPage === 1}
      >
        Previous
      </button>
      <button
        onClick={nextPage}
        className="px-4 py-2 bg-gray-500 text-white rounded-md"
        disabled={currentPage * 5 >= questions.length}
      >
        Next
      </button>
    </div>
  );
};

export default Pagination;
