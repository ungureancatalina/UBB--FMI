using lab4.domain;
using log4net;

namespace lab4.service;

public class CursaService(ICursaRepository cursaRepo, ParticipantCursaService participantCursaService)
{
    private static readonly ILog Logger = LogManager.GetLogger(typeof(CursaService));
    private readonly ParticipantCursaService _participantCursaService = participantCursaService;

    public List<Cursa> FindAllCurse()
    {
        Logger.Debug("Incep cautarea tuturor curselor.");
        return cursaRepo.FindAll();
    }
    
    public List<CursaDto> ListDtoCurse(ParticipantCursaService participantCursaService)
    {
        var curse = FindAllCurse();
        if (curse==null && !curse.Any())
        {
            return [];  
        }

        var lista = new List<CursaDto>();
        foreach (var c in curse)
        {
            var nrPart = participantCursaService.FindNrPartByCursa(c);
            var dto = new CursaDto(c.GetNume(), c.GetCapacitateMinima(), c.GetCapacitateMaxima(), nrPart);
            lista.Add(dto);
        }
        return lista;
    }

}