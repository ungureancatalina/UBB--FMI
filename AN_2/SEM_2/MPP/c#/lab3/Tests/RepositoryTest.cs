using lab3.utils;

namespace lab3.Tests;
using Xunit;
using log4net;
using lab3.repository;
using lab3.domain;

public class RepositoryTest
{
    private static readonly ILog logger = LogManager.GetLogger(typeof(RepositoryTest));

    public RepositoryTest()
    {
        log4net.Config.XmlConfigurator.Configure(new FileInfo("log4net.config"));
    }
    
    private CursaRepository GetCursaRepository()
    {
        return new CursaRepository(DatabaseConfig.GetConnectionString());
    }

    private UtilizatorRepository GetUtilizatorRepository()
    {
        return new UtilizatorRepository(DatabaseConfig.GetConnectionString());
    }

    private EchipaRepository GetEchipaRepository()
    {
        return new EchipaRepository(DatabaseConfig.GetConnectionString());
    }

    private ParticipantRepository GetParticipantRepository()
    {
        return new ParticipantRepository(DatabaseConfig.GetConnectionString());
    }
    private ParticipantCursaRepository GetParticipantCursaRepository()
    {
        return new ParticipantCursaRepository(DatabaseConfig.GetConnectionString());
    }
    
    [Fact] 
    public void TestCursaRepository() 
    {
        var cursaRepository = GetCursaRepository();
        var cursa = new Cursa("Cursa Test", 5, 10);
        cursaRepository.Adauga(cursa);

        var updatedCursa = new Cursa(cursa.GetId(), "Cursa Modificata", 6, 12);
        cursaRepository.Modifica(updatedCursa);

        var foundCursa = cursaRepository.FindById(cursa.GetId());
        Assert.NotNull(foundCursa);
        Assert.Equal("Cursa Modificata", foundCursa.GetNume());

        var curse = cursaRepository.FindAll();
        Assert.Single(curse);
        Assert.Equal("Cursa Modificata", curse[0].GetNume());

        cursaRepository.Sterge(updatedCursa.GetId());
        foundCursa = cursaRepository.FindById(cursa.GetId());
        Assert.Null(foundCursa);
    }

    [Fact]
    public void TestUtilizatorRepository()
    {
        var utilizatorRepository = GetUtilizatorRepository();
        var utilizator = new Utilizator("Utilizator Test", "password123");
        utilizatorRepository.Adauga(utilizator);

        logger.Debug("Utilizatorul a fost adăugat: " + utilizator.GetId());

        var updatedUtilizator = new Utilizator(utilizator.GetId(), "Utilizator Modificat", "newpassword123");
        utilizatorRepository.Modifica(updatedUtilizator);

        var foundUtilizator = utilizatorRepository.FindById(updatedUtilizator.GetId());
        Assert.NotNull(foundUtilizator);
        Assert.Equal("Utilizator Modificat", foundUtilizator.GetNume());

        var utilizatori = utilizatorRepository.FindAll();
        Assert.Single(utilizatori);
        Assert.Equal("Utilizator Modificat", utilizatori[0].GetNume());

        utilizatorRepository.Sterge(updatedUtilizator.GetId());
        foundUtilizator = utilizatorRepository.FindById(updatedUtilizator.GetId());
        Assert.Null(foundUtilizator);
    }

    [Fact]
    public void TestEchipaRepository()
    {
        var echipaRepository = GetEchipaRepository();
        var echipa = new Echipa("Echipa Test");
        echipaRepository.Adauga(echipa);

        logger.Debug("Echipa a fost adăugată: " + echipa.GetId());

        var updatedEchipa = new Echipa(echipa.GetId(), "Echipa Modificata");
        echipaRepository.Modifica(updatedEchipa);

        var foundEchipa = echipaRepository.FindById(updatedEchipa.GetId());
        Assert.NotNull(foundEchipa);
        Assert.Equal("Echipa Modificata", foundEchipa.GetNume());

        var echipe = echipaRepository.FindAll();
        Assert.Single(echipe);
        Assert.Equal("Echipa Modificata", echipe[0].GetNume());

        echipaRepository.Sterge(updatedEchipa.GetId());
        foundEchipa = echipaRepository.FindById(updatedEchipa.GetId());
        Assert.Null(foundEchipa);
    }

    public void TestParticipantRepository()
    {
        var participantRepository = GetParticipantRepository();
        var echipaRepository = GetEchipaRepository();
        var cursaRepository = GetCursaRepository();

        var echipa = new Echipa("Echipa Test");
        echipaRepository.Adauga(echipa);

        var cursa = new Cursa("Cursa Test", 5, 10);
        cursaRepository.Adauga(cursa);

        var participant = new Participant("Participant Test", "1234567890123", 2000, echipa);
        participantRepository.Adauga(participant);

        logger.Debug("Participantul a fost adăugat: " + participant.GetId());

        var updatedParticipant = new Participant(participant.GetId(), "Participant Modificat", "9876543210987", 2500, echipa);
        participantRepository.Modifica(updatedParticipant);

        var foundParticipant = participantRepository.FindById(updatedParticipant.GetId());
        Assert.NotNull(foundParticipant);
        Assert.Equal("Participant Modificat", foundParticipant.GetNume());
        Assert.Equal(echipa.GetId(), foundParticipant.GetEchipa().GetId());

        var participants = participantRepository.FindAll();
        Assert.Single(participants);
        Assert.Equal("Participant Modificat", participants[0].GetNume());

        participantRepository.Sterge(updatedParticipant.GetId());
        foundParticipant = participantRepository.FindById(updatedParticipant.GetId());
        Assert.Null(foundParticipant);
        
        echipaRepository.Sterge(echipa.GetId());
        cursaRepository.Sterge(cursa.GetId());
    }

    [Fact]
    public void TestParticipantCursaRepository()
    {
        var participantRepository = GetParticipantRepository();
        var echipaRepository = GetEchipaRepository();
        var cursaRepository = GetCursaRepository();
        var participantCursaRepository = GetParticipantCursaRepository();

        var echipa = new Echipa("Echipa Test");
        echipaRepository.Adauga(echipa);

        var cursa = new Cursa("Cursa Test", 5, 10);
        cursaRepository.Adauga(cursa);

        var participant = new Participant("Participant Test", "1234567890123", 2000, echipa);
        participantRepository.Adauga(participant);

        var participantCursa = new ParticipantCursa(participant, cursa);
        participantCursaRepository.Adauga(participantCursa);

        var participantiInCursa = participantCursaRepository.FindByCursa(cursa);
        Assert.Single(participantiInCursa);
        Assert.Equal(participant.GetId(), participantiInCursa[0].GetParticipant().GetId());

        var curseForParticipant = participantCursaRepository.FindByParticipant(participant);
        Assert.Single(curseForParticipant);
        Assert.Equal(cursa.GetId(), curseForParticipant[0].GetCursa().GetId());

        participantCursaRepository.Sterge(participant.GetId());
        participantiInCursa = participantCursaRepository.FindByCursa(cursa);
        Assert.Empty(participantiInCursa);

        participantRepository.Sterge(participant.GetId());
        echipaRepository.Sterge(echipa.GetId());
        cursaRepository.Sterge(cursa.GetId());
    }
}

