using System.ComponentModel;
using lab4.domain;
using lab4.service;
using log4net;

namespace lab4;

public partial class MainForm : Form
{
    private static readonly ILog Logger = LogManager.GetLogger(typeof(MainForm));

    private DataGridView? _dgvCurse;
    private DataGridView? _dgvParticipanti;
    private TextBox? _txtEchipa;
    private Button? _btnLogout;
    private Button? _btnInscriere;
    private Button? _btnCautaEchipa;
    private Label? _lblEchipa;

    private readonly CursaService _cursaService;
    private readonly EchipaService _echipaService;
    private readonly ParticipantService _participantService;
    private readonly ParticipantCursaService _participantCursaService;

    public MainForm(EchipaService echipaService, CursaService cursaService, ParticipantService participantService, ParticipantCursaService participantCursaService)
    {
        _echipaService = echipaService;
        _cursaService = cursaService;
        _participantService = participantService;
        _participantCursaService = participantCursaService;

        InitializeComponents();
        LoadCurse();
        LoadParticipanti();
    }

    private void InitializeComponents()
    {
        this.StartPosition = FormStartPosition.CenterScreen;
        this.ClientSize = new Size(1100, 400);
        this.BackColor = Color.Pink;

        _lblEchipa = new Label
        {
            Text = "Numele Echipei:",
            Left = 500,
            Top = 300,
            Width = 150,
            Font = new Font("Arial", 12, FontStyle.Bold)
        };

        _txtEchipa = new TextBox
        {
            Left = 650,
            Top = 300,
            Width = 200,
            Font = new Font("Arial", 12),
            PlaceholderText = "Echipa"
        };

        _btnCautaEchipa = new Button
        {
            Text = "Căutare",
            Left = 880,
            Top = 300,
            Width = 100,
            Height = 40,
            Font = new Font("Arial", 12, FontStyle.Bold),
            BackColor = Color.Pink,
            ForeColor = Color.White
        };
        _btnCautaEchipa.Click += BtnCautaEchipa_Click;

        _dgvCurse = new DataGridView
        {
            Left = 20,
            Top = 20,
            Width = 500,
            Height = 230,
            Font = new Font("Arial", 12),
            AutoGenerateColumns = true,
            BackgroundColor = Color.White,
            SelectionMode = DataGridViewSelectionMode.FullRowSelect,
            AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill
        };

        _dgvParticipanti = new DataGridView
        {
            Left = 550,
            Top = 20,
            Width = 500,
            Height = 230,
            Font = new Font("Arial", 12),
            AutoGenerateColumns = true,
            BackgroundColor = Color.White,
            SelectionMode = DataGridViewSelectionMode.FullRowSelect,
            AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill
        };

        _btnLogout = new Button
        {
            Text = "Logout",
            Left = 980,
            Top = 350,
            Width = 100,
            Height = 40,
            Font = new Font("Arial", 12, FontStyle.Bold),
            BackColor = Color.Pink,
            ForeColor = Color.White
        };
        _btnLogout.Click += (_, _) =>
        {
            Logger.Info("Utilizatorul s-a delogat. Aplicatia se va inchide.");
            this.Hide();
            Application.Exit();
        };

        _btnInscriere = new Button
        {
            Text = "Inscriere Participant",
            Left = 100,
            Top = 300,
            Width = 150,
            Height = 40,
            Font = new Font("Arial", 12, FontStyle.Bold),
            BackColor = Color.Pink,
            ForeColor = Color.White
        };
        _btnInscriere.Click += btnInscriere_Click;

        Controls.Add(_dgvCurse);
        Controls.Add(_dgvParticipanti);
        Controls.Add(_lblEchipa);
        Controls.Add(_txtEchipa);
        Controls.Add(_btnCautaEchipa);
        Controls.Add(_btnLogout);
        Controls.Add(_btnInscriere);
    }

    private void LoadCurse()
    {
        var curseDto = _cursaService.ListDtoCurse(_participantCursaService);
        _dgvCurse!.DataSource = curseDto;
    }

    private void LoadParticipanti()
    {
        var participanti = _participantService.FindAllParticipanti()
            .Select(p => new ParticipantDTO(p.GetNume(), p.GetCapacitateMotor(), p.GetEchipa().GetNume()))
            .ToList();

        _dgvParticipanti!.DataSource = participanti;
    }

    private void BtnCautaEchipa_Click(object? sender, EventArgs e)
    {
        var echipaCauta = _txtEchipa!.Text.Trim();
        if (string.IsNullOrEmpty(echipaCauta)) return;
        var participantiEchipa = _participantService.CautaParticipantiDupaEchipa(echipaCauta)
            .Select(p => new ParticipantDTO(p.GetNumeDto(), p.GetCapacitateMotoDto(), p.GetEchipaDto()))
            .ToList();

        _dgvParticipanti!.DataSource = new BindingList<ParticipantDTO>(participantiEchipa);
        _dgvParticipanti.Refresh();
    }
    
    private void btnInscriere_Click(object? sender, EventArgs e)
    {
        try
        {
            var inscriereForm = new InscriereForm(_echipaService, _cursaService, _participantService, _participantCursaService);
            inscriereForm.Show();
        }
        catch (Exception ex)
        {
            MessageBox.Show("A aparut o eroare. Te rugam sa incerci din nou.");
            Logger.Error("Eroare la deschidere fereastra", ex);
        } 
    }
}
