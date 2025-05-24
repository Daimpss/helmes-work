"use client";
import { useEffect, useState } from "react";
import SectorSelect from "./components/SectorSelect";
import ErrorMessage from "./components/ErrorMessage";
import PersonService from "@/services/PersonService";
import { IPersonFormDTO } from "@/domain/IPersonFormDTO";

export default function Home() {
  const [name, setName] = useState("");
  const [selectedSectors, setSelectedSectors] = useState<number[]>([]);
  const [agreed, setAgreed] = useState(false);
  const [validationError, setValidationError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [isLoadingPerson, setIsLoadingPerson] = useState(true);
  const [successMessage, setSuccessMessage] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

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

    setValidationError("");

    setIsSubmitting(true);

    try {
      console.log("Submitting:", {
        name,
        sectors: selectedSectors,
        agreed,
      });

      const personData: IPersonFormDTO = {
        name: name.trim(),
        sectorIds: selectedSectors,
        agreeTerms: agreed,
      };

      await PersonService.savePerson(personData);
      setSuccessMessage("Form submitted successfully!");
    } catch (error) {
      setValidationError(
        "There was an error submitting the form. Please try again."
      );
    } finally {
      setIsSubmitting(false);
      loadCurrentPerson();
    }
  };

  const loadCurrentPerson = async () => {
    try {
      const result = await PersonService.getCurrentPerson();

      if (result.data) {
        setName(result.data!.name || "");
        const sectorIds = result.data.sectors?.map((sector) => sector.id) || [];
        setSelectedSectors(sectorIds);

        setAgreed(result.data.agreeTerms || false);
        console.log("Existing user data loaded:", result.data);
      } else {
        console.log("No existing session found");
      }
    } catch (error) {
      console.error("Error loading current person:", error);
    } finally {
      setIsLoadingPerson(false);
    }
  };

  const handleClearSession = async () => {
    try {
      const result = await PersonService.clearSession();

      if (result.data !== undefined) {
        setName("");
        setSelectedSectors([]);
        setAgreed(false);
        setValidationError("");
        setSuccessMessage(
          "Session cleared! You can now enter new information."
        );
      } else if (result.errors) {
        setValidationError(result.errors[0] || "Failed to clear session");
      }
    } catch (error) {
      setValidationError("There was an error clearing the session.");
    }
  };

  useEffect(() => {
    loadCurrentPerson();
  }, []);

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

                {successMessage && (
                  <div
                    className="alert alert-success alert-dismissible fade show"
                    role="alert"
                  >
                    <strong>Success!</strong> {successMessage}
                    <button
                      type="button"
                      className="btn-close"
                      onClick={() => setSuccessMessage("")}
                      aria-label="Close"
                    ></button>
                  </div>
                )}

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
                    {name && (
                      <button
                        type="button"
                        className="btn btn-outline-secondary"
                        onClick={handleClearSession}
                      >
                        Start new session
                      </button>
                    )}
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
