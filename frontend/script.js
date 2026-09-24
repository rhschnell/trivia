
let currentQuestions = [];
let lastTimeCaptured = 0;
const COOLDOWN = 5000;

async function generateQuestions() {
    if (cooldownCheck()) return;

    const url = buildURL();

    try {
        const response = await fetch(url);

        if (!response.ok) {
            throw new Error(`Server responded with the following status: ${response.status}`)
        }

        const data = await response.json();

        currentQuestions = data.results || [];

        renderQuestions(currentQuestions);

        document.getElementById("check-answer-top").classList.remove("hidden");
        document.getElementById("check-answer-bottom").classList.remove("hidden");
    } catch (e) {
        console.error("Error: ", e);
        alert("Bad request was made. This is probably because there weren't enough questions available with these criteria. Try changing some parameters");
    }
}

async function getAnswers() {
    document.getElementById("check-answer-top").classList.add("hidden");
    document.getElementById("check-answer-bottom").classList.add("hidden");

    const ids = currentQuestions.map(x => x.id)

    const response = await fetch("http://localhost:8080/checkanswers", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(ids)
    });

    const correctAnswers = await response.json();

    for (const id in correctAnswers) {
        const correct = decodeHtmlEntities(correctAnswers[id])
        const questionBox = document.querySelector(`.question-box[question-id="${id}"]`);
        const answer = questionBox.querySelector(`p[question-answer="${CSS.escape(correct)}"]`);

        answer.innerHTML = "✔ " + answer.innerHTML;
    }
}

function buildURL() {
    const amount = document.getElementById("quantity").value;
    const category = document.getElementById("category").value;
    const difficulty = document.getElementById("difficulty").value;
    const type = document.getElementById("type").value;


    let url = "http://localhost:8080/get-questions?amount=" + amount;

    if (category !== "any") url += `&category=${category}`;
    if (difficulty !== "any") url += `&difficulty=${difficulty}`;
    if (type !== "any") url += `&type=${type}`

    return url;
}

function renderQuestions(data) {
    const container = document.getElementById("question-container");
    container.innerHTML = "";

    data.forEach((x) => {
        x.question = decodeHtmlEntities(x.question);
        x.answers = x.answers.map(decodeHtmlEntities);

        const question = document.createElement("div");
        question.className = "question-box center";
        question.setAttribute("question-id", x.id)

        question.innerHTML = `<h3> ${x.question} </h3>`
        x.answers.forEach((answer) => {
            question.innerHTML += `<p question-answer="${answer}">${answer}</p>`
        });

        container.appendChild(question)
    });
}

function cooldownCheck() {
    const currentTime = Date.now();

    if (currentTime - lastTimeCaptured < COOLDOWN) {
        alert("Please wait before generating more questions");
        return true;
    }

    lastTimeCaptured = currentTime;

    return false;
}

// Found on https://tertiumnon.medium.com/js-how-to-decode-html-entities-8ea807a140e5
function decodeHtmlEntities(text) {
    const textarea = document.createElement("textarea");
    textarea.innerHTML = text;
    return textarea.value;
}