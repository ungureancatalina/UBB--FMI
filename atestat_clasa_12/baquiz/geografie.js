const quizData = [
    {
        question: "Turtirea Pământului la Poli și bombarea la Ecuator determină forma de:",
        a: "geoid",
        b: "elipsoid",
        c: "sferă",
        d: "turta",
        correct: "a",
    },
    {
        question: "Adoptarea orei de vară se face prin:",
        a: "trecerea ceasului cu o oră înainte",
        b: "adoptarea orei oficiale a fusului dinspre vest",
        c: "trecerea ceasului cu o oră înapoi",
        d: "înlocuirea ceasului cu unul nou",
        correct: "a",
    },
    {
        question: "Oxigenul reprezintă circa …. din volumul atmosferei.",
        a: "21%",
        b: "40%",
        c: "69%",
        d: "50%",
        correct: "a",
    },
    {
        question: "Evaporarea reprezintă procesul de trecere a apei din …",
        a: "stare gazoasă în stare solidă",
        b: "stare lichidă în stare gazoasă",
        c: "stare gazoasă în stare lichidă",
        d: "stare lichidă în stare solidă",
        correct: "b",
    },
    {
        question: "-Cosmographia- este opera lui:",
        a: "Montesquieu",
        b: "Münster",
        c: "Humboldt",
        d: "Schopenhauer",
        correct: "b",
    },
    {
        question: "HomoHabilis a apărut în:",
        a: "Eurasia",
        b: "Europa Meridională",
        c: "China",
        d: "Africa",
        correct: "d",
    },
    {
        question: "Revoluţia industrială s-a dezvoltat în sec. al XVIII-lea în:",
        a: "Franţa",
        b: "Germania",
        c: "Anglia",
        d: "America",
        correct: "c",
    },
    {
        question: "Heliocentralele valorifică energia:",
        a: "eoliană",
        b: "solară",
        c: "mareelor",
        d: "apei",
        correct: "b",
    },
    {
        question: "Hidrocentrala de la Assuan se află pe:",
        a: "Nil",
        b: "Niger",
        c: "Orinocco",
        d: "Dunăre",
        correct: "a",
    },
    {
        question: "Hidrocentrala Porţile de Fier I a fost construită de România în colaborare cu:",
        a: "Bulgaria",
        b: "Ungaria",
        c: "Serbia",
        d: "Ucraina",
        correct: "c",
    },
    {
        question: "Canalul Suez asigură legătura între:",
        a: "Marea Caraibilor și Oceanul Pacific",
        b: "Marea Mediterană și Marea Marmară",
        c: "Marea Norvegiei si Oceanul Atlantic",
        d: "Marea Mediterană și Marea Roșie",
        correct: "d",
    },
    {
        question: "Costa Dorada se află în:",
        a: "Franţa",
        b: "Italia",
        c: "Spania",
        d: "Portugalia",
        correct: "c",
    },   
    {
        question: "Costa del Sol se află în:",
        a: "Franţa",
        b: "Spania",
        c: "Italia",
        d: "Portugalia",
        correct: "b",
    },   
    {
        question: "Macchu Picchu se află în:",
        a: "Egipt",
        b: "Bolivia",
        c: "Peru",
        d: "Italia",
        correct: "c",
    },
    {
        question: "Dintre următoarele, cel mai populat județ este:",
        a: "Iaşi",
        b: "Prahova",
        c: "Bacău",
        d: "București",
        correct: "a",
    },    
    {
        question: "Istoria geologică a Carpaților începe cu:",
        a: "orogeneza caledoniană din Silurian",
        b: "orogeneza alpină din Cretacic",
        c: "orogeneza hercinică din Carbonifer",
        d: "orogeneza antică",
        correct: "b",
    },    
    {
        question: "Cele mai mari altitudini din Carpații Orientali sunt în:",
        a: "grupa de nord",
        b: "grupa centrală",
        c: "grupa sudică",
        d: "grupa Parâng",
        correct: "a",
    },
    {
        question: "Peştera Muierii este situată în:",
        a: "centrul Munţilor Apuseni",
        b: "sudul Grupei Parâng",
        c: "sudul Munţilor Gutâi",
        d: "nordul Munților Apuseni",
        correct: "b",
    },
    {
        question: "Atât Mureșul cât și Oltul izvorăsc din:",
        a: "Munții Ceahlău",
        b: "Munții Bistriței",
        c: "Masivul Hășmașu Mare",
        d: "Munții Apuseni",
        correct: "",
    },
    {
        question: "Câmpia dintre Olt şi Argeş este numită:",
        a: "Câmpia Teleormanului",
        b: "Câmpia Burnasului",
        c: "Câmpia Bărăganului",
        d: "Câmpia Boianului",
        correct: "d",
    },
];

const quiz= document.getElementById('quiz')
const answerEls = document.querySelectorAll('.answer')
const questionEl = document.getElementById('question')
const a_text = document.getElementById('a_text')
const b_text = document.getElementById('b_text')
const c_text = document.getElementById('c_text')
const d_text = document.getElementById('d_text')
const submitBtn = document.getElementById('submit')
let answel = null

let currentQuiz = 0
let score = 0
let vari = 0
loadQuiz()

function loadQuiz() 
{
    deselectAnswers()
    currentQuiz ++
    const qrandom = quizData[Math.floor(Math.random()*quizData.length)+1]
    const currentQuizData = qrandom
    questionEl.innerText = currentQuizData.question
    answel = qrandom.correct
    answel.innerText = currentQuizData.correct
    a_text.innerText = currentQuizData.a
    b_text.innerText = currentQuizData.b
    c_text.innerText = currentQuizData.c
    d_text.innerText = currentQuizData.d
}

function deselectAnswers() 
{
    answerEls.forEach(answerEl => answerEl.checked = false)

}

function getSelected() 
{
    let answer
    answerEls.forEach(answerEl => 
    {  
        if(answerEl.checked) 
        {
           answer = answerEl.id
           vari ++
        }
    })
    return answer
}

submitBtn.addEventListener('click', () => 
{
    const answer = getSelected()
    if(answer) {
       if(answer === answel) {
           score ++
       }
       if( vari === currentQuiz && currentQuiz < 10) 
       {
           loadQuiz()
       }
       else
       {
        document.getElementById("question").innerHTML ="";
        document.getElementById("a_text").innerHTML ="Felicitări!";
        document.getElementById("b_text").innerHTML ="Ai completat jocul!";
        document.getElementById("c_text").innerHTML ="Mult succes ";
        document.getElementById("d_text").innerHTML ="în continuare!";
        myElement1 = document.getElementById("a");
        myElement1.type = "hidden";
        myElement2 = document.getElementById("b");
        myElement2.type = "hidden";
        myElement3 = document.getElementById("c");
        myElement3.type = "hidden";
        myElement4 = document.getElementById("d");
        myElement4.type = "hidden";
           quiz.innerHTML = `
            <div align="center" style="line-height:125px" font-family="Verdana, Geneva, Tahoma, sans-serif" >  <big> <big> <big> <big>
               Scorul tău este: ${score}/${10}
               </big> </big> </big> </big>
            </div>
            </a>
           `
       }
    }
})