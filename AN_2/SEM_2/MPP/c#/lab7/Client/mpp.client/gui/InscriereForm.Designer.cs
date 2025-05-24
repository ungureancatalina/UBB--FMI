using System.ComponentModel;

namespace Client.mpp.client.gui;

partial class InscriereForm
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
        this.Text = "InscriereForm";
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

            _dgvParticipantiCurse = new DataGridView
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
            
            Controls.Add(_dgvParticipantiCurse);
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

    #endregion
}