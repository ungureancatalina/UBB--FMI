using System.ComponentModel;

namespace Client.mpp.client.gui;

partial class MainForm
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
    private void InitializeComponent()
    {
        this.components = new System.ComponentModel.Container();
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(800, 450);
        this.Text = "MainForm";
                this.StartPosition = FormStartPosition.CenterScreen;
        this.ClientSize = new Size(1100, 400);
        this.BackColor = Color.Pink;

        
        _lblError = new Label
        {
            Left = 500,
            Top = 340,
            Width = 500,
            ForeColor = Color.Red,
            Font = new Font("Arial", 10, FontStyle.Italic),
            Visible = false
        };

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
        _btnLogout.Click += btnLogout_Click;

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

    #endregion
}