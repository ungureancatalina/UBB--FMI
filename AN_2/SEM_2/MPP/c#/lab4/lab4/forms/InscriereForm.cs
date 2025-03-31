using lab4.service;
using lab4.domain;

namespace lab4
{
    public partial class InscriereForm : Form
    {
        private TextBox? _txtNume;
        private TextBox? _txtCnp;
        private TextBox? _txtEchipa;
        private ComboBox? _cmbCapacitate;
        private Button? _btnInscriere;
        private DataGridView? _dgvParticipanti;
        private Label? _lblNume;
        private Label? _lblCnp;
        private Label? _lblEchipa;
        private Label? _lblCapacitate;
        private CursaService _cursaService;
        private EchipaService _echipaService;
        private ParticipantService _participantService;
        private ParticipantCursaService _participantCursaService;

        public InscriereForm(EchipaService echipaService, CursaService cursaService, ParticipantService participantService, ParticipantCursaService participantCursaService)
        {
            _echipaService = echipaService;
            _cursaService = cursaService;
            _participantService = participantService;
            _participantCursaService = participantCursaService;
            InitializeComponents();
            LoadParticipantCursaList();
        }

        private void InitializeComponents()
        {
            this.StartPosition = FormStartPosition.CenterScreen;
            this.ClientSize = new Size(800, 400);
            this.BackColor = Color.Pink;

            _lblNume = new Label
            {
                Text = "Nume Participant:",
                Left = 50,
                Top = 20,
                Font = new Font("Arial", 12, FontStyle.Bold)
            };

            _txtNume = new TextBox
            {
                Left = 200,
                Top = 20,
                Width = 200,
                Font = new Font("Arial", 12)
            };

            _lblCnp = new Label
            {
                Text = "CNP Participant:",
                Left = 50,
                Top = 60,
                Font = new Font("Arial", 12, FontStyle.Bold)
            };

            _txtCnp = new TextBox
            {
                Left = 200,
                Top = 60,
                Width = 200,
                Font = new Font("Arial", 12)
            };

            _lblEchipa = new Label
            {
                Text = "Nume Echipa:",
                Left = 50,
                Top = 100,
                Font = new Font("Arial", 12, FontStyle.Bold)
            };

            _txtEchipa = new TextBox
            {
                Left = 200,
                Top = 100,
                Width = 200,
                Font = new Font("Arial", 12)
            };

            _lblCapacitate = new Label
            {
                Text = "Capacitate Motor:",
                Left = 50,
                Top = 140,
                Font = new Font("Arial", 12, FontStyle.Bold)
            };

            _cmbCapacitate = new ComboBox
            {
                Left = 200,
                Top = 140,
                Width = 200,
                Font = new Font("Arial", 12)
            };

            for (int i = 600; i < 1100; i += 50)
            {
                _cmbCapacitate.Items.Add(i);
            }

            _cmbCapacitate.BackColor = Color.White;
            _cmbCapacitate.ForeColor = Color.Black;

            _btnInscriere = new Button
            {
                Text = "Inscriere Participant",
                Left = 100,
                Top = 180,
                Width = 300,
                Height = 50,
                Font = new Font("Arial", 12, FontStyle.Bold),
                BackColor = Color.Pink,
                ForeColor = Color.White
            };
            _btnInscriere.Click += BtnInscriere_Click;

            _dgvParticipanti = new DataGridView
            {
                Left = 450,
                Top = 20,
                Width = 300,
                Height = 300,
                Font = new Font("Arial", 12),
                AutoGenerateColumns = true,
                BackgroundColor = Color.White,
                SelectionMode = DataGridViewSelectionMode.FullRowSelect,
                AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill
            };
            Controls.Add(_dgvParticipanti);
            Controls.Add(_lblNume);
            Controls.Add(_txtNume);
            Controls.Add(_lblCnp);
            Controls.Add(_txtCnp);
            Controls.Add(_lblEchipa);
            Controls.Add(_txtEchipa);
            Controls.Add(_lblCapacitate);
            Controls.Add(_cmbCapacitate);
            Controls.Add(_btnInscriere);
        }

        private void BtnInscriere_Click(object? sender, EventArgs e)
        {
            var nume = _txtNume!.Text.Trim();
            var cnp = _txtCnp!.Text.Trim();
            var echipaNume = _txtEchipa!.Text.Trim();
            var capacitateMotoString = _cmbCapacitate!.Text.Trim();
            int capacitateMoto = int.TryParse(capacitateMotoString, out int result) ? result : 0;
            var echipa = _echipaService.FindEchipaByNume(echipaNume);

            var cursa = _cursaService.FindAllCurse().FirstOrDefault(c => c.GetCapacitateMinima() <= capacitateMoto && capacitateMoto <= c.GetCapacitateMaxima());
            if (cursa == null)
            {
                MessageBox.Show("Nu exista o cursa potrivita pentru aceasta capacitate");
                return;
            }
            
            var p=new Participant(nume, cnp, capacitateMoto, echipa);
            _participantService.InscriereParticipant(p);

            _participantCursaService.adaugainbaza(p, cursa);
            LoadParticipantCursaList();
            MessageBox.Show("Participant inscris cu succes");

        }

        private void LoadParticipantCursaList()
        {
            var participanti = _participantCursaService.FindAll().Select(pc => new
                {
                    NumeParticipant = pc.GetParticipant().GetNume(),
                    NumeCursa = pc.GetCursa().GetNume()
                })
                .ToList();
            
            if (participanti.Any())
            {
                if (_dgvParticipanti != null) _dgvParticipanti.DataSource = participanti;
            }
            else
            {
                MessageBox.Show("Nu există participanți inregistrati.");
            }
        }
    }
}
