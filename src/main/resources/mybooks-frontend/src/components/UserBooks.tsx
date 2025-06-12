import React, { useEffect, useState } from "react";
import apiClient from "../api/apiClient";

interface Book {
  id: string;
  title: string;
  authors: string;
  coverImageUrl?: string;
  publicationDate?: string;
  isRead?: boolean;
}

const UserBooks: React.FC = () => {
  const [books, setBooks] = useState<Book[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchUserBooks = async () => {
      try {
        const response = await apiClient.get(`/books/user-library`);
        setBooks(response.data);
      } catch (err) {
        setError("Failed to fetch user books.");
        console.error(err);
      } finally {
        setLoading(false);
      }
    };
    fetchUserBooks();
  }, []);

  const handleDeleteBook = async (bookId: string) => {
    try {
      await apiClient.delete(`/book/${bookId}`);
      setBooks((prevBooks) => prevBooks.filter((book) => book.id !== bookId));
      alert("Book removed from your library successfully!");
    } catch (error) {
      console.error("Error deleting book:", error);
      alert("Failed to remove book from your library.");
    }
  };

  const handleMarkAsRead = async (bookId: string) => {
    try {
      await apiClient.patch(`/book/${bookId}/read`);
      setBooks((prevBooks) =>
        prevBooks.map((book) =>
          book.id === bookId ? { ...book, isRead: true } : book
        )
      );
      alert("Book marked as read successfully!");
    } catch (error) {
      console.error("Error marking book as read:", error);
      alert("Failed to mark book as read.");
    }
  };

  if (loading) {
    return <p>Loading...</p>;
  }

  if (error) {
    return <p>{error}</p>;
  }

  if (books.length === 0) {
    return <p>Your library is empty.</p>;
  }

  return (
    <div className="container mt-4">
      <h2>Your Library</h2>
      <ul className="list-group">
        {books.map((book) => (
          <li key={book.id} className="list-group-item d-flex align-items-center">
            {book.coverImageUrl ? (
              <img src={book.coverImageUrl} alt={book.title} className="img-thumbnail me-3 my-thumbnail" />
            ) : (
              <div className="me-3 d-flex align-items-center justify-content-center missing-thumbnail">
                {/* Empty frame */}
              </div>
            )}
            <div className="flex-grow-1">
              <h5 className="mb-1">{book.title}</h5>
              <p className="mb-0">
                {book.authors},&nbsp;
                {book.publicationDate ? book.publicationDate.substring(0, 4) : ""}
              </p>
              {book.isRead && <span className="badge bg-success">Read</span>}
            </div>
            <button className="btn btn-danger btn-sm me-2" onClick={() => handleDeleteBook(book.id)}>
              Delete
            </button>
            {!book.isRead && (
              <button className="btn btn-primary btn-sm" onClick={() => handleMarkAsRead(book.id)}>
                Mark as Read
              </button>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default UserBooks;