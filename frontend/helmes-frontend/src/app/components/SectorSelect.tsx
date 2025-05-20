import { useEffect, useState } from "react";
import SectorService from "@/services/SectorService";
import { ISectorDTO } from "@/domain/ISectorDTO";

interface SectorSelectProps {
  selectedSectors: number[];
  onChange: (selectedIds: number[]) => void;
}

export const SectorSelect: React.FC<SectorSelectProps> = ({
  selectedSectors,
  onChange,
}) => {
  const [isLoading, setIsLoading] = useState(true);
  const [flatSectors, setFlatSectors] = useState<ISectorDTO[]>([]);

  useEffect(() => {
    loadSectors();
  }, []);

  const loadSectors = async () => {
    try {
      const response = await SectorService.getAllSectors();
      if (response.data) {
        const flattened = flattenSectors(response.data);
        setFlatSectors(flattened);
      }
    } catch (error) {
      console.error("Failed to load sectors:", error);
    } finally {
      setIsLoading(false);
    }
  };

  const flattenSectors = (
    sectorsList: ISectorDTO[],
    level = 0
  ): ISectorDTO[] => {
    var result: ISectorDTO[] = [];

    sectorsList.forEach((sector) => {
      result.push({ ...sector, level });

      if (sector.children && sector.children.length > 0) {
        const flattenedChildren = flattenSectors(sector.children, level + 1);
        result = [...result, ...flattenedChildren];
      }
    });

    return result;
  };

  // Toggle sector selection
  const toggleSector = (sectorId: number) => {
    const newSelection = [...selectedSectors];
    const index = newSelection.indexOf(sectorId);

    if (index === -1) {
      newSelection.push(sectorId);
    } else {
      newSelection.splice(index, 1);
    }

    onChange(newSelection);
  };

  // Generate indentation based on level
  const getIndentation = (level: number) => {
    if (level === 0) return "";
    if (level === 1) return "• ";
    if (level === 2) return "   ◦ ";
    return "      ▪ ";
  };

  if (isLoading) {
    return (
      <div className="spinner-container">
        <div className="spinner-border text-primary" role="status">
          <span className="visually-hidden">Loading sectors...</span>
        </div>
      </div>
    );
  }

  return (
    <div className="sector-container">
      <label className="form-label fw-semibold">Sectors:</label>
      <div className="sector-list">
        {flatSectors.map((sector) => (
          <div
            key={sector.id}
            className={`
              sector-item 
              ${selectedSectors.includes(sector.id) ? "selected" : ""} 
              sector-level-${sector.level}
              indent-${sector.level}
            `}
            onClick={() => toggleSector(sector.id)}
          >
            <div className="sector-item-content">
              <input
                className="form-check-input"
                type="checkbox"
                checked={selectedSectors.includes(sector.id)}
                readOnly
              />
              <span className="sector-label">
                {getIndentation(sector.level)}
                {sector.name}
              </span>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default SectorSelect;
