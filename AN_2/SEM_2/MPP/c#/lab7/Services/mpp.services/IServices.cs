namespace Services.mpp.services;
using Model.mpp.model;

public interface IServices
{
    void Login(Utilizator utilizator, IObserver client);

    void InscriereParticipant(Participant participant);

    List<Participant> FindAllParticipanti();

    List<Participant> CautaParticipantiDupaEchipa(string numeEchipa);

    Echipa FindEchipaByNume(string nume);

    void AdaugaEchipa(Echipa echipa);

    List<Cursa> FindAllCurse();

    List<CursaDTO> ListDtoCurse();

    int FindNrPartByCursa(Cursa cursa);

    void AdaugaParticipantLaCursa(ParticipantCursa participantCursa);

    List<ParticipantCursa> FindAllParticipantCursa();
    
    void Logout(Utilizator utilizator, IObserver client);

    Participant FindByNume(string nume);
}