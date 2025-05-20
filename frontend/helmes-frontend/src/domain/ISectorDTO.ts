export interface ISectorDTO {
    "id": number,
    "name": string,
    "level": number ,
    "parentId": number | null,
    children?: ISectorDTO[]
}