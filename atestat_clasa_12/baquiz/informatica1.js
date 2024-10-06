const quizData = [
    {
        question: "Indicați o expresie C/C++ echivalentă cu cea alăturată. (x>5) && (x<20) || (x!=y)",
        a: "(x>5 || x<20) && (x==y)",
        b: "!(x<=5 || x>=20) || (x!=y)",
        c: "(x>5 || x<20) && (x!=y)",
        d: "!(x<5 || x>20) && (x!=y)",
        correct: "b",
    },
    {
        question: "Variabila x este declarată alăturat. Indicați secvența care, în urma executării, memorează în variabila x ziua, luna și anul corespunzătoare unei date calendaristice citite de la tastatură : struct data {int zi, luna, an;}x; ",
        a: "cin>>x.zi>>x.luna>>x.an;",
        b: "cin>>zi.x>>luna.x>>an.x;",
        c: "cin>>x(zi,luna,an);",
        d: "cin>>x(zi)>>x(luna)>>x(an);",
        correct: "a",
    },
    {
        question: "Variabilele x și y sunt de tip întreg și memorează numere naturale nenule. Indicați o expresie C/C++ care are valoarea 1 dacă și numai dacă numărul memorat în x are cifra zecilor egală cu 2 și este un multiplu al numărului memorat în y.",
        a: "x/10%10==2 && x%y==0",
        b: "x/10%10==2 && y%x==0",
        c: "x/2%10==10 && x%y==0",
        d: "x/2%10==10 || y%x==0",
        correct: "a",
    },
    {
        question: "Variabila x este de tip real. Care dintre următoarele expresii C/C++ are valoarea 1 dacă şi numai dacă numărul real memorat în variabila x aparţine intervalului (5,8]? ",
        a: "(x<8) && (x>=5)",
        b: "(x<=8) || (x>5)",
        c: "(x>8) || (x<=5)",
        d: "(x<=8) && (x>5)",
        correct: "d",
    },
    {
        question: "Se consideră o coadă în care iniţial au fost introduse, în această ordine, elementele cu valorile 1 şi 2.Se notează cu AD(x) operaţia prin care se adaugă elementul cu valoarea x în coadă şi cu EL operaţia prin care se elimină un element din coadă. Câte elemente va conţine coada în urma executării secvenţei de operaţii: AD(4);EL;EL;AD(5);EL;AD(3)?",
        a: "3",
        b: "1",
        c: "2",
        d: "5",
        correct: "c",
    },
    {
        question: "Care dintre următoarele expresii C/C++ are valoarea 1 dacă variabila x memorează un număr natural pătrat perfect? ",
        a: "sqrt(x) == floor(sqrt(x))",
        b: "sqrt(x) != floor(sqrt(x))",
        c: "floor(sqrt(x)) != ceil(sqrt(x))",
        d: "x-floor(x) == ceil(x)",
        correct: "a",
    },
    {
        question: "Care dintre următoarele instrucţiuni C/C++ sunt corecte sintactic dacă x şi y sunt două variabile de tip întreg? ",
        a: "if (x < 2) && (x > - 5) {x=x+1; y=y-1;}",
        b: "if -5 < x < 2 {x=x+1; y=y-1;}",
        c: "if x < 2 && x >-5 {x=x+1; y=y-1;}",
        d: "if (x < 2 && x > -5) {x=x+1; y=y-1;}",
        correct: "d",
    },
    {
        question: "Care dintre următoarele expresii C/C++ are valoarea 1 dacă şi numai dacă valoarea reală memorată de variabila y nu aparţine intervalului deschis determinat de valorile reale distincte memorate de variabilele x şi z (x<z)? ",
        a: "!((z<x) || (z>y))",
        b: "(z>x) && (z<y)",
        c: "!((y>x) && (y<z))",
        d: "(z<x) && (z>y)",
        correct: "c",
    },
    {
        question: "Variabila întreagă n memorează un număr natural de exact 5 cifre. Instrucţiunea care determină eliminarea din numărul n a primei şi ultimei cifre este: ",
        a: "n=n%100/10;",
        b: "n=n/10%1000;",
        c: "n=n/10%10000;",
        d: "n=n%10/1000;",
        correct: "b",
    },
    {
        question: "Variabila întreagă n memorează un număr natural de exact 7 cifre. Instrucţiunea care determină eliminarea din numărul n a celor 3 cifre din mijlocul lui este: ",
        a: "n=n/100000*100+n%100;",
        b: "n=n%10000/100+n/100;",
        c: "n=n/10%10000;",
        d: "n=n/1000;",
        correct: "a",
    },
    {
        question: "Stabiliţi ce se afişează în urma executării secvenţei de instrucţiuni C/C++ alăturate, dacă y este o variabilă reală, iar x o variabilă întreagă. {y=10.1234;x=(int)(y*100)/100;cout<<x;}",
        a: "1012.34",
        b: "10.12",
        c: "0.12",
        d: "10",
        correct: "d",
    },
    {
        question: "Variabilele n, z şi u sunt întregi, iar n memorează un număr natural cu cel puţin 2 cifre. Secvenţa C/C++ care determină interschimbarea ultimelor două cifre din scrierea numărului memorat de n este:",
        a: "n=(n/100*10+n%10)*10+n%100/10;",
        b: "u=n%10; z=n/100%10; n=n/100+u*10+z;",
        c: "n=(n/100*10+n%10)*10+n/100%10;",
        d: "u=n%10; z=n/100%10; n=n/100*100+z*10+u;",
        correct: "a",
    },   
    {
        question: "Care dintre expresiile de mai jos are valoarea 1 după executarea secvenţei de instrucţiuni C/C++, scrise alăturat, în care toate variabilele sunt întregi? a=11;b=1;for(i=1;i<=5;i++) { b=b+1;a=a-1; }",
        a: "a < b",
        b: "a == b",
        c: "a > b",
        d: "a + b == 6",
        correct: "b",
    },   
    {
        question: "Variabilele întregi a şi b memorează numere naturale. Care dintre expresiile C/C++ de mai jos este echivalentă cu expresia alăturată? (a+b)%2==0 ",
        a: "(a%2==0) && (b%2==0) && (a%2==1) && (b%2==1)",
        b: "(a%2!=0) && (b%2!=0) && (a%2==1) && (b%2==1)",
        c: "(a%2==1) || (b%2==1) || (a%2==0) && (b%2==0)",
        d: "(a%2==0) && (b%2==0) || (a%2==1) && (b%2==1)",
        correct: "d",
    },
    {
        question: "Variabila întreagă n memorează un număr natural cu exact 4 cifre. Care dintre expresiile C/C++ de mai jos este echivalentă cu cea alăturată? n/100%10%2==0 ",
        a: "n%100/10%2!=1",
        b: "n%1000%2==0",
        c: "n/100%2==0",
        d: "n/10%10!=1",
        correct: "c",
    },    
    {
        question: "Variabilele întregi n şi m memorează numere naturale nenule pare, iar n<m. Care dintre expresiile C/C++ de mai jos are valoarea egală cu numărul de valori impare din intervalul închis [n,m] ?",
        a: "(m-n)/2+1",
        b: "m/2-n/2",
        c: "(m-n)/2-1",
        d: "m%2-n%2",
        correct: "b",
    },    
    {
        question: "Care dintre instrucţiunile C/C++ de mai jos determină înlocuirea cu 0 a ultimei cifre a numărului natural, cu cel puţin două cifre, memorat în variabila întreagă n? ",
        a: "n=n*(n%10);",
        b: "n=n/10;",
        c: "n=n-n%10;",
        d: "n=n-n/10;",
        correct: "c",
    },
    {
        question: "Care dintre variantele de mai jos declară constanta x astfel încât aceasta să memoreze corect numărul real 3,14? ",
        a: "const int x=314/100;",
        b: "const char x=3.14;",
        c: "const unsigned int x=3.14;",
        d: "const float x=3.14;",
        correct: "d",
    },
    {
        question: "In secvenţele C/C++ următoare toate variabilele sunt de tip întreg şi memorează numere cu cel mult 3 cifre. Care dintre variantele de mai jos determină interschimbarea valorilor memorate de variabilele a şi b?",
        a: "aux=b; a=b; b=aux;",
        b: "a=a+b; b=a-b; a=a-b;",
        c: "a=b; b=a;",
        d: "a=a+b; b=a-b; a=b-a;",
        correct: "a",
    },
    {
        question: "Variabila întregă n memorează un număr natural format din exact două cifre nenule. Care dintre următoarele instrucţiuni C/C++ determină memorarea în variabila întreagă t a numărului care are aceleaşi cifre ca şi n, dar în ordine inversă? ",
        a: "t=n%10*10+n/10; ",
        b: "t=n/10*10+n%10;",
        c: "t=n%10+n/10;",
        d: "t=n%10*10+t/10;",
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