function fetchData(URL, headers) {
    return fetch(URL, headers)
        .then(handleResponse)
        .then(parseResponseData)
        .catch(console.error);
}

function handleResponse(response) {
    if (!response.ok) {
        throw new Error(response.statusText);
    }

    return response;
}

function parseResponseData(response) {
    if (response.status === 204) {
        return response;
    }

    return response.json();
}

function buildHeaders(httpMethod, authType, data) {
    let headers = {
        method: httpMethod,
        headers: {
            "Content-Type": "application/json",
        }
    };

    if (data !== undefined) {
        headers.body = JSON.stringify(data);
    }

    return headers;
}

function get(endpoint) {
    let headers = buildHeaders("GET");
    let URL = buildURL(endpoint);

    return fetchData(URL, headers);
}

function post(endpoint, data) {
    let headers = buildHeaders("POST", data);
    let URL = buildURL(endpoint);

    return fetchData(URL, headers, data);
}

const ROOT_URL = " http://localhost:8080/";

function buildURL(endpoint) {
    return `${ROOT_URL}/${endpoint}`;
}

export const requester = {
    get,
    post,
};