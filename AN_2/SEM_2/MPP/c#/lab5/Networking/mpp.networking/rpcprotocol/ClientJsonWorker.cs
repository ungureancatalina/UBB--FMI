using System;
using System.IO;
using System.Net.Sockets;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Threading;
using Model.mpp.model;
using Services.mpp.services;
using Exception = System.Exception;

namespace Networking.mpp.networking.rpcprotocol
{
    public class ClientJsonWorker : IObserver
    {
        private IServices server;
        private TcpClient connection;
        private NetworkStream stream;
        private volatile bool connected;

        public ClientJsonWorker(IServices server, TcpClient connection)
        {
            this.server = server;
            this.connection = connection;
            try
            {
                stream = connection.GetStream();
                connected = true;
            }
            catch (IOException e)
            {
                Console.WriteLine("Stream initialization error: " + e.StackTrace);
            }
        }

        public void Run()
        {
            using StreamReader reader = new StreamReader(stream);
            while (connected)
            {
                try
                {
                    string requestJson = reader.ReadLine();
                    if (string.IsNullOrEmpty(requestJson)) continue;
                    Request request = JsonSerializer.Deserialize<Request>(requestJson);
                    Response response = HandleRequest(request);
                    if (response != null)
                    {
                        SendResponse(response);
                    }
                }
                catch (IOException e)
                {
                    Console.WriteLine("IOException: " + e.StackTrace);
                }
                Thread.Sleep(1000);
            }

            try
            {
                stream.Close();
                connection.Close();
            }
            catch (IOException e)
            {
                Console.WriteLine("Error closing resources: " + e.StackTrace);
            }
        }

        private void SendResponse(Response response)
        {
            string json = JsonSerializer.Serialize(response);
            lock (stream)
            {
                byte[] data = Encoding.UTF8.GetBytes(json + "\n");
                stream.Write(data, 0, data.Length);
                stream.Flush();
            }
        }


        public void Adauga(ParticipantCursa participantCursa)
        {
            Response response = JsonProtocolUtils.CreateNewParticipantCursaResponse(participantCursa);
            try
            {
                SendResponse(response);
            }
            catch (IOException e)
            {
                throw new Exception("Sending error: " + e);
            }
        }

        private static Response okResponse = JsonProtocolUtils.CreateOkResponse();

        private Response HandleRequest(Request request)
        {
            Response response = null;

            switch (request.type)
            {
                case RequestType.LOGIN:
                    response = HandleLoginRequest(request);
                    break;
                case RequestType.LOGOUT:
                    response = HandleLogoutRequest(request);
                    break;
                case RequestType.GET_ALL_CURSE:
                    response = HandleGetAllCurseRequest();
                    break;
                case RequestType.FIND_ECHIPA_BY_NUME:
                    response = HandleFindEchipaByNumeRequest(request);
                    break;
                case RequestType.GET_ALL_PARTICIPANTI:
                    response = HandleGetAllParticipantiRequest();
                    break;
                case RequestType.LIST_DTO_CURSE: 
                    response = HandleListDtoCurseRequest(request);
                    break;
                case RequestType.CAUTA_PARTICIPANTI_ECHIPA:
                    response = HandleCautaParticipantiEchipaRequest(request);
                    break;
                case RequestType.ADAUGA_ECHIPA:
                    response = HandleAdaugaEchipaRequest(request);
                    break;
                case RequestType.FIND_NR_PART_BY_CURSA:
                    response = HandleFindNrPartByCursaRequest(request);
                    break;
                case RequestType.GET_ALL_PARTICIPANT_CURSA:
                    response = HandleGetAllParticipantCursaRequest();
                    break;
                case RequestType.ADAUGA_PARTICIPANT_LA_CURSA:
                    response = HandleAdaugaParticipantLaCursaRequest(request);
                    break;
                case RequestType.INSCRIERE_PARTICIPANT:
                    response = HandleInscriereParticipantRequest(request);
                    break;
                case RequestType.FIND_PARTICIPANT_BY_NUME:
                    response = HandleFindParticipantByNumeRequest(request);
                    break;
                default:
                    response = JsonProtocolUtils.CreateErrorResponse("Unknown request type.");
                    break;
            }

            return response;
        }

        private Response HandleLoginRequest(Request request)
        {
            Utilizator utilizator = request.utilizator;

            if (utilizator == null || utilizator.Nume == null || utilizator.Parola == null)
                return JsonProtocolUtils.CreateErrorResponse("Login failed: utilizator invalid.");

            try
            {
                lock (server)
                    server.Login(utilizator, this);
                return okResponse;
            }
            catch (Exception e)
            {
                return JsonProtocolUtils.CreateErrorResponse("Login failed: " + e.Message);
            }
        }
        
        private Response HandleFindParticipantByNumeRequest(Request request)
        {
            string nume = request.numeParticipant;
            if (string.IsNullOrEmpty(nume))
                return JsonProtocolUtils.CreateErrorResponse("Nume participant lipsă.");

            try
            {
                Participant participant;
                lock (server)
                {
                    participant = server.FindByNume(nume);
                }

                if (participant == null)
                    return JsonProtocolUtils.CreateErrorResponse("Participantul nu a fost găsit.");

                return JsonProtocolUtils.CreateFindParticipantByNumeResponse(participant);
            }
            catch (Exception e)
            {
                return JsonProtocolUtils.CreateErrorResponse("Eroare căutare participant: " + e.Message);
            }
        }

        
        private Response HandleFindEchipaByNumeRequest(Request request)
        {
            try
            {
                var echipa = server.FindEchipaByNume(request.numeEchipa);
                return JsonProtocolUtils.CreateFindEchipaByNumeResponse(echipa);
            }
            catch (Exception e)
            {
                return JsonProtocolUtils.CreateErrorResponse("Eroare căutare echipă: " + e.Message);
            }
        }
        
