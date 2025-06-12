import React from "react";
import apiClient from "../api/apiClient";
import { GoogleBook } from "../types/GoogleBook";

interface AddBookProps {
  book: GoogleBook;
}

const AddBook: React.FC<AddBookProps> = ({ book }) => {
  const handleAddBook = async () => {
    try {
      await apiClient.post(`/book/add`, book);
      alert("Book added to your library successfully!");
    } catch (error) {
      console.error("Error adding book:", error);
      alert("Failed to add book to your library.");
    }
  };

  return (
    <button className="btn btn-success btn-sm float-end" onClick={handleAddBook}>
      Add to Library
    </button>
  );
};

export default AddBook;