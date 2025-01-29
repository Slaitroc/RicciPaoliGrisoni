import * as apiCalls from "../../apiCalls";

export const getInternshipOffers = async () => {
    return apiCalls.getInternshipOffers();
};

export const getCompanyInternships = async (companyID) => {
    return apiCalls.getCompanyInternships(companyID);
};

export const getFormattedCompanyInternships = async (companyID) => {
    try{
        return apiCalls.getCompanyInternships(companyID).then((response) => {
            if (response.status === 204) {
                return { success: false, data: null, message: "No internship offers found for this company", severity: "info"} 
            } else if (response.status === 404) {
                return { success: false, data: null, message: response.properties.error, severity: "error" };
            } else {
                return response.json().then((payload) => {
                    const formattedData = payload.map((internship) => {
                        const { properties } = internship;
                        return {
                            id: properties.id,
                            title: properties.title,
                            companyID: properties.companyID,
                            companyName: properties.companyName,
                            description: properties.description,
                            startDate: properties.startDate,
                            endDate: properties.endDate,
                            duration: properties.duration,
                            location: properties.location,
                            compensation: properties.compensation,
                            numberPositions: properties.numberPositions,
                            requiredSkills: properties.requiredSkills,
                        };
                    });
                    return { success: true, data: formattedData, message: "Internship offers fetched successfully", severity: "success" };
                });
            }
        })
    }catch (error){
        //NOTE lancio errore critico
        throw error;
    }
};