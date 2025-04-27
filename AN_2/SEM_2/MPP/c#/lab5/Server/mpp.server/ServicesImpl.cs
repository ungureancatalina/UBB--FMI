using System.Collections.Concurrent;

namespace Server.mpp.server;
using Model.mpp.model;
using Persistence.mpp.persistence;
using Services.mpp.services;

public class ServicesImpl : IServices
{
    private readonly ICursaRepository _cursaRepository;
    private readonly IEchipaRepository _echipaRepository;
    private readonly IParticipantRepository _participantRepository;
    private readonly IParticipantCursaRepository _participantCursaRepository;
    private readonly IUtilizatorRepository _utilizatorRepository;
    private readonly ConcurrentDictionary<string, List<IObserver>> loggedClients = new();

    public ServicesImpl(
        IUtilizatorRepository utilizatorRepository,
        IEchipaRepository echipaRepository,
        IParticipantRepository participantRepository,
        ICursaRepository cursaRepository,
        IParticipantCursaRepository participantCursaRepository)
    {
        _utilizatorRepository = utilizatorRepository;
        _echipaRepository = echipaRepository;
        _participantRepository = participantRepository;
        _cursaRepository = cursaRepository;
        _participantCursaRepository = participantCursaRepository;
    }

    public void Login(Utilizator utilizator, IObserver client)
    {
        if (utilizator == null)
            throw new Exception("Utilizator invalid.");

        lock (loggedClients)
        {
            if (!loggedClients.ContainsKey(utilizator.Nume))
            {
                loggedClients[utilizator.Nume] = new List<IObserver>();
            }

            var observers = loggedClients[utilizator.Nume];

            if (!observers.Contains(client))
            {
                observers.Add(client);
            }
            else
            {
                throw new Exception("Observer deja logat.");
            }
        }
    }
    
    public void Logout(Utilizator utilizator, IObserver client)
    {
        if (utilizator == null)
            return;

        lock (loggedClients)
        {
            if (loggedClients.TryGetValue(utilizator.Nume, out var observers))
            {
                observers.Remove(client);

                if (observers.Count == 0)
                {
                    loggedClients.TryRemove(utilizator.Nume, out _);
                }
            }
        }

        Console.WriteLine($"{utilizator.Nume} s-a delogat (observer: {client.GetType().Name})");
    }

    public Participant FindByNume(string nume)
    {
        return _participantRepository.FindByNume(nume);
    }
    
    public void InscriereParticipant(Participant participant)
    {
        _participantRepository.Adauga(participant);
    }

    public List<Participant> FindAllParticipanti()
    {
        return _participantRepository.FindAll();
    }

    public List<Participant> CautaParticipantiDupaEchipa(string numeEchipa)
    {
        var echipa = _echipaRepository.FindByNume(numeEchipa);
        if (echipa == null)
            throw new Exception("Echipa nu exista");

        return _participantRepository.FindByEchipa(echipa);
    }

    public Echipa FindEchipaByNume(string nume)
    {
        return _echipaRepository.FindByNume(nume);
    }

    public void AdaugaEchipa(Echipa echipa)
    {
        _echipaRepository.Adauga(echipa);
    }

    public List<Cursa> FindAllCurse()
    {
        return _cursaRepository.FindAll();
    }

    public List<CursaDTO> ListDtoCurse()
    {
        var curse = _cursaRepository.FindAll();
        var dtoList = new List<CursaDTO>();

        foreach (var cursa in curse)
        {
            int nrPart = _participantCursaRepository.CountByCursa(cursa);
            dtoList.Add(new CursaDTO(
                cursa.Nume,
                cursa.CapacitateMinima,
                cursa.CapacitateMaxima,
                nrPart
            ));
        }

        return dtoList;
    }

    public int FindNrPartByCursa(Cursa cursa)
    {
        return _participantCursaRepository.CountByCursa(cursa);
    }

    public void AdaugaParticipantLaCursa(ParticipantCursa participantCursa)
    {
        _participantCursaRepository.adaugainbaza(participantCursa);
        NotifyParticipantAdded(participantCursa);
    }

    public List<ParticipantCursa> FindAllParticipantCursa()
    {
        return _participantCursaRepository.FindAll();
    }

    private void NotifyParticipantAdded(ParticipantCursa participantCursa)
    {
        foreach (var observerList in loggedClients.Values)
        {
            foreach (var obs in observerList)
            {
                ThreadPool.QueueUserWorkItem(_ =>
                {
                    try
                    {
                        obs.Adauga(participantCursa);
                    }
                    catch (Exception e)
                    {
                        Console.WriteLine("Error notifying observer: " + e.Message);
                    }
                });
            }
        }
    }
}