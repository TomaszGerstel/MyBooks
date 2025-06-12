import React, { useState, useEffect } from "react";
import apiClient from "../api/apiClient";
import LogoutButton from "./LogoutButton";
import AddBook from "./AddBook";
import { GoogleBook } from "../types/GoogleBook";

interface PaginatedBooks {
  books: GoogleBook[];
  hasNextPage: boolean;
}

const SearchBooks: React.FC = () => {
  const [query, setQuery] = useState<string>("");
  const [books, setBooks] = useState<GoogleBook[]>([]);
  const [page, setPage] = useState<number>(0);
  const [size, setSize] = useState<number>(9);
  const [hasNextPage, setHasNextPage] = useState<boolean>(false);

  const searchBooks = async (pageNo: number = 0) => {
    try {
      const response = await apiClient.get<PaginatedBooks>(`/books/search`, {
        params: { q: query, page: pageNo, size },
      });
      setBooks(response.data.books);
      setHasNextPage(response.data.hasNextPage);
    } catch (error) {
      console.error("Error fetching books:", error);
    }
  };

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    setPage(0);
    searchBooks();
  };

  const handleNextPage = () => {
    const nextPage = page + 1;
    setPage(nextPage);
    searchBooks(nextPage);
  };

  const handlePreviousPage = () => {
    if (page === 0) return;
    const previousPage = page - 1
    setPage(previousPage);
    searchBooks(previousPage);
  };

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between mb-3">
        <h1>Search Books</h1>
      </div>
      <form onSubmit={handleSearch}>
        <div className="mb-3">
          <input
            type="text"
            className="form-control"
            placeholder="Search books..."
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            required
          />
        </div>
        <button type="submit" className="btn btn-primary mb-4">
          Search
        </button>
      </form>
      <div className="row">
        {books.map((book) => (
          <div key={book.id} className="col-md-4 mb-3">
            <div className="card h-100">
              <div className="card-body">
                <h5 className="card-title">{book.title}</h5>
                <p className="card-text">{book.authors}</p>
                {book.coverImageUrl && <img src={book.coverImageUrl} alt={book.title} className="img-fluid" />}
                <AddBook book={book} />
              </div>
            </div>
          </div>
        ))}
      </div>
      <div className="d-flex justify-content-center">
        <button className="btn btn-secondary m-3 w-25" disabled={page === 0} onClick={handlePreviousPage}>
         &lt;&lt; Previous
        </button>
        <button className="btn btn-secondary m-3 w-25" disabled={!hasNextPage} onClick={handleNextPage}>
          Next &gt;&gt;
        </button>
      </div>
    </div>
  );
};

export default SearchBooks;