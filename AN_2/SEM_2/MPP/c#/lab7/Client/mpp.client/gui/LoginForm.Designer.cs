using System.ComponentModel;

namespace Client.mpp.client.gui;

partial class LoginForm
{
    /// <summary>
    /// Required designer variable.
    /// </summary>
    private IContainer components = null;

    /// <summary>
    /// Clean up any resources being used.
    /// </summary>
    /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
    protected override void Dispose(bool disposing)
    {
        if (disposing && (components != null))
        {
            components.Dispose();
        }

        base.Dispose(disposing);
    }

    #region Windows Form Designer generated code

    /// <summary>
    /// Required method for Designer support - do not modify
    /// the contents of this method with the code editor.
    /// </summary>

    private void InitializeComponents()
    {
        this.components = new System.ComponentModel.Container();
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(800, 450);
        this.Text = "LoginForm";
        
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
        
        _lblError = new Label
        {
            Left = 50,
            Top = 220,
            Width = 300,
            ForeColor = Color.Red,
            Font = new Font("Arial", 10, FontStyle.Italic),
            Visible = false
        };

        _btnLogin.Click += BtnLogin_Click;
        _lblError = new Label
        {
            Left = 50,
            Top = 220,
            Width = 300,
            ForeColor = Color.Red,
            Font = new Font("Arial", 10, FontStyle.Italic),
            Visible = false
        };


        Controls.Add(_txtUsername);
        Controls.Add(_txtPassword);
        Controls.Add(_btnLogin);
        Controls.Add(_lblUsername);
        Controls.Add(_lblPassword);
        Controls.Add(_lblError);
    }
    #endregion
}