        private Response HandleListDtoCurseRequest(Request request)
        {
            try
            {
                List<CursaDTO> curseDTOs;
                lock (server)
                {
                    curseDTOs = server.ListDtoCurse();
                }

                return JsonProtocolUtils.CreateListDtoCurseResponse(curseDTOs);
            }
            catch (Exception e)
            {
                return JsonProtocolUtils.CreateErrorResponse("Error handling LIST_DTO_CURSE request: " + e.Message);
            }
        }
        private Response HandleGetAllCurseRequest()
        {
            try
            {
                List<Cursa> curse;
                lock (server)
                {
                    curse = server.FindAllCurse();
                }

                return JsonProtocolUtils.CreateGetAllCurseResponse(curse);
            }
            catch (Exception e)
            {
                return JsonProtocolUtils.CreateErrorResponse("Error retrieving courses: " + e.Message);
            }
        }

        private Response HandleGetAllParticipantiRequest()
        {
            try
            {
                List<Participant> participanti;
                lock (server)
                {
                    participanti = server.FindAllParticipanti();
                }

                return JsonProtocolUtils.CreateGetAllParticipantiResponse(participanti);
            }
            catch (Exception e)
            {
                return JsonProtocolUtils.CreateErrorResponse("Error retrieving participants: " + e.Message);
            }
        }

        private Response HandleCautaParticipantiEchipaRequest(Request request)
        {
            string echipa = request.numeEchipa;

            if (echipa == null)
            {
                return JsonProtocolUtils.CreateErrorResponse("Echipa name is null.");
            }

            try
            {
                List<Participant> participanti;
                lock (server)
                {
                    participanti = server.CautaParticipantiDupaEchipa(echipa);
                }

                return JsonProtocolUtils.CreateCautaParticipantiEchipaResponse(participanti);
            }
            catch (Exception e)
            {
                return JsonProtocolUtils.CreateErrorResponse("Error searching participants: " + e.Message);
            }
        }

        private Response HandleAdaugaEchipaRequest(Request request)
        {
            Echipa echipa = request.echipa;

            if (echipa == null)
            {
                return JsonProtocolUtils.CreateErrorResponse("Invalid Echipa data.");
            }

            try
            {
                lock (server)
                    server.AdaugaEchipa(echipa);
                return JsonProtocolUtils.CreateOkResponse();
            }
            catch (Exception e)
            {
                return JsonProtocolUtils.CreateErrorResponse("Error adding team: " + e.Message);
            }
        }
        
        

        private Response HandleFindNrPartByCursaRequest(Request request)
        {
            Cursa cursa = request.cursa;

            if (cursa == null)
            {
                return JsonProtocolUtils.CreateErrorResponse("Invalid Cursa data.");
            }

            try
            {
                int nrParticipanti;
                lock (server)
                {
                    nrParticipanti = server.FindNrPartByCursa(cursa);
                }

                return JsonProtocolUtils.CreateFindNrPartByCursaResponse(nrParticipanti);
            }
            catch (Exception e)
            {
                return JsonProtocolUtils.CreateErrorResponse("Error retrieving number of participants: " + e.Message);
            }
        }

        private Response HandleGetAllParticipantCursaRequest()
        {
            try
            {
                List<ParticipantCursa> participantCurse;
                lock (server)
                {
                    participantCurse = server.FindAllParticipantCursa();
                }

                return JsonProtocolUtils.CreateGetAllParticipantCursaResponse(participantCurse);
            }
            catch (Exception e)
            {
                return JsonProtocolUtils.CreateErrorResponse("Error retrieving participants for race: " + e.Message);
            }
        }
        
        private Response HandleLogoutRequest(Request request)
        {
            try
            {
                lock (server)
                {
                    server.Logout(request.utilizator, this);
                }

                connected = false; 
                return JsonProtocolUtils.CreateOkResponse();
            }
            catch (Exception e)
            {
                return JsonProtocolUtils.CreateErrorResponse("Logout failed: " + e.Message);
            }
        }



        private Response HandleAdaugaParticipantLaCursaRequest(Request request)
        {

            ParticipantCursa participantCurse = request.participantCursa;

            if (participantCurse == null)
            {
                return JsonProtocolUtils.CreateErrorResponse("Invalid participant or race data.");
            }

            try
            {
                lock (server)
                    server.AdaugaParticipantLaCursa(participantCurse);
                return JsonProtocolUtils.CreateOkResponse();
            }
            catch (Exception e)
            {
                return JsonProtocolUtils.CreateErrorResponse("Error adding participant to race: " + e.Message);
            }
        }
        
        private Response HandleInscriereParticipantRequest(Request request)
        {
            Participant participant = request.participant;
            if (participant == null)
            {
                return JsonProtocolUtils.CreateErrorResponse("Invalid participant data.");
            }
            try
            {
                lock (server)
                {
                    server.InscriereParticipant(participant);
                }
                return JsonProtocolUtils.CreateOkResponse();
            }
            catch (Exception e)
            {
                return JsonProtocolUtils.CreateErrorResponse("Inscriere failed: " + e.Message);
            }
        }
    }
}
