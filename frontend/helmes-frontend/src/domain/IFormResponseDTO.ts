import { ISectorDTO } from "./ISectorDTO";

export interface IFormResponseDTO {
    "id": number,
    "name": string,
    "agreeTerms": boolean,
    "sectors": ISectorDTO[]
}