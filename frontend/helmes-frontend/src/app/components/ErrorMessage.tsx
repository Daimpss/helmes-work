import React from "react";

interface ErrorMessageProps {
  message: string;
}

const ErrorMessage: React.FC<ErrorMessageProps> = ({ message }) => {
  return (
    <div className="alert alert-danger mt-2 mb-2 py-2" role="alert">
      {message}
    </div>
  );
};

export default ErrorMessage;
