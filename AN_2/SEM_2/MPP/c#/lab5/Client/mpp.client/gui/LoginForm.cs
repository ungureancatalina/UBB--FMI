using Model.mpp.model;
using Services.mpp.services;

namespace Client.mpp.client.gui;

public partial class LoginForm : Form
{
    private TextBox? _txtUsername;
    private TextBox? _txtPassword;
    private Button? _btnLogin;
    private Label? _lblPassword;
    private Label? _lblUsername;
    private Label _lblError;

    private IServices Server;
    private Utilizator LoggedUser;

    public void SetServer(IServices server)
    {
        this.Server = server;
    }
        
    public LoginForm()
    {
        InitializeComponents();
    }

    private void BtnLogin_Click(object? sender, EventArgs e)
    {
        string username = _txtUsername.Text.Trim();
        string password = _txtPassword.Text.Trim();
        try
        {
            Utilizator utilizator = new Utilizator(username,password);
            MainForm mainForm = new MainForm();
            mainForm.SetServer(Server);
            mainForm.SetLoggedUser(utilizator);
            mainForm.Text = username;
            
            Server.Login(utilizator, mainForm);
            
            mainForm.Show();
            this.Hide();
        }
        catch (Exception ex)
        {
            ShowErrorMessage(ex.Message);
        }
    }

    private async void ShowErrorMessage(string message)
    {
        _lblError.Text = message;
        _lblError.Visible = true;
        await Task.Delay(3000);
        _lblError.Visible = false;
    }
    
}
