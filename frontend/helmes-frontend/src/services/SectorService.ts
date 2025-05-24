import { IResultObject } from "@/domain/IResultObject";
import { ISectorDTO } from "@/domain/ISectorDTO";
import axios from "axios";

export default class SectorService {
    private constructor() {
    }

    private static httpClient = axios.create({
        baseURL: 'http://localhost:8080/api/sectors',
        withCredentials: true,
        headers: {
            'Content-Type': 'application/json',
        },
    });

    static async getAllSectors() : Promise<IResultObject<ISectorDTO[]>> {
        try {
            const response = await SectorService.httpClient.get<ISectorDTO[]>("");
            console.log(response.data);
            if (response.status < 300) {
                return {
                    data: response.data
                }
            }
            return {
                errors: [response.status.toString + " " + response.statusText]
            };
        }
        catch(error : any) {
            return {
                errors: [JSON.stringify(error)]
            };

        }
        

    }
}