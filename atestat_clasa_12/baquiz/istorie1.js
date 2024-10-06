const quizData = [
    {
        question: "Ce conflicte militare s-au desfășurat între 1912-1913 în spațiul balcanic?",
        a: "Primul Război Mondial",
        b: "Războiul Ruso-Turc",
        c: "Războaiele Balcanice",
        d: "Marele Război",
        correct: "c",
    },
    {
        question: "În ce perioadă a funcționat regimul Stalinist în România și sub ce lider?",
        a: "1961-1970 sub Nicolae Ceaușescu",
        b: "1948-1965 sub Gheorghe Gheorghiu-Dej ",
        c: "1951-1969 sub Ion Antonescu",
        d: "1952-1967 sub Gheorghe Gheorghiu-Dej",
        correct: "b",
    },
    {
        question: "Neamurile germanice îi numeau pe locuitorii ce vorbeau idiomuri latine",
        a: "vlah",
        b: "valachus",
        c: "olah",
        d: "w(a)lach",
        correct: "d",
    },
    {
        question: "Ioan Vodă cel Viteaz a fost înfrânt la Roșcani din cauza trădării boierilor conduși de",
        a: "Petru Rareș",
        b: "Simion Movilă",
        c: "Ieremia Movilă",
        d: "Vasile Lupu",
        correct: "c",
    },
    {
        question: "Moldova devine stat riveran Mării Negre în timpul lui",
        a: "Petru Mușat",
        b: "Alexandru cel Bun",
        c: "Roman I",
        d: "Lațcu",
        correct: "c",
    },
    {
        question: "Patentă imperială care desființa în mod oficial iobăgia în Transilvania a fost elaborată în anul",
        a: "1848",
        b: "1847",
        c: "1781",
        d: "1785",
        correct: "d",
    },
    {
        question: "Care ideologie politică interbelică românească susținea, între 1919-1924, lupta de clasă a țărănimii și muncitorimii împotriva burgheziei oligarhice?",
        a: "Comunismul",
        b: "Țărănismul ",
        c: "Liberalismul",
        d: "Legionarismul",
        correct: "c",
    },
    {
        question: "Primul umanist de origine română care a susținut unitatea de neam a românilor a fost",
        a: "Grigore Ureche",
        b: "Miron Costin",
        c: "Nicolaus Olahus ",
        d: "diaconul Coresi",
        correct: "c",
    },
    {
        question: "Noaptea de cristal a avut loc în anul",
        a: "1934",
        b: "1936",
        c: "1938 ",
        d: "1942",
        correct: "c",
    },
    {
        question: "În 1883 Romania încheie o alianță cu",
        a: "Rusia",
        b: "Austro-Ungaria",
        c: "Anglia",
        d: "Niciuna dintre cele de mai sus",
        correct: "b",
    },
    {
        question: "Iancu de Hunedoara l-a impus ca lider în Țara Românească pe ",
        a: "Vlad Uzurpatorul",
        b: "Vlad Țepes",
        c: "Vlad Dracul",
        d: "Vladislav-Vlaicu",
        correct: "c",
    },
    {
        question: "Înțelegerea Balcanică s-a încheiat in anul",
        a: "1921",
        b: "1926",
        c: "1934  ",
        d: "1935",
        correct: "c",
    },   
    {
        question: "Lucrare, de la începutul secolului al XI-lea, care atestă cucerirea maghiară a voievodatelor conduse de Gyla și Ahtum",
        a: "Podoaba istoriilor",
        b: "Strategikon",
        c: "Cronica lui Anonymus",
        d: "Legenda Sfântului Gerard",
        correct: "d",
    },   
    {
        question: "Se dau două enunțuri: 1.În sec. III d.Hr, împăratul Aurelian a fost nevoit să renunțe la administrarea provinciei Dacia.   2.Spațiul nord dunărean a evoluat fără contact cu provinciile latinofone de la sudul Dunării.",
        a: "Ambele enunțuri sunt adevărate",
        b: "Ambele enunțuri sunt false",
        c: "Primul enunț este adevărat iar al doilea fals",
        d: "Primul enunț este fals iar al doilea adevărat",
        correct: "c",
    },
    {
        question: "Se dau două enunțuri: 1.Regulamentele Organice cu rol de Constituție au fost introduse în anul 1831 în cele două Principate.  2.Programele revoluționare de la Brașov și Cernăuți solicitau înlăturarea Regulamentelor Organice și a Protectoratului Rus .",
        a: "Ambele enunțuri sunt adevărate",
        b: "Ambele enunțuri sunt false",
        c: "Primul enunț este adevărat iar al doilea fals   ",
        d: "Primul enunț este fals iar al doilea adevărat ",
        correct: "d",
    },    
    {
        question: "Se dau două enunțuri: 1.Teoriile staliniste au declanșat o mare operație de reînviere a sentimentului național al românilor.  2.După 1971, ideologia comunistă și istoriografia oficială din România minimalizau aportul factorului roman în etnogeneză.",
        a: "Primul enunț este fals iar al doilea adevărat ",
        b: "Ambele enunțuri sunt false",
        c: "Primul enunț este adevărat iar al doilea fals   ",
        d: "Ambele enunțuri sunt adevărate",
        correct: "a",
    },    
    {
        question: "Se dau două enunțuri: 1.În noiembrie 1946 au fost organizate primele alegeri postbelice din România.  2.« La 30 decembrie 1946,regele Mihai a participat la ședința inaugurală a noului parlament. ",
        a: "Ambele enunțuri sunt adevărate ",
        b: "Ambele enunțuri sunt false",
        c: "Primul enunț este adevărat iar al doilea fals ",
        d: "Primul enunț este fals iar al doilea adevărat",
        correct: "a",
    },    
    {
        question: "Se dau două enunțuri: 1.Nicolae Ceaușescu a repartizat industrializării 30% din venitul național.  2.La începutul anilor 1980 datoria externă a României s-a triplat.",
        a: "Ambele enunțuri sunt false",
        b: "Ambele enunțuri sunt adevărate ",
        c: "Primul enunț este adevărat iar al doilea fals",
        d: "Primul enunț este fals iar al doilea adevărat",
        correct: "b",
    },    
    {
        question: "Se dau două enunțuri: 1.Statul totalitar a încălcat sistematic drepturile omului.  2.S-a ajuns la terorism de stat împotriva propriilor cetățeni.",
        a: "Ambele enunțuri sunt adevărate ",
        b: "Ambele enunțuri sunt false",
        c: "Primul enunț este adevărat iar al doilea fals",
        d: "Primul enunț este fals iar al doilea adevărat",
        correct: "a",
    },    
    {
        question: "În ce an a intrat România în Uniunea Europeană?",
        a: "2004",
        b: "2007",
        c: "1999 ",
        d: "2002",
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