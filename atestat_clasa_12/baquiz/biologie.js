const quizData = [
    {
        question: "Ribozomii:",
        a: "au rol în respirația celulară",
        b: "nu se găsesc în neuron",
        c: "nu însoțesc niciodată canaliculele reticulului endoplasmatic",
        d: "au rol în sinteza proteinelor",
        correct: "d",
    },
    {
        question: "Membrana celulară:",
        a: "conține materialul genetic al celulei",
        b: "este impermeabilă",
        c: "conține mitocondrii",
        d: "este organizată după modelul mozaicului fluid",
        correct: "d",
    },
    {
        question: "În structura nucleului intră:",
        a: "organite celulare comune tuturor celulelor",
        b: "neurofibrile",
        c: "centrozomul",
        d: "carioplasma",
        correct: "d",
    },
    { 
        question: "Sunt glande endocrine:",
        a: "glandele salivare",
        b: "glandele sebacee",
        c: "glandele suprarenale",
        d: "glandele lacrimale",
        correct: "c",
    },
    {
        question: "Țesutul muscular neted:",
        a: "se contractă voluntar",
        b: "formează miocardul",
        c: "este întâlnit în musculatura organelor interne",
        d: "intră în alcătuirea limbii",
        correct: "c",
    },
    {
        question: "La nivelul bulbului rahidian se găsesc:",
        a: "Nucleii substanței reticulate",
        b: "Substanța neagră",
        c: "Nucleul roșu",
        d: "Un număr mare de neuroni",
        correct: "a",
    },
    {
        question: "ARN-ul mesager",
        a: "Se obține prin replicare",
        b: "Este asemăntor unei Frunze de trifoi",
        c: "Are porțiuni bicatenare",
        d: "Este monocatenar",
        correct: "d",
    },
    {
        question: "Transmiterea impulsului nervos de la un neuron la altul se face prin :",
        a: "Reflexe",
        b: "Articulație",
        c: "Sinapse",
        d: "Contracție",
        correct: "c",
    },
    {
        question: "Cum se numește imposibilitatea de a percepe culorile ?",
        a: "Hipermetropie",
        b: "Acromatopsie",
        c: "Daltonism",
        d: "Cataractă",
        correct: "b",
    },
    {
        question: "Care dintre următoarele se comportă ca o diafragmă ?",
        a: "Pupila",
        b: "Sclerotica",
        c: "Cornea",
        d: "Irisul",
        correct: "d",
    },
    {
        question: "Gustul amar este perceput:",
        a: "Pe marginile limbii",
        b: "Pe fata dorsală a limbii",
        c: "Pe toată zona limbii",
        d: "Pe vârful limbii",
        correct: "b",
    },
    {
        question: "Ce boală a ochiului se corectează cu lentile cilindrice ?",
        a: "Astigmatism",
        b: "Miopie",
        c: "Strabism",
        d: "Hipermetropie",
        correct: "a",
    },   
    {
        question: "Din ce este format neuronul ?",
        a: "Corp celular,axon și dendrit",
        b: "Axon și dendrite",
        c: "Nervi spinali",
        d: "Corp celular,celule gliale",
        correct: "a",
    },   
    {
        question: "Din ce este formată urechea externă ?",
        a: "Pavilion și timpan",
        b: "Utriculă și saculă",
        c: "Pavilion și conduct auditiv",
        d: "Conduct auditiv si timpan",
        correct: "c",
    },
    {
        question: "Ordinea nivelurilor de organizare a organismului uman de la simplu la complex este:",
        a: "Celular,molecular,atomic,tisular,organe,sisteme,organism.",
        b: "Atomic,molecular,celular,tisular,organic,sisteme de organe,organism.",
        c: "Organe,celule,țesuturi,organism.",
        d: "Sisteme de organe,organe,celule,molecule,atomi,organism.",
        correct: "b",
    },    
    {
        question: "Funcţiile vitale ale organismului uman sunt:",
        a: "Moleculară,celulară,organică.",
        b: "Atomică,de reproducere,moleculară.",
        c: "De relaţie,de nutriţie,de reproducere.",
        d: "De protecţie,de izolare şi de difuziune",
        correct: "c",
    },    
    {
        question: "Măduva spinării are următoarele funcţii :",
        a: "De contracţie.",
        b: "De reproducere.",
        c: "Digestivă.",
        d: "De conducere.",
        correct: "d",
    },
    {
        question: "Creierul mare este format din :",
        a: "Emisferele cerebrale",
        b: "Diencefal",
        c: "Bulb, punte şi mezencefal",
        d: "Talamus",
        correct: "a",
    },
    {
        question: "Urechea umană percepe unde sonore cu o frecvenţă cuprinsă între",
        a: "0 - 10 000 Hz",
        b: "100 - 20 000 Hz",
        c: "16 - 20 000 Hz",
        d: "1 - 20 000 Hz",
        correct: "c",
    },
    {
        question: "O adaptare bună a ochiului la întuneric total după lumină intensă necesită:",
        a: "30-45 minute",
        b: "1-2 minute",
        c: "10-15 minute",
        d: "5-10 minute",
        correct: "a",
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