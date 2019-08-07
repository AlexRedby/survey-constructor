'use strict';

const questionsBlock = document.getElementById("questionsBlock");

function addQuestion() {
    questionsBlock.appendChild(createQuestionCard());
}

function changeQuestionType(selectE) {
    const ulParent = selectE.parentNode.parentNode;

    switch (selectE.value) {
        // Single/Multiple choice
        case 'SINGLE_CHOICE':
        case 'MULTIPLE_CHOICE':
            if(ulParent.getElementsByClassName('li-answerOptionsBlock').length === 0)
                ulParent.appendChild(createAnswerOptionsBlock());
            break;
        // Всё остальное
        default:
            let a = ulParent.getElementsByClassName('li-answerOptionsBlock');
            if(a.length > 0)
                a[0].remove();
            break;
    }
}

function addAnswerOption(parentE) {
    parentE.appendChild(createAnswerOption());
}

function removeElement(elem) {
    elem.remove();
}

function sendSurvey() {
    const surveyName = document.getElementById("surveyName");
    const questionTexts = document.getElementsByName("questionText");
    const questionTypes = document.getElementsByName("questionType");
    //console.log(surveyName, questionTexts, questionTypes);

    let questions = [];
    for(let i = 0; i < questionTexts.length; i++) {
        let type = questionTypes[i].value;
        let question = {
            text : questionTexts[i].value,
            type : type,
            answerOptions : []
        };

        if(type === 'SINGLE_CHOICE' || type === 'MULTIPLE_CHOICE')
            $('[name="answerOption"]', questionTypes[i].parentElement.parentElement).each(function () {
                question.answerOptions.push({text : this.value});
            });

        questions.push(question);
    }

    let survey = {
        name : surveyName.value,
        questions : questions
    };

    sendObject(survey, "/constructor");
}

function createQuestionCard() {
    return newElement(
        '<div class="card mb-3 shadow-sm">' +
            '<div class="card-header modal-header">' +
                '<h5 class="mb-0 mr-2 align-self-center">1.</h5>' +
                '<input name="questionText" type="text" class="form-control" placeholder="Enter question">' +
                '<button type="button" class="close pt-0 pr-1"' +
                    'aria-label="Close" onclick="removeElement(this.parentNode.parentNode)">' +
                    '<span>&times;</span>' +
                '</button>' +
            '</div>' +
            '<ul class="list-group list-group-flush">' +
                '<li class="list-group-item">' +
                    '<h6>Question type:</h6>' +
                    '<select class="custom-select" name="questionType" onchange="changeQuestionType(this)">' +
                        '<option disabled selected>Select type of question</option>' +
                        '<option value="SINGLE_CHOICE">Single choice</option>' +
                        '<option value="MULTIPLE_CHOICE">Multiple choice</option>' +
                        '<option value="STRING_INPUT">Input</option>' +
                    '</select>' +
                '</li>' +
            '</ul>' +
        '</div>');
}

function createAnswerOptionsBlock() {
    let elem = newElement(
        '<li class="list-group-item li-answerOptionsBlock">' +
            '<h6>Answer options:</h6>' +
            '<div class="answerOptionsBlock"></div>' +
            '<div class="text-center">' +
                '<button class="btn btn-secondary" onclick="addAnswerOption(this.parentNode.previousSibling)">' +
                    'Add answer option' +
                '</button>' +
            '</div>' +
        '</li>');

    elem.getElementsByClassName("answerOptionsBlock")[0].appendChild(createAnswerOption());

    return elem;
}

function createAnswerOption() {
    return newElement(
        '<div class="form-group d-flex">' +
            '<input type="text" name="answerOption" class="form-control" placeholder="Enter answer">' +
            '<button type="button" class="btn btn-danger ml-3"' +
                'aria-label="Close" onclick="removeElement(this.parentNode)">' +
                'Remove' +
            '</button>' +
        '</div>');
}

const newElement = (domString) => {
    const html = new DOMParser().parseFromString(domString, 'text/html');
    return html.body.firstChild;
};