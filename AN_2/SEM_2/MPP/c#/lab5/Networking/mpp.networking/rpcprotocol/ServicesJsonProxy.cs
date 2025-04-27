using System;
using System.IO;
using System.Net.Sockets;
using System.Text.Json;
using System.Threading;
using Model.mpp.model;
using Services.mpp.services;
using System.Collections.Generic;
using System.Text.Json.Serialization;
using Networking.mpp.networking.rpcprotocol;

namespace Networking.mpp.networking.jsonprotocol
{
    public class ServicesJsonProxy : IServices
    {
        private string host;
        private int port;
        private IObserver client;
        private NetworkStream stream;
        private TcpClient connection;
        private Queue<Response> qresponses;
        private volatile bool finished;
        private EventWaitHandle waitHandle;

        public ServicesJsonProxy(string host, int port)
        {
            this.host = host;
            this.port = port;
            qresponses = new Queue<Response>();
            try {
                initializeConnection();  
            } catch (Exception e) {
                Console.WriteLine(e.StackTrace);
            }
        }
        
        private void initializeConnection()
        {
            try
            {
                connection = new TcpClient(host, port);
                stream = connection.GetStream();
                finished = false;
                waitHandle = new AutoResetEvent(false);
                StartReader();
            }
            catch (Exception e)
            {
                if (e.InnerException != null) ;
            }
        }

        private void SendRequest(Request request)
        {
            try
            {
                lock (stream)
                {
                    string json = JsonSerializer.Serialize(request);
                    byte[] bytes = Encoding.UTF8.GetBytes(json + "\n");
                    stream.Write(bytes, 0, bytes.Length);
                    stream.Flush();
                }
            }
            catch (Exception e)
            {
                throw new Exception("Error sending request " + e);
            }
        }

        private Response ReadResponse()
        {
            Response response = null;

            waitHandle.WaitOne();

            lock (qresponses)
            {
                if (qresponses.Count > 0)
                    response = qresponses.Dequeue();
            }
            return response;
        }


        private void StartReader()
        {
            Thread tw = new Thread(run);
            tw.Start();
        }
        
