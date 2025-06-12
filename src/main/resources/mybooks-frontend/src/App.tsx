import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate, Link } from "react-router-dom";
import Login from "./components/Login";
import Register from "./components/Register";
import SearchBooks from "./components/SearchBooks";
import UserBooks from "./components/UserBooks";
import ProtectedRoute from "./components/ProtectedRoute";
import LogoutButton from "./components/LogoutButton";

function App() {
  return (
    <Router>
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <div className="container">
          <Link className="navbar-brand" to="/">MyBooks</Link>
          <div className="navbar-collapse w-100">
            <ul className="navbar-nav ml-auto">
              <li className="nav-item">
                <Link className="nav-link" to="/search">Search Books</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/user-books">Your Library</Link>
              </li>
            </ul>
            <div className="ms-auto">
               <LogoutButton />
             </div>
          </div>
        </div>
      </nav>

      <Routes>
        {/* Redirect root path to /login */}
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route
          path="/search"
          element={
            <ProtectedRoute>
              <SearchBooks />
            </ProtectedRoute>
          }
        />
        <Route
          path="/user-books"
          element={
            <ProtectedRoute>
              <UserBooks />
            </ProtectedRoute>
          }
        />
      </Routes>
    </Router>
  );
}

export default App;