import * as apiCalls from "../../apiCalls";

export const getInternshipOffers = async () => {
    return apiCalls.getInternshipOffers();
};

export const getCompanyInternships = async (companyID) => {
    return apiCalls.getCompanyInternships(companyID);
};

export const getFormattedCompanyInternships = async (companyID) => {
    return apiCalls.getCompanyInternships(companyID).then((response) => {
        if (response.status === 204) {
            return { status: 204, data: {}, message: "No internship offers found for this company" };
        } else if (response.status === 404) {
            return { status: 404, data: [], message: "Company does not exist" };
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
                return { status: 200, data: formattedData, message: "Internship offers found" };
            });
        }
    }).catch((error) => {
        console.error("Error fetching internships:", error);
        return { status: 500, data: [], message: "Internal server error" };
    });
};