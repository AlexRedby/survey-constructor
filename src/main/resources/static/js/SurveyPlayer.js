'use strict';

function sendSurvey() {
    let form = document.getElementById("form");

    let responseSurvey = {
        survey : { id : form.elements["survey.id"].value },
        answers: []
    };

    let types = form.elements["answers.type"];
    let questionIds = form.elements["answers.question.id"];
    let answers = form.elements["answers.answer"];

    for(let i = 0; i < types.length; i++) {
        let answerElem = answers[i];

        let answer;
        if((answerElem.type !== "checkbox" && answerElem.type !== "radio"))
            answer = answerElem.value;
        else if(answerElem.checked)
            answer = { id : answerElem.value };

        if(typeof answer !== 'undefined')
            responseSurvey.answers.push({
                type : types[i].value,
                question : { id : questionIds[i].value },
                answer : answer
            });
    }

    console.log(responseSurvey);

    sendObject(responseSurvey, window.location.pathname)
}

// {
//    survey : { id : 1 },
//    answers: [
//                {
//                   type: input,
//                   question: { id: 2 }  ???
//                   answer: Yes
//                }
//             ]