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

  const getParentIds = (sectorId: number): number[] => {
    const sector = flatSectors.find((s) => s.id === sectorId);
    if (!sector || !sector.parentId) {
      return [];
    }

    const parentIds = [sector.parentId];
    const parentParentIds = getParentIds(sector.parentId);
    return [...parentIds, ...parentParentIds];
  };

  const getChildIds = (sectorId: number): number[] => {
    const children = flatSectors.filter((s) => s.parentId === sectorId);
    let allChildIds: number[] = [];

    children.forEach((child) => {
      allChildIds.push(child.id);
      const grandChildren = getChildIds(child.id);
      allChildIds = [...allChildIds, ...grandChildren];
    });

    return allChildIds;
  };

  const toggleSector = (sectorId: number) => {
    const newSelection = [...selectedSectors];
    const isCurrentlySelected = newSelection.includes(sectorId);

    if (isCurrentlySelected) {
      const childIds = getChildIds(sectorId);
      const idsToRemove = [sectorId, ...childIds];

      idsToRemove.forEach((id) => {
        const index = newSelection.indexOf(id);
        if (index !== -1) {
          newSelection.splice(index, 1);
        }
      });
    } else {
      const parentIds = getParentIds(sectorId);
      const idsToAdd = [sectorId, ...parentIds];

      idsToAdd.forEach((id) => {
        if (!newSelection.includes(id)) {
          newSelection.push(id);
        }
      });
    }

    onChange(newSelection);
  };

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
