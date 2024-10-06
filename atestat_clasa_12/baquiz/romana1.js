const quizData = [
    {
        question: "Prezența perspectivei subiective este o caracteristică a:",
        a: "Genului dramatic",
        b: "Romanului modern ",
        c: "Poeziei simboliste",
        d: "Nuvelei",
        correct: "b",
    },
    {
        question: "Opera lirică este creația în care:",
        a: "Autorul își transmite în mod direct gândurile, ideile și sentimentele prin intermediul personajelor",
        b: "Autorul își transmite prin intermediul eului liric în mod indirect gândurile, ideile și sentimentele",
        c: "Autorul își transmite în mod direct gândurile, ideile și sentimentele prin intermediul eului liric ",
        d: "Autorul își transmite în mod indirect gândurile, ideile și sentimentele prin intermediul acțiunii",
        correct: "c",
    },
    {
        question: "Tema familiei este prezentă în :",
        a: "Povestea lui Harap-Alb",
        b: "Iona",
        c: "Moromeții",
        d: "Plumb",
        correct: "c",
    },
    {
        question: "Roman postbelic este:",
        a: "Ultima noapte de dragoste, întaia noapte de război",
        b: "Moromeții",
        c: "Enigma Otiliei",
        d: "Ion",
        correct: "b",
    },
    {
        question: "Moș Costache Giurgiuveanu face parte din opera literară :",
        a: "Povestea lui Harap Alb",
        b: "Enigma Otiliei",
        c: "Moara cu noroc",
        d: "Ion",
        correct: "b",
    },
    {
        question: "Tehnica simetriei și a opozitiei este prezentă în secvența :",
        a: "Dormeau adânc sicriele de plumb,/ Dormea întors amorul meu de plumb ",
        b: "Acolo-n ochi de padure/ Vom ședea în foi de mure",
        c: "Nu-ți voi lăsa drept bunuri după moarte/ Decât un nume adunat pe-o carte",
        d: "Din rude mari împărătești / O prea frumoasă fată",
        correct: "a",
    },
    {
        question: "Este scris sub formă de jurnal romanul :",
        a: "Enigma Otiliei",
        b: "Ion",
        c: "Ultima noapte de dragoste, întâia noapte de război",
        d: "Mara",
        correct: "c",
    },
    {
        question: "Balzacianismul constă în :",
        a: "Rememorarea evenimentelor din trecut",
        b: "Descrieri detaliate",
        c: "Relatarea la persoana a III-a",
        d: "Număr mare de pagini",
        correct: "b",
    },
    {
        question: "Personajele ce întruchipează tipul intelectualului sunt :",
        a: "Felix Sima, Ștefan Gheorghidiu",
        b: "Ilie Moromete, Vitoria Lipan",
        c: "Niculae Moromete, Nechifor Lipan",
        d: "Spânul, Ion",
        correct: "a",
    },
    {
        question: "Concepția lui Lucian Blaga este că :",
        a: "Poetul protejează misterele universului ",
        b: "Poetul are datoria să descifreze misterele universului",
        c: "Poetul este indiferent față de misterele universului",
        d: "Poetul este un om de știință",
        correct: "a",
    },
    {
        question: "Teme specifice romantismului sunt :",
        a: "Iubirea, natura, condiția omului de geniu",
        b: "Viața, războiul, călătoria",
        c: "Familia, natura , adolescența",
        d: "Moartea, natura , războiul",
        correct: "a",
    },
    {
        question: "Personajul principal feminin din romanul Baltagul este :",
        a: "Vitoria",
        b: "Ana",
        c: "Ela",
        d: "Maria",
        correct: "a",
    },   
    {
        question: "Moara cu noroc este o nuvelă :",
        a: "Fantastică",
        b: "Istorică",
        c: "Psihologică",
        d: "Romantică",
        correct: "c",
    },   
    {
        question: "Poezia lui Mihai Eminescu se încadreaza în curentul literar :",
        a: "Simbolism",
        b: "Romantism",
        c: "Traditionalism",
        d: "Modernism",
        correct: "b",
    },
    {
        question: "O caracteristică a simbolismului este :",
        a: "Metafora",
        b: "Epitetul",
        c: "Simbolul ",
        d: "Antiteza",
        correct: "c",
    },    
    {
        question: "Următorul roman are o structură sferică/circulară",
        a: "Baltagul",
        b: "Ion",
        c: "Ultima noapte de dragoste, întâia noapte de război",
        d: "Mara",
        correct: "b",
    },    
    {
        question: "Plumb este o poezie :",
        a: "Simbolistă",
        b: "Romantică",
        c: "Modernă",
        d: "Traditională",
        correct: "a",
    },
    {
        question: "Ultima noapte de dragoste, întâia noapte de război este :",
        a: "Roman obiectiv interbelic",
        b: "Roman subiectiv postbelic",
        c: "Roman subiectiv interbelic",
        d: "Roman obiectiv postbelic",
        correct: "c",
    },
    {
        question: "Este considerată un « Luceafăr întors » poezia :",
        a: "Floare-albastră",
        b: "Riga Crypto si lapona Enigel",
        c: "Testament",
        d: "Flori de mucigai",
        correct: "b",
    },
    {
        question: "In poezia lui Nichita Stanescu iubirea este denumită metaforic :",
        a: "Lupoaica",
        b: "Leoaica",
        c: "Tigroaica",
        d: "Pisica",
        correct: "b",
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