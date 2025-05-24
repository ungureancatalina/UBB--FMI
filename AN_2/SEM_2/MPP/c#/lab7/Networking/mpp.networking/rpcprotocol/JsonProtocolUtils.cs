using System.Collections.Generic;
using Model.mpp.model;

namespace Networking.mpp.networking.rpcprotocol
{
    public static class JsonProtocolUtils
    {
        public static Response CreateOkResponse()
        {
            return new Response
            {
                type = ResponseType.OK
            };
        }

        public static Response CreateErrorResponse(string errorMessage)
        {
            return new Response
            {
                type = ResponseType.ERROR,
                errorMessage = errorMessage
            };
        }

        public static Response CreateGetAllCurseResponse(List<Cursa> curse)
        {
            return new Response
            {
                type = ResponseType.GET_ALL_CURSE,
                curse = curse
            };
        }

        public static Response CreateListDtoCurseResponse(List<CursaDTO> curseDtos)
        {
            return new Response
            {
                type = ResponseType.LIST_DTO_CURSE,
                curseDTO = curseDtos
            };
        }

        public static Response CreateGetAllParticipantiResponse(List<Participant> participanti)
        {
            return new Response
            {
                type = ResponseType.GET_ALL_PARTICIPANTI,
                participanti = participanti
            };
        }

        public static Response CreateCautaParticipantiEchipaResponse(List<Participant> participanti)
        {
            return new Response
            {
                type = ResponseType.CAUTA_PARTICIPANTI_ECHIPA,
                participanti = participanti
            };
        }

        public static Response CreateNewParticipantCursaResponse(ParticipantCursa participantCursa)
        {
            return new Response
            {
                type = ResponseType.NEW_PARTICIPANT,
                participantCursa = new ParticipantCursa(participantCursa.Participant,participantCursa.Cursa)
            };
        }
        
        public static Response CreateFindEchipaByNumeResponse(Echipa echipa)
        {
            return new Response
            {
                type = ResponseType.FIND_ECHIPA_BY_NUME,
                echipa = echipa
            };
        }

        public static Response CreateFindNrPartByCursaResponse(int numberOfParticipants)
        {
            return new Response
            {
                type = ResponseType.FIND_NR_PART_BY_CURSA,
                nrparticipant = numberOfParticipants
            };
        }

        public static Response CreateGetAllParticipantCursaResponse(List<ParticipantCursa> participantCurse)
        {
            return new Response
            {
                type = ResponseType.GET_ALL_PARTICIPANT_CURSA,
                participantiCurse = participantCurse
            };
        }

        public static Request CreateLoginRequest(Utilizator utilizator)
        {
            return new Request
            {
                type = RequestType.LOGIN,
                utilizator = utilizator
            };
        }

        public static Request CreateInscriereParticipantRequest(Participant participant)
        {
            return new Request
            {
                type = RequestType.INSCRIERE_PARTICIPANT,
                participant = participant
            };
        }

        public static Request CreateCautaParticipantiDupaEchipaRequest(string echipaNume)
        {
            return new Request
            {
                type = RequestType.CAUTA_PARTICIPANTI_ECHIPA,
                numeEchipa = echipaNume
            };
        }

        public static Request CreateFindEchipaByNumeRequest(string nume)
        {
            return new Request
            {
                type = RequestType.FIND_ECHIPA_BY_NUME,
                numeEchipa = nume
            };
        }

        public static Request CreateAdaugaEchipaRequest(Echipa echipa)
        {
            return new Request
            {
                type = RequestType.ADAUGA_ECHIPA,
                echipa = echipa
            };
        }

        public static Request CreateFindNrPartByCursaRequest(Cursa cursa)
        {
            return new Request
            {
                type = RequestType.FIND_NR_PART_BY_CURSA,
                cursa = cursa
            };
        }

        public static Request CreateAdaugaParticipantLaCursaRequest(ParticipantCursa participantCursa)
        {
            return new Request
            {
                type = RequestType.ADAUGA_PARTICIPANT_LA_CURSA,
                participantCursa = participantCursa
            };
        }


        public static Request CreateGetAllCurseRequest()
        {
            return new Request
            {
                type = RequestType.GET_ALL_CURSE
            };
        }

        public static Request CreateGetAllParticipantiRequest()
        {
            return new Request
            {
                type = RequestType.GET_ALL_PARTICIPANTI
            };
        }

        public static Request CreateGetAllParticipantCurseRequest()
        {
            return new Request
            {
                type = RequestType.GET_ALL_PARTICIPANT_CURSA
            };
        }
        
        public static Request CreateListDtoCurseRequest()
        {
            return new Request
            {
                type = RequestType.LIST_DTO_CURSE
            };
        }
        public static Request CreateFindParticipantByNumeRequest(string nume)
        {
            return new Request
            {
                type = RequestType.FIND_PARTICIPANT_BY_NUME,
                numeParticipant = nume
            };
        }

        public static Response CreateFindParticipantByNumeResponse(Participant participant)
        {
            return new Response
            {
                type = ResponseType.FIND_PARTICIPANT_BY_NUME,
                participant = participant
            };
        }


    }
}