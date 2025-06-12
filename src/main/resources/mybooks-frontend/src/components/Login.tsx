import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import apiClient from "../api/apiClient";

const Login: React.FC = () => {
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [errorMessage, setErrorMessage] = useState<string>("");
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("authToken");
    if (token) {
      console.log("Token found, redirecting to search page.");
      navigate("/search");
    }
  }, [navigate]);

  const handleLogin = async (e: React.FormEvent) => {
    console.log("api url:", process.env.REACT_APP_API_BASE_URL);
    e.preventDefault();
    try {
      const response = await apiClient.post("/auth/login", {
        username,
        password,
      });
      const token = response.data;
      localStorage.setItem("authToken", token);
      window.location.reload();
//       navigate("/search");
    } catch (error) {
      console.error("Login failed:", error);
      setErrorMessage("Invalid username or password. Please try again.");
    }
  };

  return (
    <div className="container mt-4">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card">
            <div className="card-body">
              <h5 className="card-title text-center mb-4">Login</h5>
              {errorMessage && (
                <div className="alert alert-danger" role="alert">
                  {errorMessage}
                </div>
              )}
              <form onSubmit={handleLogin}>
                <div className="mb-3">
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                  />
                </div>
                <div className="mb-3">
                  <input
                    type="password"
                    className="form-control"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                  />
                </div>
                <button type="submit" className="btn btn-primary w-100 mb-3">
                  Login
                </button>
              </form>
              <button className="btn btn-secondary w-100" onClick={() => navigate("/register")}>
                Register
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;