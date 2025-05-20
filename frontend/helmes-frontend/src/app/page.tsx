// pages/index.tsx
"use client";
import { useState } from "react";
import SectorSelect from "./components/SectorSelect";
import ErrorMessage from "./components/ErrorMessage";

export default function Home() {
  const [name, setName] = useState("");
  const [selectedSectors, setSelectedSectors] = useState<number[]>([]);
  const [agreed, setAgreed] = useState(false);
  const [validationError, setValidationError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  // Handle form submission
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    // Validate form
    if (name.trim() === "") {
      setValidationError("Please enter your name");
      return;
    }

    if (selectedSectors.length === 0) {
      setValidationError("Please select at least one sector");
      return;
    }

    if (!agreed) {
      setValidationError("You must agree to the terms");
      return;
    }

    // Clear any previous errors
    setValidationError("");

    // Show submitting state
    setIsSubmitting(true);

    try {
      // Here you would make your API call to save the data
      console.log("Submitting:", {
        name,
        sectors: selectedSectors,
        agreed,
      });

      // Simulate API call
      await new Promise((resolve) => setTimeout(resolve, 1000));

      // Success - you could show a success message or redirect
      alert("Form submitted successfully!");

      // Reset form
      setName("");
      setSelectedSectors([]);
      setAgreed(false);
    } catch (error) {
      // Handle submission error
      setValidationError(
        "There was an error submitting the form. Please try again."
      );
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <main className="bg-light min-vh-100 py-5">
      <div className="container">
        <div className="row justify-content-center">
          <div className="col-md-8 col-lg-6">
            <div className="card shadow-sm border-0">
              <div className="card-body p-4">
                <h2 className="card-title text-center text-primary mb-4">
                  Company Sectors
                </h2>

                {validationError && <ErrorMessage message={validationError} />}

                <form onSubmit={handleSubmit}>
                  <p className="card-text text-muted mb-4">
                    Please enter your name and pick the Sectors you are
                    currently involved in.
                  </p>

                  <div className="mb-4">
                    <label htmlFor="name" className="form-label fw-semibold">
                      Name:
                    </label>
                    <input
                      type="text"
                      className="form-control form-control-lg"
                      id="name"
                      value={name}
                      onChange={(e) => {
                        setName(e.target.value);
                        if (validationError) setValidationError("");
                      }}
                      placeholder="Enter your name"
                    />
                  </div>

                  <SectorSelect
                    selectedSectors={selectedSectors}
                    onChange={(sectors) => {
                      setSelectedSectors(sectors);
                      if (validationError) setValidationError("");
                    }}
                  />

                  <div className="form-check mb-4">
                    <input
                      className="form-check-input"
                      type="checkbox"
                      id="agree"
                      checked={agreed}
                      onChange={(e) => {
                        setAgreed(e.target.checked);
                        if (validationError) setValidationError("");
                      }}
                    />
                    <label className="form-check-label" htmlFor="agree">
                      Agree to terms
                    </label>
                  </div>

                  <div className="d-grid gap-2">
                    <button
                      type="submit"
                      className="btn btn-primary btn-lg"
                      disabled={isSubmitting}
                    >
                      {isSubmitting ? (
                        <>
                          <span
                            className="spinner-border spinner-border-sm me-2"
                            role="status"
                            aria-hidden="true"
                          ></span>
                          Saving...
                        </>
                      ) : (
                        "Save"
                      )}
                    </button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  );
}
