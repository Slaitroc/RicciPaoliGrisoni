import * as apiCalls from "../../apiCalls";

export const getMySpontaneousApplications = async () => {
    return new Promise(async (resolve, reject) => {
        try {
            apiCalls.getMySpontaneousApplications();
        } catch (err) {
        console.error(err);
        reject(err);
        }
    });
}

export const getFormattedSpontaneousApplications = async () => {
    try{
        return apiCalls.getMySpontaneousApplications().then((response) => {
            if(response.status === 204){
                return {success: false, data: null, message: "No spontaneous applications found for this user", severity: "info"}
            }else if(response.status === 404 || response.status === 400){
                return {success: false, data: null, message: response.properties.error, severity: "error"}
            }else{
                return response.json().then((payload) => {
                    const formattedData = payload.map((application) => {
                        const {properties} = application;
                        return {
                            id: properties.id,
                            status: properties.status,
                            internshipOfferTitle: properties.internshipOfferTitle,
                            internshipOfferCompanyName: properties.internshipOfferCompanyName,
                            studentName: properties.studentName,
                            studentID: properties.studentID,
                        }
                    });
                    return {success: true, data: formattedData, message: "Spontaneous applications fetched successfully", severity: "success"}
                });
            }
        });
    }catch{
        //NOTE lancio errore critico
        throw error
    }
}