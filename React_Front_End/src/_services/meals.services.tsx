import config from '../config.json';
import { userService } from './user.services'

export const mealServices = {
    getMealList,
    getMeal,
    getCaloriesFromAPI
};

function getMeal(id: string) {
    const requestOptions = {
        method: 'GET',
        headers: { 
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${userService.getToken()}` 
        }
    };
    return fetch(`${config.apiUrl}/api/meal/mealmanagement/${id}`, requestOptions)
        .then(
            handleResponse
            )
        .then(meal => {
            return meal;
        });    
} 

function getCaloriesFromAPI(mealItemToFind: string) {
    const requestOptions = {
        method: 'GET',
        headers: { 
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${userService.getToken()}` 
        }
    };
    return fetch(`${config.apiUrl}/api/meal/calorielookup?mealItemToLookup=${mealItemToFind}`, requestOptions)
        .then(
            handleResponse
            )
        .then(meaItems => {
            return meaItems;
        });    
}

function getMealList(dateToPull: String) {
    const requestOptions = {
    method: 'GET',
    headers: { 
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${userService.getToken()}` 
    }
};
return fetch(`${config.apiUrl}/api/meal/mealmanagement?dateToPull=${dateToPull}`, requestOptions)
    .then(
        handleResponse
        )
    .then(mealList => {
        return mealList;
    });
}

function handleResponse(response) {
    return response.text().then(text => {
        const data = text && JSON.parse(text);
        if (!response.ok) {
            const error = (data && data.message) || response.statusText;
            return Promise.reject(error);
        }
        return data;
    });
}



