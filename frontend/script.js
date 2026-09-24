
let currentQuestions = [];
let lastTimeCaptured = 0;

async function generateQuestions() {
    const currentTime = Date.now();
    const cooldown = 5000; // API can only be called once every 5 seconds

    if (currentTime - lastTimeCaptured < cooldown) {
        alert("Please wait before generating more questions");
        return;
    }
    lastTimeCaptured = currentTime;


    const amount = document.getElementById("quantity").value;

    const response = await fetch("http://localhost:8080/get-questions?amount=" + amount);
    const data = await response.json();

    currentQuestions = data.results;

    const container = document.getElementById("question-container");
    container.innerHTML = "";

    data.results.forEach((x) => {
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

    document.getElementById("check-answer-top").classList.remove("hidden");
    document.getElementById("check-answer-bottom").classList.remove("hidden");
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

        // console.log(`Question: ${id}`)
        // console.log(correct)
        // console.log(answer)

        answer.innerHTML = "✔ " + answer.innerHTML;
    }

    console.log(correctAnswers);
}

// Found on https://tertiumnon.medium.com/js-how-to-decode-html-entities-8ea807a140e5
function decodeHtmlEntities(text) {
    const textarea = document.createElement("textarea");
    textarea.innerHTML = text;
    return textarea.value;
}