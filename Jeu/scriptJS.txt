
//DOM Elements

const startScreen = document.getElementById("start-screen")
const quizScreen = document.getElementById("quiz-screen")
const resultScreen = document.getElementById("results-screen");
const startButton = document.getElementById("start-btn");
const questionText = document.getElementById("question-text");
const answersContainer = document.getElementById("answers-container");
const currentQuestionSpan = document.getElementById("current-question");
const totalQuestionsSpan = document.getElementById("max");
const scoreSpan = document.getElementById("score");
const finalScoreSpan = document.getElementById("final-score");
const maxScoreSpan = document.getElementById("max-score");
const resultMessage = document.getElementById("result-message");
const restartButton = document.getElementById("restart-btn");
const progressBar = document.getElementById("progress");

//les questions du quiz tableau d'objets)

const quizQuestions = [
  {
    question: "Quel est le plus grand océan du monde?",
    answers: [
      { text: "Océan Atlantique", correct: false },
      { text: "Océan Indien", correct: false },
      { text: "Océan Pacifique", correct: true },
      { text: "Océan Artique", correct: false },
    ],
  },
  {
    question: "Qui a peint la joconde?",
    answers: [
      { text: "Vincent van Gogh", correct: false },
      { text: "Léonardo de Vinci", correct: true },
      { text: "Pablo Picasso", correct: false },
      { text: "Michel-Ange", correct: false },
    ],
  },
  {
    question: "En quelle année a eu lieu la révolution française?",
    answers: [
      { text: "1785", correct: false },
      { text: "1792", correct: false },
      { text: "1804", correct: false },
      { text: "1789", correct: true },
    ],
  },
  {
    question: "Quelle est la capitale de l'Australie?",
    answers: [
      { text: "Sydney", correct: false },
      { text: "Melbourne", correct: false },
      { text: "Canberra", correct: true },
      { text: "Brisbane", correct: false },
    ],
  },
  {
    question: "Combien y a-t-il de continents sur Terre ?",
    answers: [
      { text: "5", correct: false },
      { text: "6", correct: false },
      { text: "7", correct: true },
      { text: "8", correct: false },
    ],
  },

  {
    question : "Quel planète est surnommée <<la planète rouge>>? ",
    answers  : [

        {text : "Vénus", correct : false},
        {text : "Mars",  correct : true},
        {text : "Jupiter", correct : false},
        {text : "Mercure", correct : false},
    ],
  },

  {
    question : "Qui a écrit <<Les Misérables>> ? ",
    answers  : [

        {text : "Emile Zola", correct : false},
        {text : "Molière",  correct : false},
        {text : "Victor Hugo", correct : true},
        {text : "Honoré de Balzac", correct : false},
    ],
  },

  {
    question : "Quel est l'élément chimique représenté par le symbole Fe ? ",
    answers  : [

        {text : "Fluor", correct : false},
        {text : "Fer",  correct : true},
        {text : "Phosphore", correct : false},
        {text : "Francium", correct : false},
    ],
  },

];

//Variables en rapport avec l'état du questionnaire

let currentQuestionIndex = 0; //initialisation de l'indice de la question à 0 (numéro de la question)
let score = 0;   //initialisation des points à collecter à 0
let answerDisabled = false;  //permettra de contorôler l'incrémentation du score

//pouvoir mettre à jour les questions et le score

totalQuestionsSpan.textContent = quizQuestions.length;
maxScoreSpan.textContent = quizQuestions.length;
 

//fonctions

//fonction pour afficher les questions

function showQuestion(){
    
    answerDisabled = false;
   
    const currentQuestion = quizQuestions[currentQuestionIndex];
    //incrémentation de l'indice de la question
    currentQuestionSpan.textContent = currentQuestionIndex + 1;

    //calcul du pourcentage du progrès
    const progressPercent = (currentQuestionIndex/quizQuestions.length)*100;
    progressBar.style.width = progressPercent + "%";
     
    //modification des questions 
    questionText.textContent = currentQuestion.question;

    answersContainer.innerHTML = "";
    // création de la liste de réponses possibles (boutton)
    currentQuestion.answers.forEach(answer=>{
       const button = document.createElement("button");
       button.textContent = answer.text; 
       button.classList.add("answer-btn");

       //reconnaissance qu'une réponse est fausse ou vraie
       button.dataset.correct = answer.correct;
       
       //évènement pour un click pour sélectionner une réponse
       button.addEventListener("click",selectAnswer);

       //ajout des bouttons dans le container
       answersContainer.appendChild(button);

    });

}

function selectAnswer(event){
  
  if(answerDisabled) return ;

  answerDisabled = true;

  const selectButton = event.target;
  const isCorrect = selectButton.dataset.correct === "true";

 Array.from(answersContainer.children).forEach(button => {
  const correct = button.dataset.correct === "true";

  if (correct) {
    button.classList.add("correct");
  }

  if (button === selectButton && !correct) {
    button.classList.add("incorrect");
  }
 });

  if (isCorrect){
    score++;
    scoreSpan.textContent = score;
  }
  
  setTimeout(() => {
    currentQuestionIndex++;

    //on veux vérifier si le questionnaire est terminé ou pas
    if(currentQuestionIndex < quizQuestions.length){
        showQuestion();
      
    }
   
    else {
          
      showResults();
    }
  },1000 /*petit délai */);
}

//on masque la fenêtre des questions et affiche celle des résultats
function showResults(){
    
  quizScreen.classList.remove("active");
  resultScreen.classList.add("active");

  finalScoreSpan.textContent = score;

  const percentage = (score/quizQuestions.length)*100;
   
  //message de félicitation selon le score (pourcentage)
  if (percentage === 100) {
    resultMessage.textContent = "Parfait! Vous êtes un(e) génie!";
  }
  else if (percentage >= 80) {
    resultMessage.textContent = "Bravo! Vous êtes cultivé(e)!";
  } else if (percentage >= 60) {
    resultMessage.textContent = "Bon effort! Ne cessez d'appendre!";
  } else if (percentage >= 40) {
    resultMessage.textContent = "Pas mal! Réessayer pour vous améliorer!";
  } else {
    resultMessage.textContent = " Vous pouvez faire mieux!";
  }

}

//fonction pour passer d'une fenêtre à une autre (démarrage vers questionnaire)
function startQuiz(){
   
    //réinitialisation des variables (remettre tout à 0 puisqu'on vient de commencer le jeu)
    currentQuestionIndex = 0;
    score = 0;
    scoreSpan.textContent = 0;
   
    startScreen.classList.remove("active"); //masquer ou enlever la fenêtre de démarrage
    quizScreen.classList.add("active"); //affichage de la fenêtre de questionnaires
    
    showQuestion();

}

//refaire le questionnaire
function restartQuiz(){

 resultScreen.classList.remove("active");
 startQuiz();

}

//les évènements

startButton.addEventListener("click", startQuiz);
restartButton.addEventListener("click",restartQuiz);

