import {requester} from "./requester.js";
import {templates} from "./templates.js";

const attachEvents = () => {
    document.getElementById("showViruses").addEventListener("click", showVirusesHandler);
    document.getElementById("showCapitals").addEventListener("click", showCapitalsHandler);
};

const uncheckBtn = (radioBtn) => {
    radioBtn.checked = false;
};

const setContentTypeText = (textValue) => {
  document.getElementById("contentType").textContent = textValue;
};

const visualizeContent = (content) => {
    document.getElementById("content").innerHTML = content;
};

const VIRUSES_RESOURCE_ENDPOINT = "/api/viruses";
const CAPITALS_RESOURCE_ENDPOINT = "/api/capitals";

const showVirusesHandler = (event) => {
    uncheckBtn(event.target);
    setContentTypeText("All Viruses");

    requester.get(VIRUSES_RESOURCE_ENDPOINT)
        .then(templates.fillVirusTemplate)
        .then(visualizeContent);
};

const showCapitalsHandler = (event) => {
    uncheckBtn(event.target);
    setContentTypeText("All Capitals");

    requester.get(CAPITALS_RESOURCE_ENDPOINT)
        .then(templates.fillCapitalTemplate)
        .then(visualizeContent);
};

document.addEventListener("DOMContentLoaded", attachEvents);