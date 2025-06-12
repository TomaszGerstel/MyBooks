import React from 'react';
import { render, screen } from '@testing-library/react';
import App from './App';

test('renders Search Books page', () => {
  render(<App />);
  const headingElement = screen.getByText(/Search Books/i);
  expect(headingElement).toBeInTheDocument();
});
