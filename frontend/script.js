
let currentQuestions = [];

async function generateQuestions() {
    const amount = document.getElementById("quantity").value;

    const response = await fetch("http://localhost:8080/get-questions?amount=" + amount);
    const data = await response.json();

    currentQuestions = data.results;

    const container = document.getElementById("question-container");
    container.innerHTML = "";

    data.results.forEach((x) => {
        console.log(x.question);
        const question = document.createElement("div");
        question.className = "question-box";

        question.innerHTML = `<h3> ${x.question} </h3>`
        x.answers.forEach((answer) => {
            question.innerHTML += `<p> ${answer} </p>`
        });

        container.appendChild(question)
    });

    document.getElementById("check-answer").classList.remove("hidden");
}

async function getAnswers() {
    const ids = currentQuestions.map(x => x.id)

    const response = await fetch("http://localhost:8080/checkanswers", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(ids)
    });

    const correctAnswers = await response.json();

    
    console.log(correctAnswers);
}