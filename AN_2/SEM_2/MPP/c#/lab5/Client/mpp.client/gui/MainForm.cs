using System.ComponentModel;
using Model.mpp.model;
using Services.mpp.services;

namespace Client.mpp.client.gui;

public partial class MainForm : Form,IObserver
{
    private DataGridView? _dgvCurse;
    private DataGridView? _dgvParticipanti;
    private TextBox? _txtEchipa;
    private Button? _btnLogout;
    private Button? _btnInscriere;
    private Button? _btnCautaEchipa;
    private Label? _lblEchipa;
    private Label? _lblError;
    private InscriereForm? _inscriereForm;
    
    private IServices server;
    private Utilizator LoggedUser;
    public MainForm()
    {
        InitializeComponent();
    }

    public void SetServer(IServices server)
    {
        this.server = server;
    }

    public void SetLoggedUser(Utilizator loggedUser)
    {
        this.LoggedUser = loggedUser;
        ActualizeazaDate();
    }

     public void ActualizeazaDate()
     {
            LoadCurse();
            LoadParticipanti();
        }

        public void Adauga(ParticipantCursa participantCursa)
        {
            this.BeginInvoke((MethodInvoker) delegate
            {
                LoadCurse();
                LoadParticipanti();
                if (_inscriereForm != null && !_inscriereForm.IsDisposed)
                {
                    _inscriereForm.Adauga(participantCursa);
                }
            });
        }
        
        private void LoadCurse()
        {
            try
            {
                var curseDto = server.ListDtoCurse();
                if (curseDto == null)
                {
                    Console.WriteLine("curseDto este null!");
                    return;
                }
                _dgvCurse.AutoGenerateColumns = true;
                _dgvCurse.DataSource = null; 
                _dgvCurse.DataSource = curseDto;
            }
            catch (Exception e)
            {
                Console.WriteLine("LoadCurse error: " + e.Message);
            }
        }


        private void LoadParticipanti()
        {
            try
            {
                var participanti = server.FindAllParticipanti()
                    .Select(p => new ParticipantDTO(
                        p.Nume,
                        p.CapacitateMotor,
                        p.Echipa.Nume))
                    .ToList();
                _dgvParticipanti.AutoGenerateColumns = true;
                _dgvParticipanti.DataSource = null; 
                _dgvParticipanti.DataSource = participanti;
            }
            catch (Exception e)
            {
                Console.WriteLine("LoadParticipanti error: " + e.Message);
            }
        }

        private void BtnCautaEchipa_Click(object? sender, EventArgs e)
        {
            var echipaCauta = _txtEchipa!.Text.Trim();
            try
            {
                if (string.IsNullOrEmpty(echipaCauta))
                {
                    var totiParticipanti = server.FindAllParticipanti()
                        .Select(p => new ParticipantDTO(p.Nume, p.CapacitateMotor, p.Echipa.Nume))
                        .ToList();

                    _dgvParticipanti!.DataSource = new BindingList<ParticipantDTO>(totiParticipanti);
                    _dgvParticipanti.Refresh();
                }
                else
                {
                    var participantiEchipa = server.CautaParticipantiDupaEchipa(echipaCauta)
                        .Select(p => new ParticipantDTO(p.Nume, p.CapacitateMotor, p.Echipa.Nume))
                        .ToList();

                    if (participantiEchipa.Count == 0)
                    {
                        MessageBox.Show("Echipa nu a fost gasita.");
                    }
                    else
                    {
                        _dgvParticipanti!.DataSource = new BindingList<ParticipantDTO>(participantiEchipa);
                        _dgvParticipanti.Refresh();
                    }
                }
            }
            catch (Exception ex)
            {
                _lblError.Text = "Eroare: " + ex.Message;
                _lblError.Visible = true;
            }
        }


        private void btnInscriere_Click(object? sender, EventArgs e)
        {
            try
            {
                _inscriereForm = new InscriereForm();
                _inscriereForm.SetServer(server);
                _inscriereForm.SetMainForm(this);
                _inscriereForm.Text = LoggedUser.Nume;
                _inscriereForm.Show();
            }
            catch (Exception ex)
            {
                MessageBox.Show("A aparut o eroare. Te rugam sa incerci din nou.");
            }
        }

        private void btnLogout_Click(object? sender, EventArgs e)
        {
            try
            {
                server.Logout(LoggedUser, this); 

                if (_inscriereForm != null && !_inscriereForm.IsDisposed)
                {
                    server.Logout(LoggedUser, _inscriereForm); 
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Logout error: " + ex.Message);
            }

            Application.Exit();
        }
}
