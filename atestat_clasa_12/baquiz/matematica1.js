const quizData = [
    {
        question: "Care este cel de-al 10-lea termen al şirului 1,3,5,7,...?",
        a: "15",
        b: "20",
        c: "19",
        d: "17",
        correct: "c",
    },
    {
        question: "Pot fi numerele 7,8,9 elemente ale unei progresii geometrice ?",
        a: "Da, în progresie geometrică în ordinea 7,8,9 cu o raţie q<1",
        b: "Da, în progresie geometrică în ordinea 9,8,7 cu o raţie q<1",
        c: "Nu, cu numerele date nu se poate forma o progresie geometrică",
        d: "Da în progresie geometrică în ordinea 7,9,8 cu o raţie q>1",
        correct: "c",
    },
    {
        question: "Dacă orice DIDU este și DULU și orice DULU este și DOLO atunci orice DIDU este DOLO.",
        a: "Adevărat",
        b: "Fals",
        c: "Și adevărat și fals",
        d: "Nu stiu",
        correct: "a",
    },
    {
        question: "Rezultatul calculului 5,1*10+0.49*100 este :",
        a: "5,149",
        b: "5,59",
        c: "10",
        d: "100",
        correct: "d",
    },
    {
        question: "Fie progresia geometrică (bn)n>=1 cu b1=1 si b2=2.Suma primilor 3 termeni ai acestei progresii este egală cu:",
        a: "7",
        b: "6",
        c: "4",
        d: "3",
        correct: "a",
    },
    {
        question: "Numărul real “a” pentru care 3^a+3^(a+1)+3^(a+2)=117 este egal cu:",
        a: "9",
        b: "4",
        c: "3",
        d: "2",
        correct: "d",
    },
    {
        question: "Rezultatul calculului (2+3)*4-5*(2+2) este:",
        a: "-1",
        b: "0",
        c: "2",
        d: "1",
        correct: "b",
    },
    {
        question: "Pentru orice număr real x, expresia E(x)=(sin x + cos x)^2+(sin x – cos x)^2  este egală cu:",
        a: "0",
        b: "2sin2x",
        c: "2+2sin2x",
        d: "2",
        correct: "d",
    },
    {
        question: "Pe multimea nr reale se definește legea de compoziție x o y = xy + 4x + 4y + 12. Numărul (-4) o 2019 este egal cu :",
        a: "0",
        b: "-4",
        c: "2015",
        d: "2019",
        correct: "b",
    },
    {
        question: "Se consideră progresia aritmetică (an)n>=1 cu a1=1 si a2=3. Suma primilor 5 termeni ai acestei progresii este egală cu :",
        a: "5",
        b: "9",
        c: "15",
        d: "25",
        correct: "d",
    },
    {
        question: "Cu cât este egal 90,3 : 10 = ?",
        a: "10,1",
        b: "903",
        c: "9,03",
        d: "9,3",
        correct: "c",
    },
    {
        question: "Cu cât este egal 1/5 din 1/2 din 1/4 din 600?",
        a: "30",
        b: "20",
        c: "15",
        d: "60",
        correct: "c",
    },
    {
        question: "Cum se numeşte triunghiul care are un unghi mai mare de 90 de grade?",
        a: "Obtuzunghic",
        b: "Dreptunghic",
        c: "Drept",
        d: "Mare",
        correct: "a",
    },   
    {
        question: "Ce număr urmează în seria 172, 84, 40, 18, …",
        a: "8",
        b: "7",
        c: "6",
        d: "5",
        correct: "b",
    },
    {
        question: "Aveți un sac în care se află 2 bile albe și 3 bile negre. Care este probabilitatea de a extrage o bila albă de la prima încercare?",
        a: "50%",
        b: "40%",
        c: "60%",
        d: "20%",
        correct: "b",
    },    
    {
        question: "Valoarea numărului x din proporția x/15=4/5 este egală cu:",
        a: "12",
        b: "4",
        c: "15",
        d: "60",
        correct: "a",
    },    
    {
        question: "Din setul de numere 3,(21) ; 32,1; 3,21; 3,2(1) cel mai mic număr este:",
        a: "3,21",
        b: "3,(21)",
        c: "3,2(1)",
        d: "32,1 ",
        correct: "a",
    },
    {
        question: "Numărul care reprezintă 15% din 200 este egal cu:",
        a: "15",
        b: "150",
        c: "200",
        d: "30",
        correct: "d",
    },
    {
        question: "Suma divizorilor naturali, care sunt numere prime, ai numărului 18 este egală cu :",
        a: "5",
        b: "11",
        c: "14",
        d: "39",
        correct: "a",
    },
    {
        question: "Scrisă sub formă de interval, mulțimea A={x$R | |x| mai mic/egal cu 2 } este egală cu: ",
        a: "[2,+∞)",
        b: "(-∞,2]",
        c: "(-∞,-2]",
        d: "[-2,2]",
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
let score1 = 0
let score2 = 0
let score = 0
let vari = 0
let nr = 0
let p = 1
let nume1 = prompt(" NUME JUCATOR 1")
let nume2 
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
       else if((currentQuiz === 10) && (p < 3))
            { if (p === 1)
                { score1 = score
                score = 0
                p = 2
                currentQuiz = 0
                vari = 0
                answel = null
                loadQuiz()
                nume2 = prompt(" NUME JUCATOR 2")
                }
             else if (p === 2)
                { score2 = score
                p = 3
                }
    }
       else
       {p++
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
            <div align="center" style="line-height:70px" font-family="Verdana, Geneva, Tahoma, sans-serif" >  <big> <big> <big> <big>
               Scorul lui ${nume1} este: ${score1}/${10}
               <br>
               Scorul lui ${nume2} este: ${score2}/${10}
               </big> </big> </big> </big>
            </div>
            </a>
           `
       }
    }
})