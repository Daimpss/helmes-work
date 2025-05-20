import { IFormResponseDTO } from "@/domain/IFormResponseDTO";
import { IPersonFormDTO } from "@/domain/IPersonFormDTO";
import { IResultObject } from "@/domain/IResultObject";
import axios from "axios";

export default class PersonService {
    private constructor() {
    }

    private static httpClient = axios.create({
        baseURL: 'http://localhost:8080/api/persons',
    });

    static async savePerson(form: IPersonFormDTO) : Promise<IResultObject<IFormResponseDTO[]>> {
        try {
            const response = await PersonService.httpClient.post<IFormResponseDTO[]>("");
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
