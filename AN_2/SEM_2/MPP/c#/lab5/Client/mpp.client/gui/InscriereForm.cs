using Model.mpp.model;
using Services.mpp.services;

namespace Client.mpp.client.gui
{
    public partial class InscriereForm : Form,IObserver
    {
        private TextBox? _txtNume;
        private TextBox? _txtCnp;
        private TextBox? _txtEchipa;
        private ComboBox? _cmbCapacitate;
        private Button? _btnInscriere;
        private DataGridView? _dgvParticipantiCurse;
        private Label? _lblNume;
        private Label? _lblCnp;
        private Label? _lblEchipa;
        private Label? _lblCapacitate;
        private IServices _server;
        private MainForm _mainForm;

        public void SetServer(IServices server)
        {
            _server = server;
            LoadParticipantCursaList();
        }

        public void SetMainForm(MainForm mainForm)
        {
            _mainForm = mainForm;
        }
        
        public InscriereForm()
        {
            InitializeComponent();
        }

        private void BtnInscriere_Click(object? sender, EventArgs e)
        {
            string nume = _txtNume!.Text.Trim();
            string cnp = _txtCnp!.Text.Trim();
            string echipaNume = _txtEchipa!.Text.Trim();
            if (!int.TryParse(_cmbCapacitate!.Text.Trim(), out int capacitateMoto))
            {
                MessageBox.Show("Capacitate invalidă.");
                return;
            }
            try
            {
                Echipa? echipa = _server.FindEchipaByNume(echipaNume);
                if (echipa == null)
                {
                    echipa = new Echipa(echipaNume);
                    _server.AdaugaEchipa(echipa);
                }
                _server.InscriereParticipant( new Participant(nume, cnp, capacitateMoto, echipa));
                Participant participant=_server.FindByNume(nume);
                
                var curse = _server.FindAllCurse();
                
                
                if (curse == null)
                {
                    MessageBox.Show("Nu exista o curse");
                    return;
                }

                foreach (Cursa cursa in curse)
                {
                    if (cursa.CapacitateMinima <= capacitateMoto && capacitateMoto <= cursa.CapacitateMaxima)
                    {
                        ParticipantCursa participantCursa = new ParticipantCursa(participant, cursa);
                        _server.AdaugaParticipantLaCursa(participantCursa);
                        break;
                    }
                }

                LoadParticipantCursaList();
                _mainForm.ActualizeazaDate();
            }
            catch (Exception ex)
            {
                MessageBox.Show("Eroare la înscriere: " + ex.Message);
            }
        }

        private void LoadParticipantCursaList()
        {
            try
            {
                var participanticurse = _server.FindAllParticipantCursa() ?? new List<ParticipantCursa>();
                
                var listaDTO = participanticurse.Select(pc => new
                {
                    Participant = pc.Participant?.Nume ?? "N/A",
                    Cursa = pc.Cursa?.Nume ?? "N/A"
                }).ToList();

                _dgvParticipantiCurse.AutoGenerateColumns = true;
                _dgvParticipantiCurse.DataSource = null;
                _dgvParticipantiCurse.DataSource = listaDTO;
            }
            catch (Exception ex)
            {
                MessageBox.Show("Eroare la încărcare participanți curse: " + ex.Message);
            }
        }
        
        public void Adauga(ParticipantCursa participantCursa)
        {
            this.BeginInvoke((MethodInvoker) delegate
            {
                LoadParticipantCursaList(); 
            });
        }
    }
}
