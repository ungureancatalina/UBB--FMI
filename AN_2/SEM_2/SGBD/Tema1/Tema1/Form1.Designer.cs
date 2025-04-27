namespace Tema1
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

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
            this.Conectare = new System.Windows.Forms.Button();
            this.gridOras = new System.Windows.Forms.DataGridView();
            this.gridTara = new System.Windows.Forms.DataGridView();
            this.Adaugare = new System.Windows.Forms.Button();
            this.Stergere = new System.Windows.Forms.Button();
            this.Actualizare = new System.Windows.Forms.Button();
            this.loras = new System.Windows.Forms.Label();
            this.lnrloc = new System.Windows.Forms.Label();
            this.lsofer = new System.Windows.Forms.Label();
            this.nrloc = new System.Windows.Forms.TextBox();
            this.sofer = new System.Windows.Forms.TextBox();
            this.oras = new System.Windows.Forms.TextBox();
            ((System.ComponentModel.ISupportInitialize)(this.gridOras)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.gridTara)).BeginInit();
            this.SuspendLayout();
            // 
            // Conectare
            // 
            this.Conectare.BackColor = System.Drawing.SystemColors.ActiveCaption;
            this.Conectare.Location = new System.Drawing.Point(712, 12);
            this.Conectare.Name = "Conectare";
            this.Conectare.Size = new System.Drawing.Size(463, 87);
            this.Conectare.TabIndex = 0;
            this.Conectare.Text = "Conectare";
            this.Conectare.UseVisualStyleBackColor = false;
            this.Conectare.Click += new System.EventHandler(this.Conectare_Click);
            // 
            // gridOras
            // 
            this.gridOras.BackgroundColor = System.Drawing.SystemColors.GradientInactiveCaption;
            this.gridOras.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.gridOras.GridColor = System.Drawing.SystemColors.GradientInactiveCaption;
            this.gridOras.Location = new System.Drawing.Point(995, 115);
            this.gridOras.Name = "gridOras";
            this.gridOras.RowHeadersWidth = 82;
            this.gridOras.RowTemplate.Height = 33;
            this.gridOras.Size = new System.Drawing.Size(905, 518);
            this.gridOras.TabIndex = 2;
            // 
            // gridTara
            // 
            this.gridTara.BackgroundColor = System.Drawing.SystemColors.GradientInactiveCaption;
            this.gridTara.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.gridTara.GridColor = System.Drawing.SystemColors.GradientInactiveCaption;
            this.gridTara.Location = new System.Drawing.Point(21, 115);
            this.gridTara.Name = "gridTara";
            this.gridTara.RowHeadersWidth = 82;
            this.gridTara.RowTemplate.Height = 33;
            this.gridTara.Size = new System.Drawing.Size(913, 518);
            this.gridTara.TabIndex = 3;
            this.gridTara.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.gridTara_CellContentClick);
            // 
            // Adaugare
            // 
            this.Adaugare.BackColor = System.Drawing.SystemColors.ActiveCaption;
            this.Adaugare.Location = new System.Drawing.Point(102, 686);
            this.Adaugare.Name = "Adaugare";
            this.Adaugare.Size = new System.Drawing.Size(463, 108);
            this.Adaugare.TabIndex = 4;
            this.Adaugare.Text = "Adaugare";
            this.Adaugare.UseVisualStyleBackColor = false;
            this.Adaugare.Click += new System.EventHandler(this.Adaugare_Click);
            // 
            // Stergere
            // 
            this.Stergere.BackColor = System.Drawing.SystemColors.ActiveCaption;
            this.Stergere.Location = new System.Drawing.Point(712, 686);
            this.Stergere.Name = "Stergere";
            this.Stergere.Size = new System.Drawing.Size(463, 108);
            this.Stergere.TabIndex = 5;
            this.Stergere.Text = "Stergere";
            this.Stergere.UseVisualStyleBackColor = false;
            this.Stergere.Click += new System.EventHandler(this.Stergere_Click);
            // 
            // Actualizare
            // 
            this.Actualizare.BackColor = System.Drawing.SystemColors.ActiveCaption;
            this.Actualizare.Location = new System.Drawing.Point(1307, 686);
            this.Actualizare.Name = "Actualizare";
            this.Actualizare.Size = new System.Drawing.Size(463, 108);
            this.Actualizare.TabIndex = 8;
            this.Actualizare.Text = "Actualizare";
            this.Actualizare.UseVisualStyleBackColor = false;
            this.Actualizare.Click += new System.EventHandler(this.Actualizare_Click);
            // 
            // loras
            // 
            this.loras.AutoSize = true;
            this.loras.Location = new System.Drawing.Point(97, 845);
            this.loras.Name = "loras";
            this.loras.Size = new System.Drawing.Size(126, 25);
            this.loras.TabIndex = 9;
            this.loras.Text = "Nume Oras:";
            // 
            // lnrloc
            // 
            this.lnrloc.AutoSize = true;
            this.lnrloc.Location = new System.Drawing.Point(1302, 845);
            this.lnrloc.Name = "lnrloc";
            this.lnrloc.Size = new System.Drawing.Size(121, 25);
            this.lnrloc.TabIndex = 11;
            this.lnrloc.Text = "Nr locuitori:";
            // 
            // lsofer
            // 
            this.lsofer.AutoSize = true;
            this.lsofer.Location = new System.Drawing.Point(707, 845);
            this.lsofer.Name = "lsofer";
            this.lsofer.Size = new System.Drawing.Size(131, 25);
            this.lsofer.TabIndex = 12;
            this.lsofer.Text = "Nume Sofer:";
            // 
            // nrloc
            // 
            this.nrloc.BackColor = System.Drawing.SystemColors.HotTrack;
            this.nrloc.ForeColor = System.Drawing.SystemColors.Window;
            this.nrloc.Location = new System.Drawing.Point(1451, 845);
            this.nrloc.Name = "nrloc";
            this.nrloc.Size = new System.Drawing.Size(319, 31);
            this.nrloc.TabIndex = 13;
            // 
            // sofer
            // 
            this.sofer.BackColor = System.Drawing.SystemColors.HotTrack;
            this.sofer.ForeColor = System.Drawing.SystemColors.Window;
            this.sofer.Location = new System.Drawing.Point(854, 842);
            this.sofer.Name = "sofer";
            this.sofer.Size = new System.Drawing.Size(321, 31);
            this.sofer.TabIndex = 14;
            // 
            // oras
            // 
            this.oras.BackColor = System.Drawing.SystemColors.HotTrack;
            this.oras.ForeColor = System.Drawing.SystemColors.Window;
            this.oras.Location = new System.Drawing.Point(239, 842);
            this.oras.Name = "oras";
            this.oras.Size = new System.Drawing.Size(326, 31);
            this.oras.TabIndex = 15;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(12F, 25F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.GhostWhite;
            this.ClientSize = new System.Drawing.Size(1958, 945);
            this.Controls.Add(this.oras);
            this.Controls.Add(this.sofer);
            this.Controls.Add(this.nrloc);
            this.Controls.Add(this.lsofer);
            this.Controls.Add(this.lnrloc);
            this.Controls.Add(this.loras);
            this.Controls.Add(this.Actualizare);
            this.Controls.Add(this.Stergere);
            this.Controls.Add(this.Adaugare);
            this.Controls.Add(this.gridTara);
            this.Controls.Add(this.gridOras);
            this.Controls.Add(this.Conectare);
            this.Name = "Form1";
            this.Text = "Form1";
            ((System.ComponentModel.ISupportInitialize)(this.gridOras)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.gridTara)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button Conectare;
        private System.Windows.Forms.DataGridView gridOras;
        private System.Windows.Forms.DataGridView gridTara;
        private System.Windows.Forms.Button Adaugare;
        private System.Windows.Forms.Button Stergere;
        private System.Windows.Forms.Button Actualizare;
        private System.Windows.Forms.Label loras;
        private System.Windows.Forms.Label lnrloc;
        private System.Windows.Forms.Label lsofer;
        private System.Windows.Forms.TextBox nrloc;
        private System.Windows.Forms.TextBox sofer;
        private System.Windows.Forms.TextBox oras;
    }
}