        private void HandleUpdate(Response response)
        {
            if (response. type == ResponseType.NEW_PARTICIPANT)
            {
                ParticipantCursa pc = response.participantCursa;
                try
                {
                    client.Adauga(pc);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }
        }

        private bool IsUpdate(Response response)
        {
            return response. type == ResponseType.NEW_PARTICIPANT;
        }
        
        private void run()
        {
            using StreamReader streamReader = new StreamReader(stream, Encoding.UTF8);
            while (!finished)
            {
                try
                {
                    string json = streamReader.ReadLine();
                    if(string.IsNullOrEmpty(json))
                        continue;
                    Response response = JsonSerializer.Deserialize<Response>(json);
                    if (IsUpdate(response))
                    {
                        HandleUpdate(response);
                    }
                    else
                    {
                        lock (qresponses)
                        {
                            qresponses.Enqueue(response);
                        }
                        waitHandle.Set();
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine("Error reading response {0}", e.Message);
                    if (e.InnerException != null) ;
                }
            }
        }


        public void CloseConnection()
        {
            finished = true;
            try
            {
                stream.Close();
                connection.Close();
                waitHandle.Close();
                client = null;
            }
            catch (Exception e)
            {
                Console.WriteLine("Error " + e);
            }
        }

        public void Login(Utilizator utilizator, IObserver client)
        {
            initializeConnection();
            Request req = JsonProtocolUtils.CreateLoginRequest(utilizator);
            SendRequest(req);
            Response response = ReadResponse();

            if (response. type == ResponseType.OK)
            {
                this.client = client;
            }
            else
            {
                string err = response.errorMessage .ToString();
                throw new Exception(err);
            }
        }

        public List<Participant> FindAllParticipanti()
        {
            Request req = JsonProtocolUtils.CreateGetAllParticipantiRequest();
            SendRequest(req);
            Response response = ReadResponse();
            if (response. type == ResponseType.ERROR)
            {
                string err = response.errorMessage .ToString();
                throw new Exception(err);
            }
            return response.participanti;
        }

        public List<Participant> CautaParticipantiDupaEchipa(string numeEchipa)
        {
            Request req = JsonProtocolUtils.CreateCautaParticipantiDupaEchipaRequest(numeEchipa);
            SendRequest(req);
            Response response = ReadResponse();
    
            if (response. type == ResponseType.ERROR)
            {
                return new List<Participant>(); 
            }

            return response.participanti;
        }

        public Echipa FindEchipaByNume(string nume)
        {
            Request req = JsonProtocolUtils.CreateFindEchipaByNumeRequest(nume);
            SendRequest(req);
            Response response = ReadResponse();
    
            if (response. type == ResponseType.ERROR)
            {
                string err = response.errorMessage .ToString();
                throw new Exception(err);
            }

            return response.echipa;
        }

        public void AdaugaEchipa(Echipa echipa)
        {
            Request req = JsonProtocolUtils.CreateAdaugaEchipaRequest(echipa);
            SendRequest(req);
            Response response = ReadResponse();
    
            if (response. type == ResponseType.ERROR)
            {
                string err = response.errorMessage .ToString();
                throw new Exception(err);
            }
        }

        public List<Cursa> FindAllCurse()
        {
            Request req = JsonProtocolUtils.CreateGetAllCurseRequest();
            SendRequest(req);
            Response response = ReadResponse();
            if (response. type == ResponseType.ERROR)
            {
                string err = response.errorMessage .ToString();
                throw new Exception(err);
            }
            return response.curse;
        }

        public List<CursaDTO> ListDtoCurse()
        {
            Request req = JsonProtocolUtils.CreateListDtoCurseRequest();
            SendRequest(req);
            Response response = ReadResponse();
    
            if (response. type == ResponseType.ERROR)
            {
                string err = response.errorMessage .ToString();
                throw new Exception(err);
            }

            return response.curseDTO;
        }

        public int FindNrPartByCursa(Cursa cursa)
        {
            Request req = JsonProtocolUtils.CreateFindNrPartByCursaRequest(cursa);
            SendRequest(req);
            Response response = ReadResponse();
    
            if (response. type == ResponseType.ERROR)
            {
                string err = response.errorMessage .ToString();
                throw new Exception(err);
            }

            return response.nrparticipant;
        }

        public void AdaugaParticipantLaCursa(ParticipantCursa participantCursa)
        {
            Request req = JsonProtocolUtils.CreateAdaugaParticipantLaCursaRequest(participantCursa);
            SendRequest(req);
            Response response = ReadResponse();
    
            if (response. type == ResponseType.ERROR)
            {
                string err = response.errorMessage .ToString();
                throw new Exception(err);
            }
        }
        
        public Participant FindByNume(string nume)
        {
            Request req = JsonProtocolUtils.CreateFindParticipantByNumeRequest(nume);
            SendRequest(req);
            Response response = ReadResponse();

            if (response. type == ResponseType.ERROR)
            {
                throw new Exception(response.errorMessage );
            }

            return response.participant;
        }

        public List<ParticipantCursa> FindAllParticipantCursa()
        {
            Request req = JsonProtocolUtils.CreateGetAllParticipantCurseRequest();
            SendRequest(req);
            Response response = ReadResponse();
            if (response. type == ResponseType.ERROR)
            {
                string err = response.errorMessage .ToString();
                throw new Exception(err);
            }
            return response.participantiCurse;
        }

        public void Logout(Utilizator utilizator, IObserver client)
        {
            try
            {
                Request req = new Request
                {
                     type = RequestType.LOGOUT,
                    utilizator = utilizator
                };

                SendRequest(req);
                ReadResponse(); 
            }
            catch (Exception e)
            {
                Console.WriteLine("Logout failed: " + e.Message);
            }
            finally
            {
                CloseConnection();
            }
        }

        public void InscriereParticipant(Participant participant)
        {
            Request req = JsonProtocolUtils.CreateInscriereParticipantRequest(participant);
            SendRequest(req);
            Response response = ReadResponse();
            if (response. type == ResponseType.ERROR)
            {
                string err = response.errorMessage .ToString();
                throw new Exception(err);
            }
        }
    }
}
