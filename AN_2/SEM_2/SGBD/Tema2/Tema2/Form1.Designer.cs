namespace Tema2
{
    partial class Form1
    {
        private System.ComponentModel.IContainer components = null;

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
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.dataGridView2 = new System.Windows.Forms.DataGridView();
            this.panel2 = new System.Windows.Forms.Panel();
            this.adaugareButton = new System.Windows.Forms.Button();
            this.stergereButton = new System.Windows.Forms.Button();
            this.actualizareButton = new System.Windows.Forms.Button();
            this.exitButton = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).BeginInit();
            this.SuspendLayout();
            // 
            // dataGridView1
            // 
            this.dataGridView1.Location = new System.Drawing.Point(9, 10);
            this.dataGridView1.Margin = new System.Windows.Forms.Padding(2);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.Size = new System.Drawing.Size(446, 233);
            this.dataGridView1.TabIndex = 0;
            this.dataGridView1.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView1_CellClick);
            // 
            // dataGridView2
            // 
            this.dataGridView2.Location = new System.Drawing.Point(459, 10);
            this.dataGridView2.Margin = new System.Windows.Forms.Padding(2);
            this.dataGridView2.Name = "dataGridView2";
            this.dataGridView2.Size = new System.Drawing.Size(446, 233);
            this.dataGridView2.TabIndex = 1;
            this.dataGridView2.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView2_CellClick);
            // 
            // panel2
            // 
            this.panel2.Location = new System.Drawing.Point(222, 292);
            this.panel2.Margin = new System.Windows.Forms.Padding(2);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(446, 77);
            this.panel2.TabIndex = 3;
            // 
            // adaugareButton
            // 
            this.adaugareButton.Location = new System.Drawing.Point(222, 389);
            this.adaugareButton.Margin = new System.Windows.Forms.Padding(2);
            this.adaugareButton.Name = "adaugareButton";
            this.adaugareButton.Size = new System.Drawing.Size(90, 32);
            this.adaugareButton.TabIndex = 4;
            this.adaugareButton.Text = "Adăugare";
            this.adaugareButton.UseVisualStyleBackColor = true;
            this.adaugareButton.Click += new System.EventHandler(this.adaugareButton_Click);
            // 
            // stergereButton
            // 
            this.stergereButton.Location = new System.Drawing.Point(408, 389);
            this.stergereButton.Margin = new System.Windows.Forms.Padding(2);
            this.stergereButton.Name = "stergereButton";
            this.stergereButton.Size = new System.Drawing.Size(90, 32);
            this.stergereButton.TabIndex = 5;
            this.stergereButton.Text = "Ștergere";
            this.stergereButton.UseVisualStyleBackColor = true;
            this.stergereButton.Click += new System.EventHandler(this.stergereButton_Click);
            // 
            // actualizareButton
            // 
            this.actualizareButton.Location = new System.Drawing.Point(578, 389);
            this.actualizareButton.Margin = new System.Windows.Forms.Padding(2);
            this.actualizareButton.Name = "actualizareButton";
            this.actualizareButton.Size = new System.Drawing.Size(90, 32);
            this.actualizareButton.TabIndex = 6;
            this.actualizareButton.Text = "Actualizare";
            this.actualizareButton.UseVisualStyleBackColor = true;
            this.actualizareButton.Click += new System.EventHandler(this.actualizareButton_Click);
            // 
            // exitButton
            // 
            this.exitButton.Location = new System.Drawing.Point(781, 404);
            this.exitButton.Margin = new System.Windows.Forms.Padding(2);
            this.exitButton.Name = "exitButton";
            this.exitButton.Size = new System.Drawing.Size(124, 44);
            this.exitButton.TabIndex = 7;
            this.exitButton.Text = "Exit";
            this.exitButton.UseVisualStyleBackColor = true;
            this.exitButton.Click += new System.EventHandler(this.exitButton_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(914, 455);
            this.Controls.Add(this.actualizareButton);
            this.Controls.Add(this.stergereButton);
            this.Controls.Add(this.adaugareButton);
            this.Controls.Add(this.exitButton);
            this.Controls.Add(this.panel2);
            this.Controls.Add(this.dataGridView2);
            this.Controls.Add(this.dataGridView1);
            this.Margin = new System.Windows.Forms.Padding(2);
            this.Name = "Form1";
            this.Text = "Master-Detail Generic";
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).EndInit();
            this.ResumeLayout(false);
        }

        #endregion

        private System.Windows.Forms.DataGridView dataGridView1;
        private System.Windows.Forms.DataGridView dataGridView2;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.Button adaugareButton;
        private System.Windows.Forms.Button stergereButton;
        private System.Windows.Forms.Button actualizareButton;
        private System.Windows.Forms.Button exitButton;
    }
}