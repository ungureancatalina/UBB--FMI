using lab4.service;
using log4net;

namespace lab4;

public partial class LoginForm : Form
{
    private static readonly ILog Logger = LogManager.GetLogger(typeof(LoginForm));

    private TextBox? _txtUsername;
    private TextBox? _txtPassword;
    private Button? _btnLogin;
    private Label? _lblPassword;
    private Label? _lblUsername;

    private readonly UtilizatorService _utilizatorService;
    private readonly CursaService _cursaService;
    private readonly EchipaService _echipaService;
    private readonly ParticipantService _participantService;
    private readonly ParticipantCursaService _participantCursaService;

    public LoginForm(
        UtilizatorService utilizatorService,
        EchipaService echipaService,
        ParticipantService participantService,
        ParticipantCursaService participantCursaService,
        CursaService cursaService)
    {
        _utilizatorService = utilizatorService;
        _cursaService = cursaService;
        _participantService = participantService;
        _participantCursaService = participantCursaService;
        _echipaService = echipaService;

        InitializeComponents();
    }

    private void InitializeComponents()
    {
        this.StartPosition = FormStartPosition.CenterScreen;
        this.ClientSize = new Size(400, 250);
        this.BackColor = Color.FromArgb(255, 204, 229);

        _lblUsername = new Label
        {
            Text = "Username",
            Left = 50,
            Top = 50,
            Width = 100,
            Font = new Font("Arial", 12, FontStyle.Bold)
        };

        _txtUsername = new TextBox
        {
            Left = 150,
            Top = 50,
            Width = 200,
            Font = new Font("Arial", 12),
            PlaceholderText = "Username"
        };

        _lblPassword = new Label
        {
            Text = "Password",
            Left = 50,
            Top = 100,
            Width = 100,
            Font = new Font("Arial", 12, FontStyle.Bold)
        };

        _txtPassword = new TextBox
        {
            Left = 150,
            Top = 100,
            Width = 200,
            Font = new Font("Arial", 12),
            PlaceholderText = "Password",
            UseSystemPasswordChar = true
        };

        _btnLogin = new Button
        {
            Text = "Login",
            Left = 150,
            Top = 150,
            Width = 100,
            Height = 40,
            Font = new Font("Arial", 12, FontStyle.Bold),
            BackColor = Color.Pink,
            ForeColor = Color.White
        };

        _btnLogin.Click += btnLogin_Click;

        Controls.Add(_txtUsername);
        Controls.Add(_txtPassword);
        Controls.Add(_btnLogin);
        Controls.Add(_lblUsername);
        Controls.Add(_lblPassword);
    }

    private void btnLogin_Click(object? sender, EventArgs e)
    {
        try
        {
            var username = _txtUsername!.Text;
            var password = _txtPassword!.Text;
            _utilizatorService.Login(username, password);

            var mainForm = new MainForm(_echipaService, _cursaService, _participantService, _participantCursaService);
            mainForm.Show();
        }
        catch (Exception ex)
        {
            MessageBox.Show("A aparut o eroare. Te rugam să incerci din nou.");
            Logger.Error("Eroare la login", ex);
        }
    }
}